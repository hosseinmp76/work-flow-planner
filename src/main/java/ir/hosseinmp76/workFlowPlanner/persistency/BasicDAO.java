package ir.hosseinmp76.workFlowPlanner.persistency;

import java.util.List;

import ir.hosseinmp76.workFlowPlanner.model.BaseModel;

public interface BasicDAO<T extends BaseModel> {

    public T createEmptyModel();

    public void delete(T t);

    public T update(T t);

    public T read(Long id);

    public List<T> list(int start, int end);

}
