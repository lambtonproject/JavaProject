package com.ecommerce.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ecommerce.beans.CategoriesDataset;
import com.ecommerce.beans.CustomersDataset;
import com.ecommerce.beans.OrdersDataset;
import com.ecommerce.beans.ProductsDataset;
import com.ecommerce.dbsql.DataProcess;

import java.util.Collections;

//@WebServlet("/ViewData")
public class ViewData extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	ServletConfig config;
	ServletContext context;
	
	@Override
	public void init() throws ServletException {

		config = getServletConfig();
		context = getServletContext();

	}
	 
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {
			
			doProcess(req, resp);
			
		} catch (ServletException e) {

			e.printStackTrace();
		}
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		try {
			
			doProcess(req, resp);
			
		} catch (ServletException e) {

			e.printStackTrace();
		}
	
	}
	
	public void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		
		try {
		
			HttpSession session = req.getSession();
	
			resp.setContentType("text/html");
			resp.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
			
			PrintWriter out = resp.getWriter();
			
			if (session.getAttribute("admin") == null || !(session.getAttribute("admin").equals("true"))) {
				
				out.println("You have to login before proceeding");
				return;
				
			}
			
			req.getRequestDispatcher("header.htm").include(req, resp);

			//CUSTOMER VIEW/////////////////////////////////////////////////////////////////////////////////////////////////////
			
			if (req.getParameter("module").equals("customers")){

				DataProcess<CustomersDataset> dataProcess = new DataProcess<CustomersDataset>(new CustomersDataset(), context);
	
				List<String> params = new ArrayList<String>();
				
				List<CustomersDataset> rs = dataProcess.selectData("select * from CUSTOMERS", params);  
 
				Collections.sort(rs);
				
				out.println(HtmlCreator.createHtml("CustomersView", rs, req.getParameter("order") != null && req.getParameter("order").equals("active") ? "order" : ""));

				req.getRequestDispatcher("footer.htm").include(req, resp);
				
			////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				
				
			//PRODUCTS VIEW/////////////////////////////////////////////////////////////////////////////////////////////////////
			
			} else if (req.getParameter("module").equals("products")){

				DataProcess<ProductsDataset> dataProcess = new DataProcess<ProductsDataset>(new ProductsDataset(), context);
	
				List<String> params = new ArrayList<String>();
				 
				List<CustomersDataset> rs = dataProcess.selectData("select * from PRODUCTS", params);  
				
				Collections.sort(rs);
				
				out.println(HtmlCreator.createHtml("ProductsView", rs, req.getParameter("order") != null && req.getParameter("order").equals("active") ? req.getParameter("customerid") : ""));

				req.getRequestDispatcher("footer.htm").include(req, resp);
				
			////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			
				
			//ORDERS VIEW/////////////////////////////////////////////////////////////////////////////////////////////////////	
				
			} else if (req.getParameter("module").equals("orders")){

				DataProcess<OrdersDataset> dataProcess = new DataProcess<OrdersDataset>(new OrdersDataset(), context);
	
				List<String> params = new ArrayList<String>();
				
				List<OrdersDataset> rs = dataProcess.selectData("select * from  ORDERS o" + 
						" inner join PRODUCTS p ON o.ORDER_PRODUCT_ID = p.PRODUCT_ID" + 
						" inner join CUSTOMERS c ON o.ORDER_CUSTOMER_ID = c.CUSTOMER_ID", params);  
				
				Collections.sort(rs);
				
				out.println(HtmlCreator.createHtml("OrdersView", rs, ""));
				
				req.getRequestDispatcher("footer.htm").include(req, resp);
			
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
			
		//CATEGORIES VIEW/////////////////////////////////////////////////////////////////////////////////////////////////////	
			
			} else if (req.getParameter("module").equals("categories")){
	
				DataProcess<CategoriesDataset> dataProcess = new DataProcess<CategoriesDataset>(new CategoriesDataset(), context);
	
				List<String> params = new ArrayList<String>();
				
				List<OrdersDataset> rs = dataProcess.selectData("select * from CATEGORIES", params);  
				
				Collections.sort(rs);
				
				out.println(HtmlCreator.createHtml("CategoriesView", rs, ""));
				
				req.getRequestDispatcher("footer.htm").include(req, resp);

		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		
		//CATEGORIES VIEW/////////////////////////////////////////////////////////////////////////////////////////////////////	
			
			} else if (req.getParameter("module").equals("logout")){

				session.invalidate();
				
				resp.sendRedirect("index.htm");
			
			}

		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}		
		
	}	
	
}
