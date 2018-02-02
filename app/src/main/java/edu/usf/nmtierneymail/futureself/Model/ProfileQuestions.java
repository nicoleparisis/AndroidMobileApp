package edu.usf.nmtierneymail.futureself.Model;

/**
 * Created by nicoletierney on 10/2/17.
 */

public class ProfileQuestions {

    public int _id;
    public String _questiontext;

    public ProfileQuestions(){

    }
    public ProfileQuestions(int id, String questiontext) {
       _questiontext = questiontext;
        _id = id;
    }
}
