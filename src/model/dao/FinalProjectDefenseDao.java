package model.dao;

import java.util.List;

import model.entities.FinalProjectDefense;
import model.entities.SupervisorOrEvaluator;

public interface FinalProjectDefenseDao {
	
	void insert(FinalProjectDefense obj);
	void update(FinalProjectDefense obj);
	void deleteById(Integer id);
	FinalProjectDefense findById(Integer id);
	List<FinalProjectDefense> findAll();
	List<FinalProjectDefense> findBySupervisor(SupervisorOrEvaluator supervisor);
	List<FinalProjectDefense> findByAvaliator(SupervisorOrEvaluator avaliator);
}
