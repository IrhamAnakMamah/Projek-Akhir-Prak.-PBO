// irhamanakmamah/projek-akhir-prak.-pbo/Projek-Akhir-Prak.-PBO-master/src/Model/Data/ModelTable.java
package Model.Data;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ModelTable extends AbstractTableModel {
    // --- PERUBAHAN DI SINI ---
    // Bikin jadi private biar lebih proper
    public List<ModelData> daftarData;

    String kolom[] = {"Nama", "Tanggal", "Prediksi"};

    public ModelTable(List<ModelData> daftarData) {
        this.daftarData = daftarData;
    }

    // --- DAN TAMBAHAN DI SINI ---
    // Ini "pintu resmi" buat ngambil datanya dari luar paket
    public List<ModelData> getDaftarData() {
        return daftarData;
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
                // Nilai buat kolom prediksi bisa diambil dari model atau diset default
                return daftarData.get(rowIndex).getPrediksi();
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 2; // Hanya kolom "Prediksi" yang bisa diklik
    }

    @Override
    public String getColumnName(int column) {
        return kolom[column];
    }
}