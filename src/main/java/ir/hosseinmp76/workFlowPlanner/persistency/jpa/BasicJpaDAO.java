package ir.hosseinmp76.workFlowPlanner.persistency.jpa;

import ir.hosseinmp76.workFlowPlanner.model.BaseModel;
import ir.hosseinmp76.workFlowPlanner.model.Formula;
import ir.hosseinmp76.workFlowPlanner.persistency.BasicDAO;
import ir.hosseinmp76.workFlowPlanner.utills.EntityManagerProvider;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

public abstract class BasicJpaDAO<T extends BaseModel> implements BasicDAO<T> {
    @Inject
    EntityManagerProvider emf;

    @Override
    public void delete(final T t) {
	final EntityManager em = this.emf.getEntityManager();
	em.getTransaction().begin();
	em.remove(t);
	em.getTransaction().commit();
    }

    @Override
    public T getById(final Long id) {
	final EntityManager em = this.emf.getEntityManager();
	return (T) em.find(this.getClass(), id);
    }

    @Transactional
    @Override
    public T update(final T t) {
	final EntityManager em = this.emf.getEntityManager();
	em.getTransaction().begin();
	em.merge(t);
	em.getTransaction().commit();
	return t;
    }

    protected T persist(T t) {
	var em = emf.getEntityManager();
	em.getTransaction().begin();
	em.persist(t);
	em.getTransaction().commit();
	return t;
    }

    @Override
    public T getByName(String formulaName) {
	throw new RuntimeException();
    }
}
