package ir.hosseinmp76.workFlowPlanner.utills;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ir.hosseinmp76.workFlowPlanner.model.Formula;
import ir.hosseinmp76.workFlowPlanner.model.Property;
import lombok.Data;

@Data
public class PropertySet {

    /**
     *
     */

    List<Property> properties;

    Map<Formula, Long> sumOfPriorities = new HashMap<>();

    public PropertySet(final List<Property> properties2) {
	this.properties = new ArrayList<>();
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
