import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
  
@WebServlet(name = "Quiz", urlPatterns = {"/quiz"},
initParams = {@WebInitParam(name = "welcome", value = "Lets start the quiz"),
				@WebInitParam(name = "result", value = "Your final score as follows")
})
public class Quiz extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	ServletConfig config;
	
	@Override
	public void init() throws ServletException {

		config = getServletConfig();
				
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		//doProceed(req, resp);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		doProceed(req, resp);
		
	}
	
	public void doProceed (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		resp.setContentType("text/html");
		
		PrintWriter out = resp.getWriter();
		
		
		switch (req.getParameter("question")) {

			case "q1":
				
				out.println("<center><h2>Hello " + req.getParameter("username") + "<br>" + config.getInitParameter("welcome") + "<h2></center>");
				out.println(createHtml("", "Q1", "q2", "What is the size of byte variable?", "8 bit", "16 bit", "32 bit", "64 bit"));

				break;
			
			case "q2":

				out.println(createHtml(req.getParameter("answer") + ",", "Q2", "q3", "What is the size of byte variable?", "8 bit", "16 bit", "32 bit", "64 bit"));
				
				break;
			
			case "q3":
				
				out.println(createHtml(req.getParameter("prevanswers") + req.getParameter("answer") + ",", "Q3", "q4", "What is the default value of byte variable?", "0", "0.0", "null", "not defined"));

				break;
				
			case "q4":
				
				out.println(createHtml(req.getParameter("prevanswers") + req.getParameter("answer") + ",", "Q4", "q5", "Which of the following stands true about default modifier of class members?", "By default, variables, methods and constructors can be accessed by subclass only.", "By default, variables, methods and constructors can be accessed by any class lying in any package.", "By default, variables, methods and constructors can be accessed by any class lying in the same package.", "None of the above."));

				break;
				
			case "q5":
				
				out.println(createHtml(req.getParameter("prevanswers") + req.getParameter("answer") + ",", "Q5", "q6", "What is Abstraction?", "Abstraction is a technique to define different methods of same type.", "Abstraction is the ability of an object to take on many forms.", "It refers to the ability to make a class abstract in OOP.", "None of the above."));
				
				break;

			case "q6":
				
				out.println(createHtml(req.getParameter("prevanswers") + req.getParameter("answer") + ",", "Q6", "q7", "Composition represents", "HAS-A relationship.", "IS-A relationship.", "DIS-A relationship.", "A relationship."));
				
				break;
			
			case "q7":

				out.println(createHtml(req.getParameter("prevanswers") + req.getParameter("answer") + ",", "Q7", "q8", "What is function overriding?", "If a subclass uses a method that is already provided by its parent class, it is known as Method Overriding.", "If a subclass provides a specific implementation of a method that is already provided by its parent class, it is known as Method Overriding.", "Both of the above.", "None of the above."));
				
				break;
			
			case "q8":
				
				out.println(createHtml(req.getParameter("prevanswers") + req.getParameter("answer") + ",", "Q8", "q9", "Which is the way in which a thread can enter the waiting state?", "Invoke its sleep() method.", "Invoke object's wait method.", "Invoke its suspend() method.", "All of the above."));

				break;
				
			case "q9":
				
				out.println(createHtml(req.getParameter("prevanswers") + req.getParameter("answer") + ",", "Q9", "q10", "super keyword in Java is used for?", "to refer to immediate child class of a class.", "to refer to immediate parent class of a class.", "to refer to current class object.", "to refer to static member of parent class."));

				break;
				
			case "q10":
				
				out.println(createHtml(req.getParameter("prevanswers") + req.getParameter("answer") + ",", "Q10", "score", "In which case, a program is expected to recover?", "If an error occurs.", "If an exception occurs.", "Both of the above.", "None of the above."));

				break;

			case "score":
				
				out.println(calcScore(req.getParameter("prevanswers") + req.getParameter("answer")));

				out.println("<br><br>Correct Answers: " 
						 + "<br>" + "Q1 - A"
						 + "<br>" + "Q2 - D"
						 + "<br>" + "Q3 - A"
						 + "<br>" + "Q4 - C"
						 + "<br>" + "Q5 - C"
						 + "<br>" + "Q6 - A"
						 + "<br>" + "Q7 - B"
						 + "<br>" + "Q8 - D"
						 + "<br>" + "Q9 - B"
						 + "<br>" + "Q10 - B"
						);
				
				out.println("<br><br><a href=\"index.htm\">Restart</a>");
						
				break;

			default:
				
				break;
	
		}

	}
	 
	public String createHtml(String previousAnswers, String Number, String NextNumber, String Question, String A1, String A2, String A3, String A4) {

		StringBuilder myvar = new StringBuilder(); 
		myvar.append("<!doctype html>\n")
		     .append("<html>\n")
		     .append("<head>\n")
		     .append("<meta charset=\"utf-8\">\n")
		     .append("<title>Java Online Quiz</title>\n")
		     .append("</head>\n")
		     .append("")
		     .append("<body>\n")
		     .append("")
		     .append("<form method=\"post\" action=\"quiz?question=" + NextNumber + "\"\n")
		     .append("	<table width=\"100%\" border=\"1\" cellspacing=\"5\" cellpadding=\"5\">\n")
		     .append("		<tr>\n")
		     .append(String.format("		  <td>%s</td>\n", Number))
		     .append("		</tr>")
		     .append("		<tr>\n")
		     .append(String.format("		  <td>%s</td>\n", Question))
		     .append("		</tr>\n")
		     .append("		<tr>\n")
		     .append("		  <td><table width=\"100%\">\n")
		     .append("			<tr>\n")
		     .append("			  <td><label>A) ")
		     .append("				<input type=\"radio\" name=\"answer\" value=\"a\">\n")
		     .append(String.format("				%s</label></td>\n", A1))
		     .append("			</tr>\n")
		     .append("			<tr>\n")
		     .append("			  <td><label>B) ")
		     .append("				<input type=\"radio\" name=\"answer\" value=\"b\">\n")
		     .append(String.format("				%s</label></td>\n", A2))
		     .append("			</tr>\n")
		     .append("			<tr>\n")
		     .append("			  <td><label>C) ")
		     .append("				<input type=\"radio\" name=\"answer\" value=\"c\">\n")
		     .append(String.format("				%s</label></td>\n", A3))
		     .append("			</tr>\n")
		     .append("			<tr>\n")
		     .append("			  <td><label>D) ")
		     .append("				<input type=\"radio\" name=\"answer\" value=\"d\">\n")
		     .append(String.format("				%s</label></td>\n", A4))
		     .append("			</tr>\n")
		     .append("		  </table></td>\n")
		     .append("		</tr>\n")
		     .append("		<tr>\n")
		     .append("		  <td>\n")
		     .append(String.format("		  	<input type=\"hidden\" name=\"prevanswers\" id=\"prevanswers\" value=\"%s\">\n", previousAnswers))
		     .append("		  	<button type=\"submit\" id=\"start\" class=\"btn btn-lg btn-default\">Next</button></td>\n")
		     .append("  		</tr>\n")
		     .append("	</table>\n")
		     .append("</form>\n")
		     .append("</body>\n")
		     .append("</html>");
		
		return myvar.toString();

	}
	
	public String calcScore(String answers) {
		
		String[] arrayAnswers = answers.split(",");
		
		String[] arrayKeyAnswers = {"a","d","a","c","c","a","b","d","b","b"};
		
		int lastscore = 0;
		
		for (int i = 0; i < arrayKeyAnswers.length; i++) {
			 
			if (arrayAnswers[i].equals(arrayKeyAnswers[i])) {
				lastscore +=1;
			}
			
		}
		
		return config.getInitParameter("result") + "<br><b>" + lastscore + " of 10</b>";

	}
	
}
