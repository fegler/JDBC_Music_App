package music_app;
import java.io.*;
import java.sql.*;

public class music {

    static private Connection con;
    static private Statement stmt;
    private ResultSet rs;
    private int music_id;
    private String music_name;
    private String music_genre;
    private int music_playtime;
    BufferedReader in
            = new BufferedReader(new InputStreamReader(System.in));

    public music(){
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

    public int get_music_num_with_genre(String genre){
        try{
            String temp = "SELECT count(*) FROM music WHERE genre = '"+genre+"' ;";
            rs = stmt.executeQuery(temp);
            if(rs.next()){
                return rs.getInt("count(*)");
            }

            return -1;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return -1;
    }
}
