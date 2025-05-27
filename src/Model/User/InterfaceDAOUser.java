package Model.User;

import java.util.List;

public interface InterfaceDAOUser {
    public void insert(ModelUser user); // dipergunakan untuk melakukan register
    public List<ModelUser> getUser(String username, String password); // dipergunakan untuk login
    public List<ModelUser> getUserbyUsername(String username); //dipergunakan untuk register
}
