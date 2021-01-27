package com.unblockme.solver;

/**
 * Representaci�n de la situaci�n de un operador o transici�n entre dos estados
 */
public class Operador {

	private char bloque;
	private int pasos;

	public Operador(char bloque, int pasos) {
		this.bloque = bloque;
		this.pasos = pasos;
	}

	public char getBloque() {
		return bloque;
	}

	public int getPasos() {
		return pasos;
	}
}
