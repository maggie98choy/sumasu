package com.queryquest.rest.jersey.domain;

public class SearchResult {
	private String name;
	private String address;
	private String phoneNo;
	private String URL;
	private int noOfStars;
    private boolean isRecommended;
    private float recommendedRating;
    private String activity;
    private String category;
    private String stateCode;
	
    
	public SearchResult(String name, String address, String category,
			String phoneNo, String stateCode) 
	{
		this.name = name;
		this.address = address;
		this.category = category;
		this.phoneNo = phoneNo;
		this.stateCode = stateCode;
	}
	
	public SearchResult()
	{}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
	@Override
	public String toString() {
		return "SearchResult [name=" + name + ", address=" + address
				+ ", phoneNo=" + phoneNo + ", URL=" + URL + ", noOfStars="
				+ noOfStars + ", isRecommended=" + isRecommended
				+ ", recommendedRating=" + recommendedRating + ", activity="
				+ activity + ", category=" + category + "]";
	}
	public int getNoOfStars() {
		return noOfStars;
	}
	public void setNoOfStars(int noOfStars) {
		this.noOfStars = noOfStars;
	}
	public boolean isRecommended() {
		return isRecommended;
	}
	public void setRecommended(boolean isRecommended) {
		this.isRecommended = isRecommended;
	}
	public float getRecommendedRating() {
		return recommendedRating;
	}
	public void setRecommendedRating(float recommendedRating) {
		this.recommendedRating = recommendedRating;
	}
	public String getActivity() {
		return activity;
	}
	public void setActivity(String activity) {
		this.activity = activity;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getStateCode() {
		return stateCode;
	}
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
	

}
