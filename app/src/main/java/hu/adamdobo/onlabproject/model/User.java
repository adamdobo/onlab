package hu.adamdobo.onlabproject.model;

/**
 * Created by Ádám on 3/12/2018.
 */

public class User {

    public String ID;
    public String uId;
    public String name;
    public String email;
    public String address;
    public String nickname;


    public User(String name, String email, String address, String nickname) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.nickname = nickname;
    }

    public User(){}
}
