package com.example.fpt.busstation.data.conn;

/**
 * Created by cuong on 11/1/2017.
 */

public class ApiURL {
    public static String getDomain(){
        return "http://"+ ApiContansts.URL_ROOT+":81/voicebus/api.php";
    }
}
