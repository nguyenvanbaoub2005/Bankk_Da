package view;

import text.Server;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import com.mysql.cj.x.protobuf.Mysqlx.Error.Severity;
import com.mysql.cj.xdevapi.Statement;
import text.Client;
import text.Client.BalanceUpdateListener;
import javax.swing.JLabel;
import javax.print.attribute.standard.MediaSize.NA;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.CardLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Canvas;
import java.awt.Label;
import java.awt.TextArea;
import javax.swing.JTextArea;

public class viewTrangChu extends JFrame implements Serializable, Client.BalanceUpdateListener {

	private JPanel contentPane;
	private JPanel pannermenubar;
	private JLabel ACTIVITI_Menu;
	private JLabel PERSONAL_menu;
	private JLabel undo;
	private JPanel panel_GOC;
	private JPanel homePANNEL;
	private JLabel lb_Transfemoney;
	private JLabel HOME_Menu;
	private JSeparator separator;
	private JSeparator separator_1;
	private JPanel thanhduoi;
	private JMenuBar menuBar_ACCOUNT;
	private JMenu Account;
	private JMenuItem changePassword;
	private JPanel panel_Nhỏ_transfer_Funds;
	private JLabel lb_UnDolBalance;
	private JPanel wallet_PANNEL;
	private JPanel personnal_PANNEL;
	private JPanel activiti_PANNEL;
	private JMenuItem accountinformation;
	private JMenuItem logout;
	private JMenuItem Seting;
	private JPanel pannerduoiw;
	Vector vData = new Vector();
	Vector vTitle = new Vector();
	Vector vData1 = new Vector();
	Vector vTitle1 = new Vector();

	JTable tb = new JTable();
	private JLabel Lb;
	private JLabel lb_;
	private JTextField txt_trogiup;
	private JLabel lb_1;
	private JMenuBar menuBar_1;
	private JMenuItem mntmNewMenuItem;
	private JMenuItem mntmNewMenuItem_1;
	private JMenuItem mntmNewMenuItem_2;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_4;
	private JLabel lblNewLabel_7;
	private JMenuItem mntmNewMenuItem_3;
	private JLabel lblContactUsFor;

	private JTextField amountField;
	private JButton depositButton;
	private JButton transferButton;
	private Client client;
	private DefaultTableModel model;
	
	
	// biến đăng nhập để hiển thị thông tin
	public String getPhoneloged() {
		SwingUtilities.invokeLater(() -> {
			String phoneNumber = viewLogIn.getPhoneNumber();
		});
		return viewLogIn.getPhoneNumber();
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					Client client = new Client("localhost", 1234);
					viewTrangChu frame = new viewTrangChu(client);
					client.setBalanceUpdateListener(frame);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// Đảm bảo rằng bạn đã khởi tạo các JTextField này trong constructor hoặc phương
	// thức tạo giao diện

	String url = "jdbc:mysql://localhost:3306/ACCOUNT";
	String username = "baobeo";
	String passwordDB = "vanbaoub123";
	private JLabel dol;

//
	public void reload() {
		try {
			// lấy số điệnt thọi để hiển thị bảng

			if (getPhoneloged() == null || getPhoneloged().isEmpty()) {
				System.out.println("No user logged in.");
				return;
			}
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ACCOUNT", "baobeo",
					"vanbaoub123");
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM HISTORY_ WHERE AccountNumber = ?");
			ps.setString(1, getPhoneloged());
			ResultSet rst = ps.executeQuery();
			ResultSetMetaData rstmeta = rst.getMetaData();
			int num_column = rstmeta.getColumnCount();

			vTitle = new Vector<>(num_column); // ten cot
			for (int i = 1; i <= num_column; i++) {
				vTitle.add(rstmeta.getColumnLabel(i));
			}

			vData = new Vector<>();
			while (rst.next()) {
				Vector<String> row = new Vector<>(num_column);
				for (int i = 1; i <= num_column; i++) {
					row.add(rst.getString(i));
				}
				vData.add(row);
			}

			rst.close();
			ps.close();
			conn.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public viewTrangChu(Client client) {
		this.client = client;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1031, 723);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel_main = new JPanel();
		panel_main.setBackground(Color.GRAY);
		panel_main.setBounds(0, 0, 1028, 621);
		contentPane.add(panel_main);
		panel_main.setLayout(null);

		pannermenubar = new JPanel();
		pannermenubar.setForeground(new Color(113, 162, 229));
		pannermenubar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});

