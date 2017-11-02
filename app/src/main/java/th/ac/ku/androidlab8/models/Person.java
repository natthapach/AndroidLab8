package th.ac.ku.androidlab8.models;

/**
 * Created by MegapiesPT on 27/10/2560.
 */

public class Person {
    private String firstname;
    private String lastname;
    private String nickname;

    public Person(String nickname, String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.nickname = nickname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
