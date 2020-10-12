package ir.hosseinmp76.workFlowPlanner.utills;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import ir.hosseinmp76.workFlowPlanner.logic.FormulaMgr;
import ir.hosseinmp76.workFlowPlanner.logic.PropertyMgr;
import ir.hosseinmp76.workFlowPlanner.model.Formula;
import ir.hosseinmp76.workFlowPlanner.model.Property;
import ir.hosseinmp76.workFlowPlanner.ui.MyTreeItem;
import javafx.collections.ObservableList;

public class ExportUtils {

    public static List<Property> findFeatures(Property p) {
	final var leaves = new ArrayList<Property>();
	if (p.isFeature()) {
	    leaves.add(p);
	} else {
	    p.getChildren().forEach(child -> {
		leaves.addAll(findFeatures(child));
	    });
	}
	return leaves;
    }

    public static void export(File selectedFile) {
	var root = UIUtills.getBean(PropertyMgr.class).getRoot();

	var features = ExportUtils.findFeatures(root);

	final FormulaMgr fmgr = UIUtills.getBean(FormulaMgr.class);

	var formulas = fmgr.getAll();
	final var res = UIUtills.generate(formulas, features);
	try {
	    final FileWriter fileWriter = new FileWriter(selectedFile);
	    final PrintWriter pw = new PrintWriter(
		    new BufferedWriter(fileWriter));

	    if (res.size() == 0)
		return;
	    List headers = new ArrayList();
	    for (Property feature : features) {
		headers.add(feature.getName());
	    }
	    for (Formula formul : formulas) {
		headers.add(formul.getName());
	    }

	    try (CSVPrinter printer = new CSVPrinter(pw, CSVFormat.DEFAULT)) {
		printer.printRecord(headers);
		res.forEach(ps -> {
		    try {

			final Collection record = new ArrayList<>(
				ps.getProperties());
			for (Formula formul : formulas) {
//			     Map<Formula, Long> sumOfPriorities = new HashMap<>();
//			     sumOfPriorities.get(formul)
			    record.add(ps.getSumOfPriorities().get(formul));
			}

			printer.printRecord(record);
		    } catch (final IOException e1) {
			e1.printStackTrace();
		    }

		});
	    }
	    pw.close();
	    fileWriter.close();
	} catch (

	final IOException e1) {
	    e1.printStackTrace();
	}

    }
}
