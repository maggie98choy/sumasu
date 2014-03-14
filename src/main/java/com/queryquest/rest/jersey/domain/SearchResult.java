package com.queryquest.rest.jersey.domain;

public class SearchResult {
	private String name;
	private String address;
	private String phoneNo;
	private String URL;
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
				+ ", phoneNo=" + phoneNo + ", URL=" + URL + "]";
	}

}
