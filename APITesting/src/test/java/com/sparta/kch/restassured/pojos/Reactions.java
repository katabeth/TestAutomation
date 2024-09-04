package com.sparta.kch.restassured.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Reactions{

	@JsonProperty("confused")
	private int confused;
	@JsonProperty("-1")
	private int minusOne;
	@JsonProperty("total_count")
	private int totalCount;
	@JsonProperty("+1")
	private int plusOne;
	@JsonProperty("rocket")
	private int rocket;
	@JsonProperty("hooray")
	private int hooray;
	@JsonProperty("eyes")
	private int eyes;
	@JsonProperty("url")
	private String url;
	@JsonProperty("laugh")
	private int laugh;
	@JsonProperty("heart")
	private int heart;

	public int getConfused(){
		return confused;
	}
	public int getPlusOne(){
		return plusOne;
	}
	public int getTotalCount(){
		return totalCount;
	}
	public int getMinusOne(){
		return minusOne;
	}
	public int getRocket(){
		return rocket;
	}
	public int getHooray(){
		return hooray;
	}
	public int getEyes(){
		return eyes;
	}
	public String getUrl(){
		return url;
	}
	public int getLaugh(){
		return laugh;
	}
	public int getHeart(){
		return heart;
	}
}
