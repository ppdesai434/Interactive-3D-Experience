package parts;

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

public class Delete extends JFrame {

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
		setTitle("Parts");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 360);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.BLACK, Color.BLACK, null, null));
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBounds(0, 0, 434, 315);
		contentPane.add(panel);
		
		JLabel lblPartsdelete = new JLabel("Parts-Delete");
		lblPartsdelete.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		lblPartsdelete.setBounds(171, 0, 110, 14);
		panel.add(lblPartsdelete);
		
		JLabel label_1 = new JLabel("Part No");
		label_1.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		label_1.setBounds(10, 37, 271, 14);
		panel.add(label_1);
		
		JLabel label_2 = new JLabel("Name");
		label_2.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		label_2.setBounds(10, 62, 271, 14);
		panel.add(label_2);
		
		JLabel label_3 = new JLabel("Category");
		label_3.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		label_3.setBounds(10, 87, 271, 14);
		panel.add(label_3);
		
		JLabel label_4 = new JLabel("Company");
		label_4.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		label_4.setBounds(10, 112, 271, 14);
		panel.add(label_4);
		
		JLabel label_5 = new JLabel("Quantity");
		label_5.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		label_5.setBounds(10, 137, 271, 14);
		panel.add(label_5);
		
		JLabel label_6 = new JLabel("Price");
		label_6.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		label_6.setBounds(10, 162, 271, 14);
		panel.add(label_6);
		
		JLabel label_7 = new JLabel("Car No");
		label_7.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		label_7.setBounds(10, 187, 271, 14);
		panel.add(label_7);
		
		JLabel label_8 = new JLabel("Sub Part");
		label_8.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		label_8.setBounds(10, 212, 271, 14);
		panel.add(label_8);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(195, 34, 86, 20);
		panel.add(textField);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(195, 59, 86, 20);
		panel.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(195, 84, 86, 20);
		panel.add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(195, 109, 86, 20);
		panel.add(textField_3);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(195, 134, 86, 20);
		panel.add(textField_4);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(195, 159, 86, 20);
		panel.add(textField_5);
		
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(195, 184, 86, 20);
		panel.add(textField_6);
		
		textField_7 = new JTextField();
		textField_7.setColumns(10);
		textField_7.setBounds(195, 209, 86, 20);
		panel.add(textField_7);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		btnDelete.setBounds(112, 251, 169, 23);
		panel.add(btnDelete);
		
		JLabel label_9 = new JLabel("Status");
		label_9.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		label_9.setBounds(10, 290, 271, 14);
		panel.add(label_9);
		
		JLabel label_10 = new JLabel(" ");
		label_10.setBounds(112, 290, 46, 14);
		panel.add(label_10);
		
		JLabel label_11 = new JLabel("Where");
		label_11.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		label_11.setBounds(95, 39, 46, 14);
		panel.add(label_11);
	}

}
