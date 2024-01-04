package sData;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.table.TableColumn;

import com.microsoft.sqlserver.jdbc.SQLServerResultSetMetaData;
import com.mysql.cj.jdbc.result.ResultSetMetaData;
import com.google.gson.*;

public class DataTable {

	public String nombre;
	public DataTableColumnCollection columnas;
	public DataTableRowCollection filas;

	public DataTable(String nombreTabla) {
		nombre = nombreTabla;
		columnas = new DataTableColumnCollection();
		filas = new DataTableRowCollection();
		filas.setDataTablePadre(this);
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
	
	public DataTable clone()
	{
		DataTable n = new DataTable(nombre);
		for (DataTableColumn tc : this.columnas.get()) {
			DataTableColumn c_nueva = new DataTableColumn(tc.nombre, tc.tipo);
			n.columnas.add(c_nueva);
		}
		
		Integer numFilas = this.filas.getRowsCount();
		Integer numColumnas = this.columnas.getColumnsCount();
		
		for (int i=0; i<numFilas;i++)
		{
			Object[] o = new Object[numColumnas];
			for (int j=0;j<numColumnas;j++)
			{
				try {
					o[j] = this.filas.getFila(i).itemArray[j];
				} catch (Exception e) {
					// TODO Auto-generated catch block
					o[j] = null;
				}
			}
			n.filas.add(o);
		}
		return n;
	}
	
	public String toCSV(boolean incluirCabeceras)
	{
		String csv = "";
		String puntoYComa = "";
		if (incluirCabeceras)
		{			
			for (DataTableColumn c : this.columnas.get())
			{
				csv += puntoYComa + c.nombre;
				puntoYComa = ";";
			}
			csv +="\r\n";
		}
		for (DataTableRow r : this.filas.get())
		{
			puntoYComa = "";
			for (Object e : r.itemArray)
			{
				csv += puntoYComa + e.toString();
				puntoYComa = ";";
			}
			csv += "\r\n";
		}
		
		return csv.trim();
	}
	
	public static DataTable fromCSV(String nombre, String csv)
	{
		DataTable t = new DataTable(nombre);
		
		String[] filas = csv.split("\r\n");
		if (filas.length > 0)
		{
			String[] nombresColumnas = filas[0].split(";");
			for (String nombreColumna : nombresColumnas)
				t.columnas.add(nombreColumna, String.class);
		}
		if (filas.length > 1)
			for (int i=1;i<filas.length;i++)
			{
				String[] elementos = filas[i].split(";");
				t.filas.add(elementos);
			}
		
		return t;
	}
} // end class
