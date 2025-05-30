package Model.Data;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ModelTable extends AbstractTableModel {
    List<ModelData> daftarData;

    String kolom[] = {"ID", "Nama", "Tanggal Lahir"};
    public ModelTable(List<ModelData> daftarData) {
        this.daftarData = daftarData;
    }

    @Override
    public int getRowCount() {
        return daftarData.size();
    }

    @Override
    public int getColumnCount() {
        return kolom.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return daftarData.get(rowIndex).getId_data();
            case 1:
                return daftarData.get(rowIndex).getNama();
            case 2:
                return daftarData.get(rowIndex).getTanggal();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return kolom[column];
    }
}
