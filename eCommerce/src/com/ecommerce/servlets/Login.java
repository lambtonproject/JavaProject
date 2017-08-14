package com.ecommerce.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
 
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ecommerce.beans.UsersDataset;
import com.ecommerce.dbsql.DataProcess;

//////////////////////////////////////////////////////////////////////////////////
//All rights of this program belong to 'KAYA CETINSU' who is a computer programmer
//Copyright 2017. All rights reserved
//////////////////////////////////////////////////////////////////////////////////



//do not use variables in servlet classes, because servlet classes are common to public

//@WebServlet("/Login", initParams = { 
//@WebInitParam(name = "welcome", value = "Hello Visitor"), 
//@WebInitParam(name = "bye", value = "See you next time") 
//})
public class Login extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	ServletConfig config;
	ServletContext context;
	
	@Override
	public void init() throws ServletException {

		Random rand = new Random();

		int n = rand.nextInt(5000) + 1;
		
		config = getServletConfig();
		context = getServletContext();
		
		context.setAttribute("db", n);
		
	}
	 
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {
			
			doControlAdmin(req, resp);
			
		} catch (ServletException e) {

			e.printStackTrace();
		}
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		try {
			
			doControlAdmin(req, resp);
			
		} catch (ServletException e) {

			e.printStackTrace();
		}
	
	}
	
	public void doControlAdmin(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		
		try {
			
		//resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html");
		resp.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
		
		PrintWriter out = resp.getWriter();

		
		DataProcess<UsersDataset> dbp = new DataProcess<UsersDataset>(new UsersDataset(), context);
		
		List<String> params = new ArrayList<String>();
		     
		params.add(req.getParameter("username"));
		params.add(req.getParameter("password"));

		//HashMap Style to fetch data
		//List<Map<String, Object>> rs = dbp.selectData("select * from USERS where USER_USERNAME = ? and USER_PASSWORD = ?", params, "UsersDataset");	
		
		//Wrapper Class Style to fetch data
		List<UsersDataset> rs = dbp.selectData("select * from USERS where USER_USERNAME = ? and USER_PASSWORD = ?", params);  

		String username = "", password = "";
		int id = 0;

		//HashMap Style for iterator
		//Iterator<Map<String, Object>> sqlParam =  rs.iterator();
		
		//Wrapper Class Style for iterator
		Iterator<UsersDataset> sqlParam =  rs.iterator();

		//HashMap Style to fetch data//////////////////////
		
//		while (sqlParam.hasNext()) {
//
//			Map<String, Object> row = (Map<String, Object>) sqlParam.next();
//
//			id = (int) row.get("USER_ID");
//			username = (String) row.get("USER_USERNAME");
//			password = (String) row.get("USER_PASSWORD");
//
//		}
		
		////////////////////////////////////////////////////
		

		//Wrapper Class Style to fetch data/////////////////
		
		UsersDataset ud = null;
		
		while (sqlParam.hasNext()) {
			
			ud = sqlParam.next();
			
			id = ud.getUSER_ID();
			username = ud.getUSER_USERNAME();
			password = ud.getUSER_PASSWORD();

		}
		
//	    for(UsersDataset b: (List<UsersDataset>) rs){  
//        System.out.println(b.getCUSTOMER_NAME()+" "+b.getUSER_PASSWORD());  
//    	} 
		
		////////////////////////////////////////////////////
		
		
		if (username.equals("admin") && password.equals("admin")) {

			HttpSession session = req.getSession();
			session.setAttribute("admin", "true");   
			session.setAttribute("admin_id", id);

			//req.getRequestDispatcher("/ViewData?module=customers").forward(req, resp);
			resp.sendRedirect("ViewData?module=customers");
	
		} else {
  
			//RequestDispatcher rd = req.getRequestDispatcher("/index.htm");  
	        //rd.include(req, resp);  
 
	        out.println("<p align='center'><b>Wrong password</b>");
			out.println("<br><a href='javascript:history.back(-1)'>back</a></p>");
			
//			HttpSession session = req.getSession();	
//	        out.println("<br>");			
//			out.println("Value from listener: " + session.getAttribute("dbstr"));	
//	        out.println("<br>");
//	        out.println("sqlconn from context - web.xml: "+ context.getInitParameter("sqlconn"));
//	        out.println("<br>");
//	        out.println("Value from context - init(): " + context.getAttribute("db"));
//	        out.println("<br>");
//	        out.println("Value from config - init(): " + config.getInitParameter("ServletLoginParam"));
//	        out.println("<br>");
	        
		}
		
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}		
		
	}
	
}
