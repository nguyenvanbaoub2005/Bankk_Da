package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import text.Client;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Random;
import java.awt.event.ActionEvent;

public class View_phụ_naptien extends JFrame {

	private JPanel contentPane;
	private JTextField txt_naptien;
	private JPasswordField txtpass;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client client = new Client("localhost", 1234);
					View_phụ_naptien frame = new View_phụ_naptien(client);
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



	public View_phụ_naptien( Client client) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel contentPane_1 = new JPanel();
		contentPane_1.setLayout(null);
		contentPane_1.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane_1.setBackground(Color.GRAY);
		contentPane_1.setBounds(0, 0, 450, 272);
		contentPane.add(contentPane_1);

		JLabel EnterNumberPhone = new JLabel("Amount");
		EnterNumberPhone.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		EnterNumberPhone.setBounds(32, 90, 163, 22);
		contentPane_1.add(EnterNumberPhone);

		txt_naptien = new JTextField();
		txt_naptien.setColumns(10);
		txt_naptien.setBounds(151, 88, 223, 29);
		contentPane_1.add(txt_naptien);

		JButton button_naptien = new JButton("Recharge");
		button_naptien.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 double amount = Double.parseDouble(txt_naptien.getText());
                 String password = new String(txtpass.getText());
				client.deposit(amount, password);
				dispose();
			}
		});
	

		button_naptien.setBounds(294, 223, 117, 29);
		contentPane_1.add(button_naptien);

		JLabel undo = new JLabel("Un");
		undo.setForeground(new Color(253, 253, 253));
		undo.setFont(new Font("Nanum Myeongjo", Font.PLAIN, 30));
		undo.setBackground(new Color(10, 8, 11));
		undo.setBounds(18, -3, 115, 53);
		contentPane_1.add(undo);

		JLabel dol = new JLabel("Dol");
		dol.setForeground(new Color(27, 39, 157));
		dol.setFont(new Font("Nanum Myeongjo", Font.PLAIN, 27));
		dol.setBounds(54, 30, 61, 28);
		contentPane_1.add(dol);

		JButton cancle = new JButton("Cancel");
		cancle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
		
			}
		});
		cancle.setBounds(151, 223, 117, 29);
		contentPane_1.add(cancle);

		JLabel lblDepositMoneyInto = new JLabel("Deposit money into account");
		lblDepositMoneyInto.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 21));
		lblDepositMoneyInto.setBounds(127, 46, 309, 22);
		contentPane_1.add(lblDepositMoneyInto);

		txtpass = new JPasswordField();
		txtpass.setColumns(10);
		txtpass.setBounds(151, 144, 223, 29);
		contentPane_1.add(txtpass);

		JLabel lblPassword = new JLabel("PassWord");
		lblPassword.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblPassword.setBounds(32, 150, 163, 22);
		contentPane_1.add(lblPassword);
		this.setLocationRelativeTo(null);
	}
}
