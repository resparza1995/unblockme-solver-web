package com.unblockme.core;

import java.io.File;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import com.unblockme.solver.Baestrella;
import com.unblockme.solver.Banchura;
import com.unblockme.solver.Bprofundidad;
import com.unblockme.solver.Estado;
import com.unblockme.solver.Operador;

public class TestUnblockme {
	
	private Estado fileToEstado(File file) {
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

			return new Estado(estadoArray);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	//@Test
	public void busquedaAnchura() {
		File file = new File("puzzles/unblockme/Principiante-01.puzzle");
		Estado einicial = fileToEstado(file);
		
		Banchura ba = new Banchura(einicial);
		Iterable<Operador> solucion = ba.solucion();
		
		System.out.println("### Movimientos hasta la solucion ###");
		solucion.forEach(step -> {
			System.out.println("Bloque: "+step.getBloque()+", pasos: "+step.getPasos());
		});
	}
	
	//@Test
	public void busquedaProfundidad() {
		File file = new File("puzzles/unblockme/Principiante-01.puzzle");
		Estado einicial = fileToEstado(file);
		
		Bprofundidad ba = new Bprofundidad(einicial);
		Iterable<Operador> solucion = ba.solucion();
		
		System.out.println("### Movimientos hasta la solucion ###");
		solucion.forEach(step -> {
			System.out.println("Bloque: "+step.getBloque()+", pasos: "+step.getPasos());
		});
	}
	
	//@Test
	public void busquedaAestrella() {
		File file = new File("puzzles/unblockme/Principiante-01.puzzle");
		Estado einicial = fileToEstado(file);
		
		Baestrella ba = new Baestrella(einicial);
		Iterable<Operador> solucion = ba.solucion();
		
		System.out.println("### Movimientos hasta la solucion ###");
		solucion.forEach(step -> {
			System.out.println("Bloque: "+step.getBloque()+", pasos: "+step.getPasos());
		});
	}
	
}
