package com.unblockme.solver;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Representaci�n de la situaci�n de un tablero en un instante del juego. -
 * heuristica() Cuantos bloques tiene por medio XX hasta el objetivo.
 * 
 */
public class Estado {

	char[][] casillas;
	private int N;
	char ultimoBloqueMovido = '?';
	int pasosMovidos = 0;
	private int heuristica = -1;
	private Operador op = new Operador(ultimoBloqueMovido, pasosMovidos);

	/**
	 * Constructor del estado (tablero de N x N) bloques [I][J] = bloque en fila I,
	 * columna J)
	 */
	public Estado(char[][] bloques) {
		// Constructor del estado (tablero de N x N)
		// bloques [I][J] = bloque en fila I, columna J)
		N = bloques.length;
		this.casillas = new char[N][N];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				this.casillas[i][j] = bloques[i][j];
	}

	/**
	 * Constructor del estado (tablero de N x N) bloques [I][J] = bloque en fila I,
	 * columna J) el char bloque representa el �ltimo bloque movido el entero
	 * pasos representa el n�mero de casillas desplazado
	 */
	public Estado(char[][] bloques, char bloque, int pasos) {
		this(bloques);
		// this.casillas=bloques;
		this.ultimoBloqueMovido = bloque;
		this.pasosMovidos = pasos;
		op = new Operador(ultimoBloqueMovido, pasosMovidos);
	}

	public int dimension() {
		return N; // tama�o del lado del cuadrado
	}

	/**
	 * Si el valor ha sido calculado, devuelve el valor en caso contrario lo calcula
	 * y lo devuelve
	 * 
	 * @return el dato de la heur�stica para este Estado n�mero de bloques entre
	 *         XX y final
	 */
	public int heuristica() {

		if (heuristica != -1)
			return heuristica;
		if (isEstadoFinal())
			return 0;
		int c = 0;
		char valor;
		int x = (N / 2) - 1, y = 0;
		for (int j = 0; j < N; j++) {
			if (casillas[x][j] == 'X') {
				y = j + 1;
			}
		}

		for (int i = y; i < N; i++) {
			if (casillas[x][i] != '-')
				c++;
		}
		heuristica = c;
		return c;
	}

	/**
	 * Comprueba si ha llegado al final del juego
	 * 
	 * @return
	 */
	public boolean isEstadoFinal() {
		if (casillas[(dimension() / 2) - 1][dimension() - 1] == 'X')
			return true;
		else
			return false;
	}

	private char[][] copiarCasillas() {
		char[][] array = new char[N][N];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				array[i][j] = casillas[i][j];
		return array;
	}

	/**
	 * Calcula todos los estados posibles partiendo del actual
	 * 
	 * @return Un objeto tipo iterable con todos los estados adyacentes
	 */
	public Iterable<Estado> eAdyacentes() {
		Stack<Estado> eAdyacentes = new Stack<>();

		int tempi = 0, tempj = 0;
		char[][] casillasMirror = copiarCasillas();
		char blq;
		int lngh = 0, lngv = 0, pasos = 0;
		ArrayList<Character> movibles = new ArrayList<>();
		Estado estTemp;

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				blq = casillas[i][j];
				if (blq != '-' && !movibles.contains(blq))
					movibles.add(blq);
			}
		}
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				blq = casillas[i][j];
				lngh = 0;
				lngv = 0;
				pasos = 0;
				if (blq != '-' && movibles.contains(blq)) {
					tempj = j;
					tempi = i;
					while (casillas[i][tempj] == blq) {

						tempj++;
						lngh++;
						if (tempj == N)
							break;
					}
					while (casillas[tempi][j] == blq) {
						tempi++;
						lngv++;
						if (tempi == N)
							break;
					}
					tempj = j;
					tempi = i;
					// HORIZONTAL
					if (lngh > 1) {
						tempj += lngh;
						while (tempj < N && (casillas[i][tempj] == '-' || casillas[i][tempj] == blq)) {
							if (casillas[i][tempj] == '-') {
								pasos++;
								for (int k = 0; k < lngh; k++) {
									swap(casillasMirror, i, i, tempj--, tempj);

								}
								estTemp = new Estado(casillasMirror, blq, pasos);
								eAdyacentes.add(estTemp);
								tempj += lngh + 1;
							}
						}
						casillasMirror = copiarCasillas();
						tempj = j - 1;
						pasos = 0;
						if (tempj >= 0) {
							while (tempj >= 0 && (casillas[i][tempj] == '-' || casillas[i][tempj] == blq)) {
								if (casillas[i][tempj] == '-') {
									pasos--;
									for (int k = 0; k < lngh; k++) {
										swap(casillasMirror, i, i, tempj++, tempj);
									}
									estTemp = new Estado(casillasMirror, blq, pasos);
									eAdyacentes.add(estTemp);
									tempj -= lngh + 1;
								}
							}
						}
						movibles.remove((Object) blq);
						pasos = 0;
					}
					// VERTICAL
					else if (lngv > 1) {
						tempi += lngv;
						while (tempi < N && (casillas[tempi][j] == '-' || casillas[tempi][j] == blq)) {
							if (casillas[tempi][j] == '-') {
								pasos++;
								for (int k = 0; k < lngv; k++) {
									swap(casillasMirror, tempi--, tempi, j, j);
								}
								estTemp = new Estado(casillasMirror, blq, pasos);
								eAdyacentes.add(estTemp);
								tempi += lngv + 1;
							}
						}
						casillasMirror = copiarCasillas();
						tempi = i - 1;
						pasos = 0;
						if (tempi >= 0) {
							while (tempi >= 0 && (casillas[tempi][j] == '-' || casillas[tempi][j] == blq)) {
								if (casillas[tempi][j] == '-') {
									pasos--;
									for (int k = 0; k < lngv; k++) {
										swap(casillasMirror, tempi++, tempi, j, j);
									}
									estTemp = new Estado(casillasMirror, blq, pasos);
									eAdyacentes.add(estTemp);
									tempi -= lngv + 1;
								}
							}
						}
						movibles.remove((Object) blq);
						pasos = 0;
					}
					casillasMirror = copiarCasillas();
				}
			}
		}
		return eAdyacentes;
	}

	/**
	 * representaci�n del estado en formato texto
	 * 
	 */
	public String toString() {
		String s = ultimoBloqueMovido + ", " + pasosMovidos + "\n";
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++)
				s += casillas[i][j] + " ";

			s += "\n";
		}

		return s;
	}

	public Operador getOperador() {
		return op;
	}

	/**
	 * Intercambia los valores en un array.
	 */
	private void swap(char[][] array, int row1, int row2, int col1, int col2) {
		char temp = array[row1][col1];
		array[row1][col1] = array[row2][col2];
		array[row2][col2] = temp;
	}

	/**
	 * Comprueba si un estado es igual a otro
	 * 
	 */
	public boolean equals(Object o) {
		if (!(o instanceof Estado))
			return false;
		Estado a = (Estado) o;

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (casillas[i][j] != a.casillas[i][j])
					return false;
			}
		}
		return true;
	}
}