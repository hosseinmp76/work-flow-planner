package ir.hosseinmp76.workFlowPlanner.persistency.dao;

import java.util.ArrayList;
import java.util.List;

import ir.hosseinmp76.workFlowPlanner.model.Priority;
import ir.hosseinmp76.workFlowPlanner.model.Property;
import ir.hosseinmp76.workFlowPlanner.persistency.BasicDAO;
import ir.hosseinmp76.workFlowPlanner.utills.EntityManagerProvider;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

public interface PropertyDAO extends BasicDAO<Property> {

    public Property create(String name, Property dadProperty);

    public void updateProportion(Property tr2, Priority tr, Long newValue);

}
