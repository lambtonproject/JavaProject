package com.ecommerce.beans;

public class UsersDataset implements Comparable<UsersDataset> {

	private int USER_ID;
	private String USER_USERNAME;
	private String USER_PASSWORD;
	private String USER_SCREENNAME;

	public int getUSER_ID() {
		return this.USER_ID;
	}
	
	public void setUSER_ID(int USER_ID) {	
		this.USER_ID = USER_ID;	
	}

	public String getUSER_USERNAME() {
		return this.USER_USERNAME;
	}
	
	public void setUSER_USERNAME(String USER_USERNAME) {	
		this.USER_USERNAME = USER_USERNAME;	
	}

	public String getUSER_PASSWORD() {
		return this.USER_PASSWORD;
	}
	
	public void setUSER_PASSWORD(String USER_PASSWORD) {	
		this.USER_PASSWORD = USER_PASSWORD;	
	}	

	public String getUSER_SCREENNAME() {
		return this.USER_SCREENNAME;
	}
	
	public void setUSER_SCREENNAME(String USER_SCREENNAME) {	
		this.USER_SCREENNAME = USER_PASSWORD;	
	}

	@Override
	public int compareTo(UsersDataset ds) {

//		return this.USER_ID - ds.USER_ID;    //Sorts the objects in ascending order
        
        return ds.USER_ID - this.USER_ID;    //Sorts the objects in descending order
        
	}			
	
}
