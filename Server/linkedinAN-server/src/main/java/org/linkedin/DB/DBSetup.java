package org.linkedin.DB;

import com.sun.net.httpserver.HttpExchange;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.*;
import java.util.Properties;

import static java.lang.StringTemplate.STR;

public class DBSetup {
    private static int port;
    private static String user;
    private static String password;
    private static Connection connection;
    public static void setup() {
        Properties DBconfig = new Properties();
        try (FileInputStream file = new FileInputStream("configs/DBconfig.properties")){
            DBconfig.load(file);
            port = Integer.parseInt(DBconfig.getProperty("Port"));
            user = DBconfig.getProperty("Username");
            password = DBconfig.getProperty("Password");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(STR."jdbc:mysql://localhost:\{port}/aliDataBase?user=\{user}&password=\{password}");
            System.out.println("successful connection mysql");

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        tableCreate();
    }
    public static Connection connect() {
        return connection;
    }
    public static void tableCreate()  {
        CreateTables.educationDB();
        CreateTables.callInfoDB();
        CreateTables.createUserDB();
        CreateTables.TextPostDB();
        CreateTables.likeDB();
        CreateTables.loginSessions();
        CreateTables.followsDB();
        CreateTables.commentDB();
    }
}
