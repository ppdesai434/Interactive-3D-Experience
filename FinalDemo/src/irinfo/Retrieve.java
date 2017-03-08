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

public class Retrieve extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

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
		
		JLabel lblIrinforetrieve = new JLabel("IrInfo-Retrieve");
		lblIrinforetrieve.setForeground(Color.BLACK);
		lblIrinforetrieve.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		lblIrinforetrieve.setBounds(146, 11, 122, 14);
		panel.add(lblIrinforetrieve);
		
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
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(218, 66, 86, 20);
		panel.add(textField_1);
		
		JButton btnRetrieve = new JButton("Retrieve");
		btnRetrieve.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		btnRetrieve.setBounds(105, 146, 89, 23);
		panel.add(btnRetrieve);
		
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
