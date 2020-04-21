package ir.hosseinmp76.workFlowPlanner.utills;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import ir.hosseinmp76.workFlowPlanner.logic.PropertyPriorityMgr;
import ir.hosseinmp76.workFlowPlanner.model.PropertySet;
import ir.hosseinmp76.workFlowPlanner.ui.MyTreeItem;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

public class UIUtills {

    private static List<PropertySet> createLists(
	    final List<List<MyTreeItem>> pro) {
	System.out.println("first create " + pro.size());

	if (pro.size() == 0)
	    return new ArrayList<>();
	if (pro.size() == 1) {
	    var rr = pro.get(0).stream()
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

	final PropertyPriorityMgr ppmgr = UIUtills
		.getBean(PropertyPriorityMgr.class);
	res.forEach(ps -> {
	    final Long[] l = new Long[1];
	    l[0] = 0L;
	    ps.getProperties().stream().forEach(prp -> {
		System.out.println("before test" + prp + " ff " + ppmgr.getAll()
			+ " k" + ppmgr);
		ppmgr.getAllPropertyPriorities(prp).stream().forEach(pp -> {
		    System.out.println("test " + pp.getValue());
		    l[0] += pp.getValue();
		});
	    });
	    ps.setSumOfPriorities(l[0]);
	});
	res.stream().forEach(System.out::println);
	System.out.println("end of generate");
	return res;

    }

    public static <T> T getBean(final Class<T> c) {
	return UIUtills.getContext().getBean(c);
    }

    final static String str = "src/main/resources/ir/hosseinmp76/workFlowPlanner/utills/applicationContext.xml";

    private static ApplicationContext applicationContext;

    public static ApplicationContext getContext() {
//	var xx = ClassLoader.getSystemResource("config.yml");
	if (applicationContext != null)
	    return applicationContext;
	else {
	    synchronized (UIUtills.class) {
		applicationContext = new FileSystemXmlApplicationContext(str);
	    }
	}
	return applicationContext;

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

    public static void printAllBeans() {
	final var con = UIUtills.getContext();
	Arrays.stream(con.getBeanDefinitionNames()).forEach(x -> {
	    Object clazz = null;
	    try {
		clazz = con.getBean(x);
	    } catch (final Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    System.out.println(x + " " + clazz.getClass());
	});

    }

    public static void registerBean(final Object bean) {
	final ConfigurableListableBeanFactory beanFactory = ((ConfigurableApplicationContext) UIUtills
		.getContext()).getBeanFactory();
	beanFactory.registerSingleton(bean.getClass().getCanonicalName(), bean);
    }
}
