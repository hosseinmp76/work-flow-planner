package ir.hosseinmp76.workFlowPlanner.utills;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.persistence.jpa.PersistenceProvider;

import ir.hosseinmp76.workFlowPlanner.logic.FormulaMgr;
import ir.hosseinmp76.workFlowPlanner.logic.PriorityMgr;
import ir.hosseinmp76.workFlowPlanner.logic.PropertyMgr;
import ir.hosseinmp76.workFlowPlanner.model.Formula;
import ir.hosseinmp76.workFlowPlanner.model.Priority;
import ir.hosseinmp76.workFlowPlanner.model.Property;
import ir.hosseinmp76.workFlowPlanner.persistency.dao.PriorityDAO;
import ir.hosseinmp76.workFlowPlanner.persistency.jpa.PriorityJpaDAO;
import ir.hosseinmp76.workFlowPlanner.ui.MyTreeItem;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.persistence.Persistence;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

public class UIUtills implements Closeable {

    public static UIUtills instance = new UIUtills();

    private static List<PropertySet> createLists(
	    final List<Property> features) {

	if (features.size() == 0) {
	    return new ArrayList<>();
	}
	if (features.size() == 1) {
	    var feature = features.get(0);
	    var manners = feature.getGrandChildren();
	    final var res = manners.stream()
		    .map(item -> new PropertySet(List.of(item)))
		    .collect(Collectors.toList());
	    return res;
	}

	final var last = features.get(features.size() - 1).getGrandChildren();

	final var temp = UIUtills
		.createLists(features.subList(0, features.size() - 1));

	final List<PropertySet> res = new ArrayList<>();
	for (final PropertySet propertySet : temp) {
	    for (final Property property : last) {
		var t = new ArrayList();
		t.addAll(propertySet.getProperties());
		t.add(property);
		final var tp = new PropertySet(t);
		res.add(tp);
	    }
	}
	return res;

    }

    public static List<PropertySet> generate(List<Formula> formulas,
	    final List<Property> features) {

	final var res = UIUtills.createLists(features);

	final PropertyMgr pmgr = UIUtills.getBean(PropertyMgr.class);
	final PriorityMgr prioritymgr = UIUtills.getBean(PriorityMgr.class);

	var prioroties = prioritymgr.getAll();
	res.forEach(ps -> {
	    formulas.forEach(formula -> {
		final Long[] l = new Long[1];
		l[0] = 0L;
		for (Priority priority : prioroties) {

		    for (Property property : ps.getProperties()) {

			var tt = formula.getCoefficient(priority)
				* property.getProportion(priority);
			l[0] +=  tt;
		    }

		}
		ps.getSumOfPriorities().put(formula, l[0]);
	    });
	});
	return res;

    }

    public static <T> T getBean(final Class<T> c) {
	return UIUtills.instance.getContext().select(c).get();
    }

    public static UIUtills getInstance() {
	return UIUtills.instance;
    }

    private SeContainer applicationContext;

    UIUtills() {
	this.createContext();
    }

    @Override
    public void close() throws IOException {
	this.applicationContext.select(EntityManagerProvider.class).get()
		.closeFactory();
	this.applicationContext.close();
	System.out.println("application context closed by me");
    }

    private SeContainer createContext() {

	final SeContainerInitializer initializer = SeContainerInitializer
		.newInstance();
	this.applicationContext = initializer
		.disableDiscovery().addPackages(true, PriorityMgr.class,
			PriorityJpaDAO.class, EntityManagerProvider.class)
		.initialize();
//		this.applicationContext = initializer.initialize();

	final var emf = Persistence
		.createEntityManagerFactory("workflowplanner-pu");

	this.applicationContext.select(EntityManagerProvider.class).get()
		.setEntityManagerFactory(emf);

	return this.applicationContext;
    }

    public SeContainer getContext() {
	return this.applicationContext;

    }

    public void printAllBeans() {
	final var con = this.getContext();
	/*
	 * Arrays.stream(con.getBeanDefinitionNames()).forEach(x -> { Object
	 * clazz = null; try { clazz = con.getBean(x); } catch (final Exception
	 * e) { // TODO Auto-generated catch block e.printStackTrace(); }
	 * System.out.println(x + " " + clazz.getClass()); });
	 */
    }

}
