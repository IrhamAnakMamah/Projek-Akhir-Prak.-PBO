package Model.Data;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ModelTable extends AbstractTableModel {
    List<ModelData> daftarData;

    String kolom[] = {"Nama", "Tanggal", "Prediksi"};
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
                return daftarData.get(rowIndex).getNama();
            case 1:
                return daftarData.get(rowIndex).getTanggal();
                case 2:
                    return daftarData.get(rowIndex).getPrediksi();
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 2; // Hanya kolom "Prediksi" yang bisa diedit/interaktif
    }

    @Override
    public String getColumnName(int column) {
        return kolom[column];
    }
}
