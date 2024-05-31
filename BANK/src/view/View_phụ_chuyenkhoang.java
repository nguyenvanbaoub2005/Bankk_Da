package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import text.Client;

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
import java.awt.Color;
import javax.swing.JPasswordField;

public class View_phụ_chuyenkhoang extends JFrame {

	private JPanel contentPane;
	private JTextField txtamountField;
	private JTextField txtcartID;
	private JPasswordField txtpasss;
	String phoneString = viewLogIn.getPhoneNumber();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client client = new Client("localhost", 1234);
					View_phụ_chuyenkhoang frame = new View_phụ_chuyenkhoang(client);
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

	

	public View_phụ_chuyenkhoang(Client client) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 508, 334);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		panel.setBounds(0, 0, 508, 307);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel label = new JLabel("New label");
		label.setBounds(69, 61, 74, -3);
		panel.add(label);

		JLabel amount = new JLabel("Amount");
		amount.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		amount.setBounds(32, 61, 83, 16);
		panel.add(amount);

		txtamountField = new JTextField();
		txtamountField.setBounds(168, 61, 215, 26);
		panel.add(txtamountField);
		txtamountField.setColumns(10);

		JButton transferButton = new JButton("Transfer ");
		transferButton.setBackground(new Color(244, 244, 244));
		transferButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				client.transfer(txtcartID.getText(), new Double(txtamountField.getText()),
						new String(txtpasss.getPassword()));
				dispose();
				
			}
		});
		transferButton.setBounds(351, 255, 146, 29);
		panel.add(transferButton);

		JLabel tlaybleransfer = new JLabel("Transfer");
		tlaybleransfer.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
		tlaybleransfer.setBounds(192, 20, 123, 29);
		panel.add(tlaybleransfer);

		JButton Cancel = new JButton("Cancel");
		Cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		Cancel.setBounds(220, 256, 117, 29);
		panel.add(Cancel);

		txtcartID = new JTextField();
		txtcartID.setBounds(168, 95, 215, 26);
		panel.add(txtcartID);
		txtcartID.setColumns(10);

		JLabel sốthẻ = new JLabel("Cart ID");
		sốthẻ.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		sốthẻ.setBounds(32, 99, 125, 16);
		panel.add(sốthẻ);

		txtpasss = new JPasswordField();
		txtpasss.setBounds(168, 164, 190, 26);
		panel.add(txtpasss);

		JLabel lbpass = new JLabel("PassWord");
		lbpass.setBounds(42, 169, 61, 16);
		panel.add(lbpass);
		this.setLocationRelativeTo(null);
	}
}