		pannermenubar.setLayout(null);
		pannermenubar.setBackground(Color.DARK_GRAY);
		pannermenubar.setBounds(0, 0, 1028, 79);
		panel_main.add(pannermenubar);

		ACTIVITI_Menu = new JLabel("Activity");
		ACTIVITI_Menu.setIcon(new ImageIcon("/Users/nguyenvan/Downloads/Messaging-Activity-Feed-icon.png"));
		ACTIVITI_Menu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				homePANNEL.setVisible(false);
				wallet_PANNEL.setVisible(false);
				personnal_PANNEL.setVisible(false);
				activiti_PANNEL.setVisible(true);
				reload();
			}
		});
		ACTIVITI_Menu.setForeground(Color.WHITE);
		ACTIVITI_Menu.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		ACTIVITI_Menu.setBounds(470, 16, 149, 55);
		pannermenubar.add(ACTIVITI_Menu);

		PERSONAL_menu = new JLabel("Personal");
		PERSONAL_menu.setIcon(new ImageIcon("/Users/nguyenvan/Downloads/Mimetype-text-2-icon.png"));
		PERSONAL_menu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				homePANNEL.setVisible(false);
				wallet_PANNEL.setVisible(false);
				personnal_PANNEL.setVisible(true);
				activiti_PANNEL.setVisible(false);

			}
		});
		PERSONAL_menu.setForeground(Color.WHITE);
		PERSONAL_menu.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		PERSONAL_menu.setBounds(655, 19, 149, 48);
		pannermenubar.add(PERSONAL_menu);

		undo = new JLabel("Un");
		undo.setForeground(new Color(253, 253, 253));
		undo.setFont(new Font("Nanum Myeongjo", Font.PLAIN, 30));
		undo.setBounds(18, 10, 39, 47);
		pannermenubar.add(undo);

		HOME_Menu = new JLabel("Home");
		HOME_Menu.setIcon(new ImageIcon("/Users/nguyenvan/Downloads/home-icon-3.png"));
		HOME_Menu.setForeground(new Color(253, 253, 253));
		HOME_Menu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				homePANNEL.setVisible(true);
				wallet_PANNEL.setVisible(false);
				personnal_PANNEL.setVisible(false);
				activiti_PANNEL.setVisible(false);

				;
			}
		});
		HOME_Menu.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		HOME_Menu.setBounds(137, 21, 126, 44);
		pannermenubar.add(HOME_Menu);

		JLabel WALLET_Menu = new JLabel("Wallet");
		WALLET_Menu.setIcon(new ImageIcon("/Users/nguyenvan/Downloads/wallet-icon.png"));
		WALLET_Menu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				homePANNEL.setVisible(false);
				wallet_PANNEL.setVisible(true);
				personnal_PANNEL.setVisible(false);
				activiti_PANNEL.setVisible(false);

			}
		});
		WALLET_Menu.setForeground(new Color(253, 253, 253));
		WALLET_Menu.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
		WALLET_Menu.setBounds(313, 25, 118, 36);
		pannermenubar.add(WALLET_Menu);

		menuBar_ACCOUNT = new JMenuBar();
		menuBar_ACCOUNT.setBackground(new Color(66, 106, 227));
		menuBar_ACCOUNT.setBounds(847, 29, 132, 22);
		pannermenubar.add(menuBar_ACCOUNT);

		Account = new JMenu("Account");
		Account.setBackground(new Color(66, 106, 227));
		menuBar_ACCOUNT.add(Account);

		changePassword = new JMenuItem("Change Password");
		changePassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				view_phụ_changePass vcp = new view_phụ_changePass();
				vcp.setVisible(true);

			}
		});
		changePassword.setBackground(Color.LIGHT_GRAY);
		Account.add(changePassword);

		accountinformation = new JMenuItem("account information");
		accountinformation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				client.requestAccountInfo(getPhoneloged());
				AccountInfoView accountInfoView = new AccountInfoView(null, null, null);
			}
		});
		accountinformation.setBackground(Color.LIGHT_GRAY);
		Account.add(accountinformation);

		Seting = new JMenuItem("Seting");
		Seting.setBackground(Color.LIGHT_GRAY);
		Account.add(Seting);

		logout = new JMenuItem("Log out");
		logout.setBackground(new Color(193, 10, 14));
		logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewLogIn vg = new viewLogIn(null);
				vg.setVisible(true);
				dispose();

			}
		});
		Account.add(logout);
		
		dol = new JLabel("Dol");
		dol.setForeground(new Color(27, 39, 157));
		dol.setFont(new Font("Nanum Myeongjo", Font.PLAIN, 27));
		dol.setBounds(53, 27, 61, 28);
		pannermenubar.add(dol);

		panel_GOC = new JPanel();
		panel_GOC.setBackground(new Color(247, 241, 235));
		panel_GOC.setBounds(0, 84, 1028, 532);
		panel_main.add(panel_GOC);
		panel_GOC.setLayout(new CardLayout(0, 0));

		homePANNEL = new JPanel();
		homePANNEL.setForeground(Color.WHITE);
		homePANNEL.setBackground(Color.DARK_GRAY);

		panel_GOC.add(homePANNEL, "name_333936788887292");
		homePANNEL.setLayout(null);

		JLabel lb_dấumuixten = new JLabel("^");
		lb_dấumuixten.setBounds(1006, 6, 42, 24);
		homePANNEL.add(lb_dấumuixten);
		lb_dấumuixten.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				closemenubar();
			}

