package ir.hosseinmp76.workFlowPlanner.ui;

import ir.hosseinmp76.workFlowPlanner.model.Property;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.util.Callback;

public class TextFieldTreeCellImpl extends TreeCell<Property> {

    public static <T> Callback<TreeView<T>, TreeCell<T>> forTreeView() {
	ContextMenu contextMenu = new ContextMenu();
	MenuItem menuItem1 = new MenuItem("Choice 1");
	MenuItem menuItem2 = new MenuItem("Choice 2");
	MenuItem menuItem3 = new MenuItem("Choice 3");
	menuItem3.setOnAction((event) -> {
	    System.out.println("Choice 3 clicked!");
	});

	contextMenu.getItems().addAll(menuItem1, menuItem2, menuItem3);

	return forTreeView(contextMenu, null);
    }

    public static <T> Callback<TreeView<T>, TreeCell<T>> forTreeView(
	    final ContextMenu contextMenu,
	    final Callback<TreeView<T>, TreeCell<T>> cellFactory) {
	return new Callback<TreeView<T>, TreeCell<T>>() {
	    @Override
	    public TreeCell<T> call(TreeView<T> listView) {
		TreeCell<T> cell = cellFactory == null
			? new DefaultTreeCell<T>()
			: cellFactory.call(listView);
		cell.setContextMenu(contextMenu);
		return cell;
	    }
	};
    }

}

class DefaultTreeCell<T> extends TreeCell<T> {
    @Override
    public void updateItem(T item, boolean empty) {
	super.updateItem(item, empty);

	if (empty) {
	    setText(null);
	    setGraphic(null);
	} else if (item instanceof Node) {
	    setText(null);
	    Node currentNode = getGraphic();
	    Node newNode = (Node) item;
	    if (currentNode == null || !currentNode.equals(newNode)) {
		setGraphic(newNode);
	    }
	} else {
	    setText(item == null ? "null" : item.toString());
	    setGraphic(null);
	}
    }
}
