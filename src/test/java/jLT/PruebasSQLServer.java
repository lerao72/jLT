package jLT;

import db.*;
import db.DB.Tipo;
import sData.*;

public class PruebasSQLServer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		main_getDataTable();
		main_ejecutaComando();
		main_getDataTable();
		main_ejecutaComandoBorra();
		main_getDataTable();
	} // end main
	
	private static void main_getDataTable()
	{
		try {
			DB myDB = new DB(Tipo.SQLServer, "jdbc:sqlserver://P-RL\\SQLEXPRESS;databaseName=SGA;user=sa;password=1;encrypt=false;");
			DataTable t = myDB.getDataTable_CCP("select * from t_contactos");

			String txtCabecera = "";
			for (DataTableColumn columna : t.columnas.get())
				txtCabecera += columna.nombre + "[" + columna.tipo.toString() + "]\t";
			
			System.out.println(txtCabecera);
			
			int i = 0;
			for (DataTableRow fila : t.filas.get()) 
			{
				String txtFila = "";
				for (Integer j = 0; j < t.columnas.getColumnsCount(); j++)
					txtFila += fila.itemArray[j] != null ? fila.itemArray[j].toString() + "\t" : "\t";

				if (i % 2 == 0 && i % 3 == 0 && i % 5 == 0)
					System.out.println(txtFila);
				i++;
			}
		} catch (Exception ex) {
			System.out.println("Exception:" + ex.getMessage());
			ex.printStackTrace();
		}	
	}

	private static void main_ejecutaComando()
	{
		String sql = "insert into t_contactos(idContacto, activo, visible, razonSocial, aplicaIva, tarifa, esCliente) values (1, 's', 's', 'nombre', 's', 2, 's')";
		DB myDB = new DB(Tipo.SQLServer, "jdbc:sqlserver://P-RL\\SQLEXPRESS;databaseName=SGA;user=sa;password=1;encrypt=false;");
		myDB.ejecutaSQL_CCP(sql);
	}
	
	private static void main_ejecutaComandoBorra()
	{
		String sql = "delete from t_contactos where idContacto=1";
		DB myDB = new DB(Tipo.SQLServer, "jdbc:sqlserver://P-RL\\SQLEXPRESS;databaseName=SGA;user=sa;password=1;encrypt=false;");
		myDB.ejecutaSQL_CCP(sql);		
	}


} // end class
