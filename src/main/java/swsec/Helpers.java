package swsec;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Helpers {

    public static boolean checkUserLogin(String username, String password) {
        int numFilas = 0;
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conexion = DriverManager.getConnection(getSqliteUrl());
            Statement statement = conexion.createStatement();
            String query = "select * from usuarios where username = '" + username + "' and password = '" + password + "'";
            ResultSet resultado = statement.executeQuery(query);
            while (resultado.next())
                numFilas++;
            statement.close();
            conexion.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numFilas > 0;
    }
    
    public static void insertPost(String message) throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		Connection conexion = DriverManager.getConnection(Helpers.getSqliteUrl());
		Statement statement = conexion.createStatement();
		String query = "insert into mensajes (mensaje) values ('" + message + "')";
		statement.executeUpdate(query);
		statement.close();
		conexion.close();
	}

	public static void insertUser(String username, String password) throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		Connection conexion = DriverManager.getConnection(Helpers.getSqliteUrl());
		Statement statement = conexion.createStatement();
		String query = "INSERT INTO usuarios(username, password, type) VALUES ('" + username + "', '" + password + "', '2')";
		statement.executeUpdate(query);
		statement.close();
		conexion.close();
	}

	public static void deleteUser(String username) throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		Connection conexion = DriverManager.getConnection(Helpers.getSqliteUrl());
		Statement statement = conexion.createStatement();
		String query = "DELETE FROM usuarios WHERE username = '" + username + "'" ;
		statement.executeUpdate(query);
		statement.close();
		conexion.close();
	}
    
	public static List<String> getPosts() throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		Connection conexion = DriverManager.getConnection(Helpers.getSqliteUrl());
		Statement statement = conexion.createStatement();
		String query = "select mensaje from mensajes";
		ResultSet resultado = statement.executeQuery(query);
		List<String> mensajes = new ArrayList<String>();
		while (resultado.next())
			mensajes.add(resultado.getString(1));
		statement.close();
		conexion.close();
		return mensajes;
    }
    
    public static List<String> getUsers(int userType) throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		Connection conexion = DriverManager.getConnection(Helpers.getSqliteUrl());
		Statement statement = conexion.createStatement();
		String query = "select username from usuarios" + (userType != 0 ? " where type = " + userType : "");
		ResultSet resultado = statement.executeQuery(query);
		List<String> usernames = new ArrayList<String>();
		while (resultado.next())
			usernames.add(resultado.getString(1));
		statement.close();
		conexion.close();
		return usernames;
	}

    public static final String getSqliteUrl() {
        return "jdbc:sqlite:swsecdemo.sqlite";
    }

}
