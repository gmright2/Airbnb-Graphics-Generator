package Interface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import com.restfb.BinaryAttachment;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;

import Classes.LerCSV;
import Classes.PhotoTag;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.UploadedMedia;
import javax.swing.JInternalFrame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Grafico extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel telaGrafico;
	
	private LerCSV csv;
	private String[] bairros;
	private Integer[] ofertas;
	private JTextArea txtStatus;
	private String graficoSelecionado = "";
	private JTextField userID;
	private JTextField userNome;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Grafico frame = new Grafico();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void verificarDiretorio(File diretorio){
		if (!diretorio.exists()) {
			diretorio.mkdir();
		}
	}
	
	public void receberCaminho(String caminhoReceber, String caminhoReceber2){
		this.csv = new LerCSV(caminhoReceber, caminhoReceber2);
		this.csv.gerarDados();
		bairros = csv.getBairrosI();
		ofertas = csv.getOfertas();
	}
	
	/**
	 * Create the frame.
	 */
	public Grafico() {
		
		File diretorio = new File("C:/graficos");
		verificarDiretorio(diretorio);
		setAlwaysOnTop(true);
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Grafico.class.getResource("/img/ico.png")));
		setTitle("Assistente");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 500);
		telaGrafico = new JPanel();
		telaGrafico.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(telaGrafico);
		telaGrafico.setLayout(null);
		
		JInternalFrame internalFrame = new JInternalFrame("Compartilhar no Twitter");
		internalFrame.setClosable(true);
		internalFrame.setFrameIcon(new ImageIcon(Grafico.class.getResource("/img/twitter.png")));
		try {
			internalFrame.setIcon(true);
		} catch (PropertyVetoException e1) {
			e1.printStackTrace();
		}
		
		JInternalFrame internalFrame2 = new JInternalFrame("Compartilhar no Facebook");
		internalFrame2.setVisible(false);
		internalFrame2.setFrameIcon(new ImageIcon(Grafico.class.getResource("/img/logofb_icon2.png")));
		internalFrame2.setClosable(true);
		internalFrame2.setBounds(170, 48, 424, 292);
		telaGrafico.add(internalFrame2);
		internalFrame2.getContentPane().setLayout(null);
		
		JLabel label = new JLabel("Escreva algo sobre isso:");
		label.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		label.setBounds(10, 69, 166, 21);
		internalFrame2.getContentPane().add(label);
		
		JLabel logoFB_COM = new JLabel("");
		logoFB_COM.setBounds(150, 11, 112, 22);
		internalFrame2.getContentPane().add(logoFB_COM);
		logoFB_COM.setIcon(new ImageIcon(Grafico.class.getResource("/img/facebook_com.png")));
		
		JTextArea mensagem = new JTextArea();
		mensagem.setWrapStyleWord(true);
		mensagem.setBounds(10, 93, 388, 89);
		internalFrame2.getContentPane().add(mensagem);
		
		JLabel marcacao = new JLabel("Deseja marcar algu\u00E9m:");
		marcacao.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		marcacao.setBounds(10, 193, 166, 21);
		internalFrame2.getContentPane().add(marcacao);
		
		userID = new JTextField();
		userID.setText("ID");
		userID.setBounds(181, 193, 86, 20);
		internalFrame2.getContentPane().add(userID);
		userID.setColumns(10);
		
		userNome = new JTextField();
		userNome.setText("Nome...");
		userNome.setColumns(10);
		userNome.setBounds(277, 193, 121, 20);
		internalFrame2.getContentPane().add(userNome);
		
		JButton postar = new JButton("Postar");
		postar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String tokenDeAcesso = "YOURACCESSTOKENHERE";
				
				@SuppressWarnings("deprecation")
				FacebookClient facebookClient = new DefaultFacebookClient(tokenDeAcesso);
				
				FileInputStream file;
				try {
					file = new FileInputStream(new File(graficoSelecionado));
				
				PhotoTag myTag = new PhotoTag(userID.getText(), userNome.getText());
				
				ArrayList<PhotoTag> myTagList = new ArrayList<PhotoTag>();
				myTagList.add(myTag);
				
				@SuppressWarnings("deprecation")
				FacebookType response = facebookClient.publish("me/photos", FacebookType.class, 
						BinaryAttachment.with("Gr�fico Nova York", file),
						Parameter.with("message", mensagem.getText()),
						Parameter.with("tags", myTagList));
				
				if (response != null)
					JOptionPane.showMessageDialog(Grafico.this, "Publicado com sucesso!");
				 else
					JOptionPane.showMessageDialog(Grafico.this, "N�o foi poss�vel fazer esta publica��o!");
				
				
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
		postar.setForeground(new Color(240, 248, 255));
		postar.setBackground(new Color(65, 105, 225));
		postar.setBounds(309, 229, 89, 23);
		internalFrame2.getContentPane().add(postar);
		
		JPanel sombra2 = new JPanel();
		sombra2.setBackground(new Color(211, 211, 211));
		sombra2.setBounds(8, 92, 392, 92);
		internalFrame2.getContentPane().add(sombra2);
		
		JPanel painelFB = new JPanel();
		painelFB.setBackground(new Color(65, 105, 225));
		painelFB.setBounds(0, 0, 408, 50);
		internalFrame2.getContentPane().add(painelFB);
		internalFrame.setBounds(170, 48, 424, 292);
		telaGrafico.add(internalFrame);
		internalFrame.getContentPane().setLayout(null);
		
		JLabel txtInternal = new JLabel("Escreva algo sobre isso:");
		txtInternal.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		txtInternal.setBounds(10, 72, 166, 21);
		internalFrame.getContentPane().add(txtInternal);
		
		txtStatus = new JTextArea();
		txtStatus.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (txtStatus.getText().length() >= 140) {
					e.consume();
					JOptionPane.showMessageDialog(Grafico.this, "Permitido apenas 140 caracteres!");
				}
			}
		});
		txtStatus.setBounds(10, 104, 388, 103);
		internalFrame.getContentPane().add(txtStatus);
		txtStatus.setColumns(10);
		
		internalFrame.setVisible(false);
		
		JButton tweetar = new JButton("Tweetar");
		tweetar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				internalFrame.dispose();
				Twitter twitter = new TwitterFactory().getInstance();
				File imagem = new File(graficoSelecionado);
				
				try {
					UploadedMedia media = twitter.uploadMedia(imagem);
					StatusUpdate update = new StatusUpdate(txtStatus.getText());
			        update.setMedia(imagem);
			        Status status = twitter.updateStatus(update);
			        JOptionPane.showMessageDialog(Grafico.this, "Tweeter publicado! \n ID: " + status.getId() + "\n Tipo da Imagem: " + media.getImageType());
				} catch (TwitterException e) {
					System.out.println("N�o foi poss�vel compartilhar. \n Tente mais tarde!");
					JOptionPane.showMessageDialog(Grafico.this, "N�o foi poss�vel compartilhar. \n Tente mais tarde!");
				}
			}
		});
		tweetar.setForeground(new Color(240, 248, 255));
		tweetar.setBackground(new Color(30, 144, 255));
		tweetar.setBounds(309, 218, 89, 23);
		internalFrame.getContentPane().add(tweetar);
		
		JLabel nick = new JLabel("TWITTER");
		nick.setForeground(Color.WHITE);
		nick.setFont(new Font("Century Gothic", Font.BOLD, 14));
		nick.setBounds(77, 0, 111, 60);
		internalFrame.getContentPane().add(nick);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(new Color(30, 144, 255));
		panel_4.setBounds(0, 0, 408, 60);
		internalFrame.getContentPane().add(panel_4);
		
		JLabel lengthChars = new JLabel("140");
		lengthChars.setBounds(272, 222, 27, 14);
		internalFrame.getContentPane().add(lengthChars);
		
		JPanel sombra = new JPanel();
		sombra.setBackground(new Color(211, 211, 211));
		sombra.setBounds(9, 103, 390, 106);
		internalFrame.getContentPane().add(sombra);
		
		JPanel exibirGrafico = new JPanel();
		exibirGrafico.setBounds(260, 77, 424, 307);
		telaGrafico.add(exibirGrafico);
		exibirGrafico.setLayout(new BorderLayout(0, 0));
		
		JLabel textGrafico = new JLabel("Gr\u00E1fico desejado:");
		textGrafico.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		textGrafico.setBounds(38, 92, 138, 21);
		telaGrafico.add(textGrafico);
		
		JButton opc1 = new JButton("Linha");
		opc1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				DefaultCategoryDataset linha = new DefaultCategoryDataset();
				
				for (int i = 0; i < csv.getQtdBairros(); i++) {
					if (ofertas[i] != null && bairros[i] != null) {
						linha.setValue(ofertas[i], "", bairros[i]);
					}
				}
				
				JFreeChart graficoLinha = ChartFactory.createLineChart3D("Ofertas de quartos por bairro (Nova York)", "", "", linha, PlotOrientation.VERTICAL, false, true, true);
				ChartPanel chartPanel = new ChartPanel(graficoLinha);
				
				exibirGrafico.removeAll();
				exibirGrafico.add(chartPanel, BorderLayout.CENTER);
				exibirGrafico.validate();
				
				try {
					ChartUtilities.saveChartAsJPEG(new java.io.File("C:/graficos/Linha.jpg"), graficoLinha, 1024, 780);
					graficoSelecionado = "C:/graficos/Linha.jpg";
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(Grafico.this, "Diret�rio da imagem n�o encontrado!");
				}
			}
		});
		opc1.setBounds(24, 146, 173, 41);
		telaGrafico.add(opc1);
		
		JButton opc2 = new JButton("Pizza");
		opc2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DefaultPieDataset pieDataset = new DefaultPieDataset();
				
				for (int i = 0; i < csv.getQtdBairros(); i++) {
					if (ofertas[i] != null && bairros[i] != null) {
						pieDataset.setValue(bairros[i] , ofertas[i]);
					}
				}
				
				JFreeChart graficoPizza = ChartFactory.createPieChart3D("Ofertas de quartos por bairro (Nova York)", pieDataset, true, true, false);
				final PiePlot3D plot = ( PiePlot3D ) graficoPizza.getPlot( );
				plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} - {1} - {2}")); 
				
				ChartPanel chartPanel = new ChartPanel(graficoPizza);
				exibirGrafico.removeAll();
				exibirGrafico.add(chartPanel, BorderLayout.CENTER);
				exibirGrafico.validate();
				
				try {
					ChartUtilities.saveChartAsJPEG(new java.io.File("C:/graficos/Pizza.jpg"), graficoPizza, 1024, 780);
					graficoSelecionado = "C:/graficos/Pizza.jpg";
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(Grafico.this, "Diret�rio da imagem n�o encontrado!");
				}
			}
		});
		opc2.setBounds(24, 223, 173, 41);
		telaGrafico.add(opc2);
		
		JButton opc3 = new JButton("3D");
		opc3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultCategoryDataset barra3D = new DefaultCategoryDataset();
				
				for (int i = 0; i < csv.getQtdBairros(); i++) {
					if (ofertas[i] != null && bairros[i] != null) {
						barra3D.setValue(ofertas[i], "", bairros[i]);
					}
				}
				
				JFreeChart grafico3D = ChartFactory.createBarChart3D("Ofertas de quartos por bairro (Nova York)", "", "", barra3D, PlotOrientation.VERTICAL, false, false, true);
				
				ChartPanel chartPanel = new ChartPanel(grafico3D);
				exibirGrafico.removeAll();
				exibirGrafico.add(chartPanel, BorderLayout.CENTER);
				exibirGrafico.validate();
				
				try {
					ChartUtilities.saveChartAsJPEG(new java.io.File("C:/graficos/3D.jpg"), grafico3D, 1024, 780);
					graficoSelecionado = "C:/graficos/3D.jpg";
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(Grafico.this, "Diret�rio da imagem n�o encontrado!");
				}
				
			}
		});
		opc3.setBounds(24, 296, 173, 41);
		telaGrafico.add(opc3);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 204, 204));
		panel.setBounds(0, 63, 217, 334);
		telaGrafico.add(panel);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(242, 36, 19, 408);
		telaGrafico.add(separator);
		
		JLabel textView = new JLabel("Visualiza\u00E7\u00E3o...");
		textView.setEnabled(false);
		textView.setBounds(260, 36, 87, 14);
		telaGrafico.add(textView);
		
		JButton butaoShare = new JButton("Compartilhar");
		butaoShare.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0)  {
				internalFrame.setVisible(true);
			}
		});
		butaoShare.setIcon(new ImageIcon(Grafico.class.getResource("/img/twitter_icon.png")));
		butaoShare.setForeground(new Color(255, 255, 255));
		butaoShare.setBackground(new Color(30, 144, 255));
		butaoShare.setBounds(482, 411, 160, 33);
		telaGrafico.add(butaoShare);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 192, 203));
		panel_1.setBounds(0, 0, 230, 10);
		telaGrafico.add(panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(255, 192, 203));
		panel_2.setBounds(242, 0, 230, 10);
		telaGrafico.add(panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(UIManager.getBorder("ComboBox.border"));
		panel_3.setBackground(new Color(255, 153, 153));
		panel_3.setBounds(482, 0, 212, 10);
		telaGrafico.add(panel_3);
		panel_3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton shareFB = new JButton("Compartilhar");
		shareFB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				internalFrame2.setVisible(true);
			}
		});
		shareFB.setIcon(new ImageIcon(Grafico.class.getResource("/img/fb_icon.png")));
		shareFB.setForeground(Color.WHITE);
		shareFB.setBackground(new Color(65, 105, 225));
		shareFB.setBounds(300, 411, 160, 33);
		telaGrafico.add(shareFB);
		
	}
}
