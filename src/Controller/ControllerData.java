package Controller;

import Model.Data.DAOData;
import Model.Data.InterfaceDAOData;
import Model.Data.ModelData;
import Model.Data.ModelTable;
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
        this.halamanAdd = ControllerData.this.halamanAdd;
        this.daodata = new DAOData();
    }
    public ControllerData(PrediksiView halamanPrediksi) {
        this.halamanPrediksi = ControllerData.this.halamanPrediksi;
        this.daodata = new DAOData();
    }

    public void showAllData() {
        daftarData = daodata.getAll(halamanMenu.getIdUser());
        ModelTable table = new ModelTable(daftarData);
        halamanMenu.getTableData().setModel(table);
    }


}
