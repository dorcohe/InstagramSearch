package webapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


/**
 * Servlet implementation class SearchServlet
 */
@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String query = request.getParameter("query");
		query = query.trim(); 
		try {
			SearchResults result = BingWebSearch.SearchWeb(query);
			String jsonResult = BingWebSearch.prettify(result.jsonResponse);
			Pattern p = Pattern.compile("\"displayUrl\": \"https://www.instagram.com/[^\"]*\"");
		    Matcher m = p.matcher(jsonResult);
		    String displayUrl = "";
		    String url = "";
		    boolean validUrl = false;
		    if (m.find()) {
		    	displayUrl = m.group();
		    	p = Pattern.compile("http[^\"]*");
		    	m = p.matcher(displayUrl);
		    	if (m.find()) {
		    		url = m.group();
		    		validUrl = true;
		    	}
		    }
		
			 // Set response content type
		      
		      if (validUrl)
		    	  response.sendRedirect(url);
		      else {
		    	  response.setContentType("text/html");
		    	  PrintWriter out = response.getWriter();
		    	  out.println("<html>");
		    	  out.println("<body>");
		    	  out.print("<h1>" + "No Result Found" + "</h1>");
		    	  out.println("<br>" + jsonResult);
		    	  out.println("</body>");
		    	  out.println("</html>");
		    	  out.close();
		      }
		}
		catch (Exception e){
			e.printStackTrace(System.out);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
