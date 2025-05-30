package Main;

import Model.User.DAOUser;
import Model.User.ModelUser;

import java.util.ArrayList;
import java.util.List;

public class testing {
    public static void main(String[] args) {
        List<ModelUser> users = null;
        DAOUser daouser = new DAOUser();
        ModelUser user = new ModelUser();
        user.setNama("Irham");
        user.setPass("admin123");
        System.out.println("TEST....");
        users = daouser.getUserbyUsername(user);
        System.out.println("user : " + users.size());
    }
}
