package com.example.my51_iot_esp8266.Dto;

/**
 * Created by LG on 2017-02-08.
 */

public class MyItem {
    public String id;
    public String name;
    public String value;
    public String updatetime;

    public MyItem(String id, String name, String value, String updatetime) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.updatetime = updatetime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }
}
