package com.queryquest.rest.jersey.domain;

public class Rating {
 private String email;
 private int rating;
 private String businessName;
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public int getRating() {
	return rating;
}
public void setRating(int rating) {
	this.rating = rating;
}
public String getBusinessName() {
	return businessName;
}
public void setBusinessName(String businessName) {
	this.businessName = businessName;
}
@Override
public String toString() {
	return "Rating [email=" + email + ", rating=" + rating + ", businessName="
			+ businessName + "]";
}
 
 
	

}
