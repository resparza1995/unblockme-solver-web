package com.unblockme.solver.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.unblockme.solver.Estado;
import com.unblockme.solver.Operador;

public class Utils {

	public static char[][] puzzle01 = { { 'a', 'a', '-', '-', '-', 'b' }, 
								 		{ 'c', '-', '-', 'd', '-', 'b' },
								 		{ 'c', 'X', 'X', 'd', '-', 'b' }, 
								 		{ 'c', '-', '-', 'd', '-', '-' }, 
								 		{ 'e', '-', '-', '-', 'f', 'f' },
								 		{ 'e', '-', 'g', 'g', 'g', '-' }, };
	
	public static char[][] puzzle02 = { { 'a', 'b', 'b', 'c', '-', '-' }, 
			 					 		{ 'a', '-', '-', 'c', '-', '-' },
			 					 		{ 'a', 'X', 'X', 'c', '-', '-' }, 
			 					 		{ '-', '-', 'd', 'e', 'e', 'e' }, 
			 					 		{ '-', '-', 'd', '-', '-', 'f' },
			 					 		{ '-', '-', 'g', 'g', 'g', 'f' }, };
	
	public static char[][] puzzle03 = { { 'a', 'a', 'b', 'c', '-', '-' }, 
			 					 		{ 'd', '-', 'b', 'c', '-', '-' },
			 					 		{ 'd', 'X', 'X', 'c', '-', '-' }, 
			 					 		{ 'd', '-', '-', '-', '-', '-' }, 
			 					 		{ '-', '-', '-', '-', '-', '-' },
			 					 		{ '-', '-', '-', 'f', 'f', 'f' }, };	

	public static List<Operador> iteratorToList (Iterable<Operador> iter) {
		
		List<Operador> result = new ArrayList<Operador>();
		
		iter.forEach(elem -> {
			result.add(elem);
		});
		return result;
	}
	
	/**
	 * Lee un fichero con el puzzle y lo convierte un char[][] con 
	 * los bloques del puzzle
	 * @param file
	 * @return char[][]
	 */
	public static char[][] fileToEstado(File file) {
		char[][] estadoArray = new char[6][6];
		
		try {
			Scanner sc = new Scanner(file);
			sc.next(); // La primera linea es el valor de dificultad del puzzle.
			
			for(int i=0; i<6; i++) {
				if (sc.hasNext()) {
					for(int j=0; j<6; j++) {
						char c = sc.next().charAt(0);
						estadoArray[i][j] = c;
					}
				}else break;
			}

			return estadoArray;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Convierte el array 2d en un array 1d.
	 * @param bloques2d
	 * @return char[]
	 */
	public static char[] bloques2dTo1d(char[][] bloques2d) {
		
		char[] bloques1d = new char[36];;
		int index = 0;
		
		if (bloques2d != null) {
			for (int i=0; i<bloques2d.length; i++) {	
				for (int j=0; j<bloques2d.length; j++) {
					bloques1d[index] = bloques2d[i][j];
					index++;
				}
			}
		}
		return bloques1d;
	}

}
