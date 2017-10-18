package Interface;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.FlowLayout;
import javax.swing.UIManager;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Menu extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel telaMenu;
	private final JPanel rodape = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu frame = new Menu();
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
	public Menu() {
		setAutoRequestFocus(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Menu.class.getResource("/img/ico.png")));
		setTitle("Assistente");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 500);
		telaMenu = new JPanel();
		telaMenu.setBackground(SystemColor.menu);
		telaMenu.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(telaMenu);
		telaMenu.setLayout(null);
		
		JButton butaoComecar = new JButton("COME\u00C7AR");
		butaoComecar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Leitura leitura = new Leitura();
				leitura.setVisible(true);
				Menu.this.dispose();
			}
		});
		butaoComecar.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		butaoComecar.setBounds(264, 420, 160, 30);
		telaMenu.add(butaoComecar);
		
		JLabel logoBnb = new JLabel("");
		logoBnb.setHorizontalAlignment(SwingConstants.CENTER);
		logoBnb.setIcon(new ImageIcon(Menu.class.getResource("/img/airbnb_logo.png")));
		logoBnb.setBounds(41, 87, 616, 254);
		telaMenu.add(logoBnb);
		rodape.setBackground(new Color(255, 153, 153));
		rodape.setBounds(0, 399, 694, 73);
		telaMenu.add(rodape);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 192, 203));
		panel.setBounds(0, 0, 230, 10);
		telaMenu.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 192, 203));
		panel_1.setBounds(242, 0, 230, 10);
		telaMenu.add(panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(UIManager.getBorder("ComboBox.border"));
		panel_2.setBackground(new Color(255, 153, 153));
		panel_2.setBounds(482, 0, 212, 10);
		telaMenu.add(panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
	}
}
