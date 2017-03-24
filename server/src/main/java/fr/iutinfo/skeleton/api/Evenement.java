package fr.iutinfo.skeleton.api;

import fr.iutinfo.skeleton.common.dto.EvenementDto;

public class Evenement {
	String nom;
	String date;
	boolean dispo;
	
	public Evenement(){}
	
	public Evenement(String name, String date) {
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
	
	public void intiFromDto(EvenementDto dto){
		this.nom = dto.getNom();
		this.date = dto.getDate();
		this.dispo = dto.getDispo();
	}
	
	public EvenementDto convertToDto(){
		EvenementDto dto = new EvenementDto();
		dto.setNom(this.getNom());
		dto.setDate(this.getDate());
		dto.setDispo(this.getDispo());
		return dto;
	}
}
