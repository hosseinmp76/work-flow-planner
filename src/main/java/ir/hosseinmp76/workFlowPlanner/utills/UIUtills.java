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
import ir.hosseinmp76.workFlowPlanner.persistency.dao.PriorityDAO;
import ir.hosseinmp76.workFlowPlanner.persistency.jpa.PriorityJpaDAO;
import ir.hosseinmp76.workFlowPlanner.ui.MyTreeItem;
import ir.hosseinmp76.workFlowPlanner.ui.PropertySet;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.persistence.Persistence;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

public class UIUtills implements Closeable {

    public static UIUtills instance = new UIUtills();

    private static List<PropertySet> createLists(
	    final List<List<MyTreeItem>> pro) {
	System.out.println("first create " + pro.size());

	if (pro.size() == 0) {
	    return new ArrayList<>();
	}
	if (pro.size() == 1) {
	    final var rr = pro.get(0).stream()
		    .map(item -> new PropertySet(item.getValue()))
		    .collect(Collectors.toList());
	    System.out.println("enter createList with size 1 ");
	    pro.stream().forEach(System.out::println);
	    rr.stream().forEach(System.out::println);
	    System.out.println("end createList with size 1 ");
	    return rr;
	}

	final var last = pro.get(pro.size() - 1);

	final var temp = UIUtills.createLists(pro.subList(0, pro.size() - 1));

	final List<PropertySet> res = new ArrayList<>();
	for (final PropertySet propertySet : temp) {
	    for (final MyTreeItem property : last) {
		final var t = new PropertySet(property.getValue(),
			propertySet.getProperties());
		res.add(t);
	    }
	}
	System.out.println("enter createList with ");
	pro.stream().forEach(System.out::println);
	temp.stream().forEach(System.out::println);
	res.stream().forEach(System.out::println);
	System.out.println("end createList with ");
	return res;

    }

    public static List<PropertySet> generate(final List<List<MyTreeItem>> pro) {

	final var res = UIUtills.createLists(pro);

	final PropertyMgr pmgr = UIUtills.getBean(PropertyMgr.class);
	final FormulaMgr fmgr = UIUtills.getBean(FormulaMgr.class);
	res.forEach(ps -> {
	    final Long[] l = new Long[1];
	    l[0] = 0L;
	    ps.getProperties().stream().forEach(prp -> {

		// TODO	
//		pmgr.getAllPropertyPriorities(prp).stream().forEach(pp -> {
//		    System.out.println("test " + pp.getValue());
//		    l[0] += pp.getValue();
//		});
	    });
	    ps.setSumOfPriorities(l[0]);
	});
	res.stream().forEach(System.out::println);
	System.out.println("end of generate");
	return res;

    }

    public static <T> T getBean(final Class<T> c) {
	return UIUtills.instance.getContext().select(c).get();
    }

    public static UIUtills getInstance() {
	return UIUtills.instance;
    }

    public static <T, V extends TreeItem<T>> List<V> getLeaves(final V item) {
	final var leaves = new ArrayList<V>();
	if (item.isLeaf()) {
	    leaves.add(item);
	} else {
	    ((ObservableList<V>) item.getChildren()).forEach(child -> {
		leaves.addAll(UIUtills.getLeaves(child));
	    });
	}
	return leaves;
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
