package GUI;

import interactive_game.AvatarTest;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Canvas;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import earth.EarthCoor;
import finaldemonstration.Ca_Reality;
import finaldemonstration.ObjLoad;
import finaldemonstration.ThreeDee;
import finaldemonstration.Vw_car;
import finaldemonstration.X;
import finaldemonstration.enginecar;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Window.Type;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Point;
import java.awt.Dimension;
import java.awt.Frame;

public class Interactive_3D_Experience extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interactive_3D_Experience frame = new Interactive_3D_Experience();
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
	public Interactive_3D_Experience() {
		setSize(new Dimension(20, 20));
		setResizable(false);
		setLocationByPlatform(true);
		setLocation(new Point(540, 200));
		setOpacity(1.0f);
		setUndecorated(true);
		setTitle("Interactive 3D Experience");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setAutoscrolls(true);
		contentPane.setRequestFocusEnabled(false);
		contentPane.setVerifyInputWhenFocusTarget(false);
		contentPane.setFocusable(false);
		contentPane.setBackground(new Color(143, 188, 143));
		contentPane.setForeground(new Color(102, 204, 102));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBackground(new Color(255, 255, 102));
		lblNewLabel.setIcon(new ImageIcon(Interactive_3D_Experience.class.getResource("/GUI/co.jpg")));
		lblNewLabel.setBounds(10, 11, 780, 331);
		contentPane.add(lblNewLabel);
		
		JButton btnCarlHouse = new JButton("Reality Based System");
		btnCarlHouse.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Ca_Reality a = new Ca_Reality(null);
				a.setDefaultCloseOperation(HIDE_ON_CLOSE);
				a.setVisible(true);
			}
		});
		btnCarlHouse.setBorder(null);
		btnCarlHouse.setBounds(44, 350, 128, 23);
		contentPane.add(btnCarlHouse);
		
		JButton btnCar = new JButton("Car(texture)");
		btnCar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Vw_car v = new Vw_car(null) ;
				v.setVisible(true);
				v.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			}
		});
		btnCar.setBounds(230, 428, 128, 23);
		contentPane.add(btnCar);
		
		JButton btnCar_1 = new JButton("Car w/o texture");
		btnCar_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				X ob = new X();
				ob.setVisible(true);
				ob.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			}
		});
		btnCar_1.setBounds(460, 428, 121, 23);
		contentPane.add(btnCar_1);
		
		JButton btnLiveGame = new JButton("Live Game");
		btnLiveGame.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		btnLiveGame.setBounds(367, 504, 107, 23);
		contentPane.add(btnLiveGame);
		
		JButton btnHelicopterRide = new JButton("Helicopter Ride");
		btnHelicopterRide.setBounds(630, 353, 128, 23);
		contentPane.add(btnHelicopterRide);
		
		JButton btnInterior = new JButton("Interior");
		btnInterior.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				enginecar e = new enginecar(null);
				e.setVisible(true);
				e.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

			}
		});
		btnInterior.setBounds(367, 394, 89, 23);
		contentPane.add(btnInterior);
	}
}
