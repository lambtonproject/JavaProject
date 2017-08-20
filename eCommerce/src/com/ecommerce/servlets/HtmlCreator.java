package com.ecommerce.servlets;

import java.util.Iterator;
import java.util.List;

import com.ecommerce.beans.CategoriesDataset;
import com.ecommerce.beans.CustomersDataset;
import com.ecommerce.beans.OrdersDataset;
import com.ecommerce.beans.ProductsDataset;

public class HtmlCreator {

	public static <T> String createHtml(String module, List<T> param, String action) {
		
		Iterator<T> sqlParam =  param.iterator();

		StringBuilder myvar = new StringBuilder(); 
		
		 
		//CUSTOMER HTML /////////////////////////////////////////////////////////////////////////////////////////
		
		//CUSTOMER VIEW
		if (module.equals("CustomersView")) {
			
			CustomersDataset ds = null;

			myvar.append("<main>\n")
			     .append("	<div class=\"container\">\n")
			     .append("		<div class=\"row\">\n")
			     .append("			<div class=\"col-md-12\">\n")
			     .append("				<form action=\"ManipulateData?process=new&module=customers\" method=\"post\">\n")
			     .append("				  <div class=\"well\">\n")
			     .append("					  <table id=\"tablelist\" width=\"100%\" cellpadding=\"5\" cellspacing=\"5\" border=\"1\">\n")
			     .append("						<tr>\n")
			     .append("							<td colspan=\"8\" align=\"center\"><b>CUSTOMERS</b></td>\n")
			     .append("						</tr>\n")
			     .append("						<tr bgcolor=\"#E9FAE1\">\n")
			     .append("							<td>ID</td>\n")
			     .append("							<td>Name</td>\n")
			     .append("							<td>Phone</td>\n")
			     .append("							<td>Email</td>\n")
			     .append("							<td>City</td>\n")
			     .append("							<td>State</td>\n")
			     .append("							<td></td>\n")
			     .append("							<td></td>\n")
			     .append("						</tr>\n");
			
		     while (sqlParam.hasNext()) {

		    	 ds =  (CustomersDataset) sqlParam.next();
		    	 
		    	 myvar.append("						<tr>\n")
		    	 .append(String.format("							<td>%s</td>\n", ds.getCUSTOMER_ID()))
			     .append(String.format("							<td>%s</td>\n", ds.getCUSTOMER_NAME()))
			     .append(String.format("							<td>%s</td>\n", ds.getCUSTOMER_PHONE()))
		    	 .append(String.format("							<td>%s</td>\n", ds.getCUSTOMER_EMAIL() == null ? "": ds.getCUSTOMER_EMAIL()))
			     .append(String.format("							<td>%s</td>\n", ds.getCUSTOMER_CITY() == null ? "": ds.getCUSTOMER_CITY()))
			     .append(String.format("							<td>%s</td>\n", ds.getCUSTOMER_STATE() == null ? "": ds.getCUSTOMER_STATE()));
			      
		    		 
	    		 if (action.equals("order")) {
			    	 
				     myvar.append(String.format("							<td><a href=\"ViewData?customerid=%s&module=products&order=active\">Select</a></td>\n", ds.getCUSTOMER_ID()))
				     .append("							<td>&nbsp;</td>\n")
			    	 .append("						</tr>\n");		    	 
			    	 
			     } else {

				     myvar.append(String.format("							<td><a href=\"ManipulateData?id=%s&process=edit&module=customers\">Edit</a></td>\n", ds.getCUSTOMER_ID()))
				     .append(String.format("							<td><a href=\"ManipulateData?id=%s&process=delete&module=customers\">Delete</a></td>\n", ds.getCUSTOMER_ID()))
			    	 .append("						</tr>\n");
				     
			     }

		     }

    		 if (!action.equals("order")) {
		    	 
    			 myvar.append("						<tr>\n")
		    	 .append("							<td colspan=\"8\" height=\"100\" align=\"center\"><input type=\"submit\" id=\"addbutton\" class=\"btn btn-lg btn-default\" value=\"Add New Customer\"></td>\n")
		    	 .append("						</tr>\n");      	 
		    	 
		     }
    		 
			 myvar.append("					  </table>\n")
				  .append("				  </div>\n")
				  .append("			  </form>\n")
				  .append("			</div>\n")
				  .append("	  </div>\n")
				  .append("	</div>\n")
				  .append("</main>\n");
		
		//CUSTOMER EDIT 
		} else if (module.equals("CustomersEdit")) {
			
			 CustomersDataset ds = null;

		     while (sqlParam.hasNext()) {

		    	ds =  (CustomersDataset) sqlParam.next();
		    	 
				myvar.append("<main>\n")
				     .append("	<div class=\"container\">\n")
				     .append("		<div class=\"row\">\n")
				     .append("			<div class=\"col-md-8 col-md-offset-2\">\n")
				     .append(String.format("				<form action=\"ManipulateData?id=%s&process=update&module=customers\" method=\"post\">\n", ds.getCUSTOMER_ID()))
				     .append("				  <div class=\"well\">\n")
				     .append("				  		<table width=\"100%\" border=\"1\" cellspacing=\"5\" cellpadding=\"5\">\n")
				     .append("				  			<tr>\n")
				     .append("		  		      		  <td colspan=\"4\" align=\"center\"><b>CUSTOMERS EDIT</b></td>\n")
				     .append("				  			</tr>\n")
				     .append("				  			<tr>\n")
				     .append("				  			  <td>ID#</td>\n")
				     .append(String.format("		  		      		  <td colspan=\"3\"><input name=\"CUSTOMER_ID\" type=\"text\" disabled id=\"CUSTOMER_ID\" value=\"%s\"></td>\n", ds.getCUSTOMER_ID()))
				     .append("	  				  		</tr>\n")
				     .append("							<tr>\n")
				     .append("		  				  	  <td width=\"100\">Name</td>\n")
				     .append(String.format("		  		      		  <td><input name=\"CUSTOMER_NAME\" type=\"text\" autofocus required id=\"CUSTOMER_NAME\" value=\"%s\"></td>\n", ds.getCUSTOMER_NAME()))
				     .append("		  		      		  <td width=\"100\">Username</td>\n")
				     .append(String.format("		  		      		  <td><input type=\"text\" name=\"CUSTOMER_USERNAME\" id=\"CUSTOMER_USERNAME\" value=\"%s\"></td>\n", ds.getCUSTOMER_USERNAME()))
				     .append("			      		    </tr>\n")
				     .append("			      		    <tr>\n")
				     .append("		  		      		  <td>Phone</td>\n")
				     .append(String.format("		  		      		  <td><input name=\"CUSTOMER_PHONE\" type=\"text\" required id=\"CUSTOMER_PHONE\" value=\"%s\"></td>\n", ds.getCUSTOMER_PHONE()))
				     .append("		  					  <td>Password</td>\n")
				     .append(String.format("		  		      		  <td><input type=\"text\" name=\"CUSTOMER_PASSWORD\" id=\"CUSTOMER_PASSWORD\" value=\"%s\"></td>\n", ds.getCUSTOMER_PASSWORD()))
				     .append("			      		    </tr>\n")
				     .append("			      		    <tr>\n")
				     .append("		  		      		  <td>Email</td>\n")
				     .append(String.format("		  		      		  <td><input type=\"text\" name=\"CUSTOMER_EMAIL\" id=\"CUSTOMER_EMAIL\" value=\"%s\"></td>\n", ds.getCUSTOMER_EMAIL() == null ? "": ds.getCUSTOMER_EMAIL()))
				     .append("		  		      		  <td>Postal Code</td>\n")
				     .append(String.format("		  		      		  <td><input type=\"text\" name=\"CUSTOMER_POSTALCODE\" id=\"CUSTOMER_POSTALCODE\" value=\"%s\"></td>\n", ds.getCUSTOMER_POSTALCODE() == null ? "": ds.getCUSTOMER_POSTALCODE()))
				     .append("			      		    </tr>\n")
				     .append("			      		    <tr>\n")
				     .append("		      		          <td>Address</td>\n")
				     .append(String.format("		      		          <td colspan=\"3\"><textarea name=\"CUSTOMER_ADDRESS\" id=\"CUSTOMER_ADDRESS\">%s</textarea></td>\n", ds.getCUSTOMER_ADDRESS() == null ? "": ds.getCUSTOMER_ADDRESS()))
				     .append("			      		    </tr>\n")
				     .append("			      		    <tr>\n")
				     .append("		      		          <td>City</td>\n")
				     .append(String.format("		      		          <td><input type=\"text\" name=\"CUSTOMER_CITY\" id=\"CUSTOMER_CITY\" value=\"%s\"></td>\n", ds.getCUSTOMER_CITY() == null ? "": ds.getCUSTOMER_CITY()))
				     .append("		      		          <td>State</td>\n")
				     .append(String.format("		      		          <td><input type=\"text\" name=\"CUSTOMER_STATE\" id=\"CUSTOMER_STATE\" value=\"%s\"></td>\n", ds.getCUSTOMER_STATE() == null ? "": ds.getCUSTOMER_STATE()))
				     .append("			      		    </tr>\n")
				     .append("			      		    <tr>\n")
				     .append("		      		          <td colspan=\"4\">&nbsp;</td>\n")
				     .append("			      		    </tr>\n") 
				     .append("			      		    <tr>\n")
				     .append("		      		          <td colspan=\"4\" height=\"100\" align=\"center\">\n")
				     .append("		      		            <input type=\"submit\" id=\"savebutton\" class=\"btn btn-lg btn-default\" value=\"Update\"></td>\n")
				     .append("			      		    </tr>\n")
				     .append("				  		</table>\n")
					 .append("				  </div>\n")
					 .append("			  </form>\n")
					 .append("			</div>\n")
					 .append("	  </div>\n")
					 .append("	</div>\n")
					 .append("</main>\n");
			
		     } 
		
		//CUSTOMER NEW
		} else if (module.equals("CustomersNew")) {
		    	 
			myvar.append("<main>\n")
			     .append("	<div class=\"container\">\n")
			     .append("		<div class=\"row\">\n")
			     .append("			<div class=\"col-md-8 col-md-offset-2\">\n")
			     .append("				<form action=\"ManipulateData?process=add&module=customers\" method=\"post\">\n")
			     .append("				  <div class=\"well\">\n")
			     .append("				  		<table width=\"100%\" border=\"1\" cellspacing=\"5\" cellpadding=\"5\">\n")
			     .append("				  			<tr>\n")
			     .append("		  		      		  <td colspan=\"4\" align=\"center\"><b>CUSTOMERS NEW</b></td>\n")
			     .append("				  			</tr>\n")
			     .append("							<tr>\n")
			     .append("		  				  	  <td width=\"100\">Name</td>\n")
			     .append("		  		      		  <td><input name=\"CUSTOMER_NAME\" type=\"text\" id=\"CUSTOMER_NAME\" required></td>\n")
			     .append("		  		      		  <td width=\"100\">Username</td>\n")
			     .append("		  		      		  <td><input type=\"text\" name=\"CUSTOMER_USERNAME\" id=\"CUSTOMER_USERNAME\" autofocus required></td>\n")
			     .append("			      		    </tr>\n")
			     .append("			      		    <tr>\n")
			     .append("		  		      		  <td>Phone</td>\n")
			     .append("		  		      		  <td><input name=\"CUSTOMER_PHONE\" type=\"text\" required id=\"CUSTOMER_PHONE\"></td>\n")
			     .append("		  					  <td>Password</td>\n")
			     .append("		  		      		  <td><input type=\"text\" name=\"CUSTOMER_PASSWORD\" id=\"CUSTOMER_PASSWORD\" required></td>\n")
			     .append("			      		    </tr>\n")
			     .append("			      		    <tr>\n")
			     .append("		  		      		  <td>Email</td>\n")
			     .append("		  		      		  <td><input type=\"text\" name=\"CUSTOMER_EMAIL\" id=\"CUSTOMER_EMAIL\"></td>\n")
			     .append("		  		      		  <td>Postal Code</td>\n")
			     .append("		  		      		  <td><input type=\"text\" name=\"CUSTOMER_POSTALCODE\" id=\"CUSTOMER_POSTALCODE\"></td>\n")
			     .append("			      		    </tr>\n")
			     .append("			      		    <tr>\n")
			     .append("		      		          <td>Address</td>\n")
			     .append("		      		          <td colspan=\"3\"><textarea name=\"CUSTOMER_ADDRESS\" id=\"CUSTOMER_ADDRESS\"></textarea></td>\n")
			     .append("			      		    </tr>\n")
			     .append("			      		    <tr>\n") 
			     .append("		      		          <td>City</td>\n")
			     .append("		      		          <td><input type=\"text\" name=\"CUSTOMER_CITY\" id=\"CUSTOMER_CITY\"></td>\n")
			     .append("		      		          <td>State</td>\n")
			     .append("		      		          <td><input type=\"text\" name=\"CUSTOMER_STATE\" id=\"CUSTOMER_STATE\"></td>\n")
			     .append("			      		    </tr>\n")
			     .append("			      		    <tr>\n")
			     .append("		      		          <td colspan=\"4\">&nbsp;</td>\n")
			     .append("			      		    </tr>\n") 
			     .append("			      		    <tr>\n")
			     .append("		      		          <td colspan=\"4\" height=\"100\" align=\"center\">\n")
			     .append("		      		            <input type=\"submit\" id=\"addbutton\" class=\"btn btn-lg btn-default\" value=\"Add New Customer\"></td>\n")
			     .append("			      		    </tr>\n")
			     .append("				  		</table>\n")
				 .append("				  </div>\n")
				 .append("			  </form>\n")
				 .append("			</div>\n")
				 .append("	  </div>\n")
				 .append("	</div>\n")
				 .append("</main>\n");
		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		
		//PRODUCT HTML //////////////////////////////////////////////////////////////////////////////////////////
		
		//PRODUCT VIEW
		} else if (module.equals("ProductsView")) {
			
			ProductsDataset ds = null;

			myvar.append("<main>\n")
			     .append("	<div class=\"container\">\n")
			     .append("		<div class=\"row\">\n")
			     .append("			<div class=\"col-md-12\">\n")
			     .append("				<form action=\"ManipulateData?process=new&module=products\" method=\"post\">\n")
			     .append("				  <div class=\"well\">\n")
			     .append("					  <table id=\"tablelist\" width=\"100%\" cellpadding=\"5\" cellspacing=\"5\" border=\"1\">\n")
			     .append("						<tr>\n")
			     .append("							<td colspan=\"9\" align=\"center\"><b>PRODUCTS</b></td>\n")
			     .append("						</tr>\n")
			     .append("						<tr bgcolor=\"#E9FAE1\">\n")
			     .append("							<td>Photo</td>\n")
			     .append("							<td>ID</td>\n")
			     .append("							<td>Name</td>\n")
			     .append("							<td>Description</td>\n")
			     .append("							<td>Category</td>\n")
			     .append("							<td>Quantity</td>\n")
			     .append("							<td>Unit Price</td>\n")
			     .append("							<td></td>\n")
			     .append("							<td></td>\n")
			     .append("						</tr>\n");
			
		     while (sqlParam.hasNext()) {

		    	 ds =  (ProductsDataset) sqlParam.next();
		    	 
		    	 myvar.append("						<tr>\n")
			     .append(String.format("							<td><img src=\"img/%s\" width=\"100\" height=\"100\"></td>\n", ds.getPRODUCT_IMAGE()))
		    	 .append(String.format("							<td>%s</td>\n", ds.getPRODUCT_ID()))
			     .append(String.format("							<td>%s</td>\n", ds.getPRODUCT_NAME()))
			     .append(String.format("							<td>%s</td>\n", ds.getPRODUCT_DESCRIPTION()))
		    	 .append(String.format("							<td>%s</td>\n", ds.getPRODUCT_CATEGORY()))
			     .append(String.format("							<td>%s</td>\n", ds.getPRODUCT_QUANTITY()))
			     .append(String.format("							<td>%s</td>\n", ds.getPRODUCT_UNIT_PRICE()));

	    		 if (!action.equals("")) {
			    	 
				     myvar.append(String.format("							<td><a href=\"ManipulateData?customerid=%s&productid=%s&process=new&module=orders\">Select</a></td>\n", action, ds.getPRODUCT_ID()))
				     .append("							<td>&nbsp;</td>\n")
			    	 .append("						</tr>\n");		    	 
			    	 
			     } else {

			    	 myvar.append(String.format("							<td><a href=\"ManipulateData?id=%s&process=edit&module=products\">Edit</a></td>\n", ds.getPRODUCT_ID()))
				     .append(String.format("							<td><a href=\"ManipulateData?id=%s&process=delete&module=products\">Delete</a></td>\n", ds.getPRODUCT_ID()))
			    	 .append("						</tr>\n");
			    	 
			     }
   
		     }

    		 if (action.equals("")) {
		    	 
		    	 myvar.append("						<tr>\n")
		    	 .append("							<td colspan=\"9\" height=\"100\" align=\"center\"><input type=\"submit\" id=\"addbutton\" class=\"btn btn-lg btn-default\" value=\"Add New Product\"></td>\n")
		    	 .append("						</tr>\n");      	 
		    	 
		     }
    		 
			 myvar.append("					  </table>\n")
				  .append("				  </div>\n")
				  .append("			  </form>\n")
				  .append("			</div>\n")
				  .append("	  </div>\n")
				  .append("	</div>\n")
				  .append("</main>\n");

		//PRODUCT EDIT
		} else if (module.equals("ProductsEdit")) {
			
			 ProductsDataset ds = null;

		     while (sqlParam.hasNext()) {

		    	ds =  (ProductsDataset) sqlParam.next();
	    	 
				myvar.append("<main>\n")
				     .append("	<div class=\"container\">\n")
				     .append("		<div class=\"row\">\n")
				     .append("			<div class=\"col-md-8 col-md-offset-2\">\n")
				     .append(String.format("				<form action=\"ManipulateData?id=%s&process=update&module=products\" method=\"post\" enctype=\"multipart/form-data\">\n", ds.getPRODUCT_ID()))
				     .append("				  <div class=\"well\">\n")
				     .append("				  		<table width=\"100%\" border=\"1\" cellspacing=\"5\" cellpadding=\"5\">\n")
				     .append("				  			<tr>\n")
				     .append("		  		      		  <td colspan=\"4\" align=\"center\"><b>PRODUCTS EDIT</b></td>\n")
				     .append("				  			</tr>\n")
				     .append("				  			<tr>\n")
				     .append("				  			  <td>ID#</td>\n")
				     .append(String.format("		  		      		  <td colspan=\"3\"><input name=\"PRODUCT_ID\" type=\"text\" disabled id=\"PRODUCT_ID\" value=\"%s\"></td>\n", ds.getPRODUCT_ID()))
				     .append("	  				  		</tr>\n")				     
				     .append("							<tr>\n")
				     .append("		  				  	  <td width=\"100\">Name</td>\n")
				     .append(String.format("		  		      		  <td colspan=\"3\"><input name=\"PRODUCT_NAME\" type=\"text\" autofocus required id=\"PRODUCT_NAME\" value=\"%s\"></td>\n", ds.getPRODUCT_NAME()))
				     .append("			      		    </tr>\n")				     
				     .append("							<tr>\n")
				     .append("		  		      		  <td width=\"100\">Photo</td>\n")
				     .append(String.format("		  		      		  <td><img src=\"img/%s\" height=\"100\" width=\"100\"/></td>\n", ds.getPRODUCT_IMAGE() == null ? "": ds.getPRODUCT_IMAGE()))
				     .append("		  					  <td>Choose Photo</td>\n")
				     .append(String.format("		  		      		  <td><input type=\"file\" name=\"PRODUCT_IMAGE_FILE\" id=\"PRODUCT_IMAGE_FILE\"><input type=\"hidden\" name=\"PRODUCT_IMAGE\" id=\"PRODUCT_IMAGE\" value=\"%s\"></td>\n", ds.getPRODUCT_IMAGE() == null ? "": ds.getPRODUCT_IMAGE()))				     
				     .append("			      		    </tr>\n")
				     .append("			      		    <tr>\n")
				     .append("		  		      		  <td>Category</td>\n")
				     .append(String.format("		  		      		  <td colspan=\"3\">%s</td>\n", action.replace("value=\"" + ds.getPRODUCT_CATEGORY() == null ? "": ds.getPRODUCT_CATEGORY() + "\"", "value=\"" + ds.getPRODUCT_CATEGORY() == null ? "": ds.getPRODUCT_CATEGORY() + "\" selected")))
				     .append("			      		    </tr>\n") 
				     .append("			      		    <tr>\n")
				     .append("		  		      		  <td>Quantity</td>\n")
				     .append(String.format("		  		      		  <td><input type=\"number\" min=\"0\" name=\"PRODUCT_QUANTITY\" id=\"PRODUCT_QUANTITY\" value=\"%s\"></td>\n", ds.getPRODUCT_QUANTITY()))
				     .append("		  		      		  <td>Unit Price</td>\n")
				     .append(String.format("		  		      		  <td><input type=\"number\" min=\"0\" name=\"PRODUCT_UNIT_PRICE\" id=\"PRODUCT_UNIT_PRICE\" value=\"%s\"></td>\n", ds.getPRODUCT_UNIT_PRICE()))
				     .append("			      		    </tr>\n")
				     .append("			      		    <tr>\n")
				     .append("		      		          <td>Description</td>\n")
				     .append(String.format("		      		          <td colspan=\"3\"><textarea name=\"PRODUCT_DESCRIPTION\" id=\"PRODUCT_DESCRIPTION\">%s</textarea></td>\n", ds.getPRODUCT_DESCRIPTION() == null ? "": ds.getPRODUCT_DESCRIPTION()))
				     .append("			      		    </tr>\n")
				     .append("			      		    <tr>\n")
				     .append("		      		          <td colspan=\"4\">&nbsp;</td>\n")
				     .append("			      		    </tr>\n") 
				     .append("			      		    <tr>\n")
				     .append("		      		          <td height=\"100\" colspan=\"4\" align=\"center\">\n")
				     .append("		      		            <input type=\"submit\" id=\"savebutton\" class=\"btn btn-lg btn-default\" value=\"Update\"></td>\n")
				     .append("			      		    </tr>\n")
				     .append("				  		</table>\n")
					 .append("				  </div>\n")
					 .append("			  </form>\n")
					 .append("			</div>\n")
					 .append("	  </div>\n")
					 .append("	</div>\n")
					 .append("</main>\n");
			
		     }

		//PRODUCT NEW
		} else if (module.equals("ProductsNew")) {
		    	 
			myvar.append("<main>\n")
			     .append("	<div class=\"container\">\n")
			     .append("		<div class=\"row\">\n")
			     .append("			<div class=\"col-md-8 col-md-offset-2\">\n")
			     .append("				<form action=\"ManipulateData?process=add&module=products\" method=\"post\" enctype=\"multipart/form-data\">\n")
			     .append("				  <div class=\"well\">\n")
			     .append("				  		<table width=\"100%\" border=\"1\" cellspacing=\"5\" cellpadding=\"5\">\n")
			     .append("				  			<tr>\n")
			     .append("		  		      		  <td colspan=\"4\" align=\"center\"><b>PRODUCTS NEW</b></td>\n")
			     .append("				  			</tr>\n")
			     .append("							<tr>\n")
			     .append("		  				  	  <td width=\"100\">Name</td>\n")
			     .append("		  		      		  <td><input name=\"PRODUCT_NAME\" type=\"text\" autofocus required id=\"PRODUCT_NAME\"></td>\n")
			     .append("		  		      		  <td width=\"100\">Photo</td>\n")
			     .append("		  		      		  <td><img src=\"\" height=\"100\" width=\"100\"/></td>\n")
			     .append("			      		    </tr>\n")
			     .append("			      		    <tr>\n")
			     .append("		  		      		  <td>Category</td>\n")
			     .append(String.format("		  		      		  <td>%s</td>\n", action.replace("value=\"\"", "value=\"\" selected")))
			     .append("		  					  <td>Choose Photo</td>\n")
			     .append("		  		      		  <td><input type=\"file\" name=\"PRODUCT_IMAGE\" id=\"PRODUCT_IMAGE\"></td>\n")
			     .append("			      		    </tr>\n")
			     .append("			      		    <tr>\n")
			     .append("		  		      		  <td>Quantity</td>\n")
			     .append("		  		      		  <td><input type=\"number\" min=\"0\" name=\"PRODUCT_QUANTITY\" id=\"PRODUCT_QUANTITY\" value=\"0\"></td>\n")
			     .append("		  		      		  <td>Unit Price</td>\n")
			     .append("		  		      		  <td><input type=\"number\" min=\"0\" name=\"PRODUCT_UNIT_PRICE\" id=\"PRODUCT_UNIT_PRICE\" value=\"0.00\"></td>\n")
			     .append("			      		    </tr>\n")
			     .append("			      		    <tr>\n")
			     .append("		      		          <td>Description</td>\n")
			     .append("		      		          <td colspan=\"3\"><textarea name=\"PRODUCT_DESCRIPTION\" id=\"PRODUCT_DESCRIPTION\"></textarea></td>\n")
			     .append("			      		    </tr>\n")
			     .append("			      		    <tr>\n")
			     .append("		      		          <td colspan=\"4\">&nbsp;</td>\n")
			     .append("			      		    </tr>\n") 
			     .append("			      		    <tr>\n")
			     .append("		      		          <td height=\"100\" colspan=\"4\" align=\"center\">\n")
			     .append("		      		            <input type=\"submit\" id=\"addbutton\" class=\"btn btn-lg btn-default\" value=\"Add New Product\"></td>\n")
			     .append("			      		    </tr>\n")
			     .append("				  		</table>\n")
				 .append("				  </div>\n")
				 .append("			  </form>\n")
				 .append("			</div>\n")
				 .append("	  </div>\n")
				 .append("	</div>\n")
				 .append("</main>\n");

		/////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		
		//ORDER HTML ///////////////////////////////////////////////////////////////////////////////////////////
			
			//ORDER VIEW
			} else if (module.equals("OrdersView")) {
				
				OrdersDataset ds = null;

				myvar.append("<main>\n")
				     .append("	<div class=\"container\">\n")
				     .append("		<div class=\"row\">\n")
				     .append("			<div class=\"col-md-12\">\n")
				     .append("				<form action=\"ViewData?module=customers&order=active\" method=\"post\">\n")
				     .append("				  <div class=\"well\">\n")
				     .append("					  <table id=\"tablelist\" width=\"100%\" cellpadding=\"5\" cellspacing=\"5\" border=\"1\">\n")
				     .append("						<tr>\n")
				     .append("							<td colspan=\"8\" align=\"center\"><b>ORDERS</b></td>\n")
				     .append("						</tr>\n")
				     .append("						<tr bgcolor=\"#E9FAE1\">\n")
				     .append("							<td>ID</td>\n")
				     .append("							<td>Product Name</td>\n")
				     .append("							<td>Customer Name</td>\n") 
				     .append("							<td>Quantity</td>\n")
				     .append("							<td>Order Date</td>\n")
				     .append("							<td>Total Price</td>\n")
				     .append("							<td></td>\n")
				     .append("							<td></td>\n")
				     .append("						</tr>\n");
				
			     while (sqlParam.hasNext()) {

			    	 ds =  (OrdersDataset) sqlParam.next();

			    	 myvar.append("						<tr>\n")
			    	 .append(String.format("							<td>%s</td>\n", ds.getORDER_ID()))
				     .append(String.format("							<td>%s</td>\n", ds.getORDER_PRODUCT_NAME()))
				     .append(String.format("							<td>%s</td>\n", ds.getORDER_CUSTOMER_NAME()))
			    	 .append(String.format("							<td>%s</td>\n", ds.getORDER_QUANTITY()))
				     .append(String.format("							<td>%s</td>\n", ds.getORDER_DATE()))
				     .append(String.format("							<td>%s</td>\n", ds.getORDER_TOTAL()))
				     .append(String.format("							<td><a href=\"ManipulateData?id=%s&process=edit&module=orders\">Edit</a></td>\n", ds.getORDER_ID()))
				     .append(String.format("							<td><a href=\"ManipulateData?id=%s&process=delete&module=orders\">Delete</a></td>\n", ds.getORDER_ID()))
			    	 .append("						</tr>\n");
			     
			     }
			     
		    	 myvar.append("						<tr>\n")
		    	 .append("							<td colspan=\"9\" height=\"100\" align=\"center\"><input type=\"submit\" id=\"addbutton\" class=\"btn btn-lg btn-default\" value=\"Add New Order\"></td>\n")
		    	 .append("						</tr>\n");
		    	 
				 myvar.append("					  </table>\n")
					  .append("				  </div>\n")
					  .append("			  </form>\n")
					  .append("			</div>\n")
					  .append("	  </div>\n")
					  .append("	</div>\n")
					  .append("</main>\n");

			//ORDER EDIT
			} else if (module.equals("OrdersEdit")) {
				
				 OrdersDataset ds = null;

			     while (sqlParam.hasNext()) {

			    	ds =  (OrdersDataset) sqlParam.next();
		    	 
					myvar.append("<main>\n")
					     .append("	<div class=\"container\">\n")
					     .append("		<div class=\"row\">\n")
					     .append("			<div class=\"col-md-8 col-md-offset-2\">\n")
					     .append(String.format("				<form action=\"ManipulateData?id=%s&process=update&module=orders\" method=\"post\">\n", ds.getORDER_ID()))
					     .append("				  <div class=\"well\">\n")
					     .append("				  		<table width=\"100%\" border=\"1\" cellspacing=\"5\" cellpadding=\"5\">\n")
					     .append("				  			<tr>\n")
					     .append("		  		      		  <td colspan=\"4\" align=\"center\"><b>ORDERS EDIT</b></td>\n")
					     .append("				  			</tr>\n")
					     .append("				  			<tr>\n")
					     .append("				  			  <td>ID#</td>\n")
					     .append(String.format("		  		      		  <td colspan=\"3\"><input name=\"ORDER_ID\" type=\"text\" disabled id=\"ORDER_ID\" value=\"%s\"></td>\n", ds.getORDER_ID()))
					     .append("	  				  		</tr>\n")				     
					     .append("							<tr>\n")
					     .append("		  				  	  <td width=\"100\">Product ID</td>\n")
					     .append(String.format("		  		      		  <td colspan=\"3\"><input name=\"ORDER_PRODUCT_NAME\" type=\"text\" style=\"background-color:#EBEBE4\" readonly id=\"ORDER_PRODUCT_NAME\" value=\"%s\">\n", ds.getORDER_PRODUCT_NAME()))
					     .append(String.format("		  		      		  <input name=\"ORDER_PRODUCT_ID\" type=\"hidden\" id=\"ORDER_PRODUCT_ID\" value=\"%s\"></td>\n", ds.getORDER_PRODUCT_ID()))
					     .append("			      		    </tr>\n")				     
					     .append("			      		    <tr>\n")
					     .append("		  		      		  <td>Customer ID</td>\n")
					     .append(String.format("		  		      		  <td colspan=\"3\"><input name=\"ORDER_CUSTOMER_NAME\" type=\"text\" style=\"background-color:#EBEBE4\" readonly id=\"ORDER_CUSTOMER_NAME\" value=\"%s\">\n", ds.getORDER_CUSTOMER_NAME()))
					     .append(String.format("		  		      		  <input name=\"ORDER_CUSTOMER_ID\" type=\"hidden\" id=\"ORDER_CUSTOMER_ID\" value=\"%s\"></td>\n", ds.getORDER_CUSTOMER_ID()))
					     .append("			      		    </tr>\n")
					     .append("			      		    <tr>\n")
					     .append("		  		      		  <td>Quantity</td>\n")
					     .append(String.format("		  		      		  <td><input type=\"number\" min=\"0\" name=\"ORDER_QUANTITY\" id=\"ORDER_QUANTITY\" value=\"%s\"></td>\n", ds.getORDER_QUANTITY()))
					     .append("		  		      		  <td>Unit Price</td>\n")
					     .append(String.format("		  		      		  <td><input type=\"number\" min=\"0\" name=\"ORDER_TOTAL\" id=\"ORDER_TOTAL\" value=\"%s\"></td>\n", ds.getORDER_TOTAL()))
					     .append("			      		    </tr>\n")
					     .append("			      		    <tr>\n")
					     .append("		      		          <td>Order Date</td>\n")
					     .append(String.format("		      		          <td colspan=\"3\"><input type=\"date\" name=\"ORDER_DATE\" id=\"ORDER_DATE\" value=\"%s\"></td>\n", ds.getORDER_DATE() == null ? "": ds.getORDER_DATE()))
					     .append("			      		    </tr>\n")
					     .append("			      		    <tr>\n")
					     .append("		      		          <td colspan=\"4\">&nbsp;</td>\n")
					     .append("			      		    </tr>\n") 
					     .append("			      		    <tr>\n")
					     .append("		      		          <td height=\"100\" colspan=\"4\" align=\"center\">\n")
					     .append("		      		            <input type=\"submit\" id=\"savebutton\" class=\"btn btn-lg btn-default\" value=\"Update\"></td>\n")
					     .append("			      		    </tr>\n")
					     .append("				  		</table>\n")
						 .append("				  </div>\n")
						 .append("			  </form>\n")
						 .append("			</div>\n")
						 .append("	  </div>\n")
						 .append("	</div>\n")
						 .append("</main>\n");
				
			     }

			//ORDER NEW
			} else if (module.equals("OrdersNew")) {
			    	 
				myvar.append("<main>\n") 
			     .append("	<div class=\"container\">\n")
			     .append("		<div class=\"row\">\n")
			     .append("			<div class=\"col-md-8 col-md-offset-2\">\n")
			     .append("				<form action=\"ManipulateData?process=add&module=orders\" method=\"post\">\n")
			     .append("				  <div class=\"well\">\n")
			     .append("				  		<table width=\"100%\" border=\"1\" cellspacing=\"5\" cellpadding=\"5\">\n")
			     .append("				  			<tr>\n")
			     .append("		  		      		  <td colspan=\"4\" align=\"center\"><b>ORDERS NEW</b></td>\n")
			     .append("				  			</tr>\n")			     
			     .append("							<tr>\n")
			     .append("		  				  	  <td width=\"100\">Product Name</td>\n")
			     .append(String.format("		  		      		  <td colspan=\"3\"><input name=\"ORDER_PRODUCT_NAME\" type=\"text\" style=\"background-color:#EBEBE4\" readonly id=\"ORDER_PRODUCT_NAME\" value=\"%s\">\n", action.split("@@")[3]))
			     .append(String.format("		  		      		  <input name=\"ORDER_PRODUCT_ID\" type=\"hidden\" id=\"ORDER_PRODUCT_ID\" value=\"%s\"></td>\n", action.split("@@")[2]))
			     .append("			      		    </tr>\n")				     
			     .append("			      		    <tr>\n")
			     .append("		  		      		  <td>Customer Name</td>\n")
			     .append(String.format("		  		      		  <td colspan=\"3\"><input name=\"ORDER_CUSTOMER_NAME\" type=\"text\" style=\"background-color:#EBEBE4\" readonly id=\"ORDER_CUSTOMER_NAME\" value=\"%s\">\n", action.split("@@")[1]))
			     .append(String.format("		  		      		  <input name=\"ORDER_CUSTOMER_ID\" type=\"hidden\" id=\"ORDER_CUSTOMER_ID\" value=\"%s\"></td>\n", action.split("@@")[0]))
			     .append("			      		    </tr>\n")
			     .append("			      		    <tr>\n")
			     .append("		  		      		  <td>Quantity</td>\n")
			     .append("		  		      		  <td><input type=\"number\" min=\"0\" name=\"ORDER_QUANTITY\" id=\"ORDER_QUANTITY\" value=\"0\" autofocus required></td>\n")
			     .append("		  		      		  <td>Unit Price</td>\n")
			     .append("		  		      		  <td><input type=\"number\" min=\"0\" name=\"ORDER_TOTAL\" id=\"ORDER_TOTAL\" value=\"0.00\"></td>\n")
			     .append("			      		    </tr>\n")
			     .append("			      		    <tr>\n")
			     .append("		      		          <td>Order Date</td>\n")
			     .append("		      		          <td colspan=\"3\"><input type=\"date\" name=\"ORDER_DATE\" id=\"ORDER_DATE\" required></td>\n")
			     .append("			      		    </tr>\n")
			     .append("			      		    <tr>\n")
			     .append("		      		          <td colspan=\"4\">&nbsp;</td>\n")
			     .append("			      		    </tr>\n") 
			     .append("			      		    <tr>\n")
			     .append("		      		          <td height=\"100\" colspan=\"4\" align=\"center\">\n")
			     .append("		      		            <input type=\"submit\" id=\"addbutton\" class=\"btn btn-lg btn-default\" value=\"Add New Order\"></td>\n")
			     .append("			      		    </tr>\n")
			     .append("				  		</table>\n")
				 .append("				  </div>\n")
				 .append("			  </form>\n")
				 .append("			</div>\n")
				 .append("	  </div>\n")
				 .append("	</div>\n")
				 .append("</main>\n");

		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		
		//CATEGORIES HTML ///////////////////////////////////////////////////////////////////////////////////////////
			
			//CATEGORIES VIEW
			} else if (module.equals("CategoriesView")) {
				
				CategoriesDataset ds = null;

				myvar.append("<main>\n")
				     .append("	<div class=\"container\">\n")
				     .append("		<div class=\"row\">\n")
				     .append("			<div class=\"col-md-12\">\n")
				     .append("				<form action=\"ManipulateData?process=new&module=categories\" method=\"post\">\n")
				     .append("				  <div class=\"well\">\n")
				     .append("					  <table id=\"tablelist\" width=\"100%\" cellpadding=\"5\" cellspacing=\"5\" border=\"1\">\n")
				     .append("						<tr>\n")
				     .append("							<td colspan=\"4\" align=\"center\"><b>CATEGORIES</b></td>\n")
				     .append("						</tr>\n")
				     .append("						<tr bgcolor=\"#E9FAE1\">\n")
				     .append("							<td>ID</td>\n")
				     .append("							<td>Name</td>\n")
				     .append("							<td></td>\n")
				     .append("							<td></td>\n")
				     .append("						</tr>\n");
				
			     while (sqlParam.hasNext()) {

			    	 ds =  (CategoriesDataset) sqlParam.next();

			    	 myvar.append("						<tr>\n")
			    	 .append(String.format("							<td>%s</td>\n", ds.getCATEGORY_ID()))
				     .append(String.format("							<td>%s</td>\n", ds.getCATEGORY_NAME()))
				     .append(String.format("							<td><a href=\"ManipulateData?id=%s&process=edit&module=categories\">Edit</a></td>\n", ds.getCATEGORY_ID()))
				     .append(String.format("							<td><a href=\"ManipulateData?id=%s&process=delete&module=categories\">Delete</a></td>\n", ds.getCATEGORY_ID()))
			    	 .append("						</tr>\n");
			     
			     }
			     
		    	 myvar.append("						<tr>\n")
		    	 .append("							<td colspan=\"9\" height=\"100\" align=\"center\"><input type=\"submit\" id=\"addbutton\" class=\"btn btn-lg btn-default\" value=\"Add New Category\"></td>\n")
		    	 .append("						</tr>\n");
		    	 
				 myvar.append("					  </table>\n")
					  .append("				  </div>\n")
					  .append("			  </form>\n")
					  .append("			</div>\n")
					  .append("	  </div>\n")
					  .append("	</div>\n")
					  .append("</main>\n");

			//CATEGORIES EDIT
			} else if (module.equals("CategoriesEdit")) {
				
				 CategoriesDataset ds = null;

			     while (sqlParam.hasNext()) {

			    	ds =  (CategoriesDataset) sqlParam.next();
		    	 
					myvar.append("<main>\n")
					     .append("	<div class=\"container\">\n")
					     .append("		<div class=\"row\">\n")
					     .append("			<div class=\"col-md-8 col-md-offset-2\">\n")
					     .append(String.format("				<form action=\"ManipulateData?id=%s&process=update&module=categories\" method=\"post\">\n", ds.getCATEGORY_ID()))
					     .append("				  <div class=\"well\">\n")
					     .append("				  		<table width=\"100%\" border=\"1\" cellspacing=\"5\" cellpadding=\"5\">\n")
					     .append("				  			<tr>\n")
					     .append("		  		      		  <td colspan=\"2\" align=\"center\"><b>CATEGORIES EDIT</b></td>\n")
					     .append("				  			</tr>\n")
					     .append("				  			<tr>\n")
					     .append("				  			  <td>ID#</td>\n")
					     .append(String.format("		  		      		  <td><input name=\"CATEGORY_ID\" type=\"text\" disabled id=\"CATEGORY_ID\" value=\"%s\"></td>\n", ds.getCATEGORY_ID()))
					     .append("	  				  		</tr>\n")				     
					     .append("							<tr>\n")
					     .append("		  				  	  <td width=\"100\">Category Name</td>\n")
					     .append(String.format("		  		      		  <td><input name=\"CATEGORY_NAME\" type=\"text\" autofocus required id=\"CATEGORY_NAME\" value=\"%s\"></td>\n", ds.getCATEGORY_NAME()))
					     .append("			      		    </tr>\n")				     
					     .append("			      		    <tr>\n")
					     .append("		      		          <td colspan=\"2\">&nbsp;</td>\n")
					     .append("			      		    </tr>\n") 
					     .append("			      		    <tr>\n")
					     .append("		      		          <td height=\"100\" colspan=\"2\" align=\"center\">\n")
					     .append("		      		            <input type=\"submit\" id=\"savebutton\" class=\"btn btn-lg btn-default\" value=\"Update\"></td>\n")
					     .append("			      		    </tr>\n")
					     .append("				  		</table>\n")
						 .append("				  </div>\n")
						 .append("			  </form>\n")
						 .append("			</div>\n")
						 .append("	  </div>\n")
						 .append("	</div>\n")
						 .append("</main>\n");
				
			     }

			//CATEGORIES NEW
			} else if (module.equals("CategoriesNew")) {
			    	 
				myvar.append("<main>\n")
			     .append("	<div class=\"container\">\n")
			     .append("		<div class=\"row\">\n")
			     .append("			<div class=\"col-md-8 col-md-offset-2\">\n")
			     .append("				<form action=\"ManipulateData?process=add&module=categories\" method=\"post\">\n")
			     .append("				  <div class=\"well\">\n")
			     .append("				  		<table width=\"100%\" border=\"1\" cellspacing=\"5\" cellpadding=\"5\">\n")
			     .append("				  			<tr>\n")
			     .append("		  		      		  <td colspan=\"2\" align=\"center\"><b>CATEGORIES NEW</b></td>\n")
			     .append("				  			</tr>\n")			     
			     .append("							<tr>\n")
			     .append("		  				  	  <td width=\"100\">Category Name</td>\n")
			     .append("		  		      		  <td><input name=\"CATEGORY_NAME\" type=\"text\" autofocus required id=\"CATEGORY_NAME\"></td>\n")
			     .append("			      		    </tr>\n")	
			     .append("			      		    <tr>\n")
			     .append("		      		          <td colspan=\"2\">&nbsp;</td>\n")
			     .append("			      		    </tr>\n") 
			     .append("			      		    <tr>\n")
			     .append("		      		          <td height=\"100\" colspan=\"2\" align=\"center\">\n")
			     .append("		      		            <input type=\"submit\" id=\"addbutton\" class=\"btn btn-lg btn-default\" value=\"Add New Category\"></td>\n")
			     .append("			      		    </tr>\n")
			     .append("				  		</table>\n")
				 .append("				  </div>\n")
				 .append("			  </form>\n")
				 .append("			</div>\n")
				 .append("	  </div>\n")
				 .append("	</div>\n")
				 .append("</main>\n");
	
		}
		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////
			
		return myvar.toString();

	}
	
}
