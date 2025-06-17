package Controller;

import Model.Data.DAOData;
import Model.Data.InterfaceDAOData;
import Model.Data.ModelData;
import View.Menu.AddView;
import View.Menu.EditView;
import View.Menu.MenuView;
import View.Menu.PrediksiView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ControllerData {

    MenuView halamanMenu;
    EditView halamanEdit;
    AddView halamanAdd;
    PrediksiView halamanPrediksi;

    InterfaceDAOData daodata;
    List<ModelData> daftarData;

    public ControllerData(MenuView halamanMenu) {
        this.halamanMenu = halamanMenu;
        this.daodata = new DAOData();
    }

    public ControllerData(EditView halamanEdit) {
        this.halamanEdit = halamanEdit;
        this.daodata = new DAOData();
    }

    public ControllerData(AddView halamanAdd) {
        this.halamanAdd = halamanAdd;
        this.daodata = new DAOData();
    }

    public ControllerData(PrediksiView halamanPrediksi) {
        this.halamanPrediksi = halamanPrediksi;
        this.daodata = new DAOData();
    }

    // Mengambil data dan mengirimkannya ke MenuView untuk ditampilkan
    public void showAllData() {
        daftarData = daodata.getAll(halamanMenu.getIdUser());
        halamanMenu.showAllData(daftarData); // Memanggil method di view untuk menampilkan data
    }

    // Method baru untuk menghapus data
    public void deleteData(int id) {
        daodata.delete(id);
    }

    public ModelData insertData(int idUser) {
        try {
            ModelData data = new ModelData();
            String nama = halamanAdd.getInputNama();
            String tanggal = halamanAdd.getInputTanggal();
            if ("".equals(nama) || "".equals(tanggal)) {
                throw new Exception("Nama atau Tanggal tidak boleh kosong!");
            }
            boolean ok = true;
            for (int i = 0; i < nama.length(); i++) {
                char cek = nama.charAt(i);
                if (cek >= '0' && cek <= '9') {
                    ok = false;
                }
            }
            if (!ok) {
                throw new Exception("Nama tidak boleh ada angka");
            }

            LocalDate date = LocalDate.now();
            LocalDate input = LocalDate.parse(tanggal, DateTimeFormatter.ISO_LOCAL_DATE);
            if (input.isAfter(date)) {
                throw new Exception("Bro Lahir Duluan");
            }
            data.setNama(nama);
            data.setTanggal(tanggal);
            daodata.insert(data, idUser);

            int id_data = daodata.cekIdDataAfterInput(data);
            data.setId_data(id_data);
            insertPrediksi(data);
            return data;
        } catch (Exception e) {
            System.out.println("Error : " + e.getLocalizedMessage());
        }
        return null;
    }

    public boolean updateData(int id_data) {
        try {
            ModelData data = new ModelData();
            String nama = halamanEdit.getInputNama();
            String tanggal = halamanEdit.getInputTanggal();
            if ("".equals(nama) || "".equals(tanggal)) {
                throw new Exception("Nama atau Tanggal tidak boleh kosong!");
            }
            boolean ok = true;
            for (int i = 0; i < nama.length(); i++) {
                char cek = nama.charAt(i);
                if (cek >= '0' && cek <= '9') {
                    ok = false;
                }
            }
            if (!ok) {
                throw new Exception("Nama tidak boleh ada angka");
            }

            LocalDate date = LocalDate.now();
            LocalDate input = LocalDate.parse(tanggal, DateTimeFormatter.ISO_LOCAL_DATE);
            if (input.isAfter(date)) {
                throw new Exception("Bro Lahir Duluan");
            }
            data.setNama(nama);
            data.setTanggal(tanggal);
            data.setId_data(id_data);

            daodata.update(data);
            insertPrediksi(data);
            return true;
        } catch (Exception e) {
            System.out.println("Error : " + e.getLocalizedMessage());
        }
        return false;
    }

    public void insertPrediksi(ModelData data) {
        try {
            int day = 0;
            int month = 0;
            String zodiac = "";
            String nama_data = data.getNama();
            char huruf_awal = nama_data.charAt(0);
            if (huruf_awal >= 'a' && huruf_awal <= 'z') {
                String tes = String.valueOf(huruf_awal).toUpperCase();
                huruf_awal = tes.charAt(0);
            }

            String tanggalLahirStr = data.getTanggal();

            if (tanggalLahirStr.matches("\\d{4}-\\d{2}-\\d{2}")) { // YYYY-MM-dd
                LocalDate date = LocalDate.parse(tanggalLahirStr, DateTimeFormatter.ISO_LOCAL_DATE);
                day = date.getDayOfMonth();
                month = date.getMonthValue();
            } else if (tanggalLahirStr.matches("\\d{2}-\\d{2}-\\d{4}")) { // dd-MM-yyyy
                LocalDate date = LocalDate.parse(tanggalLahirStr, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                day = date.getDayOfMonth();
                month = date.getMonthValue();
            } else if (tanggalLahirStr.matches("\\d{2}-\\d{2}")) { // dd-MM
                String[] parts = tanggalLahirStr.split("-");
                day = Integer.parseInt(parts[0]);
                month = Integer.parseInt(parts[1]);
            }

            if ((month == 1 && day >= 20) || (month == 2 && day <= 18)) zodiac = "Aquarius";
            if ((month == 2 && day >= 19) || (month == 3 && day <= 20)) zodiac = "Pisces";
            if ((month == 3 && day >= 21) || (month == 4 && day <= 19)) zodiac = "Aries";
            if ((month == 4 && day >= 20) || (month == 5 && day <= 20)) zodiac = "Taurus";
            if ((month == 5 && day >= 21) || (month == 6 && day <= 20)) zodiac = "Gemini";
            if ((month == 6 && day >= 21) || (month == 7 && day <= 22)) zodiac = "Cancer";
            if ((month == 7 && day >= 23) || (month == 8 && day <= 22)) zodiac = "Leo";
            if ((month == 8 && day >= 23) || (month == 9 && day <= 22)) zodiac = "Virgo";
            if ((month == 9 && day >= 23) || (month == 10 && day <= 22)) zodiac = "Libra";
            if ((month == 10 && day >= 23) || (month == 11 && day <= 21)) zodiac = "Scorpio";
            if ((month == 11 && day >= 22) || (month == 12 && day <= 21)) zodiac = "Sagittarius";
            if ((month == 12 && day >= 22) || (month == 1 && day <= 19)) zodiac = "Capricorn";

            daodata.insertZodiac(data.getId_data(), zodiac);
            daodata.insertHuruf(data.getId_data(), huruf_awal);
        } catch (Exception e) {
            System.out.println("Error : " + e.getLocalizedMessage());
        }
    }
}