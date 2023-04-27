package br.com.lufecrx.cm.visao;

import java.awt.GridLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import br.com.lufecrx.cm.modelo.Tabuleiro;

@SuppressWarnings("serial")
public class PainelTabuleiro extends JPanel{

	public PainelTabuleiro(Tabuleiro tabuleiro) {
		
		setLayout(new GridLayout(tabuleiro.getLinhas(), tabuleiro.getColunas()));
		
		
		tabuleiro.getCampos().forEach(c -> add(new BotaoCampo(c)));
		tabuleiro.registrarObservador(e -> {
			
			SwingUtilities.invokeLater(() -> {
				if(e.ganhou()) {
					JOptionPane.showMessageDialog(this, "Parabéns, você ganhou!!");
				} else {
					JOptionPane.showMessageDialog(this, "Sinto muito, você perdeu!");				
				}
				
				tabuleiro.reiniciar();
			});
		});
	}
	
}
