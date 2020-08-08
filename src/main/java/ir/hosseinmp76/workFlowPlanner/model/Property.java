package ir.hosseinmp76.workFlowPlanner.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private boolean isFeature = false;

    @Column(unique = true)
    private String name;

    // This field is a table column
    // It identifies the parent of the current row
    // It it will be written as the type of dirId
    // By default this relationship will be eagerly fetched
    // , which you may or may not want
    @ManyToOne
    private Property parent;

    // This field is not a table column
    // It is a collection of those Dir rows that have this row as a parent.
    // This is the other side of the relationship defined by the parent field.
    @OneToMany(mappedBy = "parent")
    private List<Property> children;

    Map<Priority, Long> proportions = new HashMap<Priority, Long>();

    public Property(final Long id) {
	this.id = id;

    }

    public String fullToString() {
	return "Property : " + this.name + " : " + this.id + " : ";

    }

    public List<Property> getChildren() {
	return this.children;
    }

    public void update(final Priority id, final Long coefficent) {
	this.proportions.put(id, coefficent);
    }

    public List<Property> getGrandChildren() {
	if (this.children.size() == 0) {
	  return this.getChildren();
	}
	var res = new ArrayList<Property>();
	this.children.forEach(child -> {
	    var t =child.getGrandChildren();
	    if(t.size() == 0)
		t.add(child);
	    res.addAll(t);
	});
	return res;
    }

    @Override
    public String toString() {
	return this.getName();

    }

    public Long getProportion(Priority priority) {
	var res = this.getProportions().get(priority);
	if (res == null)
	    return 0L;
	return res;
    }

}
