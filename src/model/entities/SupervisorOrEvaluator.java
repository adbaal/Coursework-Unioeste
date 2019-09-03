package model.entities;

import model.entities.enums.Gender;

public class SupervisorOrEvaluator extends Person{

	private Institution institution;

	public SupervisorOrEvaluator() {
		super();
	}

	public SupervisorOrEvaluator(Integer id, String name, String email, String mobileNamber, Gender gender, Institution institution) {
		super(id, name, email, mobileNamber, gender);
		this.institution = institution;
	}

	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	@Override
	public String toString() {
		return "Supervisor [" + super.toString() + ", institution=" + institution + "]";
	}


	
	
	
	
}
