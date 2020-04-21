package ir.hosseinmp76.workFlowPlanner.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class PropertyPriority implements BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public PropertyPriority(Long id) {
	this.id = id;
    }

    @Id
    private Long id;

    private Property property;

    private Priority priority;

    private Long value;

    public String toString() {
	return this.property.getName() + " : " + this.priority.getName()
		+ " : " + this.getValue();
    }
}
