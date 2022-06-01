package models;

public class DiaDanh {


    private int idDiaDanh;
    private String nameDiaDanh;
    private String imDiaDanh;
    private String image_int;
    private String city;
    private int favotite;
    private  Vehicle idPT;

    public DiaDanh() {
    }

    public DiaDanh(int idDiaDanh, String nameDiaDanh, String imDiaDanh, String image_int, String city, int favotite, Vehicle idPT) {
        this.idDiaDanh = idDiaDanh;
        this.nameDiaDanh = nameDiaDanh;
        this.imDiaDanh = imDiaDanh;
        this.image_int = image_int;
        this.city = city;
        this.favotite = favotite;
        this.idPT = idPT;
    }


    public int getIdDiaDanh() {
        return idDiaDanh;
    }

    public void setIdDiaDanh(int idDiaDanh) {
        this.idDiaDanh = idDiaDanh;
    }

    public String getNameDiaDanh() {
        return nameDiaDanh;
    }

    public void setNameDiaDanh(String nameDiaDanh) {
        this.nameDiaDanh = nameDiaDanh;
    }

    public String getImDiaDanh() {
        return imDiaDanh;
    }

    public void setImDiaDanh(String imDiaDanh) {
        this.imDiaDanh = imDiaDanh;
    }

    public String getImage_int() {
        return image_int;
    }

    public void setImage_int(String image_int) {
        this.image_int = image_int;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getFavotite() {
        return favotite;
    }

    public void setFavotite(int favotite) {
        this.favotite = favotite;
    }

    public Vehicle getIdPT() {
        return idPT;
    }

    public void setIdPT(Vehicle idPT) {
        this.idPT = idPT;
    }


}
