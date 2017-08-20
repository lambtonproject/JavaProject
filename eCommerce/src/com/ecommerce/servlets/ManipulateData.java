package com.ecommerce.servlets;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.ecommerce.beans.CategoriesDataset;
import com.ecommerce.beans.CustomersDataset;
import com.ecommerce.beans.OrdersDataset;
import com.ecommerce.beans.ProductsDataset;
import com.ecommerce.dbsql.DataProcess;

//do not use variables in servlet classes, because servlet classes are common to public
 
//@WebServlet("/ManipulateData")
public class ManipulateData extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	ServletConfig config;
	ServletContext context;

	private boolean isMultipart;
	private String filePath;
	private int maxFileSize = 50 * 1024 * 1024;
	private int maxMemSize = 4 * 1024 * 1024;
	private File file ;
	
	@Override
	public void init() throws ServletException {
		
		config = getServletConfig();
		context = getServletContext();
		
		filePath = config.getInitParameter("file-upload");
		
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {
			doManipulate(req, resp);
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		  
		try { 
			doManipulate(req, resp);
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		
	}
	
	public void doManipulate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, FileUploadException {

		try {
			 
			HttpSession session = req.getSession();
	
			resp.setContentType("text/html");
			resp.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
			
			PrintWriter out = resp.getWriter();
			
			if (session.getAttribute("admin") == null || !(session.getAttribute("admin").equals("true"))) {
				
				out.println("You have to login before proceeding");
				return;
				
			}

			
//CUSTOMER MANIPULATION LANGUAGE////////////////////////////////////////////////////////////////////////////////////////
			
			//CUSTOMERS MODULE//////////////////////////////////////////////////////////////////////////////////////////
			if (req.getParameter("module").equals("customers")){
				
				DataProcess<CustomersDataset> dbp = new DataProcess<CustomersDataset>(new CustomersDataset(), context);
				
				//CUSTOMER EDIT FORM
				if (req.getParameter("process").equals("edit")){

					List<Integer> params = new ArrayList<>();
	
					params.add(Integer.parseInt(req.getParameter("id")));
					
					List<CustomersDataset> rs = dbp.selectData("select * from CUSTOMERS where CUSTOMER_ID = ?", params);  
					
					req.getRequestDispatcher("header.htm").include(req, resp);
					
					out.println(HtmlCreator.createHtml("CustomersEdit", rs, ""));
					
					req.getRequestDispatcher("footer.htm").include(req, resp);
				
				//CUSTOMER DELETE
				} else if (req.getParameter("process").equals("delete")){
					
					List<Integer> params = new ArrayList<>();
					
					params.add(Integer.parseInt(req.getParameter("id")));
					
					dbp.deleteData("delete from CUSTOMERS where CUSTOMER_ID = ?", params);  
					
					resp.sendRedirect("ViewData?module=customers");
				
				//CUSTOMER UPDATE	
				} else if (req.getParameter("process").equals("update")){

					List<Object> params = new ArrayList<>();		
					  
					params.add(req.getParameter("CUSTOMER_USERNAME"));
					params.add(req.getParameter("CUSTOMER_PASSWORD"));
					params.add(req.getParameter("CUSTOMER_NAME"));
					params.add(req.getParameter("CUSTOMER_PHONE"));
					params.add(req.getParameter("CUSTOMER_EMAIL"));
					params.add(req.getParameter("CUSTOMER_ADDRESS"));
					params.add(req.getParameter("CUSTOMER_CITY"));
					params.add(req.getParameter("CUSTOMER_STATE"));
					params.add(req.getParameter("CUSTOMER_POSTALCODE"));
					params.add(Integer.parseInt(req.getParameter("id")));
					 
					int success = dbp.updateData("update CUSTOMERS set CUSTOMER_USERNAME=?, "
							+ "CUSTOMER_PASSWORD=?, "
							+ "CUSTOMER_NAME=?, "
							+ "CUSTOMER_PHONE=?, "
							+ "CUSTOMER_EMAIL=?, "
							+ "CUSTOMER_ADDRESS=?, "
							+ "CUSTOMER_CITY=?, "
							+ "CUSTOMER_STATE=?, "
							+ "CUSTOMER_POSTALCODE=?"
							+ " where CUSTOMER_ID=?"							
							, params);   
					
					if (success != 0) {
						
						resp.sendRedirect("ViewData?module=customers");
					
					} else {
						
						out.println("Something is wrong! Try again.");
						
					}
					
				//CUSTOMER NEW FORM
				} else if (req.getParameter("process").equals("new")){
					
					List<?> params = new ArrayList<>();
					
					req.getRequestDispatcher("header.htm").include(req, resp);
					
					out.println(HtmlCreator.createHtml("CustomersNew", params, ""));
					
					req.getRequestDispatcher("footer.htm").include(req, resp);
					
				//CUSTOMER INSERT
				} else if (req.getParameter("process").equals("add")){
					
					List<String> params = new ArrayList<>();

					params.add(req.getParameter("CUSTOMER_USERNAME"));
					params.add(req.getParameter("CUSTOMER_PASSWORD"));
					params.add(req.getParameter("CUSTOMER_NAME"));
					params.add(req.getParameter("CUSTOMER_PHONE"));
					params.add(req.getParameter("CUSTOMER_EMAIL"));
					params.add(req.getParameter("CUSTOMER_ADDRESS"));
					params.add(req.getParameter("CUSTOMER_CITY"));
					params.add(req.getParameter("CUSTOMER_STATE"));
					params.add(req.getParameter("CUSTOMER_POSTALCODE"));
					
					dbp.insertData("insert into CUSTOMERS ("
							+ "CUSTOMER_USERNAME, "
							+ "CUSTOMER_PASSWORD, "
							+ "CUSTOMER_NAME, "
							+ "CUSTOMER_PHONE, "
							+ "CUSTOMER_EMAIL, "
							+ "CUSTOMER_ADDRESS, "
							+ "CUSTOMER_CITY, "
							+ "CUSTOMER_STATE, "
							+ "CUSTOMER_POSTALCODE"
							+ ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", params);  
					
					resp.sendRedirect("ViewData?module=customers");
					
				}
			
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			
			
//PRODUCT MANIPULATION LANGUAGE//////////////////////////////////////////////////////////////////////////////////////////
			
			//PRODUCTS MODULE////////////////////////////////////////////////////////////////////////////////////////////
			} else if (req.getParameter("module").equals("products")){
				
				DataProcess<ProductsDataset> dbp = new DataProcess<ProductsDataset>(new ProductsDataset(), context);
				
				//PRODUCT EDIT FORM
				if (req.getParameter("process").equals("edit")){

					List<Integer> params = new ArrayList<>();
	
					params.add(Integer.parseInt(req.getParameter("id")));
			
					List<ProductsDataset> rs = dbp.selectData("select * from PRODUCTS where PRODUCT_ID = ?", params);  
					
					req.getRequestDispatcher("header.htm").include(req, resp);
					
					out.println(HtmlCreator.createHtml("ProductsEdit", rs, getCategories()));
					
					req.getRequestDispatcher("footer.htm").include(req, resp);
				
				//PRODUCT DELETE
				} else if (req.getParameter("process").equals("delete")){
					
					List<Integer> params = new ArrayList<>();
					
					params.add(Integer.parseInt(req.getParameter("id")));
					
					dbp.deleteData("delete from PRODUCTS where PRODUCT_ID = ?", params);  
					
					resp.sendRedirect("ViewData?module=products");
				
				//PRODUCT UPDATE
				} else if (req.getParameter("process").equals("update")){

					List<Object> params = getEncodedFormValues(req, resp);		
				
//					params.add(req.getParameter("PRODUCT_NAME"));
//					params.add(req.getParameter("PRODUCT_CATEGORY"));
//					params.add(Integer.parseInt(req.getParameter("PRODUCT_QUANTITY")));
//					params.add(Float.parseFloat(req.getParameter("PRODUCT_UNIT_PRICE")));
//					params.add(req.getParameter("PRODUCT_DESCRIPTION"));
//					params.add(req.getParameter("PRODUCT_IMAGE"));
					
					params.add(Integer.parseInt(req.getParameter("id")));
	
					int success = dbp.updateData("update PRODUCTS set PRODUCT_NAME=?, "
							+ "PRODUCT_CATEGORY=?, "
							+ "PRODUCT_QUANTITY=?, "
							+ "PRODUCT_UNIT_PRICE=?, "
							+ "PRODUCT_DESCRIPTION=?, "
							+ "PRODUCT_IMAGE=?"
							+ " where PRODUCT_ID=?"							
							, params);   
					
					if (success != 0) {

						resp.sendRedirect("ViewData?module=products");
					
					} else {
						
						out.println("Something is wrong! Try again.");
						
					}

				//PRODUCT NEW FORM
				} else if (req.getParameter("process").equals("new")){
					
					List<?> params = new ArrayList<>();
					
					req.getRequestDispatcher("header.htm").include(req, resp);
					
					out.println(HtmlCreator.createHtml("ProductsNew", params, getCategories()));
					
					req.getRequestDispatcher("footer.htm").include(req, resp);
					
				//PRODUCT INSERT
				} else if (req.getParameter("process").equals("add")){
					
					List<Object> params = getEncodedFormValues(req, resp);
	
//					params.add(req.getParameter("PRODUCT_NAME"));
//					params.add(req.getParameter("PRODUCT_CATEGORY"));
//					params.add(Integer.parseInt(req.getParameter("PRODUCT_QUANTITY")));
//					params.add(Float.parseFloat(req.getParameter("PRODUCT_UNIT_PRICE")));
//					params.add(req.getParameter("PRODUCT_DESCRIPTION"));
//					params.add(req.getParameter("PRODUCT_IMAGE"));
					
					dbp.insertData("insert into PRODUCTS ("
							+ "PRODUCT_NAME, "
							+ "PRODUCT_CATEGORY, "
							+ "PRODUCT_QUANTITY, "
							+ "PRODUCT_UNIT_PRICE, "
							+ "PRODUCT_DESCRIPTION, "
							+ "PRODUCT_IMAGE"
							+ ") VALUES (?, ?, ?, ?, ?, ?)", params); 
					
					resp.sendRedirect("ViewData?module=products");
					
				}
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
			
//ORDERS MANIPULATION LANGUAGE///////////////////////////////////////////////////////////////////////////////////////////
			
			//ORDERS MODULE//////////////////////////////////////////////////////////////////////////////////////////////
			} else if (req.getParameter("module").equals("orders")){
				 
				DataProcess<OrdersDataset> dbp = new DataProcess<OrdersDataset>(new OrdersDataset(), context);
				
				//ORDER EDIT FORM
				if (req.getParameter("process").equals("edit")){

					List<Integer> params = new ArrayList<>();
	
					params.add(Integer.parseInt(req.getParameter("id")));
					
					List<OrdersDataset> rs = dbp.selectData("select * from ORDERS o"
							+ " inner join PRODUCTS p ON o.ORDER_PRODUCT_ID = p.PRODUCT_ID"
							+ " inner join CUSTOMERS c ON o.ORDER_CUSTOMER_ID = c.CUSTOMER_ID "
							+ " where ORDER_ID = ?", params);  
					
					req.getRequestDispatcher("header.htm").include(req, resp);
					
					out.println(HtmlCreator.createHtml("OrdersEdit", rs, ""));
					
					req.getRequestDispatcher("footer.htm").include(req, resp);
				
				//ORDER DELETE
				} else if (req.getParameter("process").equals("delete")){
					
					List<Integer> params = new ArrayList<>();
					
					params.add(Integer.parseInt(req.getParameter("id")));
					
					dbp.deleteData("delete from ORDERS where ORDER_ID = ?", params);  
					
					resp.sendRedirect("ViewData?module=orders");
				
				//ORDER UPDATE
				} else if (req.getParameter("process").equals("update")){

					Date date = new SimpleDateFormat("yyyy-MM-dd").parse(req.getParameter("ORDER_DATE"));
					
					List<Object> params = new ArrayList<>();		
					
					params.add(Integer.parseInt(req.getParameter("ORDER_PRODUCT_ID")));
					params.add(Integer.parseInt(req.getParameter("ORDER_CUSTOMER_ID")));
					params.add(Integer.parseInt(req.getParameter("ORDER_QUANTITY")));
					params.add(new java.sql.Date(date.getTime()));
					params.add(Float.parseFloat(req.getParameter("ORDER_TOTAL")));
					params.add(Integer.parseInt(req.getParameter("id")));
	
					int success = dbp.updateData("update ORDERS set ORDER_PRODUCT_ID=?, "
							+ "ORDER_CUSTOMER_ID=?, "
							+ "ORDER_QUANTITY=?, "
							+ "ORDER_DATE=?, "
							+ "ORDER_TOTAL=?"
							+ " where ORDER_ID=?"							
							, params);   
					
					if (success != 0) {
						
						resp.sendRedirect("ViewData?module=orders");
					
					} else {
						
						out.println("Something is wrong! Try again.");
						
					}

				//ORDER NEW FORM
				} else if (req.getParameter("process").equals("new")){
					
					String customerName = "", productName = "";
						
					DataProcess<CustomersDataset> dbCustomer = new DataProcess<CustomersDataset>(new CustomersDataset(), context);
					
					List<Integer> paramsInfo = new ArrayList<>();
					
					paramsInfo.add(Integer.parseInt(req.getParameter("customerid")));
					
					List<CustomersDataset> rsCustomer = dbCustomer.selectData("select * from CUSTOMERS where CUSTOMER_ID = ?", paramsInfo);  
					
					Iterator<CustomersDataset> sqlParamCustomer =  rsCustomer.iterator();
					
					CustomersDataset dsCustomer = null;
					
					while (sqlParamCustomer.hasNext()) {
						 
						dsCustomer =  (CustomersDataset) sqlParamCustomer.next();
							 
						customerName = dsCustomer.getCUSTOMER_NAME();
						 
					}
					
					 
					paramsInfo.clear();
					
					DataProcess<ProductsDataset> dbProduct = new DataProcess<ProductsDataset>(new ProductsDataset(), context);
					
					paramsInfo.add(Integer.parseInt(req.getParameter("productid")));
					
					List<ProductsDataset> rsProduct = dbProduct.selectData("select * from PRODUCTS where PRODUCT_ID = ?", paramsInfo);  
					
					Iterator<ProductsDataset> sqlParamProduct =  rsProduct.iterator();
					
					ProductsDataset dsProduct = null;
					
					while (sqlParamProduct.hasNext()) {
						 
						dsProduct =  (ProductsDataset) sqlParamProduct.next();
							 
						productName = dsProduct.getPRODUCT_NAME();
						 
					}		    

 
					List<?> params = new ArrayList<>();
					
					req.getRequestDispatcher("header.htm").include(req, resp);
					
					out.println(HtmlCreator.createHtml("OrdersNew", params, req.getParameter("customerid") + "@@" + customerName + "@@" + req.getParameter("productid") + "@@" + productName));
					
					req.getRequestDispatcher("footer.htm").include(req, resp);
					
				//ORDER INSERT
				} else if (req.getParameter("process").equals("add")){
					
					Date date = new SimpleDateFormat("yyyy-MM-dd").parse(req.getParameter("ORDER_DATE"));
					
					List<Object> params = new ArrayList<>();
	
					params.add(Integer.parseInt(req.getParameter("ORDER_PRODUCT_ID")));
					params.add(Integer.parseInt(req.getParameter("ORDER_CUSTOMER_ID")));
					params.add(Integer.parseInt(req.getParameter("ORDER_QUANTITY")));
					params.add(new java.sql.Date(date.getTime()));
					params.add(Float.parseFloat(req.getParameter("ORDER_TOTAL")));
					
					dbp.insertData("insert into ORDERS ("
							+ "ORDER_PRODUCT_ID, "
							+ "ORDER_CUSTOMER_ID, "
							+ "ORDER_QUANTITY, " 
							+ "ORDER_DATE, "
							+ "ORDER_TOTAL"
							+ ") VALUES (?, ?, ?, ?, ?)", params); 
					
					resp.sendRedirect("ViewData?module=orders");
					
				}
			
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			
			
//CATEGORIES MANIPULATION LANGUAGE///////////////////////////////////////////////////////////////////////////////////////////
			
			//CATEGORIES MODULE//////////////////////////////////////////////////////////////////////////////////////////////
			} else if (req.getParameter("module").equals("categories")){
				 
				DataProcess<CategoriesDataset> dbp = new DataProcess<CategoriesDataset>(new CategoriesDataset(), context);
				
				//CATEGORY EDIT FORM
				if (req.getParameter("process").equals("edit")){

					List<Integer> params = new ArrayList<>();
	
					params.add(Integer.parseInt(req.getParameter("id")));
					
					List<CategoriesDataset> rs = dbp.selectData("select * from CATEGORIES where CATEGORY_ID = ?", params);  
					
					req.getRequestDispatcher("header.htm").include(req, resp);
					
					out.println(HtmlCreator.createHtml("CategoriesEdit", rs, ""));
					
					req.getRequestDispatcher("footer.htm").include(req, resp);
				
				//CATEGORY DELETE
				} else if (req.getParameter("process").equals("delete")){
					
					List<Integer> params = new ArrayList<>();
					
					params.add(Integer.parseInt(req.getParameter("id")));
					
					dbp.deleteData("delete from CATEGORIES where CATEGORY_ID = ?", params);  
					
					resp.sendRedirect("ViewData?module=categories");
				
				//CATEGORY UPDATE
				} else if (req.getParameter("process").equals("update")){

					List<Object> params = new ArrayList<>();		

					params.add(req.getParameter("CATEGORY_NAME"));
					params.add(Integer.parseInt(req.getParameter("id")));
	
					int success = dbp.updateData("update CATEGORIES set CATEGORY_NAME=?"
							+ " where CATEGORY_ID=?"							
							, params);   
					
					if (success != 0) {
						
						resp.sendRedirect("ViewData?module=categories");
					
					} else {
						
						out.println("Something is wrong! Try again.");
						
					}

				//CATEGORY NEW FORM
				} else if (req.getParameter("process").equals("new")){
					
					List<?> params = new ArrayList<>();
					
					req.getRequestDispatcher("header.htm").include(req, resp);
					
					out.println(HtmlCreator.createHtml("CategoriesNew", params, ""));
					
					req.getRequestDispatcher("footer.htm").include(req, resp);
					
				//CATEGORY INSERT
				} else if (req.getParameter("process").equals("add")){
					
					List<Object> params = new ArrayList<>();
	
					params.add(req.getParameter("CATEGORY_NAME"));
					
					dbp.insertData("insert into CATEGORIES ("
							+ "CATEGORY_NAME"
							+ ") VALUES (?)", params); 
					
					resp.sendRedirect("ViewData?module=categories");
					
				}
				
			}
			
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
						
		} catch (IOException | ParseException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	public String getCategories() {
		
		List<?> params = new ArrayList<>();
		
		DataProcess<CategoriesDataset> dbCategory = new DataProcess<CategoriesDataset>(new CategoriesDataset(), context);
		
		List<CategoriesDataset> rsCategories = dbCategory.selectData("select * from CATEGORIES", params);
		
		Iterator<CategoriesDataset> sqlParamCategory =  rsCategories.iterator();
		
		CategoriesDataset dsCategories = null;
		
		String catHtml = "";
		
		catHtml += "<select name=\"PRODUCT_CATEGORY\" id=\"PRODUCT_CATEGORY\" required>"
				+ "<option value=\"\"></option>";
		
		while (sqlParamCategory.hasNext()) {
			 
			dsCategories =  (CategoriesDataset) sqlParamCategory.next();
					
			catHtml += "<option value=\"" + dsCategories.getCATEGORY_NAME() + "\">" + dsCategories.getCATEGORY_NAME() + "</option>";
			 
		}
		
		catHtml += "</select>";	
		
		return catHtml;
		
	}
	
	List<Object> getEncodedFormValues(HttpServletRequest req, HttpServletResponse resp) throws FileUploadException{

		// Check that we have a file upload request
		isMultipart = ServletFileUpload.isMultipartContent(req);
		  
		resp.setContentType("text/html");
		   
		if( !isMultipart ) {
			return null;
		}

		DiskFileItemFactory factory = new DiskFileItemFactory();
		
		// maximum size that will be stored in memory
		factory.setSizeThreshold(maxMemSize);
		   
		// Location to save data that is larger than maxMemSize.
		factory.setRepository(new File("c:\\temp"));
		
		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		   
		// maximum file size to be uploaded.
		upload.setSizeMax( maxFileSize );
		
		// Parse the request to get file items.
		List<FileItem> fileItems = upload.parseRequest(req);
         
    
    	List<Object> params = new ArrayList<>();	
    
        String fieldPRODUCT_NAME = "", fieldPRODUCT_CATEGORY = "", fieldPRODUCT_DESCRIPTION = "", fieldPRODUCT_IMAGE = "";
        int fieldPRODUCT_QUANTITY = 0;
        float fieldPRODUCT_UNIT_PRICE = 0;
        
        String fieldname = "";
        String fieldvalue = "";
        
        String fileName = "";

		for (FileItem item : fileItems) {
			
		    // processes only fields that are form fields
		    if (item.isFormField()) {

		        fieldname = item.getFieldName();
		        fieldvalue = item.getString();
		        
		        if (fieldname.equals("PRODUCT_NAME")) {
		        	fieldPRODUCT_NAME = fieldvalue;
		        } else if (fieldname.equals("PRODUCT_CATEGORY")) {
		        	fieldPRODUCT_CATEGORY = fieldvalue;
		        } else if (fieldname.equals("PRODUCT_QUANTITY")) {
		        	fieldPRODUCT_QUANTITY = Integer.parseInt(fieldvalue);
		        } else if (fieldname.equals("PRODUCT_UNIT_PRICE")) {
		        	fieldPRODUCT_UNIT_PRICE = Float.parseFloat(fieldvalue);
		        } else if (fieldname.equals("PRODUCT_DESCRIPTION")) {
		        	fieldPRODUCT_DESCRIPTION = fieldvalue;
		        } else if (fieldname.equals("PRODUCT_IMAGE")) {
		        	fieldPRODUCT_IMAGE = fieldvalue;
		        }
		        
		    } else if (!item.isFormField()) {
		    	
		    	// processes only fields that are not form fields
		    	
				fileName = item.getName();
				
				// Write the file
				if( fileName.lastIndexOf("\\") >= 0 ) {
					file = new File( filePath + fileName.substring( fileName.lastIndexOf("\\"))) ;
				} else {
					file = new File( filePath + fileName.substring(fileName.lastIndexOf("\\")+1)) ;
				}	
				   
				try {
					item.write( file ) ;
				} catch (Exception e) {
					e.printStackTrace();
				}
			    	
		    }

		}

		params.add(fieldPRODUCT_NAME);
		params.add(fieldPRODUCT_CATEGORY);
		params.add(fieldPRODUCT_QUANTITY);
		params.add(fieldPRODUCT_UNIT_PRICE);
		params.add(fieldPRODUCT_DESCRIPTION);
		params.add(fileName == "" ? fieldPRODUCT_IMAGE : fileName);

		return params;
	
	}
	
}
