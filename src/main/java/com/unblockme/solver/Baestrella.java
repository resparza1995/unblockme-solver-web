package com.unblockme.solver;

import java.util.ArrayList;
import java.util.Comparator;

import com.unblockme.lib.MinPQ;
import com.unblockme.lib.Stack;

/**
 * Clase que hereda de Busqueda para implementar el algoritmo de b�squeda A
 * estrella.
 */
public class Baestrella extends Busqueda {
	private class Rastreador {
		private Estado estado;
		private int nMovimientos;
		private int hn;
		private Rastreador padre;

		public Rastreador(Estado b, int m, int hn, Rastreador p) {
			this.estado = b;
			this.nMovimientos = m;
			this.hn = hn;
			this.padre = p;
		}

		public boolean equals(Object o) {
			if (!(o instanceof Rastreador))
				return false;
			Rastreador r = (Rastreador) o;

			for (int i = 0; i < 6; i++) {
				for (int j = 0; j < 6; j++) {
					if (estado.casillas[i][j] != r.estado.casillas[i][j])
						return false;
				}
			}
			return true;
		}
	}

	private class ComparatorR implements Comparator<Rastreador> {
		@Override
		public int compare(Rastreador r1, Rastreador r2) {
			return ((r1.nMovimientos + r1.estado.heuristica()) - (r2.nMovimientos + r2.estado.heuristica()));
		}
	}

	/**
	 * /** Busca una soluci�n al puzzle utilizando el algoritmo de b�squeda A*. f(n)
	 * = G(n)Coste nodo actual + H(n)Coste hasta el final A�adir a rastreador F(n)
	 * -> coste G(n) es nMovimientos H(n) Viene de estado,
	 * miRast.estado.heuristica() estadoinicial pq.min
	 * 
	 * @param inicial El estado inicial del tablero
	 */
	public Baestrella(Estado initial) {
		MinPQ<Rastreador> abiertos = new MinPQ<>(new ComparatorR());
		ArrayList<Rastreador> cerrados = new ArrayList<>();
		duracion = System.currentTimeMillis();
		Rastreador sn = new Rastreador(initial, 0, initial.heuristica(), null);
		abiertos.insert(sn);

		while (!abiertos.isEmpty()) {
			sn = abiertos.delMin();
			if (cerrados.contains(sn))
				continue;
			if (!sn.estado.isEstadoFinal()) {
				expEstados++;
				cerrados.add(sn);
				for (Estado e : sn.estado.eAdyacentes()) {
					if (cerrados.contains(e)) {
						int costeA = sn.estado.heuristica() + sn.nMovimientos;
						int costeB = e.heuristica() + sn.nMovimientos + 1;
						if (costeB < costeA) {
							abiertos.insert(new Rastreador(e, sn.nMovimientos + 1, e.heuristica(), sn));
						}
					} else {
						abiertos.insert(new Rastreador(e, sn.nMovimientos + 1, e.heuristica(), sn));
					}
				}
			} else
				break;
		}
//   	Una vez completada la b�squeda pasa los valores para las variables
//      resoluble y solucion de la clase Busqueda
		if (abiertos.isEmpty()) {
			resoluble = false;
			return;
		}

		duracion = System.currentTimeMillis() - duracion;

		Rastreador prev = sn;
		solucion = new Stack<Operador>();

		while (prev != null) {
			if (prev.estado.isEstadoFinal()) setEstadoFinal(prev.estado.casillas);
			solucion.push(prev.estado.getOperador());
			nMovimientos++;
			prev = prev.padre;
		}
		resoluble = true;
	}
	
	public long getDuracion() {
		return duracion;
	}

	public int expEstados() {
		return expEstados;
	}

	public boolean isResoluble() {
		return resoluble;
	}

	public int nMovimientos() {
		return nMovimientos;
	}

	public Iterable<Operador> solucion() {
		if (!resoluble)
			return null;
		return solucion;
	}

	public char[][] getEstadoFinal() {
		return estadoFinal;
	}

	public void setEstadoFinal(char[][] estadoFinal) {
		this.estadoFinal = estadoFinal;
	}

}
