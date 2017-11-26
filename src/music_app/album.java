package music_app;
import java.io.*;
import java.sql.*;

public class album {

    static private Connection con;
    static private Statement stmt;
    private ResultSet rs;
    BufferedReader in
            = new BufferedReader(new InputStreamReader(System.in));


    public album(){
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

    public void insert_album(){
        try{
            System.out.println("Please type album information");
            System.out.println("1. album name");
            String album_name = in.readLine();
            System.out.println("2. singer");
            String singer_name = in.readLine();
            System.out.println("3. album_made_year");
            String made_year = in.readLine();
            System.out.println("4. album_made_month");
            String made_month = in.readLine();
            System.out.println("5. album_made_day");
            String made_day = in.readLine();

            String album_exist_check = "SELECT album.name FROM album,singer WHERE singer.name = '"+singer_name +"' and album.name = '"+album_name+"';";
            rs = stmt.executeQuery(album_exist_check);
            if(rs.next()){
                System.out.println(rs.getString("name") + " is already exist!");
                return;
            }

            singer sin = new singer();
            int singer_id = sin.get_singer_id_with_name(singer_name);
            String album_insert_query = "INSERT album(made_year, made_month, made_day, name, singer_id) VALUES ( '"+made_year+"', '"+made_month+"', '"+made_day+"', '"+album_name+"', '"+singer_id+"');";
            stmt.executeUpdate(album_insert_query);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public int get_album_id_with_name(String name){
        try
        {
            String temp = "SELECT album_id FROM album WHERE album.name = '" + name + "' ;";
            rs = stmt.executeQuery(temp);
            while (rs.next())
                return rs.getInt("album_id");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return -1;
    }
}
