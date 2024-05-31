package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import text.Client;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.security.auth.spi.LoginModule;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.UIManager;
import java.awt.SystemColor;
import java.util.regex.Pattern;

public class ViewSignUp extends JFrame {
	StringBuilder stb = new StringBuilder();
	private JTextField txtUserName;
	private JPasswordField passwordField_2;
	private JPasswordField passwordField_3;
	private JTextField nation;
	private JTextField txtphone;
	private JTextField gmail;
	private Client client;

	public static void main(String[] args) {
		// Thay thế thông tin kết nối của bạn

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client client= new Client("localhost", 1234);
					System.out.println("Connect ok");
					ViewSignUp frame = new ViewSignUp(client);
					frame.setVisible(true);
				} catch (Exception e) {
					System.out.println("not conected");
				}
			}
		});
	}

	public ViewSignUp(Client client) {
		getContentPane().setBackground(Color.LIGHT_GRAY);
		getContentPane().setLayout(null);

		txtUserName = new JTextField();
		txtUserName.setBackground(Color.WHITE);
		txtUserName.setBounds(63, 130, 764, 26);
		getContentPane().add(txtUserName);
		txtUserName.setColumns(10);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(50, 108, 61, 16);
		getContentPane().add(lblNewLabel);

		JLabel UserName = new JLabel("User Name");
		UserName.setBounds(35, 102, 105, 16);
		getContentPane().add(UserName);

		JLabel lblNewLabel_2 = new JLabel("Gmail");
		lblNewLabel_2.setBounds(30, 168, 61, 16);
		getContentPane().add(lblNewLabel_2);

		JLabel NationID = new JLabel("Nation ID");
		NationID.setBounds(29, 224, 134, 16);
		getContentPane().add(NationID);

		JLabel Password = new JLabel("Password");
		Password.setBounds(30, 356, 100, 16);
		getContentPane().add(Password);

		passwordField_2 = new JPasswordField(null);
		passwordField_2.setBackground(Color.WHITE);
		passwordField_2.setBounds(63, 374, 764, 26);
		getContentPane().add(passwordField_2);

		passwordField_3 = new JPasswordField(null);
		passwordField_3.setBackground(Color.WHITE);
		passwordField_3.setBounds(63, 428, 764, 26);
		getContentPane().add(passwordField_3);

		JLabel confirmpassss = new JLabel("confirmpassss");
		confirmpassss.setBounds(29, 412, 105, 14);
		getContentPane().add(confirmpassss);

		nation = new JTextField();
		nation.setBounds(63, 252, 764, 29);
		getContentPane().add(nation);
		nation.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("Phone Number");
		lblNewLabel_4.setBounds(30, 293, 105, 16);
		getContentPane().add(lblNewLabel_4);

		txtphone = new JTextField();
		txtphone.setBounds(63, 318, 764, 26);
		getContentPane().add(txtphone);
		txtphone.setColumns(10);

		gmail = new JTextField();
		gmail.setColumns(10);
		gmail.setBounds(63, 183, 764, 29);
		getContentPane().add(gmail);

		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		panel.setBounds(-11, 466, 902, 66);
		getContentPane().add(panel);
		panel.setLayout(null);

		JButton thoát = new JButton("Cancel");
		thoát.setBounds(588, 18, 97, 29);
		panel.add(thoát);

		JButton đăngkys = new JButton("Sign Up");
		đăngkys.setBounds(723, 18, 117, 29);
		panel.add(đăngkys);

		JCheckBox Stayloggedinforfaster = new JCheckBox("Stay logged in for faster purchases");
		Stayloggedinforfaster.setForeground(Color.LIGHT_GRAY);
		Stayloggedinforfaster.setBounds(72, 17, 230, 29);
		panel.add(Stayloggedinforfaster);
		Stayloggedinforfaster.setFont(new Font("Lucida Grande", Font.PLAIN, 10));

		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBackground(Color.DARK_GRAY);
		panel_1.setBounds(-11, 0, 902, 90);
		getContentPane().add(panel_1);

		JLabel paywithundol = new JLabel("Pay with UnDol");
		paywithundol.setForeground(SystemColor.window);
		paywithundol.setBounds(344, 18, 230, 29);
		panel_1.add(paywithundol);
		paywithundol.setFont(new Font("Lucida Grande", Font.ITALIC, 22));

		JLabel undo_1 = new JLabel("Un");
		undo_1.setBounds(46, 5, 115, 53);
		panel_1.add(undo_1);
		undo_1.setForeground(new Color(253, 253, 253));
		undo_1.setFont(new Font("Nanum Myeongjo", Font.PLAIN, 27));

		JLabel dol = new JLabel("Dol");
		dol.setBounds(78, 30, 61, 28);
		panel_1.add(dol);
		dol.setForeground(new Color(27, 39, 157));
		dol.setFont(new Font("Nanum Myeongjo", Font.PLAIN, 27));

		JLabel lblNewLabel_1 = new JLabel(
				"With a UnDol account, you're eligible for free return shipping, Purchase Protection, and more.");
		lblNewLabel_1.setBackground(Color.LIGHT_GRAY);
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setBounds(184, 68, 613, 16);
		panel_1.add(lblNewLabel_1);
		lblNewLabel_1.setFont(new Font("Lucida Grande", Font.PLAIN, 11));

		JButton btnNewButton = new JButton("Log In");
		btnNewButton.setForeground(Color.DARK_GRAY);
		btnNewButton.setBounds(753, 12, 117, 29);
		panel_1.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {


			}
		});
		//////////// controller

		đăngkys.addActionListener(new ActionListener() {
		    StringBuilder stb = new StringBuilder();

		    public void actionPerformed(java.awt.event.ActionEvent evt) {
		        int Dk = JOptionPane.showConfirmDialog(đăngkys, "Would you like to register?", "", JOptionPane.YES_NO_OPTION);
		        if (Dk != JOptionPane.YES_OPTION) {
		            return;
		        }

		        try {
		            if (!isValidUsername(txtUserName.getText())) {
		                showError("Please check the USERNAME format and leave it blank");
		                return;
		            }

		            if (!isValidEmail(gmail.getText())) {
		                showError("Please check the EMAIL format and leave it blank");
		                return;
		            }

		            if (!isValidNation(nation.getText())) {
		                showError("Please check the NATION ID format and leave it blank");
		                return;
		            }

		            if (!isValidPhoneNumber(txtphone.getText())) {
		                showError("Please check the PHONE NUMBER format and leave it blank");
		                return;
		            }

		            if (!isValidPassword(passwordField_2.getText())) {
		                showError("Please check the PASSWORD format and leave it blank");
		                return;
		            }

		            if (!arePasswordsMatching(passwordField_2.getText(), passwordField_3.getText())) {
		                showError("The repeated PASSWORD does not match the above password!");
		                return;
		            }

		            boolean isDuplicate = isDuplicateAccount(txtphone.getText(), nation.getText());
		            if (isDuplicate) {
		                showError("An account already exists with this phone number or nation ID.!");
		                return;
		            }

		            client.register(txtUserName.getText(), gmail.getText(), txtphone .getText(), nation.getText(), passwordField_2.getText());
		        } catch (Exception e) {
		            e.printStackTrace();
		            showError("An error occurred during registration. Please check the input fields or database connection.");
		        }
		    }

		    private void showError(String errorMessage) {
		        JOptionPane.showMessageDialog(đăngkys, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
		    }

		    private void showSuccess(String message) {
		        JOptionPane.showMessageDialog(đăngkys, message, "Success", JOptionPane.INFORMATION_MESSAGE);
		    }
		});


		thoát.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        System.exit(0); // Thoát ứng dụng
		    }
		});

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 879, 560);

		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	public boolean isValidUsername(String username) {
	    // Kiểm tra xem tên người dùng có đáp ứng các yêu cầu cụ thể không
	    // Chấp nhận tất cả các ký tự trừ ký tự đặc biệt
	    String regex = "^[^\\p{Punct}]+$";
	    return username.matches(regex);
	}


	public static boolean isValidEmail(String gmail) {
		// Kiểm tra định dạng email hợp lệ
		String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
		return Pattern.matches(regex, gmail);
	}

	public static boolean isValidNation(String nation) {
	    // Kiểm tra quốc gia có hợp lệ hay không
	    // Ví dụ đơn giản, quốc gia không được để trống và phải chứa đúng 12 số tự nhiên

	    // Kiểm tra rỗng
	    if (nation.isEmpty()) {
	        return false;
	    }

	    // Kiểm tra có phải là số hay không
	    try {
	        Long.parseLong(nation);
	    } catch (NumberFormatException e) {
	        return false; // Không phải số
	    }

	    // Kiểm tra độ dài của chuỗi là 12 ký tự
	    if (nation.length() != 12) {
	        return false;
	    }

	    // Nếu qua được tất cả các kiểm tra, coi như hợp lệ
	    return true;
	}

	

	public static boolean isValidPhoneNumber(String phoneNumber) {
	    // Kiểm tra số điện thoại có hợp lệ hay không
	    //  số điện thoại không được để trống và phải chứa đúng 10 số

	    // Kiểm tra rỗng
	    if (phoneNumber.isEmpty()) {
	        return false;
	    }

	    // Kiểm tra có phải là số hay không và độ dài là 10 ký tự
	    return phoneNumber.matches("\\d{10}") && phoneNumber.chars().allMatch(Character::isDigit);
	}


	public static boolean isValidPassword(String password) {
		//  ít nhất 8 ký tự, chứa ít nhất một chữ cái và một số
		String regex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
		return Pattern.matches(regex, password);
	}

	public static boolean arePasswordsMatching(String password1, String password2) {
		// Kiểm tra xem hai mật khẩu có giống nhau không
		return password1.equals(password2);
	}
	 // Hàm kiểm tra xem số điện thoại và số căn cước có trùng với tài khoản đã đăng ký hay không
    public static boolean isDuplicateAccount(String phoneNumber, String NationID) {
        String url = "jdbc:mysql://localhost:3306/ACCOUNT";
        String username = "baobeo";
        String password = "vanbaoub123";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            // Truy vấn kiểm tra
            String query = "SELECT * FROM DANGKY1 WHERE PhoneNumber = ? OR NationID = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, phoneNumber);
                statement.setString(2, NationID);

                try (ResultSet resultSet = statement.executeQuery()) {
                    // Nếu có bản ghi trùng, trả về true
                    return resultSet.next();
                }
            }
        } catch (SQLException e) {
            // Log hoặc in chi tiết lỗi để kiểm tra
            e.printStackTrace();
        }

        // Trường hợp có lỗi hoặc không có bản ghi trùng, trả về false
        return false;
    }


}
