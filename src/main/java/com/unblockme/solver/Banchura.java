package com.unblockme.solver;

import java.util.ArrayList;

import com.unblockme.lib.Queue;
import com.unblockme.lib.Stack;

/**
 * Clase que hereda de Busqueda para implementar el algoritmo de b�squeda de
 * primero en anchura.
 */
public class Banchura extends Busqueda {
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
	 * Busca una soluci�n al puzzle utilizando el algoritmo de b�squeda primero en
	 * anchura.
	 * 
	 * @param initial El estado inicial del tablero
	 */
	public Banchura(Estado initial) {
		Queue<Rastreador> queue = new Queue<>();
		ArrayList<Estado> consumido = new ArrayList<>();

		duracion = System.currentTimeMillis();
		Rastreador sn = new Rastreador(initial, 0, null);
		queue.enqueue(sn);

		while (!queue.isEmpty()) {
			sn = queue.dequeue();
			if (consumido.contains(sn.estado))
				continue;
			if (!sn.estado.isEstadoFinal()) {
				expEstados++;
				consumido.add(sn.estado);
				for (Estado e : sn.estado.eAdyacentes()) {
					if (!consumido.contains(e))
						queue.enqueue(new Rastreador(e, sn.nMovimientos + 1, sn));
				}
			} else
				break;
		}

//   	Una vez completada la b�squeda pasa los valores para las variables
//      resoluble y solucion de la clase Busqueda
		if (queue.isEmpty()) {
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

	@Override
	public boolean isResoluble() {
		return resoluble;
	}

	@Override
	public long getDuracion() {
		return duracion;
	}

	@Override
	public int expEstados() {
		return expEstados;
	}

	@Override
	public int nMovimientos() {
		return nMovimientos;
	}

	@Override
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
