package ir.hosseinmp76.workFlowPlanner.persistency;

import java.util.List;


import ir.hosseinmp76.workFlowPlanner.model.BaseModel;

public abstract class BasicInMemoryDAO<T extends BaseModel>
	implements BasicDAO<T> {
    private static long population = 0l;

    @Override
    public T createEmptyModel() {
	final var pro = this.emptyObjectFactory(BasicInMemoryDAO.population++);
	this.getObjects().add(pro);
	return pro;
    }

//    protected abstract T emptyObjectFactory();

    @Override
    public void delete(final T t) {
	this.getObjects().replaceAll(pro -> {
	    if (pro.getId().equals(t.getId())) {
		return null;
	    }
	    return pro;
	});

    }

    protected abstract T emptyObjectFactory(Long id);

    protected abstract List<T> getObjects();

    @Override
    public List<T> list(final int start, final int end) {
	final var newEnd = end == -1 ? this.getObjects().size() : end;
	return this.getObjects().subList(start, newEnd);
    }

    @Override
    public T read(final Long id) {
	final Object[] res = new Object[1];
	this.getObjects().forEach(pro -> {
	    if (pro.getId().equals(id)) {
		res[0] = pro;
	    }
	});
	return (T) res[0];
    }

    @Override
    public T update(final T t) {
	final boolean[] flag = { false };
	this.getObjects().replaceAll(pro -> {
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
