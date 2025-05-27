package Model.Data;

import Database.Connector;
import Model.User.ModelUser;

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
            String query = "INSERT data (id_user, nama, tanggal_lahir, prediksi) VALUES (?,?,?,?);";

            PreparedStatement statement;
            statement = Connector.Connect().prepareStatement(query);
            statement.setInt(4, data.getId_user());
            statement.setString(1, data.getNama());
            statement.setString(2, data.getTanggal());
            statement.setString(3, data.getPrediksi());

            statement.executeUpdate();

            statement.close();
        } catch (SQLException e) {
            System.out.println("update Failed! (" + e.getMessage() + ")");
        }
    }

    @Override
    public void update(ModelData data) {
        try {
            String query = "UPDATE data SET nama=?, tanggal_lahir=?, prediksi=? WHERE id=?;";

            PreparedStatement statement;
            statement = Connector.Connect().prepareStatement(query);
            statement.setString(1, data.getNama());
            statement.setString(2, data.getTanggal());
            statement.setString(3, data.getPrediksi());
            statement.setInt(4, data.getId_data());

            // Menjalankan query untuk menghapus data mahasiswa yang dipilih
            statement.executeUpdate();

            statement.close();
        } catch (SQLException e) {
            System.out.println("update Failed! (" + e.getMessage() + ")");
        }
    }

    @Override
    public void delete(int id) {
        try {
            String query = "DELETE FROM data WHERE id=?;";

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
        List<ModelData> Data = null;

        try{
            Data = new ArrayList<>();
            String query = "SELECT * FROM data where user_id=?;";

            PreparedStatement statement;
            statement = Connector.Connect().prepareStatement(query);
            statement.setInt(1,user_id);

            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()){
                ModelData dt = new ModelData();
                dt.setNama(resultSet.getString("username"));
                dt.setId_user(resultSet.getInt("id_user"));
                dt.setId_data(resultSet.getInt("id_data"));
                dt.setPrediksi(resultSet.getString("prediksi"));
                dt.setTanggal(resultSet.getString("tanggal"));

                Data.add(dt);
            }
            statement.close();
        }catch(Exception e){
            System.out.println("Error: " + e.getLocalizedMessage());
        }

        return Data;
    }
}
