package View.Menu;

import Model.Data.DAOData;
import Model.Data.ModelPrediksi;

public class PrediksiView {

    // ini pake id_data untuk prediksi view, jadi ntar diambil datanya disini
    public PrediksiView(int id_data) {
        ModelPrediksi model = new ModelPrediksi();
        DAOData dao = new DAOData();
        model = dao.getData(id_data);
        System.out.println(model.getZodiac());
        System.out.println(model.getHuruf());
    }

    public static void main(String[] args) {
        new PrediksiView(13);
    }
}
