package fr.bonneau.warhammerPainting.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import fr.bonneau.warhammerPainting.models.enums.PaintingTypes;

public class PaintingDto {

	private int id;
	
	@NotBlank
	private String name;
	
	@NotNull
	private PaintingTypes type;
	
	
	public int getId() { return id; }
	  
	public void setId(int id) { this.id = id; }
	 

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PaintingTypes getType() {
		return type;
	}

	public void setType(PaintingTypes type) {
		this.type = type;
	}

	@Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
