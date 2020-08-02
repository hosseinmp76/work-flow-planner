package ir.hosseinmp76.workFlowPlanner.persistency.memory;

import java.util.ArrayList;
import java.util.List;

import ir.hosseinmp76.workFlowPlanner.model.Priority;
import ir.hosseinmp76.workFlowPlanner.model.Property;
import ir.hosseinmp76.workFlowPlanner.persistency.dao.PriorityDAO;
import ir.hosseinmp76.workFlowPlanner.utills.EntityManagerProvider;
import jakarta.enterprise.inject.Alternative;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class PriorityMemoryDAO
	implements PriorityDAO, BasicInMemoryDAO<Priority> {

    private long population = 0L;

    private final List<Priority> priorities = new ArrayList<>();

    @Override
    public long getPopulationAndIncrease() {
	return population++;
    }

    @Inject
    EntityManagerProvider emf;

    @Override
    public Priority create(String name) {
	var res = this.createEmptyModel();
	res.setName(name);
	return res;
    }

    @Override
    public List<Priority> getAll() {
	return this.priorities;

    }

    @Override
    public Priority emptyObjectFactory(Long id) {
	return new Priority(id);
    }

    @Override
    public void delete(Priority t) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public Priority update(Priority t) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Priority getById(Long id) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Priority getByName(String formulaName) {
	// TODO Auto-generated method stub
	return null;
    }

}
