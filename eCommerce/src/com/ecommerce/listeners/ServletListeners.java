package com.ecommerce.listeners;

import com.ecommerce.beans.UsersDataset;
import com.ecommerce.dbsql.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class ServletListeners implements HttpSessionListener, ServletContextListener {
	
	ServletContext context;
	
	@Override
	public void sessionCreated(HttpSessionEvent event) {

		//LOG
		HttpSession session = event.getSession();
		
		DataProcess<UsersDataset> dbp = new DataProcess<UsersDataset>(new UsersDataset(), session.getServletContext());
		
					
		Date created = new Date(session.getCreationTime());
			        
		List<String> params = new ArrayList<String>();
		   
		params.add("Login the administration" + "; " + "ID " + session.getId() + "; " + "Created: " + created);
		
		dbp.insertData("insert into LOG (LOG_DESCRIPTION) values(?)", params);    
            
		
		System.out.println("A new session is created");
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		
		//LOG
		HttpSession session = event.getSession();
		
		DataProcess<UsersDataset> dbp = new DataProcess<UsersDataset>(new UsersDataset(), session.getServletContext());
		
        Date accessed = new Date(session.getLastAccessedTime());
			        
		List<String> params = new ArrayList<String>();
		   
		params.add("Logout the administration" + "; " + "ID " + session.getId() + "; " + "Last Accessed: " + accessed);
		
		dbp.insertData("insert into LOG (LOG_DESCRIPTION) values(?)", params);    
		
		System.out.println("session is destroyed");

	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {

		
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {

//        ServletContext contxt = event.getServletContext();
//        
//        String usr = (String) contxt.getInitParameter("user");
//        String pwd = (String) contxt.getInitParameter("password");
//        
//        DbManager dbMgr=null;
//        
//        try{
//           dbMgr= new DbManager(url, usr, pwd, className);
//           contxt.setAttribute("DbMgr", dbMgr);
//        }catch(Exception e){
//        System.out.println("DB Failure");
//         }
//        
//		ServletContext sc = event.getServletContext();
//		sc.setAttribute("dbstr", "sql");
		
	}
	
}
