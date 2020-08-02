package ir.hosseinmp76.workFlowPlanner.logic;

import ir.hosseinmp76.workFlowPlanner.model.Priority;
import ir.hosseinmp76.workFlowPlanner.persistency.BasicDAO;
import ir.hosseinmp76.workFlowPlanner.persistency.dao.PriorityDAO;
import ir.hosseinmp76.workFlowPlanner.persistency.dao.PropertyDAO;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class PriorityMgr extends BasicMgr<Priority> {

    @Inject
    PriorityDAO dao;

    @Inject
    PropertyDAO propertyDAO;

    public Priority create(final String name) {
	final var res = this.dao.create(name);
	return res;
    }

    @Override
    public Priority createEmptyModel() {
	return new Priority();
    }

    @Override
    public BasicDAO<Priority> getDAO() {
	return this.dao;
    }

}
