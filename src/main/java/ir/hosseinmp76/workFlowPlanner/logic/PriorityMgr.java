package ir.hosseinmp76.workFlowPlanner.logic;


import javax.inject.Inject;

import com.google.inject.Singleton;

import ir.hosseinmp76.workFlowPlanner.model.Priority;
import ir.hosseinmp76.workFlowPlanner.model.Property;
import ir.hosseinmp76.workFlowPlanner.persistency.BasicDAO;
import ir.hosseinmp76.workFlowPlanner.persistency.PriorityDAO;
import ir.hosseinmp76.workFlowPlanner.persistency.PropertyDAO;
@Singleton

public class PriorityMgr implements BasicMgr<Priority> {

    @Inject
    PriorityDAO dao;
    
    @Inject
    PropertyDAO propertyDAO;

    @Override
    public BasicDAO<Priority> getDAO() {
	return dao;
    }

    public Priority create(String name, Property dadProperty) {
	var res = this.dao.create(name, dadProperty);
	dadProperty.getPriorities().add(res);
	return res;
    }

}
