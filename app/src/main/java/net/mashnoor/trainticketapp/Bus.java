package net.mashnoor.trainticketapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bus {

    @SerializedName("busname")
    private String busname;

    @SerializedName("licenseno")
    private String licenseno;

    @SerializedName("company")
    private String company;

    @SerializedName("route")
    private String route;

    public String getBusname() {
        return busname;
    }

    public String getLicenseno() {
        return licenseno;
    }

    public String getCompany() {
        return company;
    }

    public String getRoute() {
        return route;
    }
}