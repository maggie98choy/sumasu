package com.queryquest.rest.jersey.domain;

import java.util.ArrayList;

public class SearchAnalysis {

	private int numOfDays;
	private ArrayList<String> activity;
	private String location;
	
	public void addNumOfDays(int numOfDays){
		this.numOfDays+=numOfDays;
	}
	public int getNumOfDays() {
		return numOfDays;
	}
	public void setNumOfDays(int numOfDays) {
		this.numOfDays = numOfDays;
	}
	public ArrayList<String> getActivity() {
		return activity;
	}
	public void setActivity(ArrayList<String> activity) {
		this.activity = activity;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	@Override
	public String toString() {
		return "SearchAnalysis [numOfDays=" + numOfDays + ", activity="
				+ activity.toString() + ", location=" + location + "]";
	}
	
}
