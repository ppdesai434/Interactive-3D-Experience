package irinfo;

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
import java.sql.SQLException;

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
		setTitle("IrInfo");
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
		
		JLabel lblIrinfodelete = new JLabel("IrInfo-Delete");
		lblIrinfodelete.setForeground(Color.BLACK);
		lblIrinfodelete.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		lblIrinfodelete.setBounds(146, 11, 122, 14);
		panel.add(lblIrinfodelete);
		
		JLabel label_1 = new JLabel("Motion");
		label_1.setForeground(Color.BLACK);
		label_1.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		label_1.setBounds(10, 44, 46, 14);
		panel.add(label_1);
		
		JLabel label_2 = new JLabel("Event");
		label_2.setForeground(Color.BLACK);
		label_2.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		label_2.setBounds(10, 69, 46, 14);
		panel.add(label_2);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(218, 41, 86, 20);
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
					ps = con.prepareStatement("Delete from irinfo where motion=?");
					ps.setString(1,textField.getText());
					ps.execute();
				}
				catch(ClassNotFoundException | SQLException e)
				{
					e.printStackTrace();
				}
			}
		});
		btnDelete.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		btnDelete.setBounds(105, 146, 89, 23);
		panel.add(btnDelete);
		
		JLabel label_3 = new JLabel("Status");
		label_3.setForeground(Color.BLACK);
		label_3.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		label_3.setBounds(10, 205, 46, 14);
		panel.add(label_3);
		
		JLabel label_4 = new JLabel(" ");
		label_4.setForeground(Color.BLACK);
		label_4.setBounds(121, 205, 46, 14);
		panel.add(label_4);
		
		JLabel label_5 = new JLabel("Where");
		label_5.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		label_5.setBounds(106, 46, 46, 14);
		panel.add(label_5);
	}

}
