package view;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.mysql.cj.x.protobuf.MysqlxNotice.Frame;

import text.Client;
import text.Server;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.HeadlessException;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.JCheckBox;
import javax.swing.ImageIcon;
import java.awt.SystemColor;

public class viewLogIn extends JFrame {

	// Kết nối đến cơ sở dữ liệu

	private JPanel contentPane;
	private Client client;
	JFrame frame;
	private JPanel panel_1;
	private Server phoneLogin;
	static JTextField phoneNumberField = new JTextField(20);
	private Connection connection;

	/**
	 * Launch the application.
	 * 
	 * @throws SQLException
	 */
	public static String getPhoneNumber() {
		return phoneNumberField.getText();
	}

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {

			public void run() {
				try {
					Client client = new Client("localhost", 1234);
					new viewLogIn(client);

				} catch (Exception e) {
					System.out.println("Not conneted");
				}
			}
		});
	}

	public viewLogIn(Client client) throws HeadlessException {
		super();
		this.client = client;
		this.client = client;

		frame = new JFrame("Client Banking System");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(882, 553);

		panel_1 = new JPanel();
		frame.getContentPane().add(panel_1);
		placeComponents(panel_1);

		JPanel panel_1_1 = new JPanel();
		panel_1_1.setLayout(null);
		panel_1_1.setBackground(Color.DARK_GRAY);
		panel_1_1.setBounds(-26, 0, 919, 90);
		panel_1.add(panel_1_1);

		JLabel paywithundol = new JLabel("Pay with");
		paywithundol.setForeground(SystemColor.window);
		paywithundol.setFont(new Font("Lucida Grande", Font.ITALIC, 23));
		paywithundol.setBounds(344, 29, 115, 29);
		panel_1_1.add(paywithundol);

		JLabel undo_1 = new JLabel("Un");
		undo_1.setForeground(new Color(253, 253, 253));
		undo_1.setFont(new Font("Nanum Myeongjo", Font.PLAIN, 27));
		undo_1.setBounds(59, 5, 115, 53);
		panel_1_1.add(undo_1);

		JButton signup = new JButton("Sign Up");
		signup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewSignUp signUp = new ViewSignUp(client);
				signup.setVisible(true);
			}
		});
		signup.setBounds(757, 30, 117, 36);
		panel_1_1.add(signup);

		JLabel lblUndol = new JLabel("UnDol");
		lblUndol.setForeground(SystemColor.window);
		lblUndol.setFont(new Font("Lucida Grande", Font.ITALIC, 23));
		lblUndol.setBounds(438, 43, 96, 29);
		panel_1_1.add(lblUndol);

		JLabel dol = new JLabel("Dol");
		dol.setForeground(new Color(12, 13, 241));
		dol.setFont(new Font("Nanum Myeongjo", Font.PLAIN, 27));
		dol.setBounds(93, 29, 61, 28);
		panel_1_1.add(dol);

		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon("/Users/nguyenvan/Downloads/image001-8277-1651140699 copy.png"));
		lblNewLabel.setBounds(-9, 85, 487, 375);
		panel_1.add(lblNewLabel);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(Color.DARK_GRAY);
		panel.setBounds(-9, 459, 902, 66);
		panel_1.add(panel);

		JLabel r = new JLabel("# Full wealth and good luck for you");
		r.setForeground(Color.LIGHT_GRAY);
		r.setFont(new Font("Mali", Font.PLAIN, 13));
		r.setBounds(42, 22, 244, 18);
		panel.add(r);

		JLabel lblNewLabel_1 = new JLabel(
				"With a UnDol account, you're eligible for free return shipping, Purchase Protection, and more.");
		lblNewLabel_1.setForeground(Color.LIGHT_GRAY);
		lblNewLabel_1.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		lblNewLabel_1.setBounds(337, 24, 613, 16);
		panel.add(lblNewLabel_1);

		JButton Cancel = new JButton("Cancel");
		Cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		Cancel.setBounds(522, 342, 147, 43);
		panel_1.add(Cancel);

		frame.setVisible(true);
	}

	private void placeComponents(JPanel panel) {
		panel_1.setLayout(null);

		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 16));
		passwordLabel.setBounds(522, 247, 105, 25);
		panel.add(passwordLabel);

		JPasswordField passwordField = new JPasswordField(20);
		passwordField.setBounds(522, 272, 295, 37);
		panel.add(passwordField);

		JLabel phoneNumberLabel = new JLabel("Phone Number");
		phoneNumberLabel.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 16));
		phoneNumberLabel.setBounds(522, 152, 137, 25);
		panel.add(phoneNumberLabel);

		phoneNumberField.setBounds(521, 177, 296, 37);
		panel.add(phoneNumberField);

		JButton loginButton = new JButton("Login");
		loginButton.setBounds(691, 342, 148, 43);
		panel.add(loginButton);
		frame.setLocationRelativeTo(null);
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StringBuilder stb1 = new StringBuilder();
				if (phoneNumberField.getText().equals("")) {
					stb1.append("Vui long nhap thong tin !\n");
					phoneNumberField.setBackground(Color.RED);
				} else {
					phoneNumberField.setBackground(Color.WHITE);
				}
				if (passwordField.getText().equals("")) {
					stb1.append("Vui long nhap thong tin !\n");
					passwordField.setBackground(Color.RED);
				} else {
					passwordField.setBackground(Color.WHITE);
				}
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					String url = "jdbc:mysql://localhost:3306/ACCOUNT";
					String username = "baobeo";
					String password = "vanbaoub123";
					// Kết nối đến cơ sở dữ liệu

					Connection connection = DriverManager.getConnection(url, username, password);
				
					String sql = "SELECT* FROM DANGKY1 WHERE PhoneNumber=? and PassWord=?";// truy vấn đến sql
					PreparedStatement ps = connection.prepareCall(sql);// cho phép chỉ định tham số đầu vào khi run
					ps.setString(1, phoneNumberField.getText());// nhận vào 1 tham số khi ai nhập vào
					ps.setString(2, passwordField.getText());// nhap apk mk
				ResultSet rs = ps.executeQuery();// executeQuery tra về thằng ResultSet khi thực hiện câu lệnh sellec
				if(rs.next()) {
						client.login(phoneNumberField.getText(), new String(passwordField.getPassword()));
						viewTrangChu home = new viewTrangChu(client);
						home.setVisible(true);
					}
				else {
						SwingUtilities.invokeLater(() -> {
		                    JOptionPane.showMessageDialog(null,
		                            "Sai số điện thoại hoặc mật khẩu!", "Error",
		                            JOptionPane.ERROR_MESSAGE);
		                    return;
		                });
					}
					
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				// Thực hiện đăng nhập
				

			}
		});
	}
}
//	 public String getAccountId() {
//			return phoneLogin.getText();
//		}

// try {
//            		
//            		if (phoneNumberField.getText().isEmpty() | passwordField.getText().isEmpty()) {
//            			JOptionPane.showMessageDialog(loginButton, "Please do not leave it blank ", "Error",ERROR_MESSAGE);
//            		} else if (rs.next())
//
////            		 có dữ liệu
//            		{
//            			JOptionPane.showMessageDialog(loginButton, "Logged in successfully ");
//            			viewTrangChu vtc = new viewTrangChu();
//            			vtc.setVisible(true);
//            			dispose();
//
//            		} else {
//            			JOptionPane.showMessageDialog(loginButton, "Login failed ! Incorrect account number or password",
//            					"Error", JOptionPane.ERROR_MESSAGE);
//            		}
//
//            	} catch (Exception e2) {
//            		System.out.println(e2);
//
//            	}
