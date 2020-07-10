package ir.hosseinmp76.workFlowPlanner.logic;

import javax.inject.Inject;

import com.google.inject.Singleton;

import ir.hosseinmp76.workFlowPlanner.model.Property;
import ir.hosseinmp76.workFlowPlanner.persistency.BasicDAO;
import ir.hosseinmp76.workFlowPlanner.persistency.PropertyDAO;

@Singleton

public class PropertyMgr implements BasicMgr<Property> {

    @Inject
    PropertyDAO dao;

    @Override
    public BasicDAO<Property> getDAO() {
	return dao;
    }

    public Property create(String name, Property dadProperty) {
	return this.dao.create(name, dadProperty);
    }

}
