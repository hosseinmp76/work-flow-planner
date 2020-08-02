package ir.hosseinmp76.workFlowPlanner.logic;

import java.util.List;

import ir.hosseinmp76.workFlowPlanner.model.BaseModel;
import ir.hosseinmp76.workFlowPlanner.persistency.BasicDAO;

public abstract class BasicMgr<T extends BaseModel> {

    abstract public T createEmptyModel();

    public void delete(final T t) {
	this.getDAO().delete(t);
    }

    public List<T> getAll() {
	return this.getDAO().getAll();
    }

    protected abstract BasicDAO<T> getDAO();

    public T getById(final Long id) {
	return this.getDAO().getById(id);
    }

    public T getByName(String formulaName) {
	return this.getDAO().getByName(formulaName);
    }

    public T update(final T t) {
	return this.getDAO().update(t);
    }

}
