package sData;

import java.util.*;

public class DataTableRowCollection {
	private DataTable t = null;
	private ArrayList<DataTableRow> filas;
	
	public DataTableRowCollection()
	{
		filas = new ArrayList<DataTableRow>(); 
	}
	
	public ArrayList<DataTableRow> get()
	{
		return filas;
	}
	
	public void setDataTablePadre(DataTable value)
	{
		t = value;
	}
	
	private boolean arrayCorrecto(Object[] array) {
		boolean correcto = t == null;
		
		if (!correcto)
		{
			correcto = array.length == t.columnas.getColumnsCount();

			int i = 0;
			while (correcto && i < t.columnas.getColumnsCount()) {
				Class<?> tipoClase = t.columnas.getColumn(i).tipo;
				Class<?> tipoElmento = array[i] != null ? array[i].getClass() : tipoClase;
				String str_tipoClase = tipoClase.toString();
				String str_tipoElemento = tipoElmento.toString();
				correcto &= str_tipoClase.equals(str_tipoElemento);
				i++;
			}
		}
		return correcto;
	}
	
	public void add(DataTableRow fila) throws Exception
	{
		if (arrayCorrecto(fila.itemArray))
			filas.add(fila);
		else 
			throw new Exception("Array incorrecto");
	}
	
	public void add(Object[] o)
	{
		DataTableRow fila = new DataTableRow(o);
		filas.add(fila);
	}
	
	public Integer getRowsCount()
	{
		return filas.size();
	}
	
	public DataTableRow getFila(int indiceDeFila) throws Exception
	{
		if (indiceDeFila < filas.size())
			return filas.get(indiceDeFila);
		else
			throw new Exception("Indice de fila incorrecto");
	}

} // end class
