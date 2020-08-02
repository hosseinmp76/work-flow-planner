package ir.hosseinmp76.workFlowPlanner.persistency.jpa;

import java.util.ArrayList;
import java.util.List;

import ir.hosseinmp76.workFlowPlanner.model.Priority;
import ir.hosseinmp76.workFlowPlanner.model.Formula;
import ir.hosseinmp76.workFlowPlanner.model.Formula_;
import ir.hosseinmp76.workFlowPlanner.persistency.dao.FormulaDAO;
import ir.hosseinmp76.workFlowPlanner.utills.EntityManagerProvider;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Singleton
public class FormulaJpaDAO extends BasicJpaDAO<Formula> implements FormulaDAO {

    @Override
    public Formula create(final String name) {
	final var res = new Formula();
	res.setName(name);
	this.persist(res);
	return res;
    }

    @Override
    public List<Formula> getAll() {
	EntityManager em = this.emf.getEntityManager();
	CriteriaBuilder cb = em.getCriteriaBuilder();
	CriteriaQuery<Formula> cq = cb.createQuery(Formula.class);
	Root<Formula> rootEntry = cq.from(Formula.class);
	CriteriaQuery<Formula> all = cq.select(rootEntry);
	TypedQuery<Formula> allQuery = em.createQuery(all);
	return allQuery.getResultList();
    }

    @Override
    public Formula getByName(String formulaName) {
	EntityManager em = this.emf.getEntityManager();
	CriteriaBuilder builder = em.getCriteriaBuilder();
	CriteriaQuery<Formula> criteria = builder.createQuery(Formula.class);
	Root<Formula> from = criteria.from(Formula.class);
	criteria.select(from);
	criteria.where(builder.equal(from.get(Formula_.name), formulaName));
	TypedQuery<Formula> typed = em.createQuery(criteria);
	try {
	    return typed.getSingleResult();
	} catch (final NoResultException nre) {
	    return null;
	}
    }

    @Override
    public void update(Formula formula, Priority tr, Long newValue) {
	EntityManager em = this.emf.getEntityManager();
	em.getTransaction().begin();
	formula.getCoefficients().put(tr, newValue);
	em.merge(formula);
	em.getTransaction().commit();

    }

}
