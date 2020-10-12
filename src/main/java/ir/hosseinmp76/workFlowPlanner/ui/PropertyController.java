package ir.hosseinmp76.workFlowPlanner.ui;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import ir.hosseinmp76.workFlowPlanner.logic.PriorityMgr;
import ir.hosseinmp76.workFlowPlanner.logic.PropertyMgr;
import ir.hosseinmp76.workFlowPlanner.model.Priority;
import ir.hosseinmp76.workFlowPlanner.model.Property;
import ir.hosseinmp76.workFlowPlanner.utills.UIUtills;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PropertyController {

    private static MyTreeItem convert(final Property property) {
	final MyTreeItem rootItem = new MyTreeItem(property);
	for (final Property child : property.getChildren()) {
	    final var t = PropertyController.convert(child);
	    rootItem.getChildren().add(t);
	}
	return rootItem;

    }

    PropertyMgr propertyMgr = UIUtills.getBean(PropertyMgr.class);

    PriorityMgr priorityMgr = UIUtills.getBean(PriorityMgr.class);

    @FXML
    private TreeView<Property> tree;

//    @FXML
//    Label detail;

    @FXML
    TextField name;

//    @FXML
//    TableView<Priority> table;

//    @FXML
//    TableColumn<Priority, Priority> column1;
//
//    @FXML
//    TableColumn<Priority, Priority> column2;

    @FXML
    VBox mainBox;

    @FXML
    AnchorPane detailPane;

    @FXML
    TextField propertyName;

    @FXML
    CheckBox isFeature;

    @FXML
    Button updatePPBut;

    @FXML
    public void addButt(final Event e) {

	if (this.name.getText().isBlank()) {
	    throw new RuntimeException("name field is empty");
	}
	final var newProperty = this.propertyMgr.create(this.name.getText(),
		this.getSelectedProperty().getValue());
	final var item = new MyTreeItem(newProperty);
	this.getSelectedProperty().getChildren().add(item);
	this.name.setText("");
    }

    @FXML
    public void changeScene(final Event e) {
	final var loc = this.getClass().getResource("test.fxml");

	try {

	    final Locale locale = new Locale("fa", "IR");

	    final String bundle = "ir/hosseinmp76/workFlowPlanner/i18n/MessagesBundle";
	    final ResourceBundle langs = ResourceBundle.getBundle(bundle,
		    locale);
	    final var fxmlLoader = new FXMLLoader(loc, langs);
	    final Parent node = fxmlLoader.load();
	    this.mainBox.getChildren().add(node);
	} catch (final IOException e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	}
    }

    @FXML
    public void editProperty(final Event e) {
	final var property = this.getSelectedProperty().getValue();
	property.setFeature(this.isFeature.isSelected());
	property.setName(this.propertyName.getText());

	this.propertyMgr.update(property);
	this.tree.refresh();
    }

    private MyTreeItem getSelectedProperty() {
	return (MyTreeItem) this.tree.getSelectionModel().getSelectedItem();
    }

    @FXML
    public void initialize() {
	final var root = this.propertyMgr.getRoot();

	this.tree.setCellFactory(TextFieldTreeCellImpl.forTreeView());

	final var rootItem = PropertyController.convert(root);

	this.tree.setRoot(rootItem);

	this.tree.getSelectionModel().selectedItemProperty()
		.addListener((observable, oldValue, newValue) -> {
		    this.updateSelectedProperty(observable, oldValue, newValue);

		});

//	this.column1
//		.setCellValueFactory(new PropertyValueFactory<>("priority"));
//	this.column2.setCellValueFactory(new PropertyValueFactory<>("value"));

//	final TableColumn<PropertyPriority, PropertyPriority> column1 = new TableColumn<>(
//		"%table.priority");
//	final TableColumn<PropertyPriority, PropertyPriority> column2 = new TableColumn<>(
//	"value");
//	this.table.getColumns().add(column1);
//	this.table.getColumns().add(column2);

    }

    @FXML
    public void removePropertyBut(final Event e) {
	Property property = this.getSelectedProperty().getValue();
	this.propertyMgr.delete(property);
	this.getSelectedProperty().getParent().getChildren()
		.remove(this.getSelectedProperty());
	this.tree.refresh();
    }

//    private void updatePriorityTable(final Property property) {
//	final var pp = property.getProportions();
//	this.table.getItems().clear();
//	this.table.getItems().addAll(pp.keySet());
//
//    }

    @FXML
    public void updatePP(final Event e) {
	Property property = this.getSelectedProperty().getValue();
	if (property.isFeature() == false)
	    return;
	final Stage stage = new Stage();
	final var mainSceneLoc = this.getClass().getResource("PPScene.fxml");
	try {
	    PPController controller = new PPController(property);
	    FXApp.openStage(stage, mainSceneLoc, controller);

	} catch (final IOException e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	}
    }

    private void updateSelectedProperty(
	    final ObservableValue<? extends TreeItem<Property>> observable,
	    final TreeItem<Property> oldValue,
	    final TreeItem<Property> newValue) {
	if (oldValue == newValue) {
	    return;
	}

//	this.detail.setText(newValue.toString());
//	this.updatePriorityTable(newValue.getValue());
	this.detailPane.setDisable(false);
	this.propertyName.setText(newValue.getValue().getName());
	this.isFeature.setSelected(newValue.getValue().isFeature());

	updatePPBut.setDisable(!newValue.getValue().isFeature());

    }

}
