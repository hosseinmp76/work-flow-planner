package ir.hosseinmp76.workFlowPlanner.persistency;

import java.util.List;

import ir.hosseinmp76.workFlowPlanner.model.BaseModel;
import ir.hosseinmp76.workFlowPlanner.model.Formula;
import ir.hosseinmp76.workFlowPlanner.model.Priority;
import ir.hosseinmp76.workFlowPlanner.model.Property;

public interface BasicDAO<T extends BaseModel> {

    public void delete(T t);

    public T update(T t);

    public T getById(Long id);

    List<T> getAll();

    public T getByName(String formulaName);


    

}
