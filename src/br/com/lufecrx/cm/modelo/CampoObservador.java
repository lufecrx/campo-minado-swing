package br.com.lufecrx.cm.modelo;

@FunctionalInterface
public interface CampoObservador {

	public void acaoEvento(Campo campo, CampoEvento evento);
}
