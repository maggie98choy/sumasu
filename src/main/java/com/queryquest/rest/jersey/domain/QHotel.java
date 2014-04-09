package com.queryquest.rest.jersey.domain;

import java.math.BigInteger;

public class QHotel 
{

	private String hotelName;
	private String hotelChain;
	private String hotelCode;
	private String hotelAddress;
	private String hotelMinAmt;
	private String hotelMaxAmt;
	private String hotelRating;
	private BigInteger hotelDistance;
	private String hotelAttraction;
	private float hotelLongtitude;
	private float hotelLatitude;
	
	public float getHotelLongtitude() {
		return hotelLongtitude;
	}
	public void setHotelLongtitude(float hotelLongtitude) {
		this.hotelLongtitude = hotelLongtitude;
	}
	public float getHotelLatitude() {
		return hotelLatitude;
	}
	public void setHotelLatitude(float hotelLatitude) {
		this.hotelLatitude = hotelLatitude;
	}
	public String getHotelName() {
		return hotelName;
	}
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	public String getHotelChain() {
		return hotelChain;
	}
	public void setHotelChain(String hotelChain) {
		this.hotelChain = hotelChain;
	}
	public String getHotelCode() {
		return hotelCode;
	}
	public void setHotelCode(String hotelCode) {
		this.hotelCode = hotelCode;
	}
	public String getHotelAddress() {
		return hotelAddress;
	}
	public void setHotelAddress(String hotelAddress) {
		this.hotelAddress = hotelAddress;
	}
	public String getHotelMinAmt() {
		return hotelMinAmt;
	}
	public void setHotelMinAmt(String hotelMinAmt) {
		this.hotelMinAmt = hotelMinAmt;
	}
	public String getHotelMaxAmt() {
		return hotelMaxAmt;
	}
	public void setHotelMaxAmt(String hotelMaxAmt) {
		this.hotelMaxAmt = hotelMaxAmt;
	}
	public String getHotelRating() {
		return hotelRating;
	}
	public void setHotelRating(String hotelRating) {
		this.hotelRating = hotelRating;
	}
	public BigInteger getHotelDistance() {
		return hotelDistance;
	}
	public void setHotelDistance(BigInteger hotelDistance) {
		this.hotelDistance = hotelDistance;
	}
	public String getHotelAttraction() {
		return hotelAttraction;
	}
	public void setHotelAttraction(String hotelAttraction) {
		this.hotelAttraction = hotelAttraction;
	}
	
	@Override
	public String toString() {
		return "QHotel [hotelName=" + hotelName + ", hotelChain=" + hotelChain
				+ ", hotelCode=" + hotelCode + ", hotelAddress=" + hotelAddress
				+ ", hotelMinAmt=" + hotelMinAmt + ", hotelMaxAmt="
				+ hotelMaxAmt + ", hotelRating=" + hotelRating
				+ ", hotelDistance=" + hotelDistance + ", hotelAttraction="
				+ hotelAttraction + ", hotelLongtitude=" + hotelLongtitude
				+ ", hotelLatitude=" + hotelLatitude + "]";
	}
	
	
	public QHotel(String hotelName, String hotelChain, String hotelCode,
			String hotelAddress, String hotelMinAmt, String hotelMaxAmt,
			String hotelRating, BigInteger hotelDistance,
			String hotelAttraction, float hotelLongtitude, float hotelLatitude) 
	{

		this.hotelName = hotelName;
		this.hotelChain = hotelChain;
		this.hotelCode = hotelCode;
		this.hotelAddress = hotelAddress;
		this.hotelMinAmt = hotelMinAmt;
		this.hotelMaxAmt = hotelMaxAmt;
		this.hotelRating = hotelRating;
		this.hotelDistance = hotelDistance;
		this.hotelAttraction = hotelAttraction;
		this.hotelLongtitude = hotelLongtitude;
		this.hotelLatitude = hotelLatitude;
	}

	public QHotel()
	{
	
	}
}
