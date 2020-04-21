package ir.hosseinmp76.workFlowPlanner.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Priority implements BaseModel {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    private String name;
    private Property baseProperty;

    public Priority(final Long id) {
	this.id = id;
    }

    @Override
    public String toString() {
	return this.name + " : " + this.baseProperty.getName();

    }
}
