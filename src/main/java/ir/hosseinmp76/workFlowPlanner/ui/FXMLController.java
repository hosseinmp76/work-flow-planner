package ir.hosseinmp76.workFlowPlanner.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ir.hosseinmp76.workFlowPlanner.exception.MyRuntimeException;
import ir.hosseinmp76.workFlowPlanner.logic.PriorityMgr;
import ir.hosseinmp76.workFlowPlanner.logic.PropertyMgr;
import ir.hosseinmp76.workFlowPlanner.logic.PropertyPriorityMgr;
import ir.hosseinmp76.workFlowPlanner.model.Priority;
import ir.hosseinmp76.workFlowPlanner.model.Property;
import ir.hosseinmp76.workFlowPlanner.model.PropertyPriority;
import ir.hosseinmp76.workFlowPlanner.utills.ExportUtils;
import ir.hosseinmp76.workFlowPlanner.utills.UIUtills;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

public class FXMLController {

    PropertyMgr propertyMgr = UIUtills.getBean(PropertyMgr.class);

    PriorityMgr priorityMgr = UIUtills.getBean(PriorityMgr.class);
    @FXML
    private Button addPriority;

    @FXML
    private TreeView<Property> tree;

    @FXML
    Button add;

    @FXML
    TextField priorityName;

    @FXML
    Button exportButton;

    @FXML
    Label detail;

    @FXML
    TextField name;

    @FXML
    ComboBox<Priority> priorities;

    @FXML
    Button appPrioValue;

    @FXML
    TextField priorityValue;

    PropertyPriorityMgr propertyPriorityMgr = UIUtills
	    .getBean(PropertyPriorityMgr.class);

    @FXML
    TableView<PropertyPriority> table;

    @FXML
    TableColumn<PropertyPriority, PropertyPriority> column1;

    @FXML
    TableColumn<PropertyPriority, PropertyPriority> column2;

    @FXML
    public void addButt(final Event e) {

	System.out.println("ddf");
	final var item = new MyTreeItem(this.propertyMgr.create(
		this.name.getText(), this.getSelectedProperty().getValue()));
	this.getSelectedProperty().getChildren().add(item);

    }

    @FXML
    private void addPriorityListner(final Event e) {
	final var selected = this.getSelectedProperty();
	if (selected == null) {
	    throw new MyRuntimeException();
	}
	this.priorityMgr.create(this.priorityName.getText(),
		selected.getValue());
	this.updateComboBox();

    }

    @FXML
    private void appPrioValueListner(final Event e) {
	this.propertyPriorityMgr.addOrUpdate(
		this.getSelectedProperty().getValue(),
		this.getSelectedPriority(),
		Long.parseLong(this.priorityValue.getText()));
	this.updatePriorityTable();
    }

    @FXML
    public void exportButtonHandler(final Event e) {
	final List<List<MyTreeItem>> propertiesValues = new ArrayList<>();

	this.tree.getRoot().getChildren().forEach(node -> {
	    propertiesValues.add(UIUtills.getLeaves((MyTreeItem) node));
	});

	final var res = UIUtills.generate(propertiesValues);
	res.sort((c1,
		c2) -> (c1.getSumOfPriorities() < c2.getSumOfPriorities() ? 1
			: -1));
	FileChooser fileChooser = new FileChooser();

	var extension = new FileChooser.ExtensionFilter("csv", "*.csv");
	fileChooser.getExtensionFilters().add(extension);
	fileChooser.setSelectedExtensionFilter(extension);
	File selectedFile = fileChooser
		.showSaveDialog(exportButton.getScene().getWindow());

	ExportUtils.export(res, selectedFile);

    }

    private Priority getSelectedPriority() {
	return this.priorities.getSelectionModel().getSelectedItem();
    }

    private MyTreeItem getSelectedProperty() {
	return (MyTreeItem) this.tree.getSelectionModel().getSelectedItem();
    }

    @FXML
    public void initialize() {

	final var catProp = this.propertyMgr.create("categories", null);
	final MyTreeItem rootItem = new MyTreeItem(catProp);

	final var genderProp = this.propertyMgr.create("gender", catProp);
	final var dastrasi = this.priorityMgr.create("dastrasi", genderProp);
	final MyTreeItem gender = new MyTreeItem(genderProp);

	final var male = this.propertyMgr.create("male", genderProp);
	gender.getChildren().add(new MyTreeItem(male));
	this.propertyPriorityMgr.addOrUpdate(male, dastrasi, 1);

	final var female = this.propertyMgr.create("female", genderProp);
	gender.getChildren().add(new MyTreeItem(female));
	this.propertyPriorityMgr.addOrUpdate(female, dastrasi, 3);

	rootItem.getChildren().add(gender);

	final var educationProp = this.propertyMgr.create("education", catProp);
	final var savad = this.priorityMgr.create("savad", educationProp);
	final MyTreeItem education = new MyTreeItem(educationProp);

	final var primarySchoole = this.propertyMgr.create("primary schoole",
		educationProp);
	education.getChildren().add(new MyTreeItem(primarySchoole));
	this.propertyPriorityMgr.addOrUpdate(primarySchoole, savad, 67);

	final var secondrySchool = this.propertyMgr.create("secondry schoole",
		educationProp);
	this.propertyPriorityMgr.addOrUpdate(secondrySchool, savad, 7654);
	education.getChildren().add(new MyTreeItem(secondrySchool));

	rootItem.getChildren().add(education);

	this.tree.setRoot(rootItem);

	this.tree.getSelectionModel().selectedItemProperty()
		.addListener((observable, oldValue, newValue) -> {
		    this.detail.setText(newValue.toString());
		    this.updatePriorityTable();
		    this.updateComboBox();
		});

//	final TableColumn<PropertyPriority, PropertyPriority> column1 = new TableColumn<>(
//		"%table.priority");

	this.column1
		.setCellValueFactory(new PropertyValueFactory<>("priority"));

//	final TableColumn<PropertyPriority, PropertyPriority> column2 = new TableColumn<>(
//		"value");
	this.column2.setCellValueFactory(new PropertyValueFactory<>("value"));

//	this.table.getColumns().add(column1);
//	this.table.getColumns().add(column2);

    }

    private void updateComboBox() {

	final var selected = this.getSelectedProperty();
	if (selected == null) {
	    throw new MyRuntimeException();
	}
	this.priorities.getItems().clear();
	this.priorities.getItems()
		.addAll(selected.getValue().getAllPriorities());
    }

    private void updatePriorityTable() {
	final var pp = this.propertyPriorityMgr.getAllPropertyPriorities(
		this.getSelectedProperty().getValue());
	this.table.getItems().clear();
	this.table.getItems().addAll(pp);

    }

    @FXML
    private void updatePropertPriority(final Event e) {
	final var selected = this.getSelectedProperty().getValue();
	final var selectedPriority = this.getSelectedPriority();
	this.propertyPriorityMgr.addOrUpdate(selected, selectedPriority,
		Long.parseLong(this.priorityValue.getText()));
    }

}
