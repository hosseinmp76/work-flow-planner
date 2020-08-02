package ir.hosseinmp76.workFlowPlanner.ui;

import ir.hosseinmp76.workFlowPlanner.model.Property;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;

public class MyTreeItem extends TreeItem<Property> {

    public MyTreeItem(final Property property) {
	
	super(property);
	this.setExpanded(true);
	

    }



    @Override
    public String toString() {
	return this.getValue().getName();
    }

}
