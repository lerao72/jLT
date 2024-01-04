package jLT;

import sData.*;
import com.google.gson.*;

public class PruebasVarias {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		DataTable t = new DataTable("nombreTabla");
		t.columnas.add("id", Integer.class);
		t.columnas.add("cadena", String.class);
		
		for (int i=1;i<=5;i++)
		{
			Object[] o = { i, "valor " + i };
			t.filas.add(o);
		}	
		
		//Gson gson = new Gson();
		
		//String representacionJSON = gson.toJson(t.columnas);
		//System.out.println(representacionJSON);
		
		String csv = t.toCSV(true);
		System.out.println(csv);
		
		System.out.println("-------------");
		
		DataTable tCSV = DataTable.fromCSV("nueva", csv);
		tCSV.filas.get().remove(3);
		System.out.println(tCSV.toCSV(true));

		System.out.println("-------------");
		System.out.println("Clonada:");
		Object[] conNulo = { 10, "" };
		try {
			t.filas.getFila(2).itemArray = conNulo;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DataTable tClonada = t.clone();
		System.out.println(tClonada.toCSV(true));

		System.out.println("-------------");
		System.out.println("Original: " + t.nombre);
		System.out.println(t.toCSV(true));
	}

} // end class
