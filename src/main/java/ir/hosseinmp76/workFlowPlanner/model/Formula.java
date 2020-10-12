package ir.hosseinmp76.workFlowPlanner.model;

import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Formula implements BaseModel {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String name;

    Map<Priority, Long> coefficients = new HashMap<>();

    public Formula() {
    }

    public Formula(final Long id) {
	this.id = id;
    }

    @Override
    public String toString() {
	return this.name + " : ";

    }

    public Long getCoefficient(Priority priority) {
	var res = this.getCoefficients().get(priority);
	if (res == null)
	    return 0L;
	return res;
    }
}