// Đóng menubar
			void closemenubar() {
				new Thread(new Runnable() {

					@Override
					public void run() {
						for (int i = HEIGHT; i > 0; i--) {
							pannermenubar.setSize(i, WIDTH);
							try {
								Thread.sleep(10);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

					}
				}).start();

			}
		});
		lb_dấumuixten.setFont(new Font("Lucida Grande", Font.PLAIN, 22));

		panel_Nhỏ_transfer_Funds = new JPanel();
		panel_Nhỏ_transfer_Funds.setBackground(Color.DARK_GRAY);
		panel_Nhỏ_transfer_Funds.setBounds(0, 381, 543, 161);
		homePANNEL.add(panel_Nhỏ_transfer_Funds);
		panel_Nhỏ_transfer_Funds.setLayout(null);

		lb_Transfemoney = new JLabel("Transfer money");
		lb_Transfemoney.setForeground(Color.WHITE);
		lb_Transfemoney.setBounds(26, 60, 150, 25);
		panel_Nhỏ_transfer_Funds.add(lb_Transfemoney);
		lb_Transfemoney.setFont(new Font("Lucida Grande", Font.PLAIN, 19));
		JButton transferButton = new JButton();
		transferButton.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		transferButton.setForeground(Color.BLACK);
		transferButton.setBounds(258, 42, 240, 68);
		panel_Nhỏ_transfer_Funds.add(transferButton);
		transferButton.setText("Transfer Funds");
		transferButton.addActionListener(new ActionListener() {

			// Các thông tin kết nối cơ sở dữ liệu

			//// chuyển tiền
			public void actionPerformed(ActionEvent e) {
				// Xử lý sự kiện chuyển tiền ở đây
				View_phụ_chuyenkhoang vck = new View_phụ_chuyenkhoang(client);
				vck.setVisible(true);

			}
		});

		lb_UnDolBalance = new JLabel("UnDol Balance");
		lb_UnDolBalance.setForeground(Color.WHITE);
		lb_UnDolBalance.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lb_UnDolBalance.setBounds(718, 385, 147, 43);
		homePANNEL.add(lb_UnDolBalance);

		JLabel lb_ảnhnen = new JLabel("New label");
		lb_ảnhnen.setIcon(new ImageIcon(
				"/Users/nguyenvan/Downloads/scb-giu-vung-da-tang-truong-hoat-dong-hieu-qua-trong-quy-i2022-104656710.jpg"));
		lb_ảnhnen.setBounds(0, 0, 1022, 381);
		homePANNEL.add(lb_ảnhnen);

		//// Số tài khoảng
		JButton btnPhoneRechage = new JButton("Phone rechage");
		btnPhoneRechage.setIcon(new ImageIcon("/Users/nguyenvan/Downloads/Master-Card-2-icon.png"));
		btnPhoneRechage.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		btnPhoneRechage.setForeground(Color.BLACK);
		btnPhoneRechage.setBounds(571, 424, 389, 62);
		homePANNEL.add(btnPhoneRechage);
		////////////
		btnPhoneRechage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				View_phụ_nạptiendt nạptiendt = new View_phụ_nạptiendt(client);
				nạptiendt.setVisible(true);
			}
		});

		wallet_PANNEL = new JPanel();
		wallet_PANNEL.setBackground(Color.GRAY);
		panel_GOC.add(wallet_PANNEL, "name_395318853280000");
		wallet_PANNEL.setLayout(null);

		JPanel Balance_Panel = new JPanel();
		Balance_Panel.setBackground(Color.LIGHT_GRAY);
		Balance_Panel.setBounds(492, 158, 509, 368);
		wallet_PANNEL.add(Balance_Panel);
		Balance_Panel.setLayout(null);

		JLabel lb_UnDolbalance = new JLabel("UnDol Balance");
		lb_UnDolbalance.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
		lb_UnDolbalance.setBounds(174, 18, 184, 67);
		Balance_Panel.add(lb_UnDolbalance);

		JLabel Availabulity = new JLabel("Availabulity");
		Availabulity.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		Availabulity.setBounds(201, 212, 130, 67);
		Balance_Panel.add(Availabulity);

		JButton butontranfer = new JButton("Transfer");
		butontranfer.setBackground(Color.BLACK);
		butontranfer.setForeground(Color.BLACK);
		butontranfer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				View_phụ_chuyenkhoang vck = new View_phụ_chuyenkhoang(client);
				vck.setVisible(true);
			}
		});
		butontranfer.setBounds(42, 285, 151, 53);
		Balance_Panel.add(butontranfer);

		JLabel $ = new JLabel("$");
		$.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		$.setBounds(423, 104, 86, 59);
		Balance_Panel.add($);

		JButton depositButtonn = new JButton("deposit");
		depositButtonn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				View_phụ_naptien naptien = new View_phụ_naptien(client);
				naptien.setVisible(true);
			}
		});
		depositButtonn.setBounds(326, 285, 177, 53);
		Balance_Panel.add(depositButtonn);

		JTextArea balanceTextArea = new JTextArea();
		balanceTextArea.setFont(new Font("Symbol", Font.BOLD | Font.ITALIC, 22));
		balanceTextArea.setEditable(false); // Thiết lập không cho chỉnh sửa

		client.setBalanceUpdateListener(new BalanceUpdateListener() {
			public void onBalanceUpdate(double newBalance) {
				// TODO Auto-generated method stub
				balanceTextArea.setText("" + newBalance);

			}
		});
		// Lấy số dư hiện tại
		client.fetchCurrentBalance();
		balanceTextArea.setBackground(Color.GRAY);
		balanceTextArea.setBounds(238, 127, 106, 42);
		Balance_Panel.add(balanceTextArea);

		JTextArea textAreanền = new JTextArea();
		textAreanền.setBackground(Color.GRAY);
		textAreanền.setBounds(119, 74, 286, 138);
		Balance_Panel.add(textAreanền);
		textAreanền.setEditable(false); // Thiết lập không cho chỉnh sửa

		JPanel panel_nhiềuchuw = new JPanel();
		panel_nhiềuchuw.setForeground(Color.DARK_GRAY);
		panel_nhiềuchuw.setBackground(Color.GRAY);
		panel_nhiềuchuw.setBounds(39, 384, 441, 142);
		wallet_PANNEL.add(panel_nhiềuchuw);
		panel_nhiềuchuw.setLayout(null);

		JLabel lblNewLabel_2_1 = new JLabel("Preferred when paying online .");
		lblNewLabel_2_1.setBounds(14, 6, 246, 19);
		panel_nhiềuchuw.add(lblNewLabel_2_1);
		lblNewLabel_2_1.setFont(new Font("Lucida Grande", Font.PLAIN, 15));

		lblNewLabel_2 = new JLabel("We’ll use your available balance when you shop online or ");
		lblNewLabel_2.setBounds(14, 29, 413, 19);
		panel_nhiềuchuw.add(lblNewLabel_2);
		lblNewLabel_2.setFont(new Font("Lucida Grande", Font.PLAIN, 15));

		lblNewLabel_1 = new JLabel("send money for goods and services.");
		lblNewLabel_1.setBounds(14, 49, 261, 19);
		panel_nhiềuchuw.add(lblNewLabel_1);
		lblNewLabel_1.setFont(new Font("Lucida Grande", Font.PLAIN, 15));

		lblNewLabel_4 = new JLabel("If you don’t have enough money in your balance, we’ll ask ");
		lblNewLabel_4.setBounds(14, 70, 425, 19);
		panel_nhiềuchuw.add(lblNewLabel_4);
		lblNewLabel_4.setFont(new Font("Lucida Grande", Font.PLAIN, 15));

		lblNewLabel_7 = new JLabel("you to pick another payment method at checkout.");
		lblNewLabel_7.setBounds(14, 90, 363, 19);
		panel_nhiềuchuw.add(lblNewLabel_7);
		lblNewLabel_7.setFont(new Font("Lucida Grande", Font.PLAIN, 15));

		JLabel icon1 = new JLabel("");
		icon1.setIcon(new ImageIcon("/Users/nguyenvan/Downloads/Bank-icon.png"));
		icon1.setFont(new Font("Lucida Grande", Font.PLAIN, 23));
		icon1.setBounds(39, 209, 128, 146);
		wallet_PANNEL.add(icon1);

		JLabel icon2 = new JLabel("");
		icon2.setIcon(new ImageIcon("/Users/nguyenvan/Downloads/credit-card-icon.png"));
		icon2.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		icon2.setBounds(210, 237, 141, 105);
		wallet_PANNEL.add(icon2);

		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(6, 142, 1016, 29);
		wallet_PANNEL.add(separator_2);

		JButton button_Recharge = new JButton("Recharge");
		button_Recharge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		button_Recharge.setBounds(53, 41, 261, 88);
		wallet_PANNEL.add(button_Recharge);
		activiti_PANNEL = new JPanel();
		panel_GOC.add(activiti_PANNEL, "name_1104117965850750");
		activiti_PANNEL.setBackground(Color.GRAY);
		activiti_PANNEL.setLayout(null);

		// Tao bang hien thi thong tin len cua so
		reload();
		model = new DefaultTableModel(vData, vTitle);
		JTable table = new JTable(model);
		JScrollPane table_Chuyenkhoan = new JScrollPane(table);
		table_Chuyenkhoan.setBounds(58, 46, 902, 446);
		activiti_PANNEL.add(table_Chuyenkhoan);
		// Tao bang hien thi thong tin len cua so

		JLabel lbchuyentien = new JLabel("History");
		lbchuyentien.setForeground(Color.BLACK);
		lbchuyentien.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 20));
		lbchuyentien.setBounds(83, 18, 140, 25);
		activiti_PANNEL.add(lbchuyentien);

		personnal_PANNEL = new JPanel();
		personnal_PANNEL.setBackground(Color.GRAY);
		panel_GOC.add(personnal_PANNEL, "name_396862147902250");
		personnal_PANNEL.setLayout(null);

		lb_ = new JLabel("Hi. How can we help?");
		lb_.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		lb_.setBackground(Color.WHITE);
		lb_.setBounds(76, 83, 293, 68);
		personnal_PANNEL.add(lb_);

		txt_trogiup = new JTextField();
		txt_trogiup.setBounds(76, 157, 837, 83);
		personnal_PANNEL.add(txt_trogiup);
		txt_trogiup.setColumns(10);

		lb_1 = new JLabel("Help Center-Pesonal Account");
		lb_1.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lb_1.setBounds(76, 76, 254, 27);
		personnal_PANNEL.add(lb_1);

		lblContactUsFor = new JLabel(
				"#If there is anything that needs to be resolved, please contact us for resolution 190010012");
		lblContactUsFor.setFont(new Font("Maku", Font.BOLD, 20));
		lblContactUsFor.setForeground(Color.LIGHT_GRAY);
		lblContactUsFor.setBounds(38, 264, 770, 52);
		personnal_PANNEL.add(lblContactUsFor);

		JLabel nhà = new JLabel("");

		nhà.setIcon(new ImageIcon("/Users/nguyenvan/Downloads/home-icon-2.png"));
		nhà.setBounds(23, 29, 48, 43);
		panel_main.add(nhà);

		separator_1 = new JSeparator();
		separator_1.setBounds(0, 615, 1043, 16);
		panel_main.add(separator_1);

		separator = new JSeparator();
		separator.setBounds(1, 75, 1043, 16);
		panel_main.add(separator);
		separator.setForeground(Color.PINK);

		pannerduoiw = new JPanel();
		pannerduoiw.setBounds(0, 619, 1028, 76);
		contentPane.add(pannerduoiw);
		pannerduoiw.setLayout(null);

		thanhduoi = new JPanel();
		thanhduoi.setBounds(0, 0, 1034, 70);
		pannerduoiw.add(thanhduoi);
		thanhduoi.setBackground(Color.DARK_GRAY);
		thanhduoi.setLayout(null);

		Lb = new JLabel("©2023-2024 Undol, Inc. All rights reserved.");
		Lb.setForeground(Color.LIGHT_GRAY);
		Lb.setBounds(46, 30, 314, 16);
		thanhduoi.add(Lb);

		JPanel menuduoi = new JPanel();
		menuduoi.setBackground(Color.DARK_GRAY);
		menuduoi.setBounds(500, 6, 447, 58);
		thanhduoi.add(menuduoi);
		menuduoi.setLayout(null);

		menuBar_1 = new JMenuBar();
		menuBar_1.setBackground(Color.DARK_GRAY);
		menuBar_1.setBounds(0, 0, 448, 58);
		menuduoi.add(menuBar_1);

		mntmNewMenuItem_1 = new JMenuItem("New menu item");
		mntmNewMenuItem_1.setBackground(Color.DARK_GRAY);
		menuBar_1.add(mntmNewMenuItem_1);

		mntmNewMenuItem_2 = new JMenuItem("New menu item");
		mntmNewMenuItem_2.setBackground(Color.DARK_GRAY);
		menuBar_1.add(mntmNewMenuItem_2);

		mntmNewMenuItem_3 = new JMenuItem("New menu item");
		mntmNewMenuItem_3.setBackground(Color.DARK_GRAY);
		menuBar_1.add(mntmNewMenuItem_3);

		mntmNewMenuItem = new JMenuItem("New menu item");
		mntmNewMenuItem.setBounds(500, 19, 105, 45);
		thanhduoi.add(mntmNewMenuItem);
		mntmNewMenuItem.setBackground(Color.DARK_GRAY);
		int WIDTH = 1028;
		int HEIGHT = 84;
		this.setLocationRelativeTo(null);
		nhà.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				openmenubar();
			}

///// mở menu mượt đa luồng
			private void openmenubar() {
				new Thread(new Runnable() {

					@Override
					public void run() {
						// Vòng lặp để tăng dần chiều cao của thanh menu
						for (int i = 0; i < HEIGHT; i++) {
							// Đặt kích thước của thanh menu với chiều cao tăng dần
							pannermenubar.setSize(WIDTH, i);
							try {
								// Tạm dừng luồng trong một khoảng thời gian ngắn để tạo hiệu ứng chuyeenr donmg
								Thread.sleep(10);
							} catch (InterruptedException e) {
								// Handle interruptions (if any)
								e.printStackTrace();
							}
						}
					}
				}).start();
			}

		});

	}



	@Override
	public void onBalanceUpdate(double newBalance) {
	}
}
