package fr.bonneau.warhammerPainting.models;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@Table(name = "USER_FIGURINE")
public class UserFigurine {
	
	@Id
	@Column(name = "USER_FIGURINE_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@JoinColumn(name = "USER_ID")
	@OneToOne
	private User user;
	
	@JoinColumn(name = "FIGURINE_ID")
	@OneToOne
	private Figurine figurine;

	
	@ManyToMany
	@JoinTable(name="PAINTING_FIGURINE",
	   joinColumns = @JoinColumn(name="USER_FIGURINE_ID"),
	   inverseJoinColumns = @JoinColumn(name = "PAINTING_ID"))
	private Set<Painting> listPainting;
	
	@Column(name = "TITLE")
	private String title;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "VISBILITY")
	private boolean visibility;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Figurine getFigurine() {
		return figurine;
	}

	public void setFigurine(Figurine figurine) {
		this.figurine = figurine;
	}

	public Set<Painting> getListPainting() {
		return listPainting;
	}

	public void setListPainting(Set<Painting> listPainting) {
		this.listPainting = listPainting;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
