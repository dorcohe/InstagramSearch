package webapp;

import java.util.HashMap;

public class SearchResults {
	 	protected HashMap<String, String> relevantHeaders;
	    protected String jsonResponse;
	    SearchResults(HashMap<String, String> headers, String json) {
	        relevantHeaders = headers;
	        jsonResponse = json;
	 }
}
