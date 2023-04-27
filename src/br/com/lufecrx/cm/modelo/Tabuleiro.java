package br.com.lufecrx.cm.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Tabuleiro implements CampoObservador {

	private final int linhas;
	private final int colunas;
	private final int minas;

	private final List<Campo> campos = new ArrayList<>();
	private final List<Consumer<ResultadoEvento>> observadores = new ArrayList<>();

	public Tabuleiro(int linhas, int colunas, int minas) {
		this.linhas = linhas;
		this.colunas = colunas;
		this.minas = minas;

		gerarCampos();
		associarVizinhos();
		sortearMinas();
	}
	
	@Override
	public void acaoEvento(Campo campo, CampoEvento evento) {		
		if(evento == CampoEvento.EXPLODIR) {
			mostrarTodasMinas();
			notificarObservadores(false);
		} else if(objetivoAlcancado()) {
			notificarObservadores(true);
		}
	}
	
	public void registrarObservador(Consumer<ResultadoEvento> observador) {
		observadores.add(observador);
	}

	public void abrir(int linha, int coluna) {
		try {
			campos.parallelStream()
			.filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
			.findFirst()
			.ifPresent(c -> c.abrir());
		} catch (Exception e) {
			
		}
	}

	public void alternarMarcacao(int linha, int coluna) {
		campos.parallelStream()
				.filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
				.findFirst()
				.ifPresent(c -> c.alternarMarcacao());
	}

	public void reiniciar() {
		campos.stream().forEach(c -> c.reiniciarJogo());
		sortearMinas();
	}

	public boolean objetivoAlcancado() {
		return campos.stream().allMatch(c -> c.objetivoAlcancado());
	}
	
	public List<Campo> getCampos() {
		return campos;
	}
	
	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}

	private void sortearMinas() {
		long minasArmadas = 0;
		Predicate<Campo> camposMinados = c -> c.isMinado();
		
		do {
			minasArmadas = campos.stream().filter(camposMinados).count();
			int aleatorio = (int) (Math.random() * campos.size());
			if (minasArmadas < this.minas) {
				campos.get(aleatorio).minar();
			}
		} while (minasArmadas < this.minas);
	}

	// Percorrer a lista 2 vezes e tentar associar todos os campos
	private void associarVizinhos() {
		for (Campo c1 : campos) {
			for (Campo c2 : campos) {
				// O método adicionarVizinho() vai definir quem pode ou não ser vizinho
				c1.adicionarVizinho(c2);
			}
		}
	}

	private void gerarCampos() {
		for (int lin = 0; lin < linhas; lin++) {
			for (int col = 0; col < colunas; col++) {
	
				Campo campo = new Campo(lin, col);
				
				campo.registrarObservador(this);
				campos.add(campo);
			}
		}
	}
	
	private void notificarObservadores(Boolean resultado) {
		observadores.stream().forEach(o -> o.accept(new ResultadoEvento(resultado)));
	}
	
	// quando o jogador perder
	private void mostrarTodasMinas() {
		campos.stream()
			.filter(c -> c.isMinado())
			.filter(c -> !c.isMarcado())
			.forEach(c -> c.setAberto(true));
	}
	
}
