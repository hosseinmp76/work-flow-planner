package ir.hosseinmp76.workFlowPlanner.utills;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class EntityManagerProvider {

    private EntityManagerFactory emf;

    @Produces
    public EntityManager getEntityManager() {
	return this.emf.createEntityManager();
    }

    @PersistenceContext
    public void setEntityManagerFactory(final EntityManagerFactory emf) {
	this.emf = emf;

    }

    public void closeFactory() {
	// TODO Auto-generated method stub
	emf.close();
    }
}