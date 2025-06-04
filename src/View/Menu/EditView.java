package View.Menu;

import Controller.ControllerData;
import Model.Data.ModelData;

public class EditView {
    ControllerData controller;

    public EditView(ModelData model) {
        controller = new ControllerData(this);
        controller.updateData(13);
    }

    public String getInputNama(){
        return "Ham";
    }

    public String getInputTanggal(){
        return "2005-08-05";
    }

    public static void main(String[] args) {
        ModelData model = new ModelData();
        new EditView(model);
    }
}
