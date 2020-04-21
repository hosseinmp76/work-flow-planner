package ir.hosseinmp76.workFlowPlanner.logic;

import java.util.List;

import org.springframework.stereotype.Component;

import ir.hosseinmp76.workFlowPlanner.model.BaseModel;
import ir.hosseinmp76.workFlowPlanner.persistency.BasicDAO;

public interface BasicMgr<T extends BaseModel> {

    BasicDAO<T> getDAO();

    public default T createEmptyModel() {
	return this.getDAO().createEmptyModel();
    }

    public default void delete(T t) {
	this.getDAO().delete(t);
    }

    public default T update(T t) {
	return this.getDAO().update(t);
    }

    public default T read(Long id) {
	return this.getDAO().read(id);
    }

    public default List<T> list(int start, int end) {
	return this.getDAO().list(start, end);
    }

}
