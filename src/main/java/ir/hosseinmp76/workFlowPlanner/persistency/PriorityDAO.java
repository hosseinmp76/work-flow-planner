package ir.hosseinmp76.workFlowPlanner.persistency;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import ir.hosseinmp76.workFlowPlanner.model.Priority;
import ir.hosseinmp76.workFlowPlanner.model.Property;

@Component
public class PriorityDAO extends BasicInMemoryDAO<Priority> {

    private final List<Priority> priorities = new ArrayList<>();

    public Priority create(String name, Property baseProperty) {
	var res = this.createEmptyModel();
	res.setBaseProperty(baseProperty);
	res.setName(name);
	return res;
    }

    @Override
    protected List<Priority> getObjects() {
	return priorities;
    }

    @Override
    protected Priority emptyObjectFactory(Long id) {
	return new Priority(id);
    }

}
