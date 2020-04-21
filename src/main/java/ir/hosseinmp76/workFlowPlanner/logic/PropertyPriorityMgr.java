package ir.hosseinmp76.workFlowPlanner.logic;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import ir.hosseinmp76.workFlowPlanner.model.Priority;
import ir.hosseinmp76.workFlowPlanner.model.Property;
import ir.hosseinmp76.workFlowPlanner.model.PropertyPriority;
import ir.hosseinmp76.workFlowPlanner.persistency.BasicDAO;
import ir.hosseinmp76.workFlowPlanner.persistency.PropertyPriorityDAO;

@Component
public class PropertyPriorityMgr implements BasicMgr<PropertyPriority> {

    @Inject
    PropertyPriorityDAO propertyPriorityDAO;

    @Override
    public BasicDAO<PropertyPriority> getDAO() {
	return propertyPriorityDAO;
    }

    public PropertyPriority get(Property property, Priority priority) {
	return this.propertyPriorityDAO.get(property, priority);
    }

    public List<PropertyPriority> getAll() {
	return this.propertyPriorityDAO.getAll();
    }

    public List<PropertyPriority> getAllPropertyPriorities(Property property) {
	return this.propertyPriorityDAO.getAllPropertyPriorities(property);
    }

    public void addOrUpdate(Property property, Priority priority, long value) {
	this.propertyPriorityDAO.addOrUpdate(property, priority, value);
    }
}
