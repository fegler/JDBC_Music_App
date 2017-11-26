package music_app;
import java.io.*;
public class works {

    BufferedReader in
            = new BufferedReader(new InputStreamReader(System.in));
    String sql = null;
    int manager_menu(String nickname) {
        System.out.println(nickname+" manager works menu");
        System.out.println("-----------------------");
        System.out.println("1. manage genre print\n" + "2. user insert\n" + "3. music insert\n" + "4. music delete\n" + "5. mangager insert\n" + "6. logout\n");
        System.out.println("-----------------------");
        try {
            int answer = Integer.parseInt(in.readLine());
            return answer;
            //else if()
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    int user_menu(String name){
        System.out.println(name+" user works menu");
        System.out.println("-------------------------");
        System.out.println("1. Make playlist\n2. show all playlists\n3. search playlist with playlist name\n4. insert music to playlist");
        System.out.println("-------------------------");
        try {
            int answer = Integer.parseInt(in.readLine());
            return answer;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    String user_already_exist_check_query(int usr_age, String usr_name, String usr_sex, String usr_psw){

        String user_exist_query = "SELECT * FROM user WHERE user.age = '" + usr_age + "' and user.name = '" + usr_name + "' and user.sex = '" + usr_sex + "' and user.password = '" + usr_psw + "'";
        return user_exist_query;
    }

    String user_insert_query(int age, String name, String sex, String password){
        String insert_user_sql = "INSERT user(age,name,sex,password) VALUES ("+age+", '"+name+"', '"+sex+"', '"+password+"')";
        return insert_user_sql;
    }


    String print_genre_works(String nickname){
        String print_genre_sql = "SELECT genre FROM manager WHERE manager.nickname = '"+nickname+"'";
        return print_genre_sql;
    }

   // String music_exist_check(String genre, String name, int manager_id, int singer_id, int album_id){
     //   String music_exist_query = "SELECT * FROM music,manager,singer,album WHERE music.genre = '"+genre+"' and music.manager_id";
    //    return music_exist_query;
    //}

    String get_manager_id(String genre){
        String temp = "SELECT manager_id FROM manager WHERE manager.genre = '"+genre+"' ;";
        return temp;
    }

    String get_singer_id(String name){
        String temp = "SELECT singer_id FROM singer WHERE singer.name = '"+name+ "' ;";
        return temp;
    }

    String get_album_id(String name){
        String temp = "SELECT album_id FROM album WHERE album.name = '"+name+"' ;";
        return temp;
    }

    String music_exist_check_query (String genre, int playtime, String name, int manager_id, int singer_id, int album_id){
        String temp = "SELECT music.name FROM music,manager,singer,album WHERE music.genre = '"+genre+"' and music.name = '"+name+"' and manager_id = '"+manager_id+"' and singer_id = '"+singer_id+"' and album_id = '"+album_id+"' ;";
        return temp;
    }

    String music_insert_query (String genre, int playtime, String name, int manager_id, int singer_id, int album_id){
        String temp = "INSERT music(genre, playtime, name, manager_id, singer_id, album_id) VALUES('"+genre+"', "+playtime+", '"+name+"', "+manager_id+", "+singer_id+", "+album_id+");";
        return temp;
    }

    String get_music_with_genre (String genre){
        String temp = "SELECT genre,playtime,music.name,singer.name,album.name FROM music,album,singer WHERE music.genre = '"+genre+"' ;";
        return temp;
    }

    String get_music_with_music_name(String name){
        String temp = "SELECT music_id,genre,playtime,music.name,singer.name,album.name FROM music,album,singer WHERE music.name = '"+name+"' ORDER BY music_id;";
        return temp;
    }

    String get_music_with_music_singer(String name){
        String temp = "SELECT music_id,genre,playtime,music.name,singer.name,album.name FROM music,album,singer WHERE singer.name = '"+name+"' ORDER BY music_id;";
        return temp;
    }

    String get_music_with_music_album(String name){
        String temp = "SELECT music_id,genre,playtime,music.name,singer.name,album.name FROM music,album,singer WHERE album.name = '"+name+"' ORDER BY music_id;";
        return temp;
    }

    String delete_music(int delete_music_id){
        String temp = "DELETE FROM music WHERE music_id = '"+delete_music_id+"' ;";
        return temp;
    }

    String root_manager_check(){
        String temp = "SELECT nickname FROM manager WHERE manager.nickname = 'root'";
        return temp;
    }

    String manager_exist_check(String manager_nickname, String manager_password, String manager_genre){
        String temp = "SELECT nickname FROM manager WHERE nickname = '"+manager_nickname+"' and password = '"+manager_password+"' and genre = '"+manager_genre+"' ;";
        return temp;
    }
    String manager_insert(String nickname, String password, String genre){
        String temp = "INSERT manager(genre, nickname, password, genre_music_sum) VALUES('"+genre+"', '"+nickname+"', '"+password+"', '0')";
        return temp;
    }
    String playlist_exist_check(int user_id, String list_name){
        String temp = "SELECT list_name FROM user,playlist WHERE list_name = '"+list_name+"' and user_id = '"+user_id+"' ;";
        return temp;
    }
    String insert_playlist(String list_name, int user_id){
        String temp = "INSERT playlist(list_name, music_sum, user_id) VALUES ( '"+list_name+"', 0, "+user_id+");";
        return temp;
    }
}
