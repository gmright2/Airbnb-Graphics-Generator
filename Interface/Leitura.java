package Interface;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Font;
import java.awt.Color;
import javax.swing.UIManager;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

public class Leitura extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel telaLeitura;
	private JTextField caminho_arquivo;
	private String caminho;
	private String caminho2;
	private Grafico grafico;
	private JTextField caminho_arquivo2;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Leitura frame = new Leitura();
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
	public Leitura() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Leitura.class.getResource("/img/ico.png")));
		setBackground(SystemColor.menu);
		setTitle("Assistente");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 500);
		telaLeitura = new JPanel();
		telaLeitura.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(telaLeitura);
		telaLeitura.setLayout(null);
		
		caminho_arquivo = new JTextField();
		caminho_arquivo.setHorizontalAlignment(SwingConstants.LEFT);
		caminho_arquivo.setBounds(87, 334, 393, 28);
		telaLeitura.add(caminho_arquivo);
		caminho_arquivo.setColumns(10);
		
		JLabel textInserir = new JLabel("Insira o CSV 2:");
		textInserir.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		textInserir.setBounds(87, 309, 131, 14);
		telaLeitura.add(textInserir);
		
		caminho_arquivo2 = new JTextField();
		caminho_arquivo2.setHorizontalAlignment(SwingConstants.LEFT);
		caminho_arquivo2.setColumns(10);
		caminho_arquivo2.setBounds(87, 259, 393, 28);
		telaLeitura.add(caminho_arquivo2);
		
		JButton enviar = new JButton("Enviar");
		enviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (caminho_arquivo2.getText().equals("") || caminho_arquivo.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Campo CSV vazio. \n Selecionar um arquivo, por favor!");
				} else {
					grafico = new Grafico();
					grafico.setVisible(true);
					grafico.receberCaminho(caminho2, caminho);
					Leitura.this.dispose();
				}
			}
		});
		enviar.setBounds(490, 430, 95, 23);
		telaLeitura.add(enviar);
		
		JPanel listra = new JPanel();
		listra.setBackground(new Color(255, 153, 153));
		listra.setBounds(0, 407, 694, 65);
		telaLeitura.add(listra);
		
		JButton arquivo = new JButton("Selecionar");
		arquivo.setBackground(Color.PINK);
		arquivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Selecionar CSV");
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivo CSV", "csv");
				fileChooser.setFileFilter(filter);
				
				int retorno = fileChooser.showOpenDialog(Leitura.this);
				
				if (retorno == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					caminho = file.getPath();
					caminho_arquivo.setText(caminho);
				}
			}
		});
		arquivo.setBounds(490, 334, 95, 28);
		telaLeitura.add(arquivo);
		
		JLabel icon_document = new JLabel("");
		icon_document.setIcon(new ImageIcon(Leitura.class.getResource("/img/document_icon.png")));
		icon_document.setBounds(290, 43, 122, 156);
		telaLeitura.add(icon_document);
		
		JLabel txtInserir1 = new JLabel("Arquivo CSV 1:");
		txtInserir1.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		txtInserir1.setBounds(87, 234, 184, 14);
		telaLeitura.add(txtInserir1);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 192, 203));
		panel.setBounds(0, 0, 230, 10);
		telaLeitura.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 192, 203));
		panel_1.setBounds(242, 0, 230, 10);
		telaLeitura.add(panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(UIManager.getBorder("ComboBox.border"));
		panel_2.setBackground(new Color(255, 153, 153));
		panel_2.setBounds(482, 0, 212, 10);
		telaLeitura.add(panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton arquivo1 = new JButton("Selecionar");
		arquivo1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Selecionar CSV");
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivo CSV", "csv");
				fileChooser.setFileFilter(filter);
				
				int retorno = fileChooser.showOpenDialog(Leitura.this);
				
				if (retorno == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					caminho2 = file.getPath();
					caminho_arquivo2.setText(caminho2);
				}
			}
		});
		arquivo1.setBackground(Color.PINK);
		arquivo1.setBounds(490, 259, 95, 28);
		telaLeitura.add(arquivo1);
	}

	public String getCaminho2() {
		return caminho2;
	}

	public void setCaminho2(String caminho2) {
		this.caminho2 = caminho2;
	}
}
