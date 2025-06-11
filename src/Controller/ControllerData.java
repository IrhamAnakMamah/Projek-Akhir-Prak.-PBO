// irhamanakmamah/projek-akhir-prak.-pbo/Projek-Akhir-Prak.-PBO-master/src/Controller/ControllerData.java
package Controller;

import Model.Data.DAOData;
import Model.Data.InterfaceDAOData;
import Model.Data.ModelData;
import View.Menu.AddView;
import View.Menu.EditView;
import View.Menu.MenuView;
import View.Menu.PrediksiView;

import java.util.List;
import javax.swing.JOptionPane;

public class ControllerData {

    MenuView halamanMenu;
    EditView halamanEdit;
    AddView halamanAdd;
    PrediksiView halamanPrediksi;

    InterfaceDAOData daodata;

    // --- PERUBAHAN DI SINI ---
    // Kita gak perlu lagi List<ModelData> di sini, biar datanya selalu fresh dari DB
    // List<ModelData> daftarData;

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

    public void showAllData() {
        // Ambil data terbaru dari DAO
        List<ModelData> daftarData = daodata.getAll(halamanMenu.getIdUser());
        // Kirim data ini ke method baru di MenuView buat nampilin
        halamanMenu.showAllData(daftarData);
    }

    public void insertData(ModelData data) {
        daodata.insert(data);
        JOptionPane.showMessageDialog(halamanAdd, "Data baru berhasil dijokul!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
    }

    public void updateData(ModelData data) {
        daodata.update(data);
        JOptionPane.showMessageDialog(halamanEdit, "Data berhasil di-update!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
    }

    public void deleteData(int id) {
        daodata.delete(id);
    }
}