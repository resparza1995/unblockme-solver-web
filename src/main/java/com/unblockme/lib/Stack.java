package com.unblockme.lib;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Stack<Item> implements Iterable<Item> { 

	private int N;          // tama�o de la pila
    private Node first;     // inicio de la pila

    private class Node {
        private Item item;
        private Node next;
    }
    
    /**
     * Crea una pila nueva (vac�a).
     */
	public Stack() {
		first = null;
		N = 0;		
	}	
	
	/**
     * Devuelve si est� vacia
     */
	public boolean isEmpty() {
		return size() == 0;
	}
	
	/**
     * Devuelve el n�mero de elemenos.
     */
	public int size() {
		return N;
	}
	
	/**
     * Introduce un elemento al inicio de la pila.
     */
	public void push(Item item) {
		Node oldfirst = first;
		first = new Node();
		first.item = item;
		first.next = oldfirst;
		
		N++;
	}
	
	/**
     * Elimina y devuelve un elemento de la pila (LIFO)
     * @throws java.util.NoSuchElementException si la pila est� vacia.
     */
	public Item pop() {
		if (isEmpty()) throw new NoSuchElementException("Stack underflow");
		Item item = first.item;
		first = first.next;
		N--;		
		return item;
	}
	

	/**
	 * Devuelve si la pila contiene un elemento espec�fico
     */
	public boolean contains(Item item) {
		for (Item it: this) {
			if (it.equals(item)) return true;
		}
		
		return false;
	}
	
	/**
     * Devuelve un objeto tipo iterator que iterar� sobre todos los elementos  
     * de la pila en orden LIFO.
     * <p>
     * Remove es opcional por lo que no se implementar� en este caso
     */
	public Iterator<Item> iterator() {
		return new ListIterator();
	}
	
    private class ListIterator implements Iterator<Item> {
    	private Node current = first;

    	public boolean hasNext() { return current != null;						}
    	public void remove() 	 { throw new UnsupportedOperationException();   }
		
		public Item next() { 
			if (!hasNext()) throw new NoSuchElementException();
			Item item = current.item;
			current = current.next;
			return item;
		}
    }
}
