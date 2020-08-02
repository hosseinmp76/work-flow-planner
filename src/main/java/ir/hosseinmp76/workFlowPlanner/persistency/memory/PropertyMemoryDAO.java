package ir.hosseinmp76.workFlowPlanner.persistency.memory;

import java.util.ArrayList;
import java.util.List;

import ir.hosseinmp76.workFlowPlanner.model.Priority;
import ir.hosseinmp76.workFlowPlanner.model.Property;
import ir.hosseinmp76.workFlowPlanner.persistency.dao.PropertyDAO;
import jakarta.enterprise.inject.Alternative;
import jakarta.inject.Singleton;

public class PropertyMemoryDAO
	implements PropertyDAO, BasicInMemoryDAO<Property> {
    private long population = 0L;

    @Override
    public long getPopulationAndIncrease() {
	return population++;
    }

    private final List<Property> properties = new ArrayList<>();

    public Property create(final String name, final Property dadProperty) {
	final var res = this.createEmptyModel();
	res.setParent(dadProperty);
	res.setName(name);
	return res;
    }

    @Override
    public List<Property> getAll() {
	return this.properties;
    }

    @Override
    public Property emptyObjectFactory(Long id) {
	return new Property(id);
    }

    @Override
    public void delete(Property t) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public Property update(Property t) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Property getById(Long id) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Property getByName(String formulaName) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void updateProportion(Property tr2, Priority tr, Long newValue) {
	// TODO Auto-generated method stub
	
    }

}
