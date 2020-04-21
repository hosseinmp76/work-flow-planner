package ir.hosseinmp76.workFlowPlanner.persistency;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import ir.hosseinmp76.workFlowPlanner.model.Property;

@Component
public class PropertyDAO extends BasicInMemoryDAO<Property> {

    private final List<Property> properties = new ArrayList<>();

    public Property create(final String name, final Property dadProperty) {
	final var res = this.createEmptyModel();
	res.setDadProperty(dadProperty);
	res.setName(name);
	return res;
    }

    @Override
    protected List<Property> getObjects() {
	return this.properties;
    }

    @Override
    protected Property emptyObjectFactory(Long id) {
	return new Property(id);
    }

}
