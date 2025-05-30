package Controller;

import Model.User.DAOUser;
import Model.User.InterfaceDAOUser;
import Model.User.ModelUser;
import View.Form.LoginView;
import View.Form.RegisterView;
import View.MainView;
import View.Menu.MenuView;

import javax.swing.*;
import java.util.List;

public class ControllerUser {

    // melakukan yang namanya login register
    /*
    1. ketika user ingin melakukan register, dicek terlebih dahulu apakah nama sudah ada atau blom
    2. ketika user mengakses login form, pastikan user dapat kembali ke form sebelumnya, dan juga
    user dapat mengakses register form dari login form
    3. ketika user melakukan login, dicek terlebih dahulu username dan passwordnya sesuai atau tidak,
    jika iya maka program akan memanggil view menu dengan parameter constructor adalah modeluser,
    dipergunakan untuk mengambil user_id
    4. user_id dipergunakan untuk mengambil data berupa prediksi
     */

    LoginView halamanLogin;
    RegisterView halamanRegister;

    InterfaceDAOUser daouser;

    public ControllerUser(LoginView halamanLogin) {
        this.halamanLogin = halamanLogin;
        this.daouser = new DAOUser();
    }

    public ControllerUser(RegisterView halamanRegister) {
        this.halamanRegister = halamanRegister;
        this.daouser = new DAOUser();
    }

    /*
    Berikut list fungsi untuk bagian login
     */

    public void cekLogin(){
        try{
            ModelUser user = new ModelUser();
            List<ModelUser> cek = null;

            String nama = halamanLogin.getNama();
            String pass = halamanLogin.getPassword();

            if("".equals(nama) || "".equals(pass)){
                throw new Exception("Nama dan password tidak kosong");
            }

            user.setNama(nama);
            user.setPass(pass);

            cek = daouser.getUser(user);
            if(cek.isEmpty()){
                JOptionPane.showMessageDialog(null, "Nama atau Password salah", "Error", JOptionPane.ERROR_MESSAGE);
                halamanLogin.Reset();
            }else{
                JOptionPane.showMessageDialog(null, "Anda Berhasil Melakukan Login");
                halamanLogin.dispose();
                new MenuView();
            }

        }catch(Exception e){
            System.out.println("Error : " + e.getLocalizedMessage());
        }
    }

    /*
    Berikut list fungsi untuk bagian register
     */

    public void cekRegister(){
        try{
            ModelUser user = new ModelUser();
            List<ModelUser> cek = null;

            String nama = halamanRegister.getNama();
            String pass = halamanRegister.getPassword();

            if("".equals(nama) || "".equals(pass)){
                throw new Exception("Nama dan password tidak kosong");
            }

            user.setNama(nama);
            user.setPass(pass);

            cek = daouser.getUserbyUsername(user);

            if(cek.isEmpty()){
                daouser.insert(user);
                JOptionPane.showMessageDialog(null, "Anda Berhasil Melakukan Registrasi");
                halamanRegister.dispose();
                new MenuView();
            }else{
                JOptionPane.showMessageDialog(null, "Nama sudah ada", "Error", JOptionPane.ERROR_MESSAGE);
                halamanRegister.Reset();
            }
        }catch(Exception e){
            System.out.println("Error : " + e.getLocalizedMessage());
        }
    }
}
