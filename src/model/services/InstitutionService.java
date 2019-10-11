package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.InstitutionDao;
import model.entities.Institution;

public class InstitutionService {

	private InstitutionDao dao = DaoFactory.createInstitutionDao();
	
	public List<Institution> findAll(){
		return dao.findAll();
	}
	
	public void saveOrUpdate(Institution obj) {
		if(obj.getId()==null) {
			dao.insert(obj);
		}
		else {
			dao.update(obj);
		}
	}
	
	public void remove(Institution obj) {
		dao.deleteById(obj.getId());
	}
}
