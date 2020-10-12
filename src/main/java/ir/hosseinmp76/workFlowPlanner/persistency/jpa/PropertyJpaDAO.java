package ir.hosseinmp76.workFlowPlanner.persistency.jpa;

import java.util.List;

import ir.hosseinmp76.workFlowPlanner.model.Priority;
import ir.hosseinmp76.workFlowPlanner.model.Property;
import ir.hosseinmp76.workFlowPlanner.persistency.dao.PropertyDAO;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Singleton
public class PropertyJpaDAO extends BasicJpaDAO<Property>
	implements PropertyDAO {

    @Override
    public Property create(final String name, final Property dadProperty) {
	final var res = new Property();
	res.setParent(dadProperty);

	res.setName(name);
	res.setFeature(false);
	this.persist(res);
	if (dadProperty != null && !dadProperty.getChildren().contains(res)) {
	    dadProperty.getChildren().add(res);
	    this.update(dadProperty);
	}
	return res;
    }

    // fuck this shit
    @Override
    public void delete(Property t) {
	for (Property child : t.getChildren()) {
	    this.delete(child);
	}

	final EntityManager em = this.emf.getEntityManager();
	em.getTransaction().begin();
	if (!em.contains(t)) {
	    t = em.merge(t);
	}

	em.remove(t);
	em.getTransaction().commit();
    }

    @Override
    public List<Property> getAll() {
	final EntityManager em = this.emf.getEntityManager();
	final CriteriaBuilder cb = em.getCriteriaBuilder();
	final CriteriaQuery<Property> cq = cb.createQuery(Property.class);
	final Root<Property> rootEntry = cq.from(Property.class);
	final CriteriaQuery<Property> all = cq.select(rootEntry);
	final TypedQuery<Property> allQuery = em.createQuery(all);
	return allQuery.getResultList();
    }

    @Override
    public void updateProportion(final Property property, final Priority tr,
	    final Long newValue) {
	final EntityManager em = this.emf.getEntityManager();
	em.getTransaction().begin();
	property.getProportions().put(tr, newValue);
	em.merge(property);
	em.getTransaction().commit();
    }

}
