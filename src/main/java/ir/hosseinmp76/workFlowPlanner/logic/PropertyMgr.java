package ir.hosseinmp76.workFlowPlanner.logic;

import ir.hosseinmp76.workFlowPlanner.model.Priority;
import ir.hosseinmp76.workFlowPlanner.model.Property;
import ir.hosseinmp76.workFlowPlanner.persistency.dao.PropertyDAO;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class PropertyMgr extends BasicMgr<Property> {

    @Inject
    PropertyDAO dao;

    private Property root;

    public Property create(final String name, Property dadProperty) {
	if (dadProperty == null) {
	    dadProperty = this.getRoot();
	}
	return this.dao.create(name, dadProperty);
    }

    @Override
    public Property createEmptyModel() {
	return new Property();
    }

    @Override
    protected PropertyDAO getDAO() {
	return this.dao;
    }

    public Property getRoot() {
	if (this.root != null) {
	    return this.root;
	}

	this.root = this.dao.getAll().stream()
		.filter(property -> property.getParent() == null).findAny()
		.orElseGet(() -> null);
	if (this.root == null) {
	    this.root = this.dao.create("categories", null);
	}
	return this.root;
    }

    public void updateProportion(final Property tr2, final Priority tr,
	    final Long newValue) {
	this.getDAO().updateProportion(tr2, tr, newValue);
    }
}
