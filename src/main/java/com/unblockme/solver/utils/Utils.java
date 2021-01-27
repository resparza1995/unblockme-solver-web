package com.unblockme.solver.utils;

import java.util.ArrayList;
import java.util.List;

import com.unblockme.solver.Operador;

public class Utils {
	
	public static List<Operador> iteratorToList (Iterable<Operador> iter) {
		
		List<Operador> result = new ArrayList<Operador>();
		
		iter.forEach(elem -> {
			System.out.println(elem);
			result.add(elem);
		});
		return result;
	}

}
