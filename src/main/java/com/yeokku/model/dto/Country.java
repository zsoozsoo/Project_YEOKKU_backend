package com.yeokku.model.dto;

public class Country {

	private String countryCode;
	private String countryName;
	private String continentName;
	private String isTravelAvailable;
	
	public Country() {}
	
	public Country(String countryCode, String countryName, String continentName, String isTravelAvailable) {
		super();
		this.countryCode = countryCode;
		this.countryName = countryName;
		this.continentName = continentName;
		this.isTravelAvailable = isTravelAvailable;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getContinentName() {
		return continentName;
	}

	public void setContinentName(String continentName) {
		this.continentName = continentName;
	}

	public String getIsTravelAvailable() {
		return isTravelAvailable;
	}

	public void setIsTravelAvaiable(String isTravelAvailable) {
		this.isTravelAvailable = isTravelAvailable;
	}
	
	
	
	
}
