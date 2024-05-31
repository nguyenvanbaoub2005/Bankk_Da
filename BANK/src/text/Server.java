package text;

import java.io.*;
import java.math.BigDecimal;
import java.net.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import view.viewTrangChu;

public class Server {

	private ServerSocket serverSocket;

	private static String loggedInPhoneNumber;

	public static String getLoggedInPhoneNumber() {
		return loggedInPhoneNumber;
	}

	public static void setLoggedInPhoneNumber(String loggedInPhoneNumber) {
		Server.loggedInPhoneNumber = loggedInPhoneNumber;
	}

	public Server(int port) throws IOException {
		serverSocket = new ServerSocket(port);
	}

	public void start() throws IOException {
		System.out.println("Server is listening on port " + serverSocket.getLocalPort());
		while (true) {
			Socket clientSocket = serverSocket.accept();
			new ClientHandler(clientSocket).start();
		}
	}

	public static void main(String[] args) {
		try {
			Server server = new Server(1234);
			server.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	class ClientHandler extends Thread {
		private Socket clientSocket;
		private BufferedReader in;
		private PrintWriter out;
		private Connection connection;

		// get tk đăngnhập

		public ClientHandler(Socket socket) {
			this.clientSocket = socket;
			try {
				this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ACCOUNT", "baobeo",
						"vanbaoub123");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		public void run() {
			try {
				in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				out = new PrintWriter(clientSocket.getOutputStream(), true);

				String request;
				while ((request = in.readLine()) != null) {
					String[] parts = request.split(" ");
					String command = parts[0];
					switch (command) {
					case "LOGIN":
						handleLogin(parts);
						break;
					case "REGISTER":
						handleRegister(parts);
						break;
					case "DEPOSIT":
						handleDeposit(parts);
						break;
					case "TRANSFER":
						handleTransfer(parts);
						break;
					case "BALANCE":
						handleGetBalance(parts);
						break;
//					case "HISTORY":
//						handleGetHistory(parts);
//						break;
					case "RECHARGE":
						handlePhoneRecharge(parts);
						break;
					case "ACCOUNT_INFO":
						handleAccountInfo(parts);
						break;
					default:
						out.println("UNKNOWN COMMAND");
						break;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					clientSocket.close();
					if (connection != null)
						connection.close();
				} catch (IOException | SQLException e) {
					e.printStackTrace();
				}
			}
		}

		
		private void handleAccountInfo(String[] parts) {
		    if (parts.length < 2) {
		        out.println("ACCOUNT_INFO_FAILED: Missing parameters");
		        return;
		    }
		    String phoneNumber = parts[1];
		    try {
		        PreparedStatement ps = connection.prepareStatement(
		            "SELECT Username, Gmail FROM DANGKY1 WHERE PhoneNumber = ?");
		        ps.setString(1, phoneNumber);
		        ResultSet rs = ps.executeQuery();

		        if (rs.next()) {
		            String username = rs.getString("Username");
		            String email = rs.getString("Gmail");
		            out.println("ACCOUNT_INFO_SUCCESS " + username + " " + phoneNumber + " " + email);
		        } else {
		            out.println("ACCOUNT_INFO_FAILED: User not found");
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		        out.println("ERROR: " + e.getMessage());
		    }
		}


		private void handleLogin(String[] parts) {
			if (parts.length < 3) {
				out.println("LOGIN_FAILED: Missing parameters");
				return;
			}

			String phoneLogin = parts[1];
			String password = parts[2];
			try {
				PreparedStatement ps = connection
						.prepareStatement("SELECT * FROM DANGKY1 WHERE PhoneNumber = ? AND PassWord = ?");
				ps.setString(1, phoneLogin);
				ps.setString(2, password);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					Server.setLoggedInPhoneNumber(phoneLogin);
					loggedInPhoneNumber = phoneLogin;
					// Lưu trữ số điện thoại của người dùng đã đăng hập
					// phản hồi cho client
					out.println("LOGIN SUCCESS" + phoneLogin);
				} else {
					out.println("LOGIN FAILURE");
					SwingUtilities.invokeLater(() -> {
						JOptionPane.showMessageDialog(null, "LOGIN_FAILED: Incorrect password");
					});
				return;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				out.println("ERROR login");
			}
		}

		private void handleRegister(String[] parts) {
			if (parts.length < 6) {
				out.println("REGISTER_FAILED: Missing parameters");
				return;
			}

			String username = parts[1];
			String gmail = parts[2];
			String phoneNumber = parts[3];
			String nationID = parts[4];
			String password = parts[5];

			try {
				// Insert user data into DANGKY1 table
				String insertUserSQL = "INSERT INTO DANGKY1 (Username, Gmail, PhoneNumber, NationID, PassWord) VALUES (?, ?, ?, ?, ?)";
				PreparedStatement ps = connection.prepareStatement(insertUserSQL);
				ps.setString(1, username);
				ps.setString(2, gmail);
				ps.setString(3, phoneNumber);
				ps.setString(4, nationID);
				ps.setString(5, password);
				int row0 = ps.executeUpdate();

				// Insert initial balance into Balance2 table
				String insertBalanceSQL = "INSERT INTO Balance2 (phoneNumber, so_du) VALUES (?, ?)";
				PreparedStatement psBalance = connection.prepareStatement(insertBalanceSQL);
				psBalance.setString(1, phoneNumber);
				psBalance.setDouble(2, 0.0); // Initial balance is 0.0
				int row = psBalance.executeUpdate();

				out.println("REGISTER_SUCCESS");

			} catch (SQLException e) {
				e.printStackTrace();
				out.println("REGISTER_FAILED: " + e.getMessage());
			}
		}

		private void handleDeposit(String[] parts) {
			if (parts.length < 3) {
				out.println("DEPOSIT_FAILED: Missing parameters");
				return;
			}

			double amount;
			try {
				amount = Double.parseDouble(parts[1]);
			} catch (NumberFormatException e) {
				out.println("DEPOSIT_FAILED: Invalid amount");
				return;
			}

			String password = parts[2];

			try {
				// Verify password
				PreparedStatement psVerify = connection
						.prepareStatement("SELECT PassWord FROM DANGKY1 WHERE PhoneNumber = ?");
				psVerify.setString(1, loggedInPhoneNumber);
				ResultSet rs = psVerify.executeQuery();

				if (rs.next()) {
					String storedPassword = rs.getString("PassWord");
					if (!storedPassword.equals(password)) {
						out.println("DEPOSIT_FAILED: Incorrect password");
						SwingUtilities.invokeLater(() -> {
							JOptionPane.showMessageDialog(null, "DEPOSIT_FAILED: Incorrect password");
						});
						return;
					}
				} else {
					out.println("DEPOSIT_FAILED: User not found");
					return;
				}

				// Perform the deposit
				PreparedStatement ps = connection
						.prepareStatement("UPDATE Balance2 SET so_du = so_du + ? WHERE phoneNumber = ?");
				ps.setDouble(1, amount);
				ps.setString(2, loggedInPhoneNumber);
				int result1 = ps.executeUpdate();
				// Insert into HISTORY_ table
				String description = "Deposit";
				String referenceCode = generateRandomReferenceCode();
				LocalDateTime currentDateTime = LocalDateTime.now();
				Timestamp historyDateTime = Timestamp.valueOf(currentDateTime);

				PreparedStatement psHistory = connection.prepareStatement(
						"INSERT INTO HISTORY_ (Id , AccountNumber, Amount, Description, History_datetime, reference_code) VALUES (DEFAULT, ?, ?, ?, ?, ?)");
				psHistory.setString(1, loggedInPhoneNumber);
				psHistory.setDouble(2, amount);
				psHistory.setString(3, description);
				psHistory.setTimestamp(4, historyDateTime);
				psHistory.setString(5, referenceCode);
				int result2 = psHistory.executeUpdate();
				if (result1 > 0 && result2 > 0) {
					out.println("DEPOSIT SUCCESS");

					handleGetBalance(new String[] { "BALANCE" }); // Send updated balance to client
				} else {
					out.println("DEPOSIT FAILURE");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				out.println("ERROR deposit");
			}
		}

		private void handleTransfer(String[] parts) {
			if (parts.length < 4) {
				out.println("TRANSFER_FAILED: Missing parameters");
				return;
			}
			String toPhone = parts[1];
			double amount = Double.parseDouble(parts[2]);
			String password = parts[3];
			try {
				PreparedStatement psVerify = connection
						.prepareStatement("SELECT PassWord FROM DANGKY1 WHERE PhoneNumber = ?");
				psVerify.setString(1, loggedInPhoneNumber);
				ResultSet rs = psVerify.executeQuery();

				if (rs.next()) {
					String storedPassword = rs.getString("PassWord");
					if (!storedPassword.equals(password)) {
						out.println("TRANSFER_FAILED: Incorrect password");
						SwingUtilities.invokeLater(() -> {
							JOptionPane.showMessageDialog(null, "TRANSFER_FAILED: Incorrect password");
						});
						return;
					}
				} else {
					out.println("TRANSFER_FAILED: User not found");
					return;
				}
				connection.setAutoCommit(false);

				// Deduct amount from sender's balance
				PreparedStatement ps1 = connection
						.prepareStatement("UPDATE Balance2 SET so_du = so_du - ? WHERE phoneNumber = ?");
				ps1.setDouble(1, amount);
				ps1.setString(2, loggedInPhoneNumber);
				int result1 = ps1.executeUpdate();

				// Add amount to receiver's balance
				PreparedStatement ps2 = connection
						.prepareStatement("UPDATE Balance2 SET so_du = so_du + ? WHERE phoneNumber = ?");
				ps2.setDouble(1, amount);
				ps2.setString(2, toPhone);
				int result2 = ps2.executeUpdate();

				// Insert into HISTORY_ table for sender
				String descriptionSender = "Transfer to " + toPhone;
				String referenceCode = generateRandomReferenceCode();
				LocalDateTime currentDateTime = LocalDateTime.now();
				Timestamp historyDateTime = Timestamp.valueOf(currentDateTime);

				PreparedStatement psHistorySender = connection.prepareStatement(
						"INSERT INTO HISTORY_ (Id , AccountNumber, Amount, Description, History_datetime, reference_code) VALUES (DEFAULT, ?, ?, ?, ?, ?)");
				psHistorySender.setString(1, loggedInPhoneNumber);
				psHistorySender.setDouble(2, amount);
				psHistorySender.setString(3, descriptionSender);
				psHistorySender.setTimestamp(4, historyDateTime);
				psHistorySender.setString(5, referenceCode);
				psHistorySender.executeUpdate();

				// Insert into HISTORY_ table for receiver
				String descriptionReceiver = "Received transfer from " + loggedInPhoneNumber;

				PreparedStatement psHistoryReceiver = connection.prepareStatement(
						"INSERT INTO HISTORY_ (Id , AccountNumber, Amount, Description, History_datetime, reference_code) VALUES (DEFAULT, ?, ?, ?, ?, ?)");
				psHistoryReceiver.setString(1, toPhone);
				psHistoryReceiver.setDouble(2, amount);
				psHistoryReceiver.setString(3, descriptionReceiver);
				psHistoryReceiver.setTimestamp(4, historyDateTime);
				psHistoryReceiver.setString(5, referenceCode);
				int result3 = psHistoryReceiver.executeUpdate();
				if (result1 > 0 && result2 > 0 && result3>0) {

					connection.commit();
					out.println("TRANSFER SUCCESS");
					handleGetBalance(new String[] { "BALANCE" });
				} else {
					connection.rollback();
					out.println("TRANSFER FAILURE");
				}
			} catch (SQLException e) {
				try {
					connection.rollback();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
				e.printStackTrace();
				out.println("ERROR");
			} finally {
				try {
					connection.setAutoCommit(true);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		private void handlePhoneRecharge(String[] parts) {

			if (parts.length < 4) {
				out.println("RECHARGE_FAILED: Missing parameters");
				return;
			}
			String toPhone = parts[1];
			double amount;
			try {
				amount = Double.parseDouble(parts[2]);
			} catch (NumberFormatException e) {
				out.println("RECHARGE_FAILED: Invalid amount format");
				return;
			}
			String password = parts[3];
			try {
				PreparedStatement psVerify = connection
						.prepareStatement("SELECT PassWord FROM DANGKY1 WHERE PhoneNumber = ?");
				psVerify.setString(1, loggedInPhoneNumber);
				ResultSet rs = psVerify.executeQuery();

				if (rs.next()) {
					String storedPassword = rs.getString("PassWord");
					if (!storedPassword.equals(password)) {
						out.println("RECHARGE_FAILED: Incorrect password");
						return;
					}
				} else {
					out.println("RECHARGE_FAILED: User not found");
					return;
				}

				connection.setAutoCommit(false);

				// Deduct amount from sender's balance
				PreparedStatement ps1 = connection
						.prepareStatement("UPDATE Balance2 SET so_du = so_du - ? WHERE phoneNumber = ?");
				ps1.setDouble(1, amount);
				ps1.setString(2, loggedInPhoneNumber);
				int result1 = ps1.executeUpdate();

				// Insert into HISTORY_ table for sender
				String descriptionSender = "Phone recharge to " + toPhone;
				String referenceCode = generateRandomReferenceCode();
				LocalDateTime currentDateTime = LocalDateTime.now();
				Timestamp historyDateTime = Timestamp.valueOf(currentDateTime);

				PreparedStatement psHistorySender = connection.prepareStatement(
						"INSERT INTO HISTORY_ (AccountNumber, Amount, Description, History_datetime, reference_code) VALUES (?, ?, ?, ?, ?)");
				psHistorySender.setString(1, loggedInPhoneNumber);
				psHistorySender.setDouble(2, amount);
				psHistorySender.setString(3, descriptionSender);
				psHistorySender.setTimestamp(4, historyDateTime);
				psHistorySender.setString(5, referenceCode);
				int result2 = psHistorySender.executeUpdate();

				if (result1 > 0 && result2 > 0) {
					connection.commit();
					out.println("RECHARGE SUCCESS");
					handleGetBalance(new String[] { "BALANCE" });
				} else {
					connection.rollback();
					out.println("RECHARGE FAILURE");
				}
			} catch (SQLException e) {
				try {
					connection.rollback();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
				e.printStackTrace();
				out.println("ERROR");
			} finally {
				try {
					connection.setAutoCommit(true);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		private void handleGetBalance(String[] parts) {
			if (loggedInPhoneNumber == null) {
				out.println("NO_USER_LOGGED_IN");
				return;
			}

			try {
				PreparedStatement ps = connection.prepareStatement("SELECT so_du FROM Balance2 WHERE phoneNumber = ?");
				ps.setString(1, loggedInPhoneNumber);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					double balance = rs.getDouble("so_du");
					out.println("CURRENT_BALANCE " + balance);// Ensure correct format
				}
				else {
					out.println("USER_NOT_FOUND");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				out.println("ERROR_FETCHING_BALANCE");
			}
		}


		public String generateRandomReferenceCode() {
			// Generate a random alphanumeric reference code
			String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
			int length = 8;
			StringBuilder randomCode = new StringBuilder();

			Random random = new Random();
			for (int i = 0; i < length; i++) {
				int randomIndex = random.nextInt(characters.length());
				randomCode.append(characters.charAt(randomIndex));
			}

			return randomCode.toString();
		}

//	

	}
}
