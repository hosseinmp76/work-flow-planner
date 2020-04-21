package ir.hosseinmp76.workFlowPlanner.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Id;

import ir.hosseinmp76.workFlowPlanner.ui.MyTreeItem;
import lombok.Data;

@Data
public class PropertySet implements BaseModel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    Long id;

    List<Property> properties;

    Long sumOfPriorities;

    public PropertySet(final Property item) {
	this.properties = new ArrayList<>();
	this.properties.add(item);
    }

    public PropertySet(final Property property,
	    final List<Property> properties2) {
	this.properties = new ArrayList<>();
	this.properties.add(property);
	for (final Property property2 : properties2) {
	    this.properties.add(property2);
	}

    }

    public String toString() {
	StringBuilder props = new StringBuilder();
	this.properties.stream().forEach(p -> props.append(p.getName() + ", "));
	return "PropertySet: [" + props + "]" + "with pririty value = "
		+ this.sumOfPriorities;
    }
}
