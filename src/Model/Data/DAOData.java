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
    public ModelPrediksi getData(int idData){
        ModelPrediksi modelPrediksi = new ModelPrediksi();
        try{
            String query = "SELECT * FROM prediksi WHERE id_data=?;";
            PreparedStatement statement;
            statement = Connector.Connect().prepareStatement(query);
            statement.setInt(1, idData);
            ResultSet rs = statement.executeQuery();
            int i = 0;
            while(rs.next()){
                int id = rs.getInt("id_referensi");
                String masuk = "SELECT persona_text FROM referensi WHERE id_referensi=?;";
                PreparedStatement statement2;
                statement2 = Connector.Connect().prepareStatement(masuk);
                statement2.setInt(1, id);
                ResultSet rs2 = statement2.executeQuery();
                while(rs2.next()){
                    String text = rs2.getString("persona_text");
                    if(i == 0){
                         modelPrediksi.setZodiac(text);
                    }else{
                        modelPrediksi.setHuruf(text);
                    }
                }
                statement2.close();
                i++;
            }
            statement.close();
            return modelPrediksi;
        } catch (Exception e) {
            System.out.println("Error : " + e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public void insertPrediksi(int idData, int kode) {
        try{
            String Query = "INSERT INTO prediksi(id_data, id_referensi) VALUES(?,?);";
            PreparedStatement statement;
            statement = Connector.Connect().prepareStatement(Query);
            statement.setInt(1, idData);
            statement.setInt(2, kode);
            statement.executeUpdate();
            statement.close();
        } catch (Exception e) {
            System.out.println("Error : " + e.getLocalizedMessage());
        }
    }

    @Override
    public void insertZodiac(int idData, String zodiac) {
        int kode = 0;
        try{
            String query = "SELECT * FROM referensi WHERE tipe = 'zodiac' AND `key`=?;";
            PreparedStatement statement;
            statement = Connector.Connect().prepareStatement(query);
            statement.setString(1, zodiac);

            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                kode = rs.getInt("id_referensi");
            }
            insertPrediksi(idData, kode);
            statement.close();
        } catch (Exception e) {
            System.out.println("Error : " + e.getLocalizedMessage());
        }
    }

    @Override
    public void insertHuruf(int idData, char huruf) {
        int kode = 0;
        try{
            String query = "SELECT * FROM referensi WHERE tipe = 'huruf_awal' AND `key`=?;";
            PreparedStatement statement;
            statement = Connector.Connect().prepareStatement(query);
            statement.setString(1, String.valueOf(huruf));

            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                kode = rs.getInt("id_referensi");
            }
            insertPrediksi(idData, kode);
            statement.close();
        } catch (Exception e) {
            System.out.println("Error : " + e.getLocalizedMessage());
        }
    }

    @Override
    public int cekIdDataAfterInput(ModelData data) {
        ModelData dataBaru = new ModelData();
        try{
            int ada = 0;
            Statement statement = Connector.Connect().createStatement();
            String query = "SELECT id_data FROM data ORDER BY id_data DESC LIMIT 1;";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                dataBaru.setId_data(resultSet.getInt("id_data"));
                ada = resultSet.getInt("id_data");
            }
            statement.close();
            return ada;
        } catch (Exception e) {
            System.out.println("Error : " + e.getLocalizedMessage());
        }
        return 0;
    }

    @Override
    public void insert(ModelData data, int idUser) {
        try {
            String query = "INSERT data (id_user, nama, tanggal_lahir) VALUES (?,?,?);";
            PreparedStatement statement;
            statement = Connector.Connect().prepareStatement(query);
            statement.setInt(1, idUser);
            statement.setString(2, data.getNama());
            statement.setString(3, data.getTanggal());

            statement.executeUpdate();

            statement.close();
        } catch (SQLException e) {
            System.out.println("update Failed! (" + e.getMessage() + ")");
        }
    }

    @Override
    public void update(ModelData data) {
        try {
            deletePrediksi(data.getId_data());
            String query = "UPDATE data SET nama=?, tanggal_lahir=? WHERE id_data=?;";

            PreparedStatement statement;
            statement = Connector.Connect().prepareStatement(query);
            statement.setString(1, data.getNama());
            statement.setString(2, data.getTanggal());
            statement.setInt(3, data.getId_data());

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
            deletePrediksi(id);
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
    public void deletePrediksi(int id){
        try{
            String query = "DELETE FROM prediksi WHERE id_data=?;";
            PreparedStatement statement;
            statement = Connector.Connect().prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();
            statement.close();
        }catch (Exception e){
            System.out.println("Error : " + e.getLocalizedMessage());
        }
    }

    @Override
    public List<ModelData> getAll(int user_id) {
        List<ModelData> Data = null;

        try{
            Data = new ArrayList<>();
            String query = "SELECT nama,tanggal_lahir FROM data where id_user=?;";

            PreparedStatement statement;
            statement = Connector.Connect().prepareStatement(query);
            statement.setInt(1,user_id);

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                ModelData dt = new ModelData();
                dt.setNama(resultSet.getString("nama"));
                dt.setTanggal(resultSet.getString("tanggal_lahir"));
                dt.setPrediksi("Lihat");

                Data.add(dt);
            }
            statement.close();
        }catch(Exception e){
            System.out.println("Error: " + e.getLocalizedMessage());
        }

        return Data;
    }
}
