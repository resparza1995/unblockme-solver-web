package com.unblockme.lib;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Queue<Item> implements Iterable<Item> { 

	private int N;          // tama�o de la cola
    private Node first;     // inicio de la cola
    private Node last;     //  fin de la cola

    private class Node {
        private Item item;
        private Node next;
    }
    

    /**
      * Crea una nueva cola (vac�a).
      */
	public Queue() {
		first = null;
		last = null;
		N = 0;		
	}	
	
	/**
     * Devuelve si la cola est� vac�a.
     */
	public boolean isEmpty() {
		return size() == 0;
	}
	
	/**
     * Devuelve el tama�o actual de la cola
     */
	public int size() {
		return N;
	}
	
	/**
     * A�ade un elemento al final de la cola.
     */
	public void enqueue(Item item) {
		Node oldlast = last;
		last = new Node();
		last.item = item;
		last.next = null;
		
        if (isEmpty()) first = last;        
        else oldlast.next = last;
        
		N++;
	}
	
	/**
     * Elimina y devuelve un elemento de la pila (FIFO)
     * @throws java.util.NoSuchElementException si la cola est� vac�a.
     */
	public Item dequeue() {
		if (isEmpty()) throw new NoSuchElementException("Queue underflow");
		Item item = first.item;
		first = first.next;
		N--;
		if (isEmpty()) last = null;
		return item;
	}
	
	/**
     * Devuelve un objeto tipo iterator que iterar� sobre todos los elementos  
     * de la pila en orden FIFO.
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
