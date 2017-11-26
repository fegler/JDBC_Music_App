package music_app;
import java.sql.*;
import java.io.*;
public class login {

    BufferedReader in
            = new BufferedReader(new InputStreamReader(System.in));
    String manager_login(){
        System.out.println("First input manager nickname and password");
        try{
            System.out.println("nickname");
            String nname = in.readLine();
            System.out.println("password");
            String pwd = in.readLine();
            String sql = "SELECT nickname FROM manager WHERE manager.nickname = '" + nname + "" + "' and manager.password = '" + pwd +"';";
            return sql;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    String user_login(){
        System.out.println("First input user name and password");
        try{
            String user_name= null;
            String user_password = null;
            System.out.println("name:");
            user_name = in.readLine();
            System.out.println("password:");
            user_password = in.readLine();

            String temp = "SELECT name,user_id FROM user WHERE name = '"+user_name+"' and password = '"+user_password+"' ;";
            return temp;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
