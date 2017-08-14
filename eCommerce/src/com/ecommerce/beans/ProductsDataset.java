package com.ecommerce.beans;

public class ProductsDataset {
	
	private int PRODUCT_ID;
	private String PRODUCT_NAME;
	private String PRODUCT_CATEGORY;
	private int PRODUCT_QUANTITY;
	private float PRODUCT_UNIT_PRICE;
	private String PRODUCT_DESCRIPTION;
	private String PRODUCT_IMAGE;

	public int getPRODUCT_ID() {
		return this.PRODUCT_ID;
	}
	
	public void setPRODUCT_ID(int PRODUCT_ID) {	
		this.PRODUCT_ID = PRODUCT_ID;	
	}

	public String getPRODUCT_NAME() {
		return this.PRODUCT_NAME;
	}
	
	public void setPRODUCT_NAME(String PRODUCT_NAME) {	
		this.PRODUCT_NAME = PRODUCT_NAME;	
	}

	public String getPRODUCT_CATEGORY() {
		return this.PRODUCT_CATEGORY;
	}
	
	public void setPRODUCT_CATEGORY(String PRODUCT_CATEGORY) {	
		this.PRODUCT_CATEGORY = PRODUCT_CATEGORY;	
	}	

	public int getPRODUCT_QUANTITY() {
		return this.PRODUCT_QUANTITY;
	}
	
	public void setPRODUCT_QUANTITY(int PRODUCT_QUANTITY) {	
		this.PRODUCT_QUANTITY = PRODUCT_QUANTITY;	
	}	

	public float getPRODUCT_UNIT_PRICE() {
		return this.PRODUCT_UNIT_PRICE;
	}
	
	public void setPRODUCT_UNIT_PRICE(float PRODUCT_UNIT_PRICE) {	
		this.PRODUCT_UNIT_PRICE = PRODUCT_UNIT_PRICE;	
	}		

	public String getPRODUCT_DESCRIPTION() {
		return this.PRODUCT_DESCRIPTION;
	}
	
	public void setPRODUCT_DESCRIPTION(String PRODUCT_DESCRIPTION) {	
		this.PRODUCT_DESCRIPTION = PRODUCT_DESCRIPTION;	
	}		

	public String getPRODUCT_IMAGE() {
		return this.PRODUCT_IMAGE;
	}
	
	public void setPRODUCT_IMAGE(String PRODUCT_IMAGE) {	
		this.PRODUCT_IMAGE = PRODUCT_IMAGE;	
	}	
	
}
