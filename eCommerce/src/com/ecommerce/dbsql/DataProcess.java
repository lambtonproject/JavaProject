package com.ecommerce.dbsql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;

import com.ecommerce.beans.*;
import com.ecommerce.interfaces.DataExchange;

public class DataProcess<K> implements DataExchange {
 
	Connection conn = null;
	
	K objWrapper;
	ServletContext context;
	
	public DataProcess(K objWrapper, ServletContext context) {
		
		this.objWrapper = objWrapper; 
		this.context = context; 

	}
	
	//SELECT DATA //////////////////////////////////////////////////////////////////////////////////
	 
	@SuppressWarnings("unchecked")
	public <T> List<T> selectData(String sqlString, List<?> param) {
		
		//Wrapper Class Style Return Type
		List<T> cArray = null;
		
		//HashMap Style Return Type
		//List<Map<String, Object>> resultList = null;
				
		try {
			 
			conn = ServerConn.getConn(context);
 
			PreparedStatement selectStat = conn.prepareStatement(sqlString);
			
			int i = 1; //index of parameter
			  
			Iterator<?> sqlParam =  param.iterator();
 
			while (sqlParam.hasNext()) {
				
				selectStat.setObject(i, sqlParam.next());
				i++;
				
			}
 
			ResultSet rs =  selectStat.executeQuery();
	        
			
			//HashMap Style to fetch data////////////////

//			resultList = new ArrayList<>();
//			Map<String, Object> row = null;
//			
//			ResultSetMetaData rsmd = rs.getMetaData();
//			int numberOfColumns = rsmd.getColumnCount();
//			
//			while (rs.next()) {
//				
//				row = new HashMap<>();
//				
//				for (int j = 1; j <= numberOfColumns; j++) {
//					
//					row.put(rsmd.getColumnName(j), rs.getObject(j));
//					
//				}
//				
//				resultList.add(row);
//				
//			}
			
			////////////////////////////////////////////
			
			
			//Wrapper Class Style to fetch data/////////
			
			cArray = new ArrayList<T>();
			
			
	        if (objWrapper instanceof UsersDataset) {
 
				while (rs.next()) {
					
					UsersDataset cdata = new UsersDataset();
					
					cdata.setUSER_USERNAME(rs.getString("USER_USERNAME"));
					cdata.setUSER_PASSWORD(rs.getString("USER_PASSWORD"));
					
					cArray.add((T) cdata);
					
				}
			
	        } else if (objWrapper instanceof CustomersDataset) {
		        
				while (rs.next()) {	
					
					CustomersDataset cdata = new CustomersDataset();
					
					cdata.setCUSTOMER_ID(rs.getInt("CUSTOMER_ID"));
					cdata.setCUSTOMER_USERNAME(rs.getString("CUSTOMER_USERNAME"));
					cdata.setCUSTOMER_PASSWORD(rs.getString("CUSTOMER_PASSWORD"));
					cdata.setCUSTOMER_NAME(rs.getString("CUSTOMER_NAME"));
					cdata.setCUSTOMER_PHONE(rs.getString("CUSTOMER_PHONE"));
					cdata.setCUSTOMER_EMAIL(rs.getString("CUSTOMER_EMAIL"));
					cdata.setCUSTOMER_ADDRESS(rs.getString("CUSTOMER_ADDRESS"));
					cdata.setCUSTOMER_CITY(rs.getString("CUSTOMER_CITY"));
					cdata.setCUSTOMER_STATE(rs.getString("CUSTOMER_STATE"));
					cdata.setCUSTOMER_POSTALCODE(rs.getString("CUSTOMER_POSTALCODE"));
				
					cArray.add((T) cdata);
					
				}
			
	        } else if (objWrapper instanceof ProductsDataset) {
		        
				while (rs.next()) {	
					
					ProductsDataset cdata = new ProductsDataset();
					
					cdata.setPRODUCT_ID(rs.getInt("PRODUCT_ID"));
					cdata.setPRODUCT_NAME(rs.getString("PRODUCT_NAME"));
					cdata.setPRODUCT_DESCRIPTION(rs.getString("PRODUCT_DESCRIPTION"));
					cdata.setPRODUCT_CATEGORY(rs.getString("PRODUCT_CATEGORY"));
					cdata.setPRODUCT_QUANTITY(rs.getInt("PRODUCT_QUANTITY"));
					cdata.setPRODUCT_UNIT_PRICE(rs.getFloat("PRODUCT_UNIT_PRICE"));
					cdata.setPRODUCT_IMAGE(rs.getString("PRODUCT_IMAGE"));
				
					cArray.add((T) cdata);
					
				}
			
	        } else if (objWrapper instanceof OrdersDataset) {
		        
				while (rs.next()) {	
					
					OrdersDataset cdata = new OrdersDataset();
					
					cdata.setORDER_ID(rs.getInt("ORDER_ID"));
					cdata.setORDER_PRODUCT_ID(rs.getInt("ORDER_PRODUCT_ID"));
					cdata.setORDER_CUSTOMER_ID(rs.getInt("ORDER_CUSTOMER_ID"));
					cdata.setORDER_PRODUCT_NAME(rs.getString("PRODUCT_NAME"));
					cdata.setORDER_CUSTOMER_NAME(rs.getString("CUSTOMER_NAME"));
					cdata.setORDER_QUANTITY(rs.getInt("ORDER_QUANTITY"));
					cdata.setORDER_DATE(rs.getDate("ORDER_DATE"));
					cdata.setORDER_TOTAL(rs.getFloat("ORDER_TOTAL"));
				
					cArray.add((T) cdata);

				}
			
	        } else if (objWrapper instanceof CategoriesDataset) {
		        
				while (rs.next()) {	
					
					CategoriesDataset cdata = new CategoriesDataset();
					
					cdata.setCATEGORY_ID(rs.getInt("CATEGORY_ID"));
					cdata.setCATEGORY_NAME(rs.getString("CATEGORY_NAME"));

					cArray.add((T) cdata);
					
				}
			
	        }
	        
	        ////////////////////////////////////////////
			
		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();
	
	    } finally {
	    	
	        try {
	        	
	        	conn.close();
	        	
	        } catch (SQLException ex) {
	        	
	            ex.printStackTrace();
	            
	        }
	        
	    }
		
		//HashMap Style return data
		//return (List<T>) resultList;	

		//Wrapper Class Style return data
		return cArray;	
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	//UPDATE DATA //////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public int updateData(String sqlString, List<?> param) {

		int success = 0;
		
		try {
			 
			conn = ServerConn.getConn(context);
			
			conn.setAutoCommit(false);

			PreparedStatement updateStat = conn.prepareStatement(sqlString);
			
			int i = 1; //index of parameter
			  
			Iterator<?> sqlParam =  param.iterator();
			
			while (sqlParam.hasNext()) {
				 
				updateStat.setObject(i, sqlParam.next());
				i++;
				
			}

			success = updateStat.executeUpdate();
	        
	        conn.commit();
	        
	        
		} catch (ClassNotFoundException | SQLException e) {
			
			try {
				
				conn.rollback();
				
			} catch (SQLException e1) {

				e1.printStackTrace();
			}
			
			e.printStackTrace();
			
	    } finally {
	    	
	        try {
	        	
	        	conn.close();
	        	
	        } catch (SQLException ex) {
	        	
	            ex.printStackTrace();
	            
	        }
	        
	    }
		
		return success;
		
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	//DELETE DATA //////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public void deleteData(String sqlString, List<?> param) {

		try {
			 
			conn = ServerConn.getConn(context);
			conn.setAutoCommit(false);
			
			PreparedStatement deleteStat = conn.prepareStatement(sqlString);
			
			int i = 1; //index of parameter
			  
			Iterator<?> sqlParam =  param.iterator();
			
			while (sqlParam.hasNext()) {
				 
				deleteStat.setObject(i, sqlParam.next());
				i++;
				
			}

	        deleteStat.executeUpdate();
	        
	        conn.commit();
	        
	        
		} catch (ClassNotFoundException | SQLException e) {
			
			try {
				
				conn.rollback();
				
			} catch (SQLException e1) {

				e1.printStackTrace();
			}
			
			e.printStackTrace();
			
	    } finally {
	    	
	        try {
	        	
	        	conn.close();
	        	
	        } catch (SQLException ex) {
	        	
	            ex.printStackTrace();
	            
	        }
	        
	    }	
		
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	//INSERT DATA //////////////////////////////////////////////////////////////////////////////////
	
	public void insertData(String sqlString, List<?> param) {
		
		try {
			 
			conn = ServerConn.getConn(context);
			conn.setAutoCommit(false);
			
			PreparedStatement insertStat = conn.prepareStatement(sqlString);
			
			int i = 1; //index of parameter
			  
			Iterator<?> sqlParam =  param.iterator();
			
			while (sqlParam.hasNext()) {
				 
				insertStat.setObject(i, sqlParam.next());
				i++;
				
			}

	        insertStat.executeUpdate();
	        
	        conn.commit();
	        
	        
		} catch (ClassNotFoundException | SQLException e) {
			
			try {
				
				conn.rollback();
				
			} catch (SQLException e1) {

				e1.printStackTrace();
			}
			
			e.printStackTrace();
			
	    } finally {
	    	
	        try {
	        	
	        	conn.close();
	        	
	        } catch (SQLException ex) {
	        	
	            ex.printStackTrace();
	            
	        }
	        
	    }	
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////

}
