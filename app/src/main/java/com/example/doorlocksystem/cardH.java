package com.example.doorlocksystem;

public class cardH {
    String Time, Id, Direc, Res;

    public String getTime() {
        return Time;
    }

    public String getId() {
        return Id;
    }

    public String getDirec() {
        return Direc;
    }

    public String getRes() {
        return Res;
    }

    public cardH(String time, String id, String direc, String res) {
        Time = time;
        Id = id;
        Direc = direc;
        Res = res;
    }
}
