package parts;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Create extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Create frame = new Create();
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
	public Create() {
		setTitle("Parts");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 353);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.BLACK, Color.BLACK, null, null));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPartscreate = new JLabel("Parts-Create");
		lblPartscreate.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		lblPartscreate.setBounds(171, 0, 110, 14);
		contentPane.add(lblPartscreate);
		
		JLabel lblPartNo = new JLabel("Part No");
		lblPartNo.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		lblPartNo.setBounds(10, 37, 271, 14);
		contentPane.add(lblPartNo);
		
		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		lblName.setBounds(10, 62, 271, 14);
		contentPane.add(lblName);
		
		JLabel lblCategory = new JLabel("Category");
		lblCategory.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		lblCategory.setBounds(10, 87, 271, 14);
		contentPane.add(lblCategory);
		
		JLabel lblCompany = new JLabel("Company");
		lblCompany.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		lblCompany.setBounds(10, 112, 271, 14);
		contentPane.add(lblCompany);
		
		JLabel lblQuantity = new JLabel("Quantity");
		lblQuantity.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		lblQuantity.setBounds(10, 137, 271, 14);
		contentPane.add(lblQuantity);
		
		JLabel lblPrice = new JLabel("Price");
		lblPrice.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		lblPrice.setBounds(10, 162, 271, 14);
		contentPane.add(lblPrice);
		
		JLabel lblCarNo = new JLabel("Car No");
		lblCarNo.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		lblCarNo.setBounds(10, 187, 271, 14);
		contentPane.add(lblCarNo);
		
		JLabel lblSubPart = new JLabel("Sub Part");
		lblSubPart.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		lblSubPart.setBounds(10, 212, 271, 14);
		contentPane.add(lblSubPart);
		
		textField = new JTextField();
		textField.setBounds(195, 34, 86, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(195, 59, 86, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(195, 84, 86, 20);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setBounds(195, 109, 86, 20);
		contentPane.add(textField_3);
		textField_3.setColumns(10);
		
		textField_4 = new JTextField();
		textField_4.setBounds(195, 134, 86, 20);
		contentPane.add(textField_4);
		textField_4.setColumns(10);
		
		textField_5 = new JTextField();
		textField_5.setBounds(195, 159, 86, 20);
		contentPane.add(textField_5);
		textField_5.setColumns(10);
		
		textField_6 = new JTextField();
		textField_6.setBounds(195, 184, 86, 20);
		contentPane.add(textField_6);
		textField_6.setColumns(10);
		
		textField_7 = new JTextField();
		textField_7.setBounds(195, 209, 86, 20);
		contentPane.add(textField_7);
		textField_7.setColumns(10);
		
		JButton btnNewButton = new JButton("Create");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				PreparedStatement ps = null;
				Connection con=null;
				try {
					Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
					con = DriverManager.getConnection("JDBC:ODBC:projectdb");
					ps = con.prepareStatement("Insert into parts values(?,?,?,?,?,?,?,?)");
					ps.setInt(1,Integer.parseInt(textField.getText()));
					ps.setString(2, textField_1.getText());
					ps.setString(3, textField_2.getText());
					ps.setString(4, textField_3.getText());
					ps.setInt(5, Integer.parseInt(textField_4.getText()));
					ps.setInt(6, Integer.parseInt(textField_5.getText()));
					ps.setString(7, textField_6.getText());
					ps.setString(8, textField_7.getText());
					ps.execute();
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnNewButton.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		btnNewButton.setBounds(112, 251, 169, 23);
		contentPane.add(btnNewButton);
		
		JLabel lblStatus = new JLabel("Status");
		lblStatus.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		lblStatus.setBounds(10, 290, 271, 14);
		contentPane.add(lblStatus);
		
		JLabel label = new JLabel(" ");
		label.setBounds(112, 290, 46, 14);
		contentPane.add(label);
	}

}
