package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.SupervisorOrEvaluatorDao;
import model.entities.SupervisorOrEvaluator;

public class SupervisorOrEvaluatorService {

	private SupervisorOrEvaluatorDao dao = DaoFactory.createSupervisorOrEvaluatorDao();
	
	public List<SupervisorOrEvaluator> findAll(){
		return dao.findAll();
	}
	
	public void saveOrUpdate(SupervisorOrEvaluator obj) {
		if(obj.getId()==null) {
			dao.insert(obj);
		}
		else {
			dao.update(obj);
		}
	}
	
	public void remove(SupervisorOrEvaluator obj) {
		dao.deleteById(obj.getId());
	}
}
