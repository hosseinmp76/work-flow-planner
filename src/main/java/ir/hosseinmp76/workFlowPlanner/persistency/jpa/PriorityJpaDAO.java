package ir.hosseinmp76.workFlowPlanner.persistency.jpa;

import java.util.List;

import ir.hosseinmp76.workFlowPlanner.model.Formula;
import ir.hosseinmp76.workFlowPlanner.model.Formula_;
import ir.hosseinmp76.workFlowPlanner.model.Priority;
import ir.hosseinmp76.workFlowPlanner.model.Priority_;
import ir.hosseinmp76.workFlowPlanner.model.Property;
import ir.hosseinmp76.workFlowPlanner.persistency.dao.PriorityDAO;
import ir.hosseinmp76.workFlowPlanner.utills.EntityManagerProvider;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Singleton
public class PriorityJpaDAO extends BasicJpaDAO<Priority>
	implements PriorityDAO {

    @Override
    public Priority create(final String name) {
	final var res = new Priority();
	res.setName(name);
	this.persist(res);
	return res;
    }

    @Override
    public List<Priority> getAll() {
	EntityManager em = this.emf.getEntityManager();
	CriteriaBuilder cb = em.getCriteriaBuilder();
	CriteriaQuery<Priority> cq = cb.createQuery(Priority.class);
	Root<Priority> rootEntry = cq.from(Priority.class);
	CriteriaQuery<Priority> all = cq.select(rootEntry);
	TypedQuery<Priority> allQuery = em.createQuery(all);
	return allQuery.getResultList();

    }

    @Override
    public Priority getByName(String formulaName) {
	EntityManager em = this.emf.getEntityManager();
	CriteriaBuilder builder = em.getCriteriaBuilder();
	CriteriaQuery<Priority> criteria = builder.createQuery(Priority.class);
	Root<Priority> from = criteria.from(Priority.class);
	criteria.select(from);
	criteria.where(builder.equal(from.get(Priority_.name), formulaName));
	TypedQuery<Priority> typed = em.createQuery(criteria);
	try {
	    return typed.getSingleResult();
	} catch (final NoResultException nre) {
	    return null;
	}
    }

}
