package ir.hosseinmp76.workFlowPlanner.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Property implements BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    private String name;

    private Property dadProperty;

    private List<Priority> priorities;

    public Property(Long id) {
	this.id = id;
	priorities = new ArrayList<Priority>();

    }

    @Override
    public String toString() {
	return this.getName();

    }

    public String fullToString() {
	return "Priority : " + this.name + " : " + this.id + " : "
		+ this.priorities.stream().reduce("",
			(value, combined) -> combined.getName() + " + " + value,
			(a, b) -> a + b);

    }

    public List<Priority> getAllPriorities() {
	var res = new ArrayList<>(this.getPriorities());
	if (this.getDadProperty() != null)
	    res.addAll(this.getDadProperty().getAllPriorities());
	return res;

    }
}
