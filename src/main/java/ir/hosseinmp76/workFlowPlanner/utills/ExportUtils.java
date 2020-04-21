package ir.hosseinmp76.workFlowPlanner.utills;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import ir.hosseinmp76.workFlowPlanner.model.PropertySet;

public class ExportUtils {

    public static void export(final List<PropertySet> res, File selectedFile) {
	try {
	    final FileWriter fileWriter = new FileWriter(selectedFile);
	    final PrintWriter pw = new PrintWriter(
		    new BufferedWriter(fileWriter));
//	    res.forEach(p -> {
//		pw.println(p.toString());
//	    });

	    String[] headers;
	    try (CSVPrinter printer = new CSVPrinter(pw, CSVFormat.DEFAULT)) {
		res.forEach(ps -> {
		    try {

			final List record = new ArrayList<>(ps.getProperties());
			var properties = ps.getProperties();
			printer.printRecord(properties);
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
