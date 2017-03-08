package car;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Retrieve extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Retrieve frame = new Retrieve();
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
	public Retrieve() {
		setTitle("Car");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setForeground(Color.LIGHT_GRAY);
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.BLACK, Color.BLACK, null, null));
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBounds(0, 0, 434, 262);
		contentPane.add(panel);
		
		JLabel lblCarretrieve = new JLabel("Car-Retrieve");
		lblCarretrieve.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		lblCarretrieve.setBounds(162, 11, 104, 14);
		panel.add(lblCarretrieve);
		
		JLabel label_1 = new JLabel("Car No");
		label_1.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		label_1.setBounds(10, 39, 135, 14);
		panel.add(label_1);
		
		JLabel label_2 = new JLabel("Car Make");
		label_2.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		label_2.setBounds(10, 64, 256, 14);
		panel.add(label_2);
		
		JLabel label_3 = new JLabel("Car Model");
		label_3.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		label_3.setBounds(10, 92, 256, 14);
		panel.add(label_3);
		
		JLabel label_4 = new JLabel("Car Varient");
		label_4.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		label_4.setBounds(10, 117, 256, 14);
		panel.add(label_4);
		
		JLabel label_5 = new JLabel("File Name");
		label_5.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		label_5.setBounds(10, 146, 256, 14);
		panel.add(label_5);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(265, 33, 86, 20);
		panel.add(textField);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(265, 58, 86, 20);
		panel.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(264, 86, 86, 20);
		panel.add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(265, 111, 86, 20);
		panel.add(textField_3);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(264, 140, 86, 20);
		panel.add(textField_4);
		
		JButton btnRetrieve = new JButton("Retrieve");
		btnRetrieve.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Connection con=null;
				PreparedStatement ps = null;
				ResultSet rs=null;
				try
				{
					Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
					con = DriverManager.getConnection("JDBC:ODBC:projectdb");
					ps = con.prepareStatement("select * from car where car_no=?");
					ps.setInt(1,Integer.parseInt(textField.getText()));
					rs = ps.executeQuery();
					rs.next();
					textField_1.setText(rs.getString(2));
					textField_2.setText(rs.getString(3));
					textField_3.setText(rs.getString(4));
					textField_4.setText(rs.getString(5));
					//textField_3.setText(rs.getString());
					
					
				//	Statement s=con.createStatement();
				//	ResultSet rs=s.executeQuery("select * from car where car_no=?");
				}
				catch(ClassNotFoundException | SQLException e)
				{
					e.printStackTrace();
				}
			}
		});
		btnRetrieve.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
		btnRetrieve.setBounds(151, 177, 89, 23);
		panel.add(btnRetrieve);
		
		JLabel label_6 = new JLabel("Status");
		label_6.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		label_6.setBounds(10, 224, 87, 14);
		panel.add(label_6);
		
		JLabel label_7 = new JLabel(" ");
		label_7.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		label_7.setBounds(107, 224, 159, 14);
		panel.add(label_7);
		
		JLabel label_8 = new JLabel("Where");
		label_8.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		label_8.setBounds(145, 36, 53, 14);
		panel.add(label_8);
	}

}
