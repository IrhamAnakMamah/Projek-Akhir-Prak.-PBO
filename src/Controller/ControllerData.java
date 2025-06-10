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
    // Constructor lain ini sebenarnya tidak perlu jika controller hanya dibuat di MenuView
    // Tapi kita biarkan saja sesuai struktur awal lu
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

    public void showAllData() {
        halamanMenu.clearDataList();
        daftarData = daodata.getAll(halamanMenu.getIdUser());

        if (daftarData != null && !daftarData.isEmpty()) {
            for (ModelData data : daftarData) {
                halamanMenu.addDataRow(data);
            }
        }

        halamanMenu.revalidate();
        halamanMenu.repaint();
    }

    // --- METHOD BARU YANG BIKIN TOMBOLNYA JALAN ---
    public void insertData(ModelData data) {
        // Validasi sudah ada di AddView, jadi di sini kita langsung eksekusi
        daodata.insert(data);
        JOptionPane.showMessageDialog(halamanAdd, "Data baru berhasil dijokul!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
    }

    public void updateData(ModelData data) {
        // Validasi sudah ada di EditView, jadi di sini kita langsung eksekusi
        daodata.update(data);
        JOptionPane.showMessageDialog(halamanEdit, "Data berhasil di-update!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
    }

    public void deleteData(int id) {
        daodata.delete(id);
        // Bisa tambahin notif sukses juga kalo mau
        // JOptionPane.showMessageDialog(halamanMenu, "Data berhasil dihapus!");
    }
}