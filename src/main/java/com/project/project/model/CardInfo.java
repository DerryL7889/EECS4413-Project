package com.project.project.model;

public class CardInfo {

	String creditCardNumber, nameOnCard, expirationDate, securityCode;
	boolean expedited;
	
	public CardInfo(String creditCardNumber, String nameOnCard, String expirationDate, String securityCode, Boolean expedited) {
        this.creditCardNumber = creditCardNumber;
        this.nameOnCard = nameOnCard;
        this.expirationDate = expirationDate;
        this.securityCode = securityCode;
        if(expedited == null) {
        	this.expedited = false;
        }else {
        	this.expedited = expedited;
        }
    }
	
//	public CardInfo(String creditCardNumber, String nameOnCard, String expirationDate, String securityCode) {
//        this.creditCardNumber = creditCardNumber;
//        this.nameOnCard = nameOnCard;
//        this.expirationDate = expirationDate;
//        this.securityCode = securityCode;
//        this.expedited = false;
//    }
	
    public boolean getExpedited() {
        return expedited;
    }
}
