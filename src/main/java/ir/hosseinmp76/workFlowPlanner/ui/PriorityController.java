package ir.hosseinmp76.workFlowPlanner.ui;

import java.awt.List;
import java.util.ArrayList;

import ir.hosseinmp76.workFlowPlanner.logic.FormulaMgr;
import ir.hosseinmp76.workFlowPlanner.logic.PriorityMgr;
import ir.hosseinmp76.workFlowPlanner.logic.PropertyMgr;
import ir.hosseinmp76.workFlowPlanner.model.Formula;
import ir.hosseinmp76.workFlowPlanner.model.Priority;
import ir.hosseinmp76.workFlowPlanner.utills.UIUtills;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;

public class PriorityController {

    PropertyMgr propertyMgr = UIUtills.getBean(PropertyMgr.class);

    FormulaMgr formulaMgr = UIUtills.getBean(FormulaMgr.class);
    PriorityMgr priorityMgr = UIUtills.getBean(PriorityMgr.class);

    @FXML
    TextField newPriorityName;

    @FXML
    ComboBox<Priority> removePriorityConboBox;

    @FXML
    TextField newFormulaName;

    @FXML
    ComboBox<Formula> removeFormulaCombo;

    @FXML
    public void addPriority(final Event e) {
	final var name = this.newPriorityName.getText();
	if (name.isBlank()) {
	    return;
	}
	final var priority = this.priorityMgr.create(name);
	this.newPriorityName.setText("");
	this.priorityTable.getItems().add(priority);
	this.removePriorityConboBox.getItems().add(priority);

    }

    @FXML
    public void newFormula(final Event e) {
	final var name = this.newFormulaName.getText();
	if (name.isBlank()) {
	    return;
	}
	final var formula = this.formulaMgr.create(name);
	this.newFormulaName.setText("");
	this.removeFormulaCombo.getItems().add(formula);
	this.priorityTable.getColumns().add(addFormulaColumn(formula));

    }

    @FXML
    TableView<Priority> priorityTable;

    private TableColumn<Priority, Long> addFormulaColumn(Formula f) {

	final TableColumn<Priority, Long> firstNameCol = new TableColumn<>(
		f.getName());

	firstNameCol.setCellValueFactory(
		new Callback<CellDataFeatures<Priority, Long>, ObservableValue<Long>>() {
		    @Override
		    public ObservableValue<Long> call(
			    final CellDataFeatures<Priority, Long> p) {
			var res = f.getCoefficients().get(p.getValue());
			if (res == null) {
			    res = 0L;
			}
			return (ObservableValue) new SimpleLongProperty(res);
//			    return null;
		    }
		});

	firstNameCol.setCellFactory(
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

	firstNameCol.setOnEditCommit(
		(final TableColumn.CellEditEvent<Priority, Long> t) -> {
		    final var tr = t.getTableView().getItems()
			    .get(t.getTablePosition().getRow());
		    final var formulaName = t.getTableColumn().getText();
		    final var formula = this.formulaMgr.getByName(formulaName);
		    this.formulaMgr.updateCoefficent(formula, tr,
			    t.getNewValue());
		    System.out.println(t.getNewValue());
//			kk
//			tr.setFieldUsername(t.getNewValue());
		});
	firstNameCol.setEditable(true);
	return firstNameCol;

    }

    @FXML
    public void initialize() {
	final var fomulas = this.formulaMgr.getAll();

	final var columns = new ArrayList();

	final TableColumn<Priority, String> priorityNamesColumn = new TableColumn<>(
		FXApp.getI18nTranslation("newpriority.label"));
	priorityNamesColumn.setCellValueFactory(
		new Callback<CellDataFeatures<Priority, String>, ObservableValue<String>>() {
		    @Override
		    public ObservableValue<String> call(
			    final CellDataFeatures<Priority, String> p) {
			// p.getValue() returns the Person instance for a
			// particular TableView row
			return new SimpleStringProperty(p.getValue().getName());
//			    return null;
		    }
		});
	columns.add(priorityNamesColumn);

	fomulas.forEach(f -> {
	    columns.add(addFormulaColumn(f));
	});
	this.priorityTable.getSelectionModel().cellSelectionEnabledProperty()
		.set(true);

	this.priorityTable.setEditable(true);
	this.priorityTable.getColumns().addAll(columns);

	this.removeFormulaCombo.getItems().addAll(this.formulaMgr.getAll());
	final var priorities = this.priorityMgr.getAll();
	this.removePriorityConboBox.getItems().addAll(priorities);

	this.priorityTable.getItems().addAll(priorities);

    }

    @FXML
    public void removeFormula(final Event e) {
	final var forula = this.removeFormulaCombo.getValue();
	if (forula == null) {
	    return;
	}
	
	this.formulaMgr.delete(forula);
	this.removeFormulaCombo.getItems().remove(forula);
	var index =this.priorityTable.getColumns().stream().filter(col ->{
	    if(col.getText().equals(forula.getName()))
		return true;
	    return false;
	}).findAny().get();
	this.priorityTable.getColumns().remove(index);

    }

    @FXML
    public void removePriority(final Event e) {
	final var priority = this.removePriorityConboBox.getValue();
	if (priority == null) {
	    return;
	}
	
	this.priorityMgr.delete(priority);
	this.priorityTable.getItems().remove(priority);
	this.removePriorityConboBox.getItems().remove(priority);

    }

}
