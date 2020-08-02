package ir.hosseinmp76.workFlowPlanner.persistency.dao;

import ir.hosseinmp76.workFlowPlanner.model.Formula;
import ir.hosseinmp76.workFlowPlanner.model.Priority;
import ir.hosseinmp76.workFlowPlanner.persistency.BasicDAO;

public interface FormulaDAO extends BasicDAO<Formula> {

    Formula create(String name);

    public void update(Formula formula, Priority tr, Long newValue);
}
