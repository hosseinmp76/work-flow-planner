package ir.hosseinmp76.workFlowPlanner.ui;

import java.util.ArrayList;
import java.util.List;

import ir.hosseinmp76.workFlowPlanner.model.BaseModel;
import ir.hosseinmp76.workFlowPlanner.model.Property;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class PropertySet {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

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

    @Override
    public String toString() {
	final StringBuilder props = new StringBuilder();
	this.properties.stream().forEach(p -> props.append(p.getName() + ", "));
	return "PropertySet: [" + props + "]" + "with pririty value = "
		+ this.sumOfPriorities;
    }
}
