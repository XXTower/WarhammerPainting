package fr.bonneau.warhammerPainting.models.enums;

public enum SubFaction {

	ULTRAMARINS(Faction.SPACE_MARINS), BLOOD_ANGEL(Faction.SPACE_MARINS),
	CANOPTECK(Faction.NECRON), SKORPEKH(Faction.NECRON);
	
	private Faction faction;

	private SubFaction(Faction faction) {
		this.faction = faction;
	}

	public Faction getFaction() {
		return faction;
	}
}
