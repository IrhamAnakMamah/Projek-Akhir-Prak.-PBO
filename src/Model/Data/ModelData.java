package Model.Data;

public class ModelData {
    private int id_data,id_user;
    private String tanggal,nama;
    private String prediksi;

    public int getId_data() {
        return id_data;
    }

    public void setId_data(int id_data) {
        this.id_data = id_data;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getPrediksi() {
        return prediksi;
    }

    public void setPrediksi(String prediksi) {
        this.prediksi = prediksi;
    }
}
