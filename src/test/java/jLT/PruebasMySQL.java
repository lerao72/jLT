package jLT;

import db.*;
import db.DB.Tipo;
import sData.*;

public class PruebasMySQL {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		main_getDataTable();
		main_ejecutaComando("PENELOPEX");
		main_getDataTable();
		main_ejecutaComando("PENELOPE");
		main_getDataTable();
	} // end main
	
	private static void main_getDataTable()
	{
		try {
			DB myDB = new DB(Tipo.MySQL, "jdbc:mysql://localhost:3306/mysql");
			myDB.setUser("root");
			myDB.setPsw("1111");
			
			DataTable t = myDB.getDataTable_CCP("select * from sakila.actor");

			String txtCabecera = "";
			for (DataTableColumn columna : t.columnas.get())
				txtCabecera += columna.nombre + "[" + columna.tipo.toString() + "]\t";
			
			System.out.println(txtCabecera);
			
			int i = 0;
			for (DataTableRow fila : t.filas.get()) 
			{
				String txtFila = "";
				for (Integer j = 0; j < t.columnas.getColumnsCount(); j++)
					txtFila += fila.itemArray[j].toString() + "\t";

				if (i % 2 == 0 && i % 3 == 0 && i % 5 == 0)
					System.out.println(txtFila);
				i++;
			}
		} catch (Exception ex) {
			System.out.println("Exception:" + ex.getMessage());
			ex.printStackTrace();
		}	
	}

	private static void main_ejecutaComando(String nombrePoner)
	{
		String sql = "update sakila.actor set first_name='" + nombrePoner +"' where actor_id=1";

		DB myDB = new DB(Tipo.MySQL, "jdbc:mysql://localhost:3306/mysql");
		myDB.setUser("root");
		myDB.setPsw("1111");
		
		myDB.ejecutaSQL_CCP(sql);
	}

	
} // end class
