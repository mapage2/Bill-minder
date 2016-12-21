package com.example.amasio.bill_minder;

/**
 * Created by amasio on 12/10/16.
 */

public class User {

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    private String displayName;

    public User(){
        setFirstName("");
        setLastName("");
        setEmail("");
        setPassword("");
    }

    public User(String fName, String lName, String email, String pWord){
        setFirstName(fName);
        setLastName(lName);
        this.setEmail(email);
        setPassword(pWord);
    }

    public User(String name, String email){
        this.setDisplayName(name);
        this.setEmail(email);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDisplayName(){
        return displayName;
    }

    public void setDisplayName(String name){
        this.displayName = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString(){

        String user = getFirstName() +" "+getLastName()+" "+getEmail()+" "+getPassword();
        return user;

    }
}
