package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JButton;

import finaldemonstration.X;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Inter extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Inter frame = new Inter();
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
	public Inter() {
		setUndecorated(true);
		setTitle("Interactive 3D Experience");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(107, 142, 35));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnCarWoTexture = new JButton("Car w/o texture");
		btnCarWoTexture.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				X ne = new X();
				ne.setVisible(true);
				ne.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			}
		});
		btnCarWoTexture.setBounds(235, 117, 124, 23);
		contentPane.add(btnCarWoTexture);
	}
}
