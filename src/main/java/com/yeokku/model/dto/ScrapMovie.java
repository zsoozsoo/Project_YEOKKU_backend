package com.yeokku.model.dto;

public class ScrapMovie {

	private String movieID; //영화 구별 ID
	private String movieTitle; //영화 제목
	private String prodYear;
	private String movieNation;
	private String runtime;
	private String rating;
	private String genre;
	private String infoUrl;
	

	public ScrapMovie() {
		super();
		
	}
	
	public ScrapMovie(String movieID, String movieTitle, String prodYear, String movieNation, String runtime,
			String rating, String genre, String infoUrl) {
		super();
		this.movieID = movieID;
		this.movieTitle = movieTitle;
		this.prodYear = prodYear;
		this.movieNation = movieNation;
		this.runtime = runtime;
		this.rating = rating;
		this.genre = genre;
		this.infoUrl = infoUrl;
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

	@Override
	public String toString() {
		return "ScrapMovie [movieID=" + movieID + ", movieTitle=" + movieTitle + ", prodYear=" + prodYear
				+ ", movieNation=" + movieNation + ", runtime=" + runtime + ", rating=" + rating + ", genre=" + genre
				+ ", infoUrl=" + infoUrl + "]";
	}

	
	
	
}
