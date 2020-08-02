package ir.hosseinmp76.workFlowPlanner.persistency.memory;

import java.util.List;

import ir.hosseinmp76.workFlowPlanner.model.BaseModel;
import ir.hosseinmp76.workFlowPlanner.persistency.BasicDAO;

public interface BasicInMemoryDAO<T extends BaseModel> extends BasicDAO<T> {
    default T createEmptyModel() {
	final var pro = this
		.emptyObjectFactory(this.getPopulationAndIncrease());
	this.getAll().add(pro);
	return pro;
    }

    @Override
    default void delete(final T t) {
	this.getAll().replaceAll(pro -> {
	    if (pro.getId().equals(t.getId())) {
		return null;
	    }
	    return pro;
	});

    }

//    protected abstract T emptyObjectFactory();

    T emptyObjectFactory(Long id);

    long getPopulationAndIncrease();

    @Override
    default T getById(final Long id) {
	final Object[] res = new Object[1];
	this.getAll().forEach(pro -> {
	    if (pro.getId().equals(id)) {
		res[0] = pro;
	    }
	});
	return (T) res[0];
    }

    @Override
    default T update(final T t) {
	final boolean[] flag = { false };
	this.getAll().replaceAll(pro -> {
	    if (pro.getId().equals(t.getId())) {
		flag[0] = true;
		return t;
	    }
	    return pro;
	});
	if (flag[0] == true) {
	    return t;
	}
	throw new RuntimeException();
    }

}
