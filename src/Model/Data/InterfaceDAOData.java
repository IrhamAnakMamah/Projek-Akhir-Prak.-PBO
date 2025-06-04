package Model.Data;

import java.util.List;

public interface InterfaceDAOData {
    public void insert(ModelData data, int idUser);
    public void update(ModelData data);
    public void delete(int id);
    public List<ModelData> getAll(int id_user);
    public int cekIdDataAfterInput(ModelData data);
    public void insertZodiac(int idData, String zodiac);
    public void insertHuruf(int idData, char huruf);
    public void insertPrediksi(int idData, int kode);
    public void deletePrediksi(int idData);
}