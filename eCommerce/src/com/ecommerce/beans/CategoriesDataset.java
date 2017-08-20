package com.ecommerce.beans;

public class CategoriesDataset implements Comparable<CategoriesDataset> {

	private int CATEGORY_ID;
	private String CATEGORY_NAME;

	public int getCATEGORY_ID() {
		return this.CATEGORY_ID;
	}
	
	public void setCATEGORY_ID(int CATEGORY_ID) {	
		this.CATEGORY_ID = CATEGORY_ID;	
	}

	public String getCATEGORY_NAME() {
		return this.CATEGORY_NAME;
	}
	
	public void setCATEGORY_NAME(String CATEGORY_NAME) {	
		this.CATEGORY_NAME = CATEGORY_NAME;	
	}

	@Override
	public int compareTo(CategoriesDataset ds) {

//		return this.CATEGORY_ID - ds.CATEGORY_ID;    //Sorts the objects in ascending order
        
        return ds.CATEGORY_ID - this.CATEGORY_ID;    //Sorts the objects in descending order
        
	}		
	
}
