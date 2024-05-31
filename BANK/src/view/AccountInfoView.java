package view;

import javax.swing.*;
import java.awt.*;

public class AccountInfoView extends JFrame {
    private JTextArea accountInfoTextArea;

    public AccountInfoView(String username, String phoneNumber, String email) {
    	getContentPane().setBackground(Color.DARK_GRAY);
        setTitle("Thông tin tài khoản");
        setSize(573, 391);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(224, 136, 342, 117);
        getContentPane().add(scrollPane);
         
                accountInfoTextArea = new JTextArea();
                accountInfoTextArea.setForeground(new Color(255, 255, 255));
                scrollPane.setViewportView(accountInfoTextArea);
                accountInfoTextArea.setBackground(Color.DARK_GRAY);
                accountInfoTextArea.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
                accountInfoTextArea.setEditable(false);
                accountInfoTextArea.setText("Tên đăng nhập: " + username + "\n"
                                            + "Số điện thoại: " + phoneNumber + "\n"
                                            + "Email: " + email);
        
        JLabel undo_1 = new JLabel("Un");
        undo_1.setForeground(new Color(253, 253, 253));
        undo_1.setFont(new Font("Nanum Myeongjo", Font.PLAIN, 30));
        undo_1.setBounds(17, 6, 115, 53);
        getContentPane().add(undo_1);
        
        JLabel dol = new JLabel("Dol");
        dol.setForeground(new Color(27, 39, 157));
        dol.setFont(new Font("Nanum Myeongjo", Font.PLAIN, 30));
        dol.setBounds(53, 28, 100, 44);
        getContentPane().add(dol);
        
        JLabel lblNewLabel_2 = new JLabel("New label");
        lblNewLabel_2.setIcon(new ImageIcon("/private/var/folders/sf/_zpmw6312yx10bnb63lj81nm0000gn/T/com.apple.Photos.NSItemProvider/uuid=9BA243C1-7C9C-4BDA-96B6-F1891C6D068B&library=1&type=1&mode=1&loc=true&cap=true.jpeg/Hình ảnh.jpeg"));
        lblNewLabel_2.setBounds(-21, 107, 241, 256);
        getContentPane().add(lblNewLabel_2);
        
        JLabel lblAccountInformation = new JLabel("Account information");
        lblAccountInformation.setForeground(SystemColor.window);
        lblAccountInformation.setFont(new Font("Lucida Grande", Font.ITALIC, 25));
        lblAccountInformation.setBounds(165, 19, 317, 67);
        getContentPane().add(lblAccountInformation);
    }
}
