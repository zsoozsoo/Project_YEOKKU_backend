package com.yeokku.model.dto;

public class ScrapMusic {

	private String songTitle;
	private String singer;
	private String albumImageLink;
	private String albumInfoLink;
	private String chartRank;
	
	public ScrapMusic() {
		super();
		
	}

	public ScrapMusic(String songTitle, String singer, String albumImageLink, String albumInfoLink, String chartRank) {
		super();
		this.songTitle = songTitle;
		this.singer = singer;
		this.albumImageLink = albumImageLink;
		this.albumInfoLink = albumInfoLink;
		this.chartRank = chartRank;
	}

	public String getsongTitle() {
		return songTitle;
	}

	public void setsongTitle(String songTitle) {
		this.songTitle = songTitle;
	}

	public String getSinger() {
		return singer;
	}

	public void setSinger(String singer) {
		this.singer = singer;
	}

	public String getAlbumImageLink() {
		return albumImageLink;
	}

	public void setAlbumImageLink(String albumImageLink) {
		this.albumImageLink = albumImageLink;
	}

	public String getAlbumInfoLink() {
		return albumInfoLink;
	}

	public void setAlbumInfoLink(String albumInfoLink) {
		this.albumInfoLink = albumInfoLink;
	}

	public String getChartRank() {
		return chartRank;
	}

	public void setChartRank(String chartRank) {
		this.chartRank = chartRank;
	}

	@Override
	public String toString() {
		return "ScrapMusic [songTitle=" + songTitle + ", singer=" + singer + ", albumImageLink=" + albumImageLink
				+ ", albumInfoLink=" + albumInfoLink + ", chartRank=" + chartRank + "]";
	}
	
	
	
	
	
	
}
