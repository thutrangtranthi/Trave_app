package models;

public class Hotel {
    private int id;
    private String name;
    private String image;
    private String phone;
    private String price;
    private int idDiaDanh;

    public Hotel(){}

    public Hotel(int id, String name, String image, String phone, String price, int idDiaDanh) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.phone = phone;
        this.price = price;
        this.idDiaDanh = idDiaDanh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getIdDiaDanh() {
        return idDiaDanh;
    }

    public void setIdDiaDanh(int idDiaDanh) {
        this.idDiaDanh = idDiaDanh;
    }
}
