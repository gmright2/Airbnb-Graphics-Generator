package Classes;

import java.io.BufferedReader;
import java.io.FileReader;

import javax.swing.JOptionPane;

public class LerCSV {
	private String caminhoArquivo;
	private String caminhoArquivo2;
	private String[] bairros;
	private String[] bairrosI = new String[1000];;
	private Integer[] ofertas;
	private int qtdBairros = 0;
	int bronx = 0, brooklyn = 0, manhattan = 0, queens = 0, statenIsland = 0;

	public LerCSV(String caminho, String caminho2) {
		this.setCaminhoArquivo2(caminho2);
		this.setCaminhoArquivo(caminho);
		bairros = new String[1000000];
		ofertas = new Integer[1000000];
	}

	@SuppressWarnings("resource")
	public void gerarDados() {
		// BAIRROS
		BufferedReader conteudoCSV = null;
		String linha = "";
		try {
			conteudoCSV = new BufferedReader(new FileReader(caminhoArquivo));
			int j = 0, x = 0, soma = 1;
			while ((linha = conteudoCSV.readLine()) != null) {
				String[] percorrer = linha.split(",");
				if (percorrer[0].equals("neighbourhood_group")) {
					continue;
				} else {
					bairros[x] = percorrer[0];
				}
				x++;
			}
			for (int i = 0; i < bairros.length; i++) {
				if (bairros[i] != null) {
					if (bairros[i].equals(bairros[i + 1])) {
						soma++;
					} else {
						bairrosI[j] = bairros[i];
						ofertas[j] = soma;
						soma = 1;
						j++;
						qtdBairros++;
					}
				}
			}
			// OFERTAS
			BufferedReader conteudoCSV2 = null;
			conteudoCSV2 = new BufferedReader(new FileReader(caminhoArquivo2));
			int y = 0;
			while ((linha = conteudoCSV2.readLine()) != null) {
				String[] percorrer = linha.split(",");
				bairros[y] = percorrer[0];
				if (bairros[y].equals(bairrosI[0])) {
					bronx++;
				} else if(bairros[y].equals(bairrosI[1])){
					brooklyn++;
				}else if(bairros[y].equals(bairrosI[2])){
					manhattan++;
				}else if(bairros[y].equals(bairrosI[3])){
					queens++;
				}else if(bairros[y].equals(bairrosI[4])){
					statenIsland++;
				}
				y++;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao ler arquivo!");
			e.printStackTrace();
		}
	}

	public String getCaminhoArquivo() {
		return caminhoArquivo;
	}

	public void setCaminhoArquivo(String caminhoArquivo) {
		this.caminhoArquivo = caminhoArquivo;
	}

	public String[] getBairros() {
		return bairros;
	}

	public int getQtdBairros() {
		return qtdBairros;
	}

	public String[] getBairrosI() {
		return bairrosI;
	}
	
	public String getCaminhoArquivo2() {
		return caminhoArquivo2;
	}

	public void setCaminhoArquivo2(String caminhoArquivo2) {
		this.caminhoArquivo2 = caminhoArquivo2;
	}

	public Integer[] getOfertas(){
		this.ofertas = new Integer[5];
		this.ofertas[0] = bronx;
		this.ofertas[1] = brooklyn;
		this.ofertas[2] = manhattan;
		this.ofertas[3] = queens;
		this.ofertas[4] = statenIsland;
		return ofertas;
	}
}
