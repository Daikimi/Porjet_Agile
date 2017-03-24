package fr.iutinfo.skeleton.common.dto;

public class EvenementDto {
	String nom;
	String date;
	boolean dispo;
	
	public EvenementDto(){}
	
	public EvenementDto(String name, String date) {
		super();
		this.nom = name;
		this.date = date;
		this.dispo = true;
	}

	/**
	 * @return the name
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @param name the name to set
	 */
	public void setNom(String name) {
		this.nom = name;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the dispo
	 */
	public boolean getDispo() {
		return dispo;
	}

	/**
	 * @param dispo the dispo to set
	 */
	public void setDispo(boolean dispo) {
		this.dispo = dispo;
	}
}
