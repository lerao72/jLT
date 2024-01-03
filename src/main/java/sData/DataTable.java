package sData;

import java.sql.SQLException;
import com.microsoft.sqlserver.jdbc.SQLServerResultSetMetaData;
import com.mysql.cj.jdbc.result.ResultSetMetaData;
import com.mysql.cj.result.Field;

public class DataTable {

	public String nombre;
	public DataTableColumnCollection columnas;
	public DataTableRowCollection filas;

	public DataTable(String nombreTabla) {
		nombre = nombreTabla;
		columnas = new DataTableColumnCollection();
		filas = new DataTableRowCollection();
	}
	
	public void load(java.sql.ResultSet rs)
	{
		try {
			if (rs.getMetaData().getClass().toString().equals(SQLServerResultSetMetaData.class.toString()))
				loadSQLServer(rs);
			else
				loadNormal(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
			
	private void loadNormal(java.sql.ResultSet rs)
	{
		try {
			ResultSetMetaData md = (ResultSetMetaData)rs.getMetaData();
			Integer numColumnas = md.getColumnCount();
			for (int i=1;i<=numColumnas;i++)
			{
				String nombreColumna = md.getColumnName(i);
				String str_tipoColumna = md.getColumnClassName(i);
				Class<?> tipoColumna = Class.forName(str_tipoColumna);
				DataTableColumn nuevaColumna = new DataTableColumn(nombreColumna, tipoColumna);
				columnas.add(nuevaColumna);
			}
			
			//rs.first();
			while (rs.next())
			{				
				Object[] o = new Object[numColumnas];
				for (Integer j=1;j<=numColumnas;j++)
					o[j-1] = rs.getObject(j);
				
				DataTableRow fila = new DataTableRow(o);
				filas.add(fila);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void loadSQLServer(java.sql.ResultSet rs)
	{
		try {
			SQLServerResultSetMetaData md = (SQLServerResultSetMetaData)rs.getMetaData();
			Integer numColumnas = md.getColumnCount();
			//Field[] f = md.getFields();
			for (int i=1;i<=numColumnas;i++)
			{
				String nombreColumna = md.getColumnName(i);
				String str_tipoColumna = md.getColumnClassName(i);
				Class<?> tipoColumna = Class.forName(str_tipoColumna);
				DataTableColumn nuevaColumna = new DataTableColumn(nombreColumna, tipoColumna);
				columnas.add(nuevaColumna);
			}
			
			//rs.first();
			while (rs.next())
			{				
				Object[] o = new Object[numColumnas];
				for (Integer j=1;j<=numColumnas;j++)
					o[j-1] = rs.getObject(j);
				
				DataTableRow fila = new DataTableRow(o);
				filas.add(fila);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

} // end class
