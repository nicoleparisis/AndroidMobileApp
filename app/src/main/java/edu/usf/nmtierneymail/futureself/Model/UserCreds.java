package edu.usf.nmtierneymail.futureself.Model;

/**
 * Created by nicoletierney on 9/24/17.
 */

public class UserCreds {

    public int _id;
    public String _firstname;
    public String _lastname;
    public String _username;
    public String _password;


    public UserCreds(){

    }
   public UserCreds(int id, String firstname, String lastname, String username, String password) {
       _firstname = firstname;
        _lastname = lastname;
        _username = username;
        _password = password;
        _id = id;
    }

}
