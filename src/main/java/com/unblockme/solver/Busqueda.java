package com.unblockme.solver;
import com.unblockme.lib.Stack;


public abstract class Busqueda {
	protected boolean resoluble;
	protected int nMovimientos = -1;
	protected Stack<Operador> solucion;
	protected int expEstados = 0;
	protected long duracion = 0;
	protected char[][] estadoFinal;

		
	/**
	 *  Devuelve si el puzzle se puede resolver
	 *  @return true si se puede resolver
	 */
	protected abstract boolean isResoluble();

	/**
	 *  Numero de movimientos necesarios para llegar a la soluci�n
	 *  @return Dato de tipo entero
	 */
	protected abstract int nMovimientos();
	
	/**
	 *  Numero de estados visitados para llegar a la soluci�n
	 *  @return Dato de tipo entero
	 */
	protected abstract int expEstados();
	
	/**
	 *  Tiempo de ejecuci�n necesario para llegar a la soluci�n
	 *  @return Tiempo en tipo long
	 */	
    protected abstract long getDuracion();
    

	/**
	 *  Devuelve la soluci�n estado a estado
	 *  @return Un dato tipo iterable<Operador> con la soluci�n
	 */
    protected abstract Iterable<Operador> solucion();
    
    protected abstract char[][] getEstadoFinal();
    protected abstract void setEstadoFinal(char[][] estadoFinal);

}
