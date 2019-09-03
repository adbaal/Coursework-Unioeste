package model.entities;

import model.entities.enums.Gender;

public class Student extends Person{


	public Student() {
		super();
	}

	public Student(Integer id, String name, String email, String mobileNamber, Gender gender) {
		super(id, name, email, mobileNamber, gender);
	}

	
	@Override
	public String toString() {
		return "Student [" + super.toString() + "]";
	}

	

	
	


	
	
	
	

}
