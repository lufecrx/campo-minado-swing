package br.com.lufecrx.cm.visao;

import javax.swing.JFrame;

import br.com.lufecrx.cm.modelo.Tabuleiro;

@SuppressWarnings("serial")
public class TelaPrincipal extends JFrame{

	public TelaPrincipal() {
		Tabuleiro tabuleiro = new Tabuleiro(16, 30, 55);	
		add( new PainelTabuleiro(tabuleiro));
		
		
		setTitle("Campo Minado - lufecrx");
		setSize(690, 438);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
		
		
	}
	
	public static void main(String[] args) {
		new TelaPrincipal();
		
	}
	
}
