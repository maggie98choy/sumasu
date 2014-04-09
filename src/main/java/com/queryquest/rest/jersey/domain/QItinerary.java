package com.queryquest.rest.jersey.domain;

import java.math.BigInteger;
import java.util.Date;

public class QItinerary 
{
	private String origin;
	private String destination;
	private Date departureTime;
	private String carrier;
	private BigInteger flightTime;
	private String arrivalTime;
	private String price;
	private String flightNo;
	private boolean nonStop;
	

	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public Date getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}
	public String getCarrier() {
		return carrier;
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	public BigInteger getFlightTime() {
		return flightTime;
	}
	public void setFlightTime(BigInteger flightTime) {
		this.flightTime = flightTime;
	}
	public String getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public String getFlightNo() {
		return flightNo;
	}
	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}
	public boolean getNonStop() {
		return nonStop;
	}
	public void setNonStop(boolean nonStop) {
		this.nonStop = nonStop;
	}
	
	@Override
	public String toString() {
		return "QItinerary[origin=" + origin + ", destination=" + destination
				+ ", departureTime=" + departureTime + ", carrier=" + carrier 
				+ ", flightTime=" + flightTime + ", arrivalTime=" + arrivalTime +", price=" + price +", flightNo=" + flightNo +", nonStop=" + nonStop +"]";
	}
	
	public QItinerary(String origin2, String destination2, Date departureTime2,
			String carrier2, BigInteger flightTime2, String arrivalTime2,
			String price2, String flightNo2, boolean nonStop) 
	{
		this.origin = origin2;
		this.destination = destination2;
		this.departureTime = departureTime2;
		this.carrier = carrier2;
		this.flightTime = flightTime2;
		this.arrivalTime = arrivalTime2;
		this.price = price2;
		this.flightNo = flightNo2;
		this.nonStop = nonStop;
		
	}
	
	public QItinerary()
	{
		
	}
	
}
