package music_app;
import java.io.*;
import java.sql.*;

public class singer {

    static private Connection con;
    static private Statement stmt;
    private ResultSet rs;
    BufferedReader in
            = new BufferedReader(new InputStreamReader(System.in));


    public singer(){
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


    public void singer_insert(){
        try{
            System.out.println("Please type singer information");
            System.out.println("1. singer name");
            String singer_name = in.readLine();
            System.out.println("2. singer age");
            String singer_age = in.readLine();
            System.out.println("3. singer sex");
            String singer_sex = in.readLine();

            String singer_exist_check = "SELECT name FROM singer WHERE singer.name = '"+singer_name +"';";
            rs = stmt.executeQuery(singer_exist_check);
            if(rs.next()){
                System.out.println(rs.getString("name") + " is already exist!");
                return;
            }

            String singer_insert_query = "INSERT singer(age, name, sex) VALUES ( '"+singer_age+"', '"+singer_name+"', '"+singer_sex+"');";
            stmt.executeUpdate(singer_insert_query);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public int get_singer_id_with_name(String name){
        try{
            String temp = "SELECT singer_id FROM singer WHERE singer.name = '"+name+ "' ;";
            rs = stmt.executeQuery(temp);
            while(rs.next())
                return rs.getInt("singer_id");

            System.out.println("There is not that singer!!");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return -1;
    }


}
