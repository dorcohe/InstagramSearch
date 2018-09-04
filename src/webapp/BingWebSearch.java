package webapp;
import java.net.*;
import java.util.*;
import java.io.*;
import javax.net.ssl.HttpsURLConnection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class BingWebSearch {
	// Enter a valid subscription key.
	static String subscriptionKey = Constans.MY_SUBSCRIPTION_KEY;

	/*
	 * If you encounter unexpected authorization errors, double-check these values
	 * against the end point for your "Bing" Web search instance in your Azure
	 * dashboard !.
	 */
	static String host = "https://api.cognitive.microsoft.com";
	static String path = "/bingcustomsearch/v7.0/search";
	static String customConfigId = "2333203261";  
	
	public static SearchResults SearchWeb (String searchQuery) throws Exception {
	    // Construct the URL.
	    URL url = new URL(host + path + "?q=" +  URLEncoder.encode(searchQuery, "UTF-8" ) + "&CustomConfig=" + customConfigId);

	    // Open the connection.
	    HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
	    connection.setRequestProperty("Ocp-Apim-Subscription-Key", subscriptionKey);

	    // Receive the JSON response body.
	    InputStream stream = connection.getInputStream();
	    String response = new Scanner(stream).useDelimiter("\\A").next();

	    // Construct the result object.
	    SearchResults results = new SearchResults(new HashMap<String, String>(), response);
	    // Extract Bing-related HTTP headers.
	    Map<String, List<String>> headers = connection.getHeaderFields();
	    for (String header : headers.keySet()) {
	        if (header == null) continue;      // may have null key
	        if (header.startsWith("BingAPIs-") || header.startsWith("X-MSEdge-")){
	            results.relevantHeaders.put(header, headers.get(header).get(0));
	        }
	    }
	    stream.close();
	    return results;
	}
	
	public static String prettify(String json_text) {
	    JsonParser parser = new JsonParser();
	    JsonObject json = parser.parse(json_text).getAsJsonObject();
	    Gson gson = new GsonBuilder().setPrettyPrinting().create();
	    return gson.toJson(json);
	}
	
	
}
