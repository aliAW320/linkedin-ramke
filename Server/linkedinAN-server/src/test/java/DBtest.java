import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBtest {
    private static Connection conn = null;

    private static int port = 3306;
    private static String user = "alireza";
    private static String password = "14261426";
    static Properties prop = new Properties();


    public static void main(String[] args) {
        try (FileInputStream file = new FileInputStream("configs/DBconfig.properties")){
            prop.load(file);
            port = Integer.parseInt(prop.getProperty("Port"));
            user = prop.getProperty("Username");
            password = prop.getProperty("Password");


        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection(STR."jdbc:mysql://localhost:\{port}/linkedinDB?user=\{user}&password=\{password}");
            System.out.println("successful connection mysql");

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
}
