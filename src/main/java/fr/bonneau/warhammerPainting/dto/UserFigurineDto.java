package fr.bonneau.warhammerPainting.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import fr.bonneau.warhammerPainting.models.Figurine;

public class UserFigurineDto {
	
	@NotNull
	private UserDto user;

	@NotNull
	private Figurine figurine;

	private List<PaintingDto> listPainting;

	@NotBlank
	private String title;

	@NotBlank
	private String descripsion;

	private int id;
	
	private boolean visibility;
	
	public UserDto getUser() {
		
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public Figurine getFigurine() {
		
		return figurine;
	}

	public void setFigurine(Figurine figurine) {
		this.figurine = figurine;
	}

	public List<PaintingDto> getListPainting() {
		
		return listPainting;
	}

	public void setListPainting(List<PaintingDto> listPainting) {
		this.listPainting = listPainting;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescripsion() {
		
		return descripsion;
	}

	public void setDescripsion(String descripsion) {
		this.descripsion = descripsion;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public boolean isVisibility() {
		return visibility;
	}

	public void setVisibility(boolean visibility) {
		this.visibility = visibility;
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
