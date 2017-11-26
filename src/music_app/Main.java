package music_app;
import com.sun.jmx.snmp.internal.SnmpSubSystem;
import com.sun.org.apache.regexp.internal.RE;

import java.sql.*;
import java.io.Console;
import java.io.*;
public class Main {

    public static void main(String[] args) throws IOException{
	// write your code here

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        String sql = null;
        BufferedReader in
                = new BufferedReader(new InputStreamReader(System.in));
        login log_in = new login();
        works manager_works = new works();
        try{
            Class.forName("org.mariadb.jdbc.Driver").newInstance();
            String url = "jdbc:mariadb://localhost:3306/MUSIC_APP";
            String user = "root";
            String psw = "ehdrb948";
            con = DriverManager.getConnection(url,user,psw);
            stmt = con.createStatement();
            System.out.println("database connection success\n");

            //first start menu
            while(true) {
                System.out.println("First Login to music_app!!\n");

                System.out.println("1. login with manager\n" + "2. login with user\n" + "3. for exit music_app");
                int login_menu;
                login_menu = Integer.parseInt(in.readLine());


                //******** manager **********
                if(login_menu == 1){
                    manager man = new manager();
                    if(man.login()){
                        while(true) {
                            man.menu_print();
                            //1. manage genre print  2. user insert  3. singer insert 4. album insert 5. music insert  5. music delete  7. manager insert  8. logout

                            int command = Integer.parseInt(in.readLine());

                            if (command == 1)
                                man.manage_genre_print();

                            else if (command == 2)
                                man.user_insert();

                            else if (command == 3)
                                man.singer_insert();

                            else if (command == 4)
                                man.album_insert();

                            else if (command == 5)
                                man.music_insert();

                            else if(command == 6)
                                man.music_delete();

                            else if (command == 7)
                                man.manager_insert();

                            else if( command == 8)
                                break;

                            else {
                                System.out.println("Command is wrong Please retype");
                                continue;
                            }
                        }
                    }
                }


                //********* user **********
                if(login_menu == 2){
                    user use = new user();
                    if(use.login()){
                        while(true){
                            use.user_menu();
                            //1. show all playlist\n2. playlist make\n3. playlist delete\n4. insert music to playlist\n5. delete music from playlist\n6. search music\n7. logout");

                            int command = Integer.parseInt(in.readLine());

                            if(command == 1)
                                use.show_playlist();
                            else if(command == 2)
                                use.playlist_make();
                            else if(command == 3)
                                use.playlist_delete();
                            else if(command ==4)
                                use.insert_music_to_playlist();
                            else if(command ==5)
                                use.delete_music_from_playlist();
                            else if(command == 6)
                                use.search_musics();
                            else if(command == 7)
                                break;
                            else{
                                System.out.println("command is wrong!!");
                                continue;
                            }
                        }
                    }
                }
            }
        }


        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
