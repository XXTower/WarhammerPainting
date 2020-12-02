package fr.bonneau.warhammerPainting.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import fr.bonneau.warhammerPainting.models.enums.Faction;
import fr.bonneau.warhammerPainting.models.enums.SubFaction;

@Entity
@Table(name = "FIGURINE")
public class Figurine {

	@Id
	@Column(name = "FIGURINE_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "NAME")
	private String name;
	@Column(name = "FACTION")
	private Faction faction;
	@Column(name = "SUBFACTION")
	private SubFaction subFaction;
	
	public Figurine(int id, String name, Faction faction, SubFaction subFaction) {
		super();
		this.id = id;
		this.name = name;
		this.faction = faction;
		this.subFaction = subFaction;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Faction getFaction() {
		return faction;
	}
	public void setFaction(Faction faction) {
		this.faction = faction;
	}
	public SubFaction getSubFaction() {
		return subFaction;
	}
	public void setSubFaction(SubFaction subFaction) {
		this.subFaction = subFaction;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
