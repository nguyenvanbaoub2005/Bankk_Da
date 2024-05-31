package view;

import java.awt.EventQueue;

import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class view_phụ_changePass extends JFrame {

	private JPanel contentPane;
	private JPasswordField txt_CurrentPass;
	private JPasswordField txt_New_Pass;
	private JPasswordField txt_Confirm_new_passWord;
	private JTextField txt_PhoneNumber;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					view_phụ_changePass frame = new view_phụ_changePass();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public view_phụ_changePass() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 498, 338);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel contentPane_ChangePass = new JPanel();
		contentPane_ChangePass.setLayout(null);
		contentPane_ChangePass.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane_ChangePass.setBackground(Color.GRAY);
		contentPane_ChangePass.setBounds(0, 0, 498, 310);
		contentPane.add(contentPane_ChangePass);

		JLabel txt_Currentpassword = new JLabel("Current password");
		txt_Currentpassword.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		txt_Currentpassword.setBounds(34, 129, 163, 22);
		contentPane_ChangePass.add(txt_Currentpassword);

		txt_CurrentPass = new JPasswordField();
		txt_CurrentPass.setColumns(10);
		txt_CurrentPass.setBounds(193, 127, 223, 29);
		contentPane_ChangePass.add(txt_CurrentPass);

		JButton button_changePass = new JButton("Change");
		button_changePass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleChangePasswordButtonClicked();
				
			}

			private void initComponents() {
				txt_PhoneNumber = new JTextField();
				txt_CurrentPass = new JPasswordField();
				txt_New_Pass = new JPasswordField();
				txt_Confirm_new_passWord = new JPasswordField();
				JButton button_changePass = new JButton("Change Password");
			}

			private void handleChangePasswordButtonClicked() {

				String phoneNumber = txt_PhoneNumber.getText();
				char[] oldPasswordChars = txt_CurrentPass.getPassword();
				char[] newPasswordChars = txt_New_Pass.getPassword();
				char[] confirmPasswordChars = txt_Confirm_new_passWord.getPassword();

				String oldPassword = new String(oldPasswordChars);
				String newPassword = new String(newPasswordChars);
				String confirmPassword = new String(confirmPasswordChars);

				if (txt_PhoneNumber.getText().isEmpty() || txt_Confirm_new_passWord.getText().isEmpty()
						|| txt_CurrentPass.getText().isEmpty() || txt_New_Pass.getText().isEmpty()) {
					JOptionPane.showMessageDialog(button_changePass, "Please do not leave it blank", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (!newPassword.equals(confirmPassword)) {
					JOptionPane.showMessageDialog(button_changePass, "New passwords do not match. Please try again.",
							"Error", JOptionPane.ERROR_MESSAGE);
					clearPasswordFields();
					return;
				}

				String oldPasswordString = new String(oldPasswordChars);
				if (newPassword.equals(oldPasswordString)) {
					JOptionPane.showMessageDialog(button_changePass, "Password overlaps with old password !", "Error",
							JOptionPane.ERROR_MESSAGE); //  "Mật khẩu trùng" 
					clearPasswordFields();
					return;
				}

				if (checkOldPassword(phoneNumber, oldPassword) && updatePassword(phoneNumber, newPassword)) {
					JOptionPane.showMessageDialog(button_changePass, "Password changed successfully!");
					dispose();
							
				} else {
					JOptionPane.showMessageDialog(button_changePass,
							"Failed to change password. Check your information.");
				}

				clearPasswordFields();
			}

			private boolean checkOldPassword(String phoneNumber, String CurrentPass) {
				try (Connection connection = getConnection()) {
					String query = "SELECT * FROM DANGKY1 WHERE PhoneNumber = ? AND PassWord = ?";
					try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
						preparedStatement.setString(1, phoneNumber);
						preparedStatement.setString(2, CurrentPass);
						try (ResultSet resultSet = preparedStatement.executeQuery()) {
							return resultSet.next();
						}
					}
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(button_changePass,
							"Failed to change password. Check your information.", "Error", JOptionPane.ERROR_MESSAGE);
					return false;
				}
			}

			private boolean updatePassword(String phoneNumber, String newPassword) {
				try (Connection connection = getConnection()) {
					String query = "UPDATE DANGKY1 SET PassWord = ? WHERE PhoneNumber = ?";
					try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
						preparedStatement.setString(1, newPassword);
						preparedStatement.setString(2, phoneNumber);
						int affectedRows = preparedStatement.executeUpdate();
						return affectedRows > 0;
					}
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(button_changePass,
							"Failed to change password. Check your information.", "Error", JOptionPane.ERROR_MESSAGE);
					return false;
				}
			}

			private Connection getConnection() throws SQLException {
				String url = "jdbc:mysql://localhost:3306/ACCOUNT";
				String user = "baobeo";
				String password = "vanbaoub123";
				return DriverManager.getConnection(url, user, password);
			}

			private void clearPasswordFields() {

				Arrays.fill(((JPasswordField) txt_CurrentPass).getPassword(), ' ');
				Arrays.fill(((JPasswordField) txt_New_Pass).getPassword(), ' ');
				Arrays.fill(((JPasswordField) txt_Confirm_new_passWord).getPassword(), ' ');
			}

		});

		button_changePass.setBounds(344, 258, 117, 29);
		contentPane_ChangePass.add(button_changePass);

		JLabel undo = new JLabel("Un");
		undo.setForeground(new Color(253, 253, 253));
		undo.setFont(new Font("Nanum Myeongjo", Font.PLAIN, 30));
		undo.setBackground(new Color(10, 8, 11));
		undo.setBounds(18, -3, 115, 53);
		contentPane_ChangePass.add(undo);

		JLabel dol = new JLabel("Dol");
		dol.setForeground(new Color(27, 39, 157));
		dol.setFont(new Font("Nanum Myeongjo", Font.PLAIN, 27));
		dol.setBounds(54, 30, 61, 28)
		;
		contentPane_ChangePass.add(dol);

		JButton cancle = new JButton("Cancel");
		cancle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cancle.setBounds(190, 258, 117, 29);
		contentPane_ChangePass.add(cancle);

		JLabel lblChangePassword = new JLabel("Change PassWord");
		lblChangePassword.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 21));
		lblChangePassword.setBounds(127, 46, 309, 22);
		contentPane_ChangePass.add(lblChangePassword);

		txt_New_Pass = new JPasswordField();
		txt_New_Pass.setColumns(10);
		txt_New_Pass.setBounds(193, 171, 223, 29);
		contentPane_ChangePass.add(txt_New_Pass);

		JLabel txt_New_PassWord = new JLabel("New PassWord");
		txt_New_PassWord.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		txt_New_PassWord.setBounds(34, 173, 163, 22);
		contentPane_ChangePass.add(txt_New_PassWord);

		txt_Confirm_new_passWord = new JPasswordField();
		txt_Confirm_new_passWord.setColumns(10);
		txt_Confirm_new_passWord.setBounds(193, 217, 223, 29);
		contentPane_ChangePass.add(txt_Confirm_new_passWord);

		JLabel txt_Confirm_new_PassWord = new JLabel("Confirm new PassWord");
		txt_Confirm_new_PassWord.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		txt_Confirm_new_PassWord.setBounds(34, 224, 163, 22);
		contentPane_ChangePass.add(txt_Confirm_new_PassWord);

		JLabel txt_Currentpassword_1 = new JLabel("Phone number");
		txt_Currentpassword_1.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		txt_Currentpassword_1.setBounds(34, 80, 163, 22);
		contentPane_ChangePass.add(txt_Currentpassword_1);

		txt_PhoneNumber = new JTextField();
		txt_PhoneNumber.setColumns(10);
		txt_PhoneNumber.setBounds(193, 80, 223, 29);
		contentPane_ChangePass.add(txt_PhoneNumber);
		this.setLocationRelativeTo(null);
	}
}
