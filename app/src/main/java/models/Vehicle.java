package models;

public class Vehicle {
    private int idPT;
    private String name;


    public Vehicle(){}

    public Vehicle(int idPT, String name) {
        this.idPT = idPT;
        this.name = name;
    }

    public int getId() {
        return idPT;
    }

    public void setId(int id) {
        this.idPT = idPT;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
