// irhamanakmamah/projek-akhir-prak.-pbo/Projek-Akhir-Prak.-PBO-master/src/Model/Data/DAOData.java
package Model.Data;

import Database.Connector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAOData implements InterfaceDAOData{
    @Override
    public void insert(ModelData data) {
        try {
            // --- PERUBAHAN: Query disesuaikan dengan skema DB (tanpa 'prediksi') ---
            String query = "INSERT INTO data (id_user, nama, tanggal_lahir) VALUES (?,?,?);";

            PreparedStatement statement;
            statement = Connector.Connect().prepareStatement(query);
            // --- PERUBAHAN: Urutan parameter disesuaikan ---
            statement.setInt(1, data.getId_user());
            statement.setString(2, data.getNama());
            statement.setString(3, data.getTanggal());

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Insert Failed! (" + e.getMessage() + ")");
        }
    }

    @Override
    public void update(ModelData data) {
        try {
            // --- PERUBAHAN: Query disesuaikan (tanpa 'prediksi', WHERE ke id_data) ---
            String query = "UPDATE data SET nama=?, tanggal_lahir=? WHERE id_data=?;";

            PreparedStatement statement;
            statement = Connector.Connect().prepareStatement(query);
            statement.setString(1, data.getNama());
            statement.setString(2, data.getTanggal());
            statement.setInt(3, data.getId_data());

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Update Failed! (" + e.getMessage() + ")");
        }
    }

    @Override
    public void delete(int id) {
        try {
            // --- PERUBAHAN: WHERE ke id_data ---
            String query = "DELETE FROM data WHERE id_data=?;";

            PreparedStatement statement;
            statement = Connector.Connect().prepareStatement(query);
            statement.setInt(1, id);

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Delete Failed: " + e.getLocalizedMessage());
        }
    }

    @Override
    public List<ModelData> getAll(int user_id) {
        List<ModelData> listData = null;
        try{
            listData = new ArrayList<>();
            // --- PERUBAHAN: Ambil id_data juga, ini PENTING! ---
            String query = "SELECT id_data, nama, tanggal_lahir FROM data where id_user=?;";

            PreparedStatement statement;
            statement = Connector.Connect().prepareStatement(query);
            statement.setInt(1,user_id);

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                ModelData dt = new ModelData();
                // --- PERUBAHAN: Set id_data dari hasil query ---
                dt.setId_data(resultSet.getInt("id_data"));
                dt.setNama(resultSet.getString("nama"));
                dt.setTanggal(resultSet.getString("tanggal_lahir"));
                // dt.setPrediksi("Lihat"); // Ini gak perlu lagi, kita handle di view

                listData.add(dt);
            }
            statement.close();
        }catch(Exception e){
            System.out.println("Error get all data: " + e.getLocalizedMessage());
        }
        return listData;
    }
}