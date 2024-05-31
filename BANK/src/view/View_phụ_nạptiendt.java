package view;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import text.Client;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

public class View_phụ_nạptiendt extends JFrame {

	private JPanel contentPane;
	private JTextField textField_phoneNumber;
	private JTextField textField_amount;
	private JPasswordField textField_password;
	private Client client;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client client = new Client("localhost", 1234);
					System.out.println("Connect ok");
					View_phụ_nạptiendt frame = new View_phụ_nạptiendt(client);
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
	public View_phụ_nạptiendt(Client client) {
		this.client = client;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 632, 400);
		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel EnterNumberPhone = new JLabel("Enter phone number");
		EnterNumberPhone.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		EnterNumberPhone.setBounds(26, 138, 163, 22);
		contentPane.add(EnterNumberPhone);

		textField_phoneNumber = new JTextField();
		textField_phoneNumber.setBackground(Color.LIGHT_GRAY);
		textField_phoneNumber.setBounds(266, 136, 220, 29);
		contentPane.add(textField_phoneNumber);
		textField_phoneNumber.setColumns(10);

		JButton butonnaptien = new JButton("Recharge");
		butonnaptien.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				butonnaptien.setForeground(Color.cyan);
				
				
			}
			@Override
			public void mouseExited(MouseEvent e) {
				butonnaptien.setForeground(Color.darkGray);
				
			}
		});
		butonnaptien.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				String phoneNumber = textField_phoneNumber.getText();
//				double amount = Double.parseDouble(textField_amount.getText());
//				String password = textField_password.getText();
//				client.phonecharge(phoneNumber, amount, password);
				
				client.phonecharge(textField_phoneNumber.getText(), new Double(textField_amount.getText()),
						new String(textField_password.getPassword()));
				dispose();
			
			}
		});
		butonnaptien.setBounds(350, 287, 184, 42);
		contentPane.add(butonnaptien);

		JLabel undo = new JLabel("Un");
		undo.setBackground(new Color(10, 8, 11));
		undo.setForeground(new Color(253, 253, 253));
		undo.setFont(new Font("Nanum Myeongjo", Font.PLAIN, 30));
		undo.setBounds(18, -3, 115, 53);
		contentPane.add(undo);

		JLabel dol = new JLabel("Dol");
		dol.setForeground(new Color(27, 39, 157));
		dol.setFont(new Font("Nanum Myeongjo", Font.PLAIN, 27));
		dol.setBounds(54, 30, 61, 28);
		contentPane.add(dol);

		JButton cancel = new JButton("Cancel");
		cancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				butonnaptien.setForeground(Color.darkGray);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				butonnaptien.setForeground(Color.RED);
			
			}
		});
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cancel.setBounds(94, 287, 152, 42);
		contentPane.add(cancel);

		JLabel lblPhoneRecharge = new JLabel("Phone recharge");
		lblPhoneRecharge.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 25));
		lblPhoneRecharge.setBounds(228, 18, 206, 50);
		contentPane.add(lblPhoneRecharge);

		JLabel lblEnterPhoneNu = new JLabel("Enter the amount");
		lblEnterPhoneNu.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblEnterPhoneNu.setBounds(26, 87, 163, 22);
		contentPane.add(lblEnterPhoneNu);

		textField_amount = new JTextField();
		textField_amount.setBackground(Color.LIGHT_GRAY);
		textField_amount.setColumns(10);
		textField_amount.setBounds(266, 85, 220, 29);
		contentPane.add(textField_amount);

		JLabel lblEnterPassword = new JLabel("Enter password");
		lblEnterPassword.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblEnterPassword.setBounds(26, 187, 163, 22);
		contentPane.add(lblEnterPassword);

		textField_password = new JPasswordField();
		textField_password.setBackground(Color.LIGHT_GRAY);
		textField_password.setBounds(266, 185, 220, 29);
		contentPane.add(textField_password);
		textField_password.setColumns(10);

		// Add labels for predefined amounts
		JLabel[] amountLabels = new JLabel[3];
		String[] amounts = { "10000", "50000", "100000" };

		for (int i = 0; i < amounts.length; i++) {
			final int index = i; // Create a final copy of i for use inside the anonymous class
			amountLabels[i] = new JLabel(amounts[i]);
			amountLabels[i].setFont(new Font("Lucida Grande", Font.PLAIN, 14));
			amountLabels[i].setBounds(201 + (i * 65), 236, 55, 22);
			amountLabels[i].setOpaque(true);
			amountLabels[i].setBackground(Color.LIGHT_GRAY);
			amountLabels[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					amountLabels[index].setBackground(Color.YELLOW);
					textField_amount.setText(amounts[index]);
				}

				@Override
				public void mouseExited(MouseEvent e) {
					amountLabels[index].setBackground(Color.LIGHT_GRAY);
				}
			});
			contentPane.add(amountLabels[i]);
		}

		this.setLocationRelativeTo(null);
	}
}
