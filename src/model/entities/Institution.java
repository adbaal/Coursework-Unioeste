package model.entities;

public class Institution {

	private Integer id;
	private String abbreviationOrAcronym;
	private String name;
	
	public Institution() {
		
	}

	public Institution(Integer id, String abbreviationOrAcronym, String name) {
		this.id = id;
		this.abbreviationOrAcronym = abbreviationOrAcronym;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAbbreviationOrAcronym() {
		return abbreviationOrAcronym;
	}

	public void setAbbreviationOrAcronym(String abbreviationOrAcronym) {
		this.abbreviationOrAcronym = abbreviationOrAcronym;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((abbreviationOrAcronym == null) ? 0 : abbreviationOrAcronym.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Institution other = (Institution) obj;
		if (abbreviationOrAcronym == null) {
			if (other.abbreviationOrAcronym != null)
				return false;
		} else if (!abbreviationOrAcronym.equals(other.abbreviationOrAcronym))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Institution [id=" + id + ", abbreviationOrAcronym=" + abbreviationOrAcronym + ", name=" + name + "]";
	}
	
	
	
	
	
	
}

