package com.unblockme.core.controller;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.unblockme.solver.*;
import com.unblockme.solver.utils.Utils;

@RestController
public class SolverController {

	static final Gson gson = new Gson();
	static Estado einicial = null;
	static char[][] efinal = null;
	
	@PostMapping("/estado")
	public String setEstado(@RequestBody String json) {
		String strBloques = gson.fromJson(json, String.class);
		char[][] bloques = new char[6][6];
		strBloques = strBloques.substring(1);
		strBloques = strBloques.replaceAll(" ", "");
		strBloques = strBloques.replaceAll("\n", "");
		int index = 0;

		try {
			for(int i =0; i<6;i++) {
				for (int j=0; j<6; j++) {
					bloques[i][j] = strBloques.charAt(index);
					index++;	
				}
			}
			einicial = new Estado (bloques);
			return gson.toJson(bloques);
			
		}catch(Exception e) {
			String err = gson.toJson("No se puede leer el puzzle.");
			return err;
		}
	}
	
	@GetMapping("/bfs")
	public String solveWithBfs() {
		Banchura ba = new Banchura(einicial);
		Iterable<Operador> solucion = ba.solucion();
		efinal = ba.getEstadoFinal();

		List<Operador> result = Utils.iteratorToList(solucion);
		return gson.toJson(result);
	}
	
	@GetMapping("/dfs")
	public String solveWithDfs() {
		Bprofundidad bp = new Bprofundidad(einicial);
		Iterable<Operador> solucion = bp.solucion();
		efinal = bp.getEstadoFinal();

		List<Operador> result = Utils.iteratorToList(solucion);
		return gson.toJson(result);
	}
	
	@GetMapping("/aestar")
	public String solveWithAestar() {
		Baestrella bas = new Baestrella(einicial);
		Iterable<Operador> solucion = bas.solucion();
		efinal = bas.getEstadoFinal();
		
		List<Operador> result = Utils.iteratorToList(solucion);		
		return gson.toJson(result);
	}
	
	@GetMapping("/estadoFinal")
	public String getEstadoFinal() {
		char[] efinal1d = new char[36];
		int index = 0;
		
		for (int i=0; i<efinal.length; i++) {	
			for (int j=0; j<efinal.length; j++) {
				efinal1d[index] = efinal[i][j];
				index++;
			}
		}
		return gson.toJson(efinal1d);
	}

}





