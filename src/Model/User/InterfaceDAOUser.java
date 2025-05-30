package Model.User;

import java.util.List;

public interface InterfaceDAOUser {
    public void insert(ModelUser user); // dipergunakan untuk melakukan register
    public List<ModelUser> getUser(ModelUser user); // dipergunakan untuk login
    public List<ModelUser> getUserbyUsername(ModelUser user); //dipergunakan untuk register
}
