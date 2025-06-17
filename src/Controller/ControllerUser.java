package Controller;

import Model.User.DAOUser;
import Model.User.InterfaceDAOUser;
import Model.User.ModelUser;
import View.Form.LoginView;
import View.Form.RegisterView;
// import View.MainView; // Kalo gak kepake di sini, bisa dikomen/hapus
import View.Menu.MenuView;
import View.Menu.CustomDialogView; // Sesuaikan path package-nya
import javax.swing.ImageIcon; // Buat background image
import java.awt.Font; // Buat font

import javax.swing.*;
import java.util.List;

public class ControllerUser {

    // melakukan yang namanya login register
    /*
    1. ketika user ingin melakukan register, dicek terlebih dahulu apakah nama sudah ada atau blom
    2. ketika user mengakses login form, pastikan user dapat kembali ke form sebelumnya, dan juga
    user dapat mengakses register form dari login form
    3. ketika user melakukan login, dicek terlebih dahulu username dan passwordnya sesuai atau tidak,
    jika iya maka program akan memanggil view menu dengan parameter constructor adalah modeluser, // INI YANG MAU KITA LAKUKAN
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
        Font helveticaFontForDialog = new Font("Helvetica", Font.PLAIN, 14); // Bikin instance font
        ImageIcon dialogBg = null;
        try {
            java.net.URL bgUrl = ControllerUser.class.getResource("/resources/BackgroundMainView.png"); // Pake class ControllerUser buat getResource
            if (bgUrl != null) {
                dialogBg = new ImageIcon(bgUrl);
            } else {
                System.err.println("Background image GAK KETEMU buat dialog di ControllerUser!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try{
            ModelUser userDariForm = new ModelUser(); // User dari inputan form
            List<ModelUser> hasilCekUserDb = null;

            String nama = halamanLogin.getNama();
            String pass = halamanLogin.getPassword();

            if("".equals(nama) || "".equals(pass)){
                throw new Exception("Nama dan password tidak kosong");
            }

            userDariForm.setNama(nama);
            userDariForm.setPass(pass);

            hasilCekUserDb = daouser.getUser(userDariForm); // Cek user ke DB

            if(hasilCekUserDb.isEmpty()){
                JOptionPane.showMessageDialog(null, "Nama atau Password salah", "Error", JOptionPane.ERROR_MESSAGE);
                halamanLogin.Reset();
            }else{
                // --- PERUBAHAN DI SINI ---
                // Ambil objek ModelUser yang berhasil login dari list
                ModelUser userYangLogin = hasilCekUserDb.get(0);

                CustomDialogView.showStyledMessageDialog(
                        halamanLogin, // parent component (bisa null atau frame/dialog yg aktif)
                        "Anda Berhasil Melakukan Login", // message
                        "Login Sukses", // title dialog
                        JOptionPane.INFORMATION_MESSAGE, // message type
                        dialogBg, // background image
                        helveticaFontForDialog // font
                );
                halamanLogin.dispose();
                // Panggil MenuView dengan ngasih data userYangLogin
                new MenuView(userYangLogin).setVisible(true);
                // --- AKHIR PERUBAHAN ---
            }

        }catch(Exception e){
            System.out.println("Error Login: " + e.getLocalizedMessage()); // Pesan error lebih spesifik
        }
    }

    /*
    Berikut list fungsi untuk bagian register
     */

    public void cekRegister(){
        try{
            ModelUser userDariForm = new ModelUser(); // User dari inputan form
            List<ModelUser> cekNamaSama = null;

            String nama = halamanRegister.getNama();
            String pass = halamanRegister.getPassword();

            if("".equals(nama) || "".equals(pass)){
                throw new Exception("Nama dan password tidak kosong");
            }

            userDariForm.setNama(nama);
            userDariForm.setPass(pass);

            cekNamaSama = daouser.getUserbyUsername(userDariForm); // Cek dulu apakah username sudah ada

            if(cekNamaSama.isEmpty()){ // Jika username belum ada, baru insert
                daouser.insert(userDariForm); // Insert user baru

                // --- PERUBAHAN DI SINI ---
                // Setelah insert, kita ambil lagi data user yang baru di-insert itu
                // biar dapet data lengkapnya (termasuk ID kalo ada auto-increment)
                // Kita bisa pake getUserbyUsername lagi karena username-nya unik
                List<ModelUser> userBaruList = daouser.getUserbyUsername(userDariForm);
                ModelUser userYangBaruDaftar = null;
                if (!userBaruList.isEmpty()) {
                    userYangBaruDaftar = userBaruList.get(0);
                } else {
                    // Fallback jika karena suatu hal user tidak ditemukan setelah insert
                    // Ini seharusnya tidak terjadi jika insert berhasil & username unik
                    // Kita bisa set nama dari form aja untuk sementara
                    userYangBaruDaftar = new ModelUser();
                    userYangBaruDaftar.setNama(nama);
                    System.out.println("Warning: User baru tidak ditemukan setelah insert, hanya nama yang dikirim ke MenuView.");
                }
                // --- AKHIR PERUBAHAN ---

                JOptionPane.showMessageDialog(null, "Anda Berhasil Melakukan Registrasi");
                halamanRegister.dispose();
                // Panggil MenuView dengan ngasih data userYangBaruDaftar
                new MenuView(userYangBaruDaftar).setVisible(true);

            }else{ // Jika username sudah ada
                JOptionPane.showMessageDialog(null, "Nama sudah ada", "Error", JOptionPane.ERROR_MESSAGE);
                halamanRegister.Reset();
            }
        }catch(Exception e){
            System.out.println("Error Register: " + e.getLocalizedMessage()); // Pesan error lebih spesifik
        }
    }
}