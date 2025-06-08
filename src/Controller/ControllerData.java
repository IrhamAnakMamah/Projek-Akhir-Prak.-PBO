// irhamanakmamah/projek-akhir-prak.-pbo/Projek-Akhir-Prak.-PBO-master/src/Controller/ControllerData.java
package Controller;

import Model.Data.DAOData;
import Model.Data.InterfaceDAOData;
import Model.Data.ModelData;
// ModelTable udah gak dipake lagi di sini, jadi bisa diapus import-nya
// import Model.Data.ModelTable; 
import View.Menu.AddView;
import View.Menu.EditView;
import View.Menu.MenuView;
import View.Menu.PrediksiView;

import java.util.List;

public class ControllerData {
    // mengontrol flow dari tabel data (tabel data digunakan ketika sudah berhasil login)

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
        // Ini kayaknya ada typo dari kode asli lu, harusnya gini:
        this.halamanAdd = halamanAdd;
        this.daodata = new DAOData();
    }
    public ControllerData(PrediksiView halamanPrediksi) {
        // Sama kayak di atas
        this.halamanPrediksi = halamanPrediksi;
        this.daodata = new DAOData();
    }

    // --- INI METHOD YANG KITA UBAH TOTAL ---
    public void showAllData() {
        // 1. Minta view buat ngebersihin list lamanya dulu
        halamanMenu.clearDataList();

        // 2. Ambil data terbaru dari database
        daftarData = daodata.getAll(halamanMenu.getIdUser());

        // 3. Looping semua data yang didapet
        for (ModelData data : daftarData) {
            // 4. Minta view buat nambahin satu baris baru untuk setiap data
            halamanMenu.addDataRow(data);
        }

        // 5. Suruh view buat refresh tampilan, biar data barunya muncul
        halamanMenu.revalidate();
        halamanMenu.repaint();
    }
}