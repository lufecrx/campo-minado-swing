package br.com.lufecrx.cm.modelo;

public class ResultadoEvento {

	private final boolean resultado;

	ResultadoEvento(boolean resultado) {
		this.resultado = resultado;
	}

	public boolean ganhou() {
		return resultado;
	}	
	
}
