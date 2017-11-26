package music_app;

public class structure {

    int command;
    String command_str;
    String name;
    String password;
    String genre;
    int sum;
    structure(int command, String name, String password, String genre, int music_genre_sum){
        this.command = command;
        this.name = name;
        this.password = password;
        this.genre = genre;
        this.sum = music_genre_sum;
    }
    structure(int command, String manager_name, String manager_password){
        this.command = command;
        this.name = manager_name;
        this.password = manager_password;
    }
    structure(int command, String command_str){
        this.command = command;
        this.command_str = command_str;
    }
}
