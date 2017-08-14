package com.ecommerce.interfaces;

import java.util.List;

public interface DataExchange {
 
	public <T> List<T> selectData(String sqlString, List<?> param);
	public int updateData(String sqlString, List<?> param);
	public void deleteData(String sqlString, List<?> param);
	public void insertData(String sqlString, List<?> param);
	
}
