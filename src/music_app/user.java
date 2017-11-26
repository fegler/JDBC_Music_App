package music_app;
import java.io.*;
import java.sql.*;

public class user {

    static private Connection con;
    static private Statement stmt;
    private ResultSet rs;

    BufferedReader in
            = new BufferedReader(new InputStreamReader(System.in));

    private String user_name = null;
    private int user_id = -1;
    private String user_sex = null;
    private int user_age = -1;

    public user(){
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
    public boolean login(){

        System.out.println("First input user name and password and sex and age");
        try{
            System.out.println("name");
            String name = in.readLine();
            System.out.println("password");
            String pwd = in.readLine();
            System.out.println("sex");
            String sex = in.readLine();
            System.out.println("age");
            int age = Integer.parseInt(in.readLine());

            String sql = "SELECT user_id, name, sex, age FROM user WHERE user.name = '" + name +"' and user.password = '" + pwd +"';";
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                user_id = rs.getInt("user_id");
                user_name = rs.getString("name");
                user_sex = rs.getString("sex");
                user_age = rs.getInt("age");
                return true;
            }

            return false;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public void user_menu(){
        System.out.println(this.user_name + " user works menu!!");
        System.out.println("----------------------");
        System.out.println("1. show all playlist\n2. playlist make\n3. playlist delete\n4. insert music to playlist\n5. delete music from playlist\n6. search music\n7. logout");
        System.out.println("----------------------");
    }

    public void show_playlist(){
        try {
            String show_playlist_query = "SELECT playlist_id, list_name, music_sum FROM playlist, user WHERE user_id = '" + this.user_id + "';";
            rs = stmt.executeQuery(show_playlist_query);
            boolean flag =false;
            while(rs.next()){
                flag = true;
                int pid= rs.getInt("playlist_id");
                String lname = rs.getString("list_name");
                int msum = rs.getInt("music_sum");
                System.out.println("(1) playlist_id = "+pid +"(2) list_name = "+lname+"(3) music_sum = "+msum);
            }

            if(!flag)
                System.out.println("There is no playlist!!");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void playlist_make(){
        try {
            System.out.println("First type playlist name!");
            System.out.println("list name:");
            String lname = in.readLine();

            String list_exist_check = "SELECT list_name FROM playlist WHERE list_name = '"+lname+"';";
            rs = stmt.executeQuery(list_exist_check);
            if(rs.next()){
                System.out.println(lname+" is already exist");
                return;
            }

            String list_make_query = "INSERT playlist(list_name, music_sum, user_id) VALUES( '"+lname+"', 0, "+this.user_id+");";
            stmt.executeUpdate(list_make_query);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void playlist_delete(){
        try{
            System.out.println("Please type playlist name for delete");
            String dname = in.readLine();
            int delete_list_id = -1;

            String list_exist_check = "SELECT playlist_id FROM playlist WHERE list_name = '"+dname+"';";
            rs = stmt.executeQuery(list_exist_check);
            if(rs.next()){
                delete_list_id = rs.getInt("playlist_id");
            }

            if(delete_list_id == -1)
            {
                System.out.println("There is not playlist with that name!");
                return;
            }

            String delete_list = "DELETE FROM playlist WHERE playlist_id = "+delete_list_id+";";
            stmt.executeUpdate(delete_list);
            delete_list = "DELETE FROM music_playlist_relation WHERE playlist_id = "+delete_list_id+";";
            stmt.executeUpdate(delete_list);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void insert_music_to_playlist(){
        try {
            System.out.println("Please type list name");
            String lname = in.readLine();

            String list_exist_check = "SELECT list_name FROM playlist WHERE list_name = '"+lname+"';";
            rs = stmt.executeQuery(list_exist_check);
            if(!rs.next())
            {
                System.out.println("There is not that playlist!!");
                return;
            }


            System.out.println("This playlist have these musics");
            String show_musics = "SELECT music_id, music.name, singer.name, album.name FROM music_playlist_relation, singer, album, playlist WHERE list_name = '"+lname+"';";
            rs = stmt.executeQuery(show_musics);
            while(rs.next()){
                int mid = rs.getInt("music_id");
                String mname = rs.getString("name");
                String sname = rs.getString("name");
                String aname = rs.getString("name");
                System.out.println("(1) music_id = "+mid+" (2) music_name = "+mname+" (3) singer_name = "+sname+" (4) album_name = "+aname);
            }

            System.out.println("Please type music_id for delete!!");
            int delete_id = Integer.parseInt(in.readLine());

            String music_correct_check = "SELECT music_id FROM music_playlist_relation, playlist WHERE list_name = '"+lname+"' and music_id = '"+delete_id+"';";
            rs = stmt.executeQuery(music_correct_check);
            if(!rs.next())
            {
                System.out.println("The music_id is wrong!!");
                return;
            }

            String delete_query = "DELETE FROM music_playlist_relation WHERE music_id = '"+delete_id+"';";
            stmt.executeUpdate(delete_query);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void delete_music_from_playlist(){

        try{
            System.out.println("First type list_id from these playlist!!");
            String print_lists = "SELECT playlist_id, list_name FROM playlist WHERE user_id = '"+this.user_id+"';";
            rs = stmt.executeQuery(print_lists);
            while(rs.next()){
                int pid = rs.getInt("playlist_id");
                String pname = rs.getString("list_name");
                System.out.println("(1) playlist_id = "+pid+" (2) lisy_name = "+pname);
            }

            System.out.println("Type id!!");
            int delete_pid = Integer.parseInt(in.readLine());
            String print_musics_in_list = "SELECT music_id, music.name, playlist_id, singer.name, album.name FROM music,music_playlist_relation,singer,album WHERE playlist_id = '"+delete_pid+"';";
            rs = stmt.executeQuery(print_musics_in_list);
            while(rs.next()){
                int mid = rs.getInt("music_id");
                String mname = rs.getString("name");
                int ppid = rs.getInt("playlist_id");
                String sname = rs.getString("name");
                String aname = rs.getString("name");
                System.out.println("(1) music_id = "+mid+" (2) music_name = "+mname+" (3) list_id = "+ppid+" (4) singer_name = "+sname+" (5) album_name = "+aname);
            }

            System.out.println("Please Type delete music_id");
            int delete_music_id = Integer.parseInt(in.readLine());

            String delete_music_query = "DELETE FROM music_playlist_relation WHERE playlist_id = '"+delete_music_id+"';";
            stmt.executeUpdate(delete_music_query);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void search_musics(){
        try{
            System.out.println("Search menu!!");
            System.out.println("----------------------");
            System.out.println("1. search with music_name\n2. search with music_genre\n3. search with singer\n4. search with album\n5. search with playlist");
            System.out.println("----------------------");
            int search_command = Integer.parseInt(in.readLine());

            String search_query = null;
            ResultSet search_rs = null;
            if(search_command == 1){
                System.out.println("Type music_name");
                String mname = in.readLine();
                search_query = "SELECT music_id, music.name, singer.name, album.name, list_name FROM music, singer, album WHERE music.name = '"+mname+"';";
            }
            else if(search_command == 2){
                System.out.println("Type music_genre");
                String mgenre = in.readLine();
                search_query = "SELECT music_id, music.name, singer.name, album.name, list_name FROM music, singer, album WHERE music.genre = '"+mgenre+"';";
            }
            else if(search_command == 3){
                System.out.println("Type singer_name");
                String sname = in.readLine();
                search_query = "SELECT music_id, music.name, singer.name, album.name, list_name FROM music, singer, album WHERE singer.name = '"+sname+"';";
            }
            else if(search_command == 4){
                System.out.println("Type album_name");
                String aname = in.readLine();
                search_query = "SELECT music_id, music.name, singer.name, album.name, list_name FROM music, singer, album WHERE album.name = '"+aname+"';";
            }
            else if(search_command == 5){
                System.out.println("Type playlist_name");
                String pname = in.readLine();
                search_query = "SELECT music_id, music.name, singer.name, album.name, list_name FROM music, singer, album WHERE list_name = '"+pname+"';";
            }
            else {
                System.out.println("command is wrong!!");
                return;
            }

            search_rs = stmt.executeQuery(search_query);
            boolean flag = false;
            while(search_rs.next()){
                flag = true;
                int mid2 = search_rs.getInt("music_id");
                String mname2 = search_rs.getString("name");
                String sname2 = search_rs.getString("name");
                String aname2 = search_rs.getString("name");
                String pname2 = search_rs.getString("list_name");

                System.out.println("(1) music_id = "+mid2+" (2) music_name = "+mname2+" (3) singer_name = "+sname2+" (4) album_name"+aname2+" (5) list_name = "+pname2);
            }

            if(!flag){
                System.out.println("There is no musics with that information!!");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void logout(){
        this.user_id = 0;
        this.user_age = 0;
        this.user_name = null;
        this.user_sex = null;
    }
}
