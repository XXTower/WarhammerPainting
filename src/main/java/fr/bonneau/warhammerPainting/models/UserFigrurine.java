package fr.bonneau.warhammerPainting.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

public class UserFigrurine {
	
	@Id
	@Column(name = "USER_FIGURINE_ID")
	@GeneratedValue
	private int id;
	
	@JoinColumn(name = "USER_ID")
	private int userId;
	
	@JoinColumn(name = "FIGURINE_ID")
	private int figurineId;
	
	@JoinColumn(name = "PAINTING_ID")
	private List<Integer> listPaintingId;
	
	@Column(name = "TITLE")
	private String title;
	
	@Column(name = "DESCRIPTION")
	private String descripsion;

	public UserFigrurine(int userId, int figurineId, List<Integer> listPaintingId, String title, String descripsion) {
		super();
		this.userId = userId;
		this.figurineId = figurineId;
		this.listPaintingId = listPaintingId;
		this.title = title;
		this.descripsion = descripsion;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getFigurineId() {
		return figurineId;
	}

	public void setFigurineId(int figurineId) {
		this.figurineId = figurineId;
	}

	public List<Integer> getListPaintingId() {
		return listPaintingId;
	}

	public void setListPaintingId(List<Integer> listPaintingId) {
		this.listPaintingId = listPaintingId;
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

	public int getId() {
		return id;
	}

}
