package Model.Data;

import java.util.List;

public interface InterfaceDAOData {
    public void insert(ModelData data);
    public void update(ModelData data);
    public void delete(int id);
    public List<ModelData> getAll(int id_user);
}