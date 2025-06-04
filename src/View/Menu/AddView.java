package View.Menu;

import Controller.ControllerData;

import java.time.LocalDate;

public class AddView {
    ControllerData controller;

    public AddView(int idUser) {
        controller = new ControllerData(this);
        controller.insertData(idUser);
    }

    public String getInputNama(){
        return "Irham";
    }

    public String getInputTanggal(){
        return "2005-08-05";
    }

    public static void main(String[] args) {
        new AddView(1);
    }
}
