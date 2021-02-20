package com.unblockme.solver;

import java.util.ArrayList;

import com.unblockme.lib.Stack;

/**
 * Clase que hereda de Busqueda para implementar el algoritmo de busqueda de
 * primero en profundidad.
 */
public class Bprofundidad extends Busqueda {
	private class Rastreador {
		private Estado estado;
		private int nMovimientos;
		private Rastreador padre;

		public Rastreador(Estado b, int m, Rastreador p) {
			this.estado = b;
			this.nMovimientos = m;
			this.padre = p;
		}
	}

	/**
	 * Busca una solucion al puzzle utilizando el algoritmo de busqueda primero en
	 * profundidad.
	 * 
	 * @param inicial El estado inicial del tablero
	 */

	public Bprofundidad(Estado inicial) {
		Stack<Rastreador> stack = new Stack<>();
		ArrayList<Estado> cerrados = new ArrayList<>();

		duracion = System.currentTimeMillis();

		Rastreador sn = new Rastreador(inicial, 0, null);
		stack.push(sn);

		while (!stack.isEmpty()) {
			sn = stack.pop();
			if (cerrados.contains(sn.estado)) {
				continue;
			}
			if (!sn.estado.isEstadoFinal()) {
				expEstados++;
				cerrados.add(sn.estado);

				for (Estado e : sn.estado.eAdyacentes()) {
					if (!cerrados.contains(e)) {
						stack.push(new Rastreador(e, sn.nMovimientos + 1, sn));
					}
				}
			} else
				break;
		}
//   	Una vez completada la busqueda pasa los valores para las variables
//      resoluble y solucion de la clase Busqueda
		if (stack.isEmpty()) {
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

	@Override
	public char[][] getEstadoFinal() {
		return estadoFinal;
	}

	@Override
	public void setEstadoFinal(char[][] estadoFinal) {
		this.estadoFinal = estadoFinal;
	}
}
