package ir.hosseinmp76.workFlowPlanner.utills;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import ir.hosseinmp76.workFlowPlanner.model.Formula;
import ir.hosseinmp76.workFlowPlanner.model.Property;
import ir.hosseinmp76.workFlowPlanner.ui.PropertySet;
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

    public static void export(final List<Formula> formulas,
	    final List<PropertySet> res, File selectedFile) {
	try {
	    final FileWriter fileWriter = new FileWriter(selectedFile);
	    final PrintWriter pw = new PrintWriter(
		    new BufferedWriter(fileWriter));
//	    res.forEach(p -> {
//		pw.println(p.toString());
//	    });

	    if (res.size() == 0)
		return;
	    List headers = new ArrayList();
	    for (int i = 0; i < res.get(0).getProperties().size(); i++) {
		headers.add("features");
	    }

	    headers.addAll(formulas);

	    try (CSVPrinter printer = new CSVPrinter(pw, CSVFormat.DEFAULT)) {
		printer.printRecord(headers);
		res.forEach(ps -> {
		    try {

			final Collection record = new ArrayList<>(
				ps.getProperties());

			record.addAll(ps.getSumOfPriorities().values());
			printer.printRecord(record);
		    } catch (final IOException e1) {
			// TODO Auto-generated catch block
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
