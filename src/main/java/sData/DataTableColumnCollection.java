package sData;

import java.util.*;

public class DataTableColumnCollection {
	
	private ArrayList<DataTableColumn> columnas;
	
	public DataTableColumnCollection()
	{
		columnas = new ArrayList<DataTableColumn>();
	}
	
	public ArrayList<DataTableColumn> get()
	{
		return columnas;
	}
	
	public void add(DataTableColumn columna)
	{
		columnas.add(columna);
	}
	
	public void add(String name, Class<?> tipoClase)
	{
		DataTableColumn columna = new DataTableColumn(name, tipoClase);
		columnas.add(columna);
	}
	
	public Integer getColumnsCount()
	{
		return columnas.size();
	}
	
	public DataTableColumn getColumn(Integer indice)
	{
		if (indice < columnas.size())
			return columnas.get(indice);
		else
			return null;
	}	
}
