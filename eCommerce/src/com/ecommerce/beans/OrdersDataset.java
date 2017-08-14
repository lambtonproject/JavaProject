package com.ecommerce.beans;

import java.util.Date;

public class OrdersDataset {

	private int ORDER_ID;
	private int ORDER_PRODUCT_ID;
	private int ORDER_CUSTOMER_ID;
	private String ORDER_PRODUCT_NAME;
	private String ORDER_CUSTOMER_NAME;
	private int ORDER_QUANTITY;
	private Date ORDER_DATE;
	private float ORDER_TOTAL;

	public int getORDER_ID() {
		return this.ORDER_ID;
	}
	
	public void setORDER_ID(int ORDER_ID) {	
		this.ORDER_ID = ORDER_ID;	
	}

	public int getORDER_PRODUCT_ID() {
		return this.ORDER_PRODUCT_ID;
	}
	
	public void setORDER_PRODUCT_ID(int ORDER_PRODUCT_ID) {	
		this.ORDER_PRODUCT_ID = ORDER_PRODUCT_ID;	
	}

	public int getORDER_CUSTOMER_ID() {
		return this.ORDER_CUSTOMER_ID;
	}
	
	public void setORDER_CUSTOMER_ID(int ORDER_CUSTOMER_ID) {	
		this.ORDER_CUSTOMER_ID = ORDER_CUSTOMER_ID;	
	}	

	public String getORDER_PRODUCT_NAME() {
		return this.ORDER_PRODUCT_NAME;
	}
	
	public void setORDER_PRODUCT_NAME(String ORDER_PRODUCT_NAME) {	
		this.ORDER_PRODUCT_NAME = ORDER_PRODUCT_NAME;	
	}	

	public String getORDER_CUSTOMER_NAME() {
		return this.ORDER_CUSTOMER_NAME;
	}
	
	public void setORDER_CUSTOMER_NAME(String ORDER_CUSTOMER_NAME) {	
		this.ORDER_CUSTOMER_NAME = ORDER_CUSTOMER_NAME;	
	}
	
	public int getORDER_QUANTITY() {
		return this.ORDER_QUANTITY;
	}
	
	public void setORDER_QUANTITY(int ORDER_QUANTITY) {	
		this.ORDER_QUANTITY = ORDER_QUANTITY;	
	}	

	public Date getORDER_DATE() {
		return this.ORDER_DATE;
	}
	
	public void setORDER_DATE(Date ORDER_DATE) {	
		this.ORDER_DATE = ORDER_DATE;	
	}		

	public float getORDER_TOTAL() {
		return this.ORDER_TOTAL;
	}
	
	public void setORDER_TOTAL(float ORDER_TOTAL) {	
		this.ORDER_TOTAL = ORDER_TOTAL;	
	}		
	
}
