package music_app;
import java.io.*;
import java.sql.*;

public class manager {

    static private Connection con;
    static private Statement stmt;
    private ResultSet rs;
    BufferedReader in
            = new BufferedReader(new InputStreamReader(System.in));

    int manager_id = 0;
    String manager_name = null;
    String manage_genre = null;
    int genre_music_sum = 0;
    public manager(){
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

    public void menu_print(){
        System.out.println(this.manager_name+" manager works menu");
        System.out.println("-----------------------");
        System.out.println("1. manage genre print\n" + "2. user insert\n" + "3. singer insert\n"+ "4. album insert\n"+"5. music insert\n" + "6. music delete\n" + "7. manager insert\n" + "8. logout\n");
        System.out.println("-----------------------");
    }

    //true-> 로그인 성공, false -> 로그인 실패
    public boolean login(){
        System.out.println("First input manager nickname and password");
        try{
            System.out.println("nickname");
            String nname = in.readLine();
            System.out.println("password");
            String pwd = in.readLine();
            String sql = "SELECT nickname, genre, genre_music_sum, manager_id FROM manager WHERE manager.nickname = '" + nname + "" + "' and manager.password = '" + pwd +"';";
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                manager_name = rs.getString("nickname");
                manage_genre = rs.getString("genre");
                genre_music_sum = rs.getInt("genre_music_sum");
                manager_id = rs.getInt("manager_id");
                return true;
            }

            return false;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public void manage_genre_print(){
        if(this.manage_genre != null) {
            System.out.println(manager_name+" is managing "+this.manage_genre+" musics!!");
        }
        else{
            System.out.println("this manager is root manager or not manage musics");
        }
    }

    public boolean user_insert(){
        try {

            System.out.println("Please type user information!!");
            System.out.println("1. user name\n");
            String usr_name = in.readLine();
            System.out.println("2. user password\n");
            String usr_psw = in.readLine();
            System.out.println("3. user age\n");
            int usr_age = Integer.parseInt(in.readLine());
            System.out.println("4. user sex\n");
            String usr_sex = in.readLine();


            String usr_check_query = "SELECT * FROM user WHERE user.name = '" + usr_name + "'and user.password = '" + usr_psw + "';";
            rs = stmt.executeQuery(usr_check_query);

            while (rs.next()) {
                int age = rs.getInt("age");
                String name = rs.getString("name");
                String sex = rs.getString("sex");
                String password = rs.getString("password");
                System.out.println( name +" is already exist\n");
                return false;
            }
            String usr_insert_query = "INSERT user(age,name,sex,password,list_num) VALUES ("+usr_age+", '"+usr_name+"', '"+usr_sex+"', '"+usr_psw+"', 0)";
            stmt.executeUpdate(usr_insert_query);
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public void singer_insert(){
        singer sin2 = new singer();
        sin2.singer_insert();
    }

    public void album_insert(){
        album al1 = new album();
        al1.insert_album();
    }

    public boolean music_insert(){
        singer sin = new singer();
        album al = new album();
        try {
            System.out.println("Please type music information!!");
            System.out.println("1. music_name");
            String music_name = in.readLine();
            System.out.println("2. music_playtime");
            int playtime = Integer.parseInt(in.readLine());
            System.out.println("3. music_genre");
            String music_genre = in.readLine();
            System.out.println("4. music_singer_name");
            String singer_name = in.readLine();
            System.out.println("5. music_album_name");
            String album_name = in.readLine();

            if(music_genre != this.manage_genre)
            {
                System.out.println(this.manager_name+" is not managing "+music_genre);
                return false;
            }
            int singer_id = -1, album_id = -1;

            singer_id = sin.get_singer_id_with_name(singer_name);
            album_id = al.get_album_id_with_name(album_name);


            String music_exist_check = "SELECT music.name FROM music,manager,singer,album WHERE music.genre = '"+music_genre+"' and music.name = '"+music_name+"' and manager_id = '"+this.manager_id+"' and singer_id = '"+singer_id+"' and album_id = '"+album_id+"' ;";
            rs = stmt.executeQuery(music_exist_check);
            while(rs.next()){
                System.out.println(rs.getString("name")+" is already exist");
                return false;
            }

            String music_insert_query = "INSERT music(genre, playtime, name, manager_id, singer_id, album_id) VALUES('"+music_genre+"', "+playtime+", '"+music_name+"', "+this.manager_id+", "+singer_id+", "+album_id+");";
            stmt.executeUpdate(music_insert_query);
            System.out.println("music_insert success!!");
            this.genre_music_sum++;

            String set_genre_music_sum = "update manager SET genre_music_sum = "+this.genre_music_sum+" WHERE manager_id = "+this.manager_id+";";
            stmt.executeUpdate(set_genre_music_sum);

            return true;

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean music_delete(){
        System.out.println("First see musics you can delete");
        try {
            String music_list = "SELECT music_id, music.name, singer.name, album.name FROM manager, music, singer, album WHERE music.genre = '" + this.manage_genre + "' ORDER BY music_id ;";
            rs = stmt.executeQuery(music_list);
            boolean flag = false;
            while(rs.next()){
                flag = true;
                System.out.println("(1) music_id = "+rs.getInt("music_id"));
                System.out.println("(2) music_name = "+rs.getString("name"));
                System.out.println("(3) music_id = "+rs.getString("name"));
                System.out.println("(4) music_id = "+rs.getString("name"));
            }
            if(!flag) {
                System.out.println("There are not musics you can delete");
                return false;
            }

            System.out.println("Please type the music_id you want to delete!!");
            int delete_id = Integer.parseInt(in.readLine());
            String delete_music_query = "DELETE FROM music WHERE music_id = '"+delete_id+"' ;";
            stmt.executeUpdate(delete_music_query);
            System.out.println("Delete success!!");

            this.genre_music_sum--;
            String set_genre_music_sum = "update manager SET genre_music_sum = "+this.genre_music_sum+" WHERE manager_id = "+this.manager_id+";";
            stmt.executeUpdate(set_genre_music_sum);
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean manager_insert(){
        music mu = new music();
        try{
            String root_check_query = "SELECT nickname FROM manager WHERE manager.nickname = 'root'";
            rs = stmt.executeQuery(root_check_query);
            if(!rs.next())
            {
                System.out.println(manager_name + " is not root manager so, you can't insert manager!");
                return false;
            }

            System.out.println("Please type manager information");
            System.out.println("1. manage Genre");
            String insert_manage_genre = in.readLine();
            System.out.println("2. nickname");
            String insert_manager_name = in.readLine();
            System.out.println("3. password");
            String insert_manager_psw = in.readLine();

            String manager_exist_check = "SELECT nickname FROM manager WHERE nickname = '"+insert_manager_name+"' and password = '"+insert_manager_psw+"' and genre = '"+insert_manage_genre+"' ;";
            rs = stmt.executeQuery(manager_exist_check);
            if(rs.next()){
               System.out.println(rs.getString("nickname") + " is already exist manager!!");
               return false;
            }

            int genre_music_nums = mu.get_music_num_with_genre(insert_manage_genre);
            String manager_insert_query = "INSERT manager(genre, nickname, password, genre_music_sum) VALUES('"+insert_manage_genre+"', '"+insert_manager_name+"', '"+insert_manager_psw+"', '"+genre_music_nums+"')";
            stmt.executeUpdate(manager_insert_query);
            System.out.println("manager insert success");
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public void logout(){
        this.manage_genre = null;
        this.manager_name = null;
        this.genre_music_sum = 0;
        this.manager_id = 0;
    }

    public int get_manager_id_with_name(String manager_name){
        try {
            String temp = "SELECT manager_id FROM manager WHERE manager.nickname = '" + manager_name + "' ;";
            rs = stmt.executeQuery(temp);
            while(rs.next()){
                return rs.getInt("manager_id");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return -1;
    }

    public int get_manager_id_with_genre(String genre){
        try {
            String temp = "SELECT manager_id FROM manager WHERE manager.genre = '" + genre + "' ;";
            rs = stmt.executeQuery(temp);
            while (rs.next()){
                return rs.getInt("manager_id");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return -1;
    }
}
