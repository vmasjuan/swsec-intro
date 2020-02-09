package swsec.http;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import swsec.Helpers;
import swsec.ApplicationProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Logger.getLogger(getClass().getName()).log(Level.INFO, "JWT is " + (ApplicationProperties.INSTANCE.usesJWT() ? "enabled" : "disabled"));
        populateDatabase();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

    private void populateDatabase() {
        try {
            Class.forName("org.sqlite.JDBC");
            String messageTable = "CREATE TABLE IF NOT EXISTS 'mensajes' ('id' INTEGER PRIMARY KEY NOT NULL, 'mensaje' TEXT NOT NULL );";
            String userTable = "CREATE TABLE IF NOT EXISTS 'usuarios' ('id' INTEGER PRIMARY KEY NOT NULL, 'username' VARCHAR(10) NOT NULL, 'password' VARCHAR(10) NOT NULL ,'type' INTEGER);";
            Connection conn = DriverManager.getConnection(Helpers.getSqliteUrl());
            Statement stmt = conn.createStatement();
            stmt.setQueryTimeout(30);
            stmt.executeUpdate(userTable);
            stmt.executeUpdate(messageTable);
            stmt.executeUpdate("DELETE FROM usuarios");
            stmt.executeUpdate("DELETE FROM mensajes");
            stmt.executeUpdate("INSERT INTO usuarios(username, password, type) VALUES ('jperez', '123', '1')");
            stmt.executeUpdate("INSERT INTO usuarios(username, password, type) VALUES ('basaber', '12345', '1')");
            stmt.executeUpdate("INSERT INTO usuarios(username, password, type) VALUES ('skramer', 'power', '1')");
            stmt.executeUpdate("INSERT INTO usuarios(username, password, type) VALUES ('aeinstein', 'simple', '1')");
            stmt.executeUpdate("INSERT INTO usuarios(username, password, type) VALUES ('tstark', 'ironman', '1')");
            stmt.executeUpdate("INSERT INTO usuarios(username, password, type) VALUES ('cpalma', 'lepego', '1')");
            stmt.executeUpdate("INSERT INTO usuarios(username, password, type) VALUES ('asavage', 'boom', '1')");
            stmt.executeUpdate("INSERT INTO usuarios(username, password, type) VALUES ('jhyneman', 'boom', '1')");
            stmt.executeUpdate("INSERT INTO usuarios(username, password, type) VALUES ('tanderson', 'matrix', '1')");
            stmt.executeUpdate("INSERT INTO usuarios(username, password, type) VALUES ('zcool', 'god', '1')");
            stmt.executeUpdate("INSERT into mensajes(mensaje) VALUES ('Bienvenidos al foro de Fans de las Aves Chilenas. Soy el Administrador.')");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
