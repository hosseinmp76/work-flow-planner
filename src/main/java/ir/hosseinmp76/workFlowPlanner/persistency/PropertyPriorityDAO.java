package ir.hosseinmp76.workFlowPlanner.persistency;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.inject.Singleton;

import ir.hosseinmp76.workFlowPlanner.exception.MyRuntimeException;
import ir.hosseinmp76.workFlowPlanner.model.BaseModel;
import ir.hosseinmp76.workFlowPlanner.model.Priority;
import ir.hosseinmp76.workFlowPlanner.model.Property;
import ir.hosseinmp76.workFlowPlanner.model.PropertyPriority;

@Singleton
public class PropertyPriorityDAO extends BasicInMemoryDAO<PropertyPriority> {
    List<PropertyPriority> propertyPriorities = new ArrayList<>();

    @Override
    protected List<PropertyPriority> getObjects() {
	return propertyPriorities;
    }

    @Override
    protected PropertyPriority emptyObjectFactory(Long id) {
	return new PropertyPriority(id);

    }

    public PropertyPriority addOrUpdate(Property property, Priority priority,
	    Long value) {
	var pp = get(property, priority);
	if (pp == null) {
	    return create(property, priority, value);
	}
	pp.setValue(value);
	return pp;
    }

    public PropertyPriority create(Property property, Priority priority,
	    Long value) {
	var pp = createEmptyModel();
	pp.setProperty(property);
	pp.setPriority(priority);
	pp.setValue(value);
	return pp;
    }

    public PropertyPriority get(Property property, Priority priority) {
	return this.propertyPriorities.stream().filter(pp -> {
	    return pp.getPriority().getId().equals(priority.getId())
		    && pp.getProperty().getId().equals(property.getId());
	}).findAny().orElse(null);
    }

    public List<PropertyPriority> getAll() {
	return this.propertyPriorities;
    }

    public List<PropertyPriority> getAllPropertyPriorities(Property property) {

	var res = this.propertyPriorities.stream().filter(pp -> {
	    return pp.getProperty().getId().equals(property.getId());
	}).collect(Collectors.toList());

	System.out.println(
		"tt " + property + " qq " + res + " ll " + property.getId());
	System.out.println("ee " + this.propertyPriorities);
	return res;
    }
}
