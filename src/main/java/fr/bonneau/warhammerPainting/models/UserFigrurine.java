package fr.bonneau.warhammerPainting.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

public class UserFigrurine {
	
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
	
	@JoinColumn(name = "PAINTING_ID")
	@OneToMany
	private List<Painting> listPainting;
	
	@Column(name = "TITLE")
	private String title;
	
	@Column(name = "DESCRIPTION")
	private String descripsion;

	public UserFigrurine(int id, User user, Figurine figurine, List<Painting> listPainting, String title,
			String descripsion) {
		super();
		this.id = id;
		this.user = user;
		this.figurine = figurine;
		this.listPainting = listPainting;
		this.title = title;
		this.descripsion = descripsion;
	}

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

	public List<Painting> getListPainting() {
		return listPainting;
	}

	public void setListPainting(List<Painting> listPainting) {
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

	
}
