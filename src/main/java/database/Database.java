package database;
import java.io.*;
import java.sql.*;
import java.util.*;
import classes.*;

public class Database {
    Connection con;
    Properties props;

    public Database() {
        try {
            props = getProperties();
            con = getConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(props.getProperty("db.driver"));
        return DriverManager.getConnection(
                props.getProperty("db.path"),
                props.getProperty("db.user"),
                props.getProperty("db.password")
        );
    }

    public Properties getProperties() throws IOException {
        InputStream input = new FileInputStream("src/main/java/database/config.properties");
        Properties props = new Properties();
        props.load(input);
        return props;
    }
}