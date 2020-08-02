package ir.hosseinmp76.workFlowPlanner.persistency.dao;

import ir.hosseinmp76.workFlowPlanner.model.Priority;
import ir.hosseinmp76.workFlowPlanner.model.Property;
import ir.hosseinmp76.workFlowPlanner.persistency.BasicDAO;

public interface PriorityDAO extends BasicDAO<Priority> {

    public Priority create(String name);

}
