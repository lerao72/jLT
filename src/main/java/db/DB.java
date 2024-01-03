package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB.Tipo;
import sData.DataTable;

public class DB {
	
public enum Tipo { SQLServer, MySQL }

	/*
	 * Ejemplos de cadena de conexión:
	 * SQL Server: 	"jdbc:sqlserver://P-RL\\SQLEXPRESS;databaseName=SGA;user=<usuario>;password=<contraseña>;encrypt=false;"
	 * MySQL:		"jdbc:mysql://localhost:3306/mysql"
	 */	
	private String cadenaDeConexion = "";
	private Tipo tipo;
	
	private String user = "";	// Para mySQL es necesario para la cadena de conexión
	private String psw = "";
	
	public DB(Tipo dbTipo, String url)
	{
		tipo = dbTipo;
		cadenaDeConexion = url;
	}
	
	public String getCadenaDeConexion() { 
		return cadenaDeConexion; 
	}
	
	public void setCadenaDeConexion(String cadena) {
		cadenaDeConexion = cadena;
	}
		
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPsw() {
		return psw;
	}

	public void setPsw(String psw) {
		this.psw = psw;
	}

	public Connection getConnection()
	{
		Connection conn = null;
		if (tipo.equals(Tipo.MySQL))
			conn = getConnection_mySQL();
		else if (tipo.equals(Tipo.SQLServer))
			conn = getConnection_SQLServer();
			
		return conn;
	}
	
	private Connection getConnection_mySQL() {
		Connection conn = null;
		try {
			// connection
			conn = DriverManager.getConnection(cadenaDeConexion, user, psw);

			if (conn != null) {
				System.out.println("Conectado a la database mysql");
			}
		} catch (SQLException ex) {
			System.out.println("Exception :" + ex.getMessage());
			ex.printStackTrace();
		}

		return conn;
	}

	private Connection getConnection_SQLServer() {
		Connection conn = null;
		try {
			// connection
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection(cadenaDeConexion);

			if (conn != null) {
				System.out.println("Conectado a la database SQLServer");
			}
		} catch (SQLException ex) {
			System.out.println("Exception :" + ex.getMessage());
			ex.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return conn;
	}

	public void closeConnection(Connection conn) {
		try {
			conn.close();
			if (conn.isClosed())
				System.out.println("Conexión a base de datos cerrada");
						
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public DataTable getDataTable(String sql, Connection conn) {
		DataTable t = new DataTable("");
		try {
			Statement sentencia = conn.createStatement();
			ResultSet resultado = sentencia.executeQuery(sql);
			t.load(resultado);

		} catch (SQLException ex) {
			System.out.println("Exception ::" + ex.getMessage());
			ex.printStackTrace();

			t = null;
		}

		return t;
	}

	public DataTable getDataTable_CCP(String sql) {
		DataTable t = new DataTable("");
		try {

			Connection conn = getConnection();
			t = getDataTable(sql, conn);
			closeConnection(conn);

		} catch (Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
			ex.printStackTrace();
			t = null;
		}

		return t;
	}

	public Integer ejecutaSQL(String sql, Connection conn) {
		Integer afectados = 0;
		try {
			Statement sentencia = conn.createStatement();
			afectados = sentencia.executeUpdate(sql);
		} catch (SQLException ex) {
			System.out.println("Exception :" + ex.getMessage());
			ex.printStackTrace();
		}
		return afectados;
	}
	
	public Integer ejecutaSQL_CCP(String sql)
	{
		Integer afectados = 0;
		try {
			Connection conn = getConnection();
			afectados = ejecutaSQL(sql, conn);
			closeConnection(conn);
		}
		catch (Exception ex)
		{
			System.out.println("Exception: " + ex.getMessage());
			ex.printStackTrace();
		}
		return afectados;
	}
	
	

} // end class
