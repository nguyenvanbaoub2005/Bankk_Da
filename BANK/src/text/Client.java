package text;

import java.io.*;
import java.net.*;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import view.AccountInfoView;
import view.ViewSignUp;
import view.View_phụ_chuyenkhoang;
import view.View_phụ_naptien;
import view.View_phụ_nạptiendt;
import view.viewLogIn;
import view.viewTrangChu;

public class Client {
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	private viewLogIn viewLogIn;
	private ViewSignUp viewsignUp;
	private View_phụ_naptien naptien;
	private viewTrangChu home;
	private BalanceUpdateListener balanceUpdateListener;
	private double currentBalance;
	private View_phụ_chuyenkhoang chuyenkhoang;
	private View_phụ_nạptiendt napdt;
// goị biến qua từ lớp swing qua phải để luòng SwingUtilities.invokeLater(() -> { 
	//
	public String getpPhoneloged() {
		SwingUtilities.invokeLater(() -> {
			String phoneNumber = viewLogIn.getPhoneNumber();
		});
		return viewLogIn.getPhoneNumber();
	}

	public Client(String address, int port) throws IOException {
		socket = new Socket(address, port);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);
	}

	public void setHome(viewTrangChu home) {
		this.home = home;
	}

	public void setViewLogIn(viewLogIn viewLogIn) {
		this.viewLogIn = viewLogIn;
	}

	public void setViewsignUp(ViewSignUp viewsignUp) {
		this.viewsignUp = viewsignUp;
	}

	public void setNaptien(View_phụ_naptien naptien) {
		this.naptien = naptien;
	}

	public void setChuyenkhoang(View_phụ_chuyenkhoang chuyenkhoang) {
		this.chuyenkhoang = chuyenkhoang;
	}

	public void setBalanceUpdateListener(BalanceUpdateListener listener) {
		this.balanceUpdateListener = listener;
	}

	public interface BalanceUpdateListener {
		void onBalanceUpdate(double newBalance);
	}

	public void login(String phone, String password) {
		new Thread(() -> {
			out.println("LOGIN " + phone + " " + password);
			try {
				String response = in.readLine();
				System.out.println(response);
				if (response.startsWith("LOGIN SUCCESS")) {
					JOptionPane.showMessageDialog(null, "LOGIN SUCCESS");
//					viewTrangChu home = new viewTrangChu();
//					home.setVisible(true);
//

				} else {
					showErrorDialog("Hãy kiểm tra lại số tài khoản và mật khẩu ");
					return;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}).start();
	}

	public void requestAccountInfo(String phoneNumber) {
		new Thread(() -> {
			try {
				out.println("ACCOUNT_INFO " + phoneNumber);
				out.flush(); // Đảm bảo rằng lệnh được gửi đi ngay lập tức

				String response = in.readLine();
				System.out.println("Received response: " + response);

				if (response.startsWith("ACCOUNT_INFO_SUCCESS")) {
					String[] parts = response.split(" ", 4);
					String username = parts[1];
					String phone = parts[2];
					String email = parts[3];
					SwingUtilities.invokeLater(() -> {
						AccountInfoView accountInfoView = new AccountInfoView(username, phone, email);
						accountInfoView.setVisible(true);
					});
				} else {
					showErrorDialog("An error occurred while retrieving account info: " + response);
				}
			} catch (IOException e) {
				e.printStackTrace();
				showErrorDialog("An error occurred while retrieving account info: " + e.getMessage());
			}
		}).start();
	}

	public void register(String username, String gmail, String phoneNumber, String nationID, String password) {
		new Thread(() -> {
			out.println("REGISTER " + username + " " + gmail + " " + phoneNumber + " " + nationID + " " + password);
			try {
				String response = in.readLine();
				System.out.println(response);
			} catch (IOException e) {
				e.printStackTrace();
				showErrorDialog("Lỗi đăng ký " + e.getMessage());
			}
		}).start();
	}

	public void fetchCurrentBalance() {
		new Thread(() -> {
			out.println("BALANCE");
			try {
				String response = in.readLine();
				System.out.println("Received response: " + response); // Log the response for debugging
				double currentBalance = extractBalanceFromResponse(response);
				if (balanceUpdateListener != null) {
					balanceUpdateListener.onBalanceUpdate(currentBalance);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}).start();
	}

	private double extractBalanceFromResponse(String response) {
		if (response.startsWith("CURRENT_BALANCE ")) {
			String[] parts = response.split(" ");
			if (parts.length == 2) {
				try {
					double balance = Double.parseDouble(parts[1]);
					return balance;
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}
		}
		return 0.0;
	}

	public void deposit(double amount, String password) {
		new Thread(() -> {
			out.println("DEPOSIT " + amount + " " + password);
			try {
				String response;
				while ((response = in.readLine()) != null) {
					System.out.println(response); // Log the response
					if (response.equals("DEPOSIT SUCCESS")) {

						fetchCurrentBalance(); // Gọi hàm để cập nhật số dư hiện tại
						JOptionPane.showMessageDialog(null, "Deposit success");
					} else if (response.startsWith("CURRENT_BALANCE")) {
						double currentBalance = extractBalanceFromResponse(response);
						if (balanceUpdateListener != null) {
							balanceUpdateListener.onBalanceUpdate(currentBalance);
						}
						break; // Exit the loop after getting balance
					} else {
						System.out.println("Deposit failed: " + response); // Hiển thị thông báo lỗi nếu có
						JOptionPane.showMessageDialog(null, "Vui lòng kiểm tra lại mật khẩu");
						if (naptien != null) {
							SwingUtilities.invokeLater(() -> naptien.dispose()); // Close the transfer frame on
																					// failure
						}
						return;
					}
				}
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Vui lòng kiểm tra lại!");
			}
		}).start();
	}

	public void transfer(String toPhone, double amount, String password) {
		try {
			if (getpPhoneloged().equals(toPhone)) {
				SwingUtilities.invokeLater(() -> {
					JOptionPane.showMessageDialog(null, "Bạn không thể thực hiện chuyển khoản vào chính tài khoản gốc!",
							"Error", JOptionPane.ERROR_MESSAGE);
				});
				return;
			} else if (toPhone.isEmpty() || password.isEmpty()) {
				SwingUtilities.invokeLater(() -> {
					JOptionPane.showMessageDialog(null, "Bạn không được để trống!", "Error", JOptionPane.ERROR_MESSAGE);
				});
				return;
			} else if (amount <= 0) {
				SwingUtilities.invokeLater(() -> {
					JOptionPane.showMessageDialog(null, "Số tiền không hợp lệ!", "Error", JOptionPane.ERROR_MESSAGE);
				});
				return;
			}
		}
		// cach không hoạt động
		catch (Exception e2) {
			SwingUtilities.invokeLater(() -> {
				JOptionPane.showMessageDialog(null, "Số tiền không thể để trống!", "Error", JOptionPane.ERROR_MESSAGE);
			});
			return;
		}

		new Thread(() -> {
			out.println("TRANSFER " + toPhone + " " + amount + " " + password);
			try {
				String response;
				while ((response = in.readLine()) != null) {
					System.out.println(response);
					if (response.startsWith("TRANSFER SUCCESS")) {
						fetchCurrentBalance(); // Gọi hàm lấy số dư hiện tại
						SwingUtilities.invokeLater(() -> {
							JOptionPane.showMessageDialog(null, "Transfer success");
						});
					} else if (response.startsWith("CURRENT_BALANCE")) {
						double currentBalance = extractBalanceFromResponse(response);
						if (balanceUpdateListener != null) {
							balanceUpdateListener.onBalanceUpdate(currentBalance);
						}
						break; // Thoát vòng lặp sau khi nhận được số dư
					} else {
						System.out.println("TRANSFER failed: " + response); // Hiển thị thông báo lỗi nếu có
						SwingUtilities.invokeLater(() -> {
							JOptionPane.showMessageDialog(null, "Số tài khoản không tồn tại");
						});
						if (chuyenkhoang != null) {
							SwingUtilities.invokeLater(() -> chuyenkhoang.dispose()); // Đóng cửa sổ chuyển khoản
																						// khi thất bại
						}
						return;
					}
				}
			} catch (IOException e) {
				SwingUtilities.invokeLater(() -> {
					JOptionPane.showMessageDialog(null, "Lỗi ở Thread", "Error", JOptionPane.ERROR_MESSAGE);
				});
				return;
			}
		}).start();

	}

	public void phonecharge(String toPhone, double amount, String password) {
		new Thread(() -> {
			out.println("RECHARGE " + toPhone + " " + amount + " " + password);
			try {
				String response;
				while ((response = in.readLine()) != null) {
					if (response.startsWith("RECHARGE SUCCESS")) {
						System.out.println("Received response: " + response); // Debugging
						JOptionPane.showMessageDialog(null, "RECHARGE SUCCESS");
						fetchCurrentBalance(); // Gọi hàm để cập nhật số dư hiện tại
						break;
					} else {
						System.out.println("RECHARGE failed: " + response); // Hiển thị thông báo lỗi nếu có
						JOptionPane.showMessageDialog(null, response);
						return;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}).start();
	}

	public String receiveResponse() throws IOException {
		return in.readLine();
	}

	private void showErrorDialog(String message) {
		SwingUtilities
				.invokeLater(() -> JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE));
	}

	public static void main(String[] args) {
		try {
			Client client = new Client("localhost", 1234);

			viewLogIn viewLogIn = new viewLogIn(client);
			client.setViewLogIn(viewLogIn);

//			ViewSignUp viewSignUp = new ViewSignUp(client);
//			client.setViewsignUp(viewSignUp);
//
			viewTrangChu home = new viewTrangChu(client);
			client.setHome(home);
//
//			View_phụ_naptien naptien = new View_phụ_naptien(client);
//			client.setNaptien(naptien);
//
//			View_phụ_chuyenkhoang chuyenkhoang = new View_phụ_chuyenkhoang(client);
//			client.setChuyenkhoang(chuyenkhoang);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
