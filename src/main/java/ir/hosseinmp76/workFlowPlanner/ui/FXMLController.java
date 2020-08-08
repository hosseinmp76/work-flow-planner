package ir.hosseinmp76.workFlowPlanner.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ir.hosseinmp76.workFlowPlanner.logic.PriorityMgr;
import ir.hosseinmp76.workFlowPlanner.logic.PropertyMgr;
import ir.hosseinmp76.workFlowPlanner.utills.ExportUtils;
import ir.hosseinmp76.workFlowPlanner.utills.UIUtills;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;

public class FXMLController {

    PropertyMgr propertyMgr = UIUtills.getBean(PropertyMgr.class);

    PriorityMgr priorityMgr = UIUtills.getBean(PriorityMgr.class);

    @FXML
    MenuItem exportButton;

    @FXML
    public void aboutMenu(final Event e) {
	FXApp.openLink("https://github.com/hosseinmp76/work-flow-planner");
    }

    @FXML
    public void exportButtonHandler(final Event e) {

	var root = propertyMgr.getRoot();
	var features = ExportUtils.findFeatures(root);
	final List<List<MyTreeItem>> propertiesValues = new ArrayList<>();

	final var res = UIUtills.generate(features);

	final FileChooser fileChooser = new FileChooser();

	final var extension = new FileChooser.ExtensionFilter("csv", "*.csv");
	fileChooser.getExtensionFilters().add(extension);
	fileChooser.setSelectedExtensionFilter(extension);
	final File selectedFile = fileChooser.showSaveDialog(
		this.exportButton.getParentPopup().getScene().getWindow());

	ExportUtils.export(res, selectedFile);

    }
}
