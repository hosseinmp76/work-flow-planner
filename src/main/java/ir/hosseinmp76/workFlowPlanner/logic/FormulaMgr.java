package ir.hosseinmp76.workFlowPlanner.logic;

import ir.hosseinmp76.workFlowPlanner.model.Formula;
import ir.hosseinmp76.workFlowPlanner.model.Priority;
import ir.hosseinmp76.workFlowPlanner.persistency.BasicDAO;
import ir.hosseinmp76.workFlowPlanner.persistency.dao.FormulaDAO;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class FormulaMgr extends BasicMgr<Formula> {

    @Inject
    FormulaDAO dao;

    public Formula create(final String name) {
	return this.dao.create(name);
    }

    @Override
    public Formula createEmptyModel() {
	return new Formula();
    }

    @Override
    protected FormulaDAO getDAO() {
	return this.dao;
    }

    public void updateCoefficent(Formula formula, Priority tr, Long newValue) {
	this.getDAO().update(formula, tr, newValue);
    }

}
