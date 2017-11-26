package music_app;
import java.io.*;
import java.sql.*;

public class playlist {

    static private Connection con;
    static private Statement stmt;
    private ResultSet rs;

    public playlist(){
        try {
            Class.forName("org.mariadb.jdbc.Driver").newInstance();
            String url = "jdbc:mariadb://localhost:3306/MUSIC_APP";
            String user = "root";
            String psw = "ehdrb948";
            con = DriverManager.getConnection(url, user, psw);
            stmt = con.createStatement();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
