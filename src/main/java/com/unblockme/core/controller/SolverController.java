package com.unblockme.core.controller;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.unblockme.solver.Baestrella;
import com.unblockme.solver.Banchura;
import com.unblockme.solver.Bprofundidad;
import com.unblockme.solver.Estado;
import com.unblockme.solver.Operador;
import com.unblockme.solver.utils.Utils;

@RestController
public class SolverController {

	static final Gson gson = new Gson();
	static Estado einicial = null;
	static char[][] efinal = null;
	
	private Map<Integer, char[][]> puzzlesMap = new HashMap<Integer, char[][]>(){{
		put(0, Utils.puzzle01);
		put(1, Utils.puzzle02);
		put(2, Utils.puzzle03);
	}};
	
	/*
	* POST - recibe un json con los bloques del puzzle.
	*/
	@PostMapping("/estado")
	public String setEstado(@RequestBody String json) {
		char[][] bloques = new char[6][6];
		String strBloques = json.replaceAll("[^a-zA-Z-]", "");

		try {
			int index = 0;
			for(int i =0; i<6;i++) {
				for (int j = 0; j < 6; j++) {
					bloques[i][j] = strBloques.charAt(index);
					index++;
				}
			}
			einicial = new Estado(bloques);
			return gson.toJson(Utils.bloques2dTo1d(bloques));

		}catch(Exception e) {
			String err = gson.toJson("No se puede leer el puzzle.");
			return err;
		}
	}
	
	/*
	* GET - Resuelve el puzzle y encuentra la solucion 
	*       utilizando el algoritmo BFS y devuelve los pasos.
	*/
	@GetMapping("/bfs")
	public String solveWithBfs() {
		Banchura ba = new Banchura(einicial);
		Iterable<Operador> solucion = ba.solucion();
		efinal = ba.getEstadoFinal();

		List<Operador> result = Utils.iteratorToList(solucion);
		return gson.toJson(result);
	}
	
	/*
	* GET - Resuelve el puzzle y encuentra la solucion 
	*       utilizando el algoritmo DFS y devuelve los pasos.
	*/
	@GetMapping("/dfs")
	public String solveWithDfs() {
		Bprofundidad bp = new Bprofundidad(einicial);
		Iterable<Operador> solucion = bp.solucion();
		efinal = bp.getEstadoFinal();

		List<Operador> result = Utils.iteratorToList(solucion);
		return gson.toJson(result);
	}
	
	/*
	* GET - Resuelve el puzzle y encuentra la solucion 
	*       utilizando el algoritmo A* y devuelve los pasos.
	*/
	@GetMapping("/aestar")
	public String solveWithAestar() {
		Baestrella bas = new Baestrella(einicial);
		Iterable<Operador> solucion = bas.solucion();
		efinal = bas.getEstadoFinal();
		
		List<Operador> result = Utils.iteratorToList(solucion);		
		return gson.toJson(result);
	}
	
	/*
	* GET - Devuelve el estado final. 
	*/
	@GetMapping("/estadoFinal")
	public String getEstadoFinal() {
		char[] efinal1d = Utils.bloques2dTo1d(efinal);
		return gson.toJson(efinal1d);
	}
	
	/*
	 * POST - Recibe un int con el id del puzzle y devuelve 
	 * 		  el estado correspondiente.
	 */
	@PostMapping("/setEstadoById")
	public String setEstadoById(@RequestBody String json) {
		
		try {
			int key = gson.fromJson(json, Integer.class);
			
			if (key >= 0) {
				char[][] bloques2d = puzzlesMap.get(key);
				char[] bloques1d = Utils.bloques2dTo1d(bloques2d);
				einicial = new Estado (bloques2d);
				return gson.toJson(bloques1d);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}






