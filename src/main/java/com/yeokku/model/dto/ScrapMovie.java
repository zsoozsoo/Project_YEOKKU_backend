package com.yeokku.model.dto;

import java.util.List;

public class ScrapMovie {

	private String movieID; //영화 구별 ID
	private String movieTitle; //영화 제목
	private String prodYear;
	private String movieNation;
	private String runtime;
	private String rating;
	private String genre;
	private String infoUrl;
	private String director;
	private List<String> actors;
	private String company;
	private String plot;
	private String imageUrl;
	

	public ScrapMovie() {
		super();
		
	}

	public ScrapMovie(String movieID, String movieTitle, String prodYear, String movieNation, String runtime,
			String rating, String genre, String infoUrl, String director, List<String> actors, String company, String plot) {
		super();
		this.movieID = movieID;
		this.movieTitle = movieTitle;
		this.prodYear = prodYear;
		this.movieNation = movieNation;
		this.runtime = runtime;
		this.rating = rating;
		this.genre = genre;
		this.infoUrl = infoUrl;
		this.director = director;
		this.actors = actors;
		this.company = company;
		this.plot = plot;
	}
	
	

	public ScrapMovie(String movieID, String movieTitle, String prodYear, String movieNation, String runtime,
			String rating, String genre, String infoUrl, String director, List<String> actors, String company,
			String plot, String imageUrl) {
		super();
		this.movieID = movieID;
		this.movieTitle = movieTitle;
		this.prodYear = prodYear;
		this.movieNation = movieNation;
		this.runtime = runtime;
		this.rating = rating;
		this.genre = genre;
		this.infoUrl = infoUrl;
		this.director = director;
		this.actors = actors;
		this.company = company;
		this.plot = plot;
		this.imageUrl = imageUrl;
	}

	public String getMovieID() {
		return movieID;
	}

	public void setMovieID(String movieID) {
		this.movieID = movieID;
	}

	public String getMovieTitle() {
		return movieTitle;
	}

	public void setMovieTitle(String movieTitle) {
		this.movieTitle = movieTitle;
	}

	public String getProdYear() {
		return prodYear;
	}

	public void setProdYear(String prodYear) {
		this.prodYear = prodYear;
	}

	public String getMovieNation() {
		return movieNation;
	}

	public void setMovieNation(String movieNation) {
		this.movieNation = movieNation;
	}

	public String getRuntime() {
		return runtime;
	}

	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getInfoUrl() {
		return infoUrl;
	}

	public void setInfoUrl(String infoUrl) {
		this.infoUrl = infoUrl;
	}

	
	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public List<String> getActors() {
		return actors;
	}

	public void setActors(List<String> actors) {
		this.actors = actors;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPlot() {
		return plot;
	}

	public void setPlot(String plot) {
		this.plot = plot;
	}
	
	

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Override
	public String toString() {
		return "ScrapMovie [movieID=" + movieID + ", movieTitle=" + movieTitle + ", prodYear=" + prodYear
				+ ", movieNation=" + movieNation + ", runtime=" + runtime + ", rating=" + rating + ", genre=" + genre
				+ ", infoUrl=" + infoUrl + ", director=" + director + ", actors=" + actors + ", company=" + company
				+ ", plot=" + plot + ", imageUrl=" + imageUrl + "]";
	}

	
	

	
	
	
}
