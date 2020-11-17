package com.example.doorlocksystem;

public class fruit {
    String name;
    Boolean Checked;
    int type;

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Boolean getChecked() {
        return Checked;
    }

    public fruit(String name, Boolean checked, int type) {
        this.name = name;
        Checked = checked;
        this.type = type;
    }
}
