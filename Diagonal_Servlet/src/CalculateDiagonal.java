import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CalculateDiagonal extends HttpServlet{
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		
		doCalculate(req, res);
		
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
		
		doCalculate(req, res);
		
	}
	
	public void doCalculate(HttpServletRequest req, HttpServletResponse res) {

		int d1, d2, d3, d4, d5, d6, d7, d8, d9;
		d1 = Integer.parseInt(req.getParameter("d1"));
		d2 = Integer.parseInt(req.getParameter("d2"));	
		d3 = Integer.parseInt(req.getParameter("d3"));
		d4 = Integer.parseInt(req.getParameter("d4"));	
		d5 = Integer.parseInt(req.getParameter("d5"));
		d6 = Integer.parseInt(req.getParameter("d6"));	
		d7 = Integer.parseInt(req.getParameter("d7"));
		d8 = Integer.parseInt(req.getParameter("d8"));		
		d9 = Integer.parseInt(req.getParameter("d9"));	
		
		try {

			int [][] matrix =  {
	    		  {d1, d2, d3}, 
	    		  {d4, d5, d6}, 
	    		  {d7, d8, d9}
	    		  };
	
			int diagonalPrimarySum = 0;
			int diagonalSecondarySum = 0;
	
			int s = matrix.length - 1;
	
			for(int i = 0; i < matrix.length; i++){
	
				diagonalPrimarySum += matrix[i][i];
				diagonalSecondarySum += matrix[i][s-i];
		
			}
			
			res.setContentType("text/html");
			res.getWriter().write("Primary Diagonal Sum =" + diagonalPrimarySum + "<br>");
			res.getWriter().write("Secondary Diagonal Sum =" + diagonalSecondarySum + "<br>");

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
