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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Delete extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Delete frame = new Delete();
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
	public Delete() {
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
		
		JLabel lblCardelete = new JLabel("Car-Delete");
		lblCardelete.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		lblCardelete.setBounds(162, 11, 83, 14);
		panel.add(lblCardelete);
		
		JLabel label_1 = new JLabel("Car No");
		label_1.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		label_1.setBounds(10, 39, 135, 14);
		panel.add(label_1);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(265, 33, 86, 20);
		panel.add(textField);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				PreparedStatement ps=null;
				Connection con=null;
				try
				{
					Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
					con = DriverManager.getConnection("JDBC:ODBC:projectdb");
					ps = con.prepareStatement("Delete from car where car_no=?");
					ps.setInt(1,Integer.parseInt(textField.getText()));
					ps.execute();
				}
				catch(ClassNotFoundException | SQLException e)
				{
					e.printStackTrace();
				}
			}
		});
		
		btnDelete.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
		btnDelete.setBounds(151, 177, 89, 23);
		panel.add(btnDelete);
		
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
