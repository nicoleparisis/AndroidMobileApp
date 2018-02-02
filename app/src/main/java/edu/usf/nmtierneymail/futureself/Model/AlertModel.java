package edu.usf.nmtierneymail.futureself.Model;

/**
 * Created by nicoletierney on 10/15/17.
 */

public class AlertModel {
    public int _id;
    public int _userid;
    public int _alertid;

    public AlertModel(){

    }
    public AlertModel(int id, int userid, int alertid) {
        _userid = userid;
        _alertid = alertid;
        _id = id;
    }

}
