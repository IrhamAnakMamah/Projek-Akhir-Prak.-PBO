package Model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import Database.Connector;

public class DAOUser implements InterfaceDAOUser{

    @Override
    public void insert(ModelUser user) {
        try {

            String query = "INSERT INTO user (username, password) VALUES (?, ?);";

            PreparedStatement statement;
            statement = Connector.Connect().prepareStatement(query);
            statement.setString(1, user.getNama());
            statement.setString(2, user.getPass());

            statement.executeUpdate();

            statement.close();
        } catch (SQLException e) {
            System.out.println("Input Failed: " + e.getLocalizedMessage());
        }
    }

    @Override
    public List<ModelUser> getUser(ModelUser user) {
        List<ModelUser> users = null;

        // jika ukuran lebih dari 0, maka login berhasil
        try{
            users = new ArrayList<>();
            String query = "SELECT * FROM user where username =? AND password =?;";

            PreparedStatement statement;
            statement = Connector.Connect().prepareStatement(query);

            statement.setString(1, user.getNama());
            statement.setString(2, user.getPass());

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                ModelUser usr = new ModelUser();
                usr.setNama(resultSet.getString("username"));
                usr.setPass(resultSet.getString("password"));
                usr.setId(resultSet.getInt("id_user"));

                users.add(usr);
            }
            System.out.println("Apalah bang");
            statement.close();
        }catch(Exception e){
            System.out.println("Error: " + e.getLocalizedMessage());
        }

        return users;
    }

    @Override
    public List<ModelUser> getUserbyUsername(ModelUser user) {
        List<ModelUser> users = null;

        // jika ukuran lebih dari 0, maka register gagal dan harus mengulang kembali
        try{
            users = new ArrayList<>();
            String query = "SELECT * FROM user where username =?;";
            PreparedStatement statement;
            statement = Connector.Connect().prepareStatement(query);

            statement.setString(1, user.getNama());

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                ModelUser usr = new ModelUser();
                usr.setNama(resultSet.getString("username"));
                usr.setPass(resultSet.getString("password"));
                usr.setId(resultSet.getInt("id_user"));

                users.add(usr);
            }

            statement.close();
        }catch(Exception e){
            System.out.println("Error: " + e.getLocalizedMessage());
        }

        return users;
    }
}
