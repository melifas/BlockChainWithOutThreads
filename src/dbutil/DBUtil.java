package dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    public static Connection getConnection(){
        Connection c = null;
            {
                try {
                    c = DriverManager.getConnection("jdbc:mysql://localhost:3306/blockchain?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","user","user");
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
            return c;
    }

    public static void  closeConnection(Connection c){
        try {
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
