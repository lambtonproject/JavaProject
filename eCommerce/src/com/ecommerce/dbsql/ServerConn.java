package com.ecommerce.dbsql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletContext;

public class ServerConn {

	public static Connection getConn(ServletContext context) throws SQLException, ClassNotFoundException {
		
	    Class.forName("oracle.jdbc.OracleDriver");

	    Connection conn = DriverManager.getConnection(context.getInitParameter("sqlconn"));

		return conn;
	       
	}
	
}
