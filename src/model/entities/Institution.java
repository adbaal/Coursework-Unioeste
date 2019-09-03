package model.entities;

public class Institution {

	private Integer id;
	private String abreviationOrAcronym;
	private String name;
	
	public Institution() {
		
	}

	public Institution(Integer id, String abreviationOrAcronym, String name) {
		this.id = id;
		this.abreviationOrAcronym = abreviationOrAcronym;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAbreviationOrAcronym() {
		return abreviationOrAcronym;
	}

	public void setAbreviationOrAcronym(String abreviationOrAcronym) {
		this.abreviationOrAcronym = abreviationOrAcronym;
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
		result = prime * result + ((abreviationOrAcronym == null) ? 0 : abreviationOrAcronym.hashCode());
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
		if (abreviationOrAcronym == null) {
			if (other.abreviationOrAcronym != null)
				return false;
		} else if (!abreviationOrAcronym.equals(other.abreviationOrAcronym))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Institution [id=" + id + ", abreviationOrAcronym=" + abreviationOrAcronym + ", name=" + name + "]";
	}
	
	
	
	
	
	
}

