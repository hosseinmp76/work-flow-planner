package ir.hosseinmp76.workFlowPlanner.ui;

import java.util.ArrayList;

import ir.hosseinmp76.workFlowPlanner.logic.PriorityMgr;
import ir.hosseinmp76.workFlowPlanner.logic.PropertyMgr;
import ir.hosseinmp76.workFlowPlanner.model.Formula;
import ir.hosseinmp76.workFlowPlanner.model.Priority;
import ir.hosseinmp76.workFlowPlanner.model.Property;
import ir.hosseinmp76.workFlowPlanner.utills.UIUtills;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class PPController {

    Property feature;

    PPController(Property feature) {
	this.feature = feature;
    }

    PropertyMgr propertyMgr = UIUtills.getBean(PropertyMgr.class);

    PriorityMgr priorityMgr = UIUtills.getBean(PriorityMgr.class);

    @FXML
    TableView<Property> tableView;

    @FXML
    public void initialize() {

	this.tableView.getItems().addAll(this.feature.getGrandChildren());

	String s = FXApp.getI18nTranslation("pp.species") + " "
		+ this.feature.getName();
	final TableColumn<Property, String> column1 = new TableColumn<>(s);
	column1.setCellValueFactory(
		new Callback<TableColumn.CellDataFeatures<Property, String>, ObservableValue<String>>() {

		    @Override
		    public ObservableValue<String> call(
			    CellDataFeatures<Property, String> param) {
			return new SimpleStringProperty(
				param.getValue().getName());
		    }
		});
	var columns = new ArrayList<TableColumn<Property, ?>>();
	columns.add(column1);

	var priorities = priorityMgr.getAll();
	priorities.forEach(priority -> {

	    columns.add(addFormulaColumn(priority));

	});
	this.tableView.getColumns().addAll(columns);
	this.tableView.getSelectionModel().cellSelectionEnabledProperty()
		.set(true);

	this.tableView.setEditable(true);
    }

    private TableColumn<Property, Long> addFormulaColumn(Priority priority) {

	final TableColumn<Property, Long> column = new TableColumn<>(
		priority.getName());

	column.setCellValueFactory(
		new Callback<CellDataFeatures<Property, Long>, ObservableValue<Long>>() {
		    @Override
		    public ObservableValue<Long> call(
			    final CellDataFeatures<Property, Long> p) {

			var res = p.getValue().getProportions().get(priority);
			if (res == null) {
			    res = 0L;
			}
			return (ObservableValue) new SimpleLongProperty(res);
//			    return null;
		    }
		});

	column.setCellFactory(
		TextFieldTableCell.forTableColumn(new StringConverter<Long>() {

		    @Override
		    public String toString(final Long object) {
			return "" + object;
		    }

		    @Override
		    public Long fromString(final String string) {
			return Long.valueOf(string);
		    }

		}));

	column.setOnEditCommit(
		(final TableColumn.CellEditEvent<Property, Long> t) -> {
		    final var tr = t.getTableView().getItems()
			    .get(t.getTablePosition().getRow());
		    final var priorityName = t.getTableColumn().getText();
		    final var p = this.priorityMgr.getByName(priorityName);
		    this.propertyMgr.updateProportion(tr, p, t.getNewValue());
		    System.out.println(t.getNewValue());
//			kk
//			tr.setFieldUsername(t.getNewValue());
		});
	column.setEditable(true);
	return column;

    }
}
