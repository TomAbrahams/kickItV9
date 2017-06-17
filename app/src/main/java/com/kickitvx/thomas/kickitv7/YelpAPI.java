package com.kickitvx.thomas.kickitv7;

/**
 * Created by Thomas on 4/23/2017.
 */

/**
 * Created by Thomas on 3/1/2017.
 */

import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.scribe.builder.ServiceBuilder;
import org.scribe.exceptions.OAuthConnectionException;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

/**
 * Code sample for accessing the Yelp API V2.
 *
 * This program demonstrates the capability of the Yelp API version 2.0 by using the Search API to
 * query for businesses by a search term and location, and the Business API to query additional
 * information about the top result from the search query.
 *
 * <p>
 * See <a href="http://www.yelp.com/developers/documentation">Yelp Documentation</a> for more info.
 *
 */
public class YelpAPI  {


    private static final String API_HOST = "api.yelp.com";
    private static final String DEFAULT_TERM = "dinner";
    private static final String DEFAULT_LOCATION = "San Francisco, CA";
    private static final int SEARCH_LIMIT = 3;
    private static final String SEARCH_PATH = "/v2/search";
    private static final String BUSINESS_PATH = "/v2/business";

    /*
     * Update OAuth credentials below from the Yelp Developers API site:
     * http://www.yelp.com/developers/getting_started/api_access
     */
    private static final String CONSUMER_KEY = "PUT YOUR YELP CONSUMER KEY HERE";
    private static final String CONSUMER_SECRET = "PUT YOUR CONSUMER SECRET KEY HERE";
    private static final String TOKEN = "PUT YOUR TOKEN HERE";
    private static final String TOKEN_SECRET = "PUT YOUR TOKEN SECRET HERE.";

    private static final Vector<Event> RecommendedEvents = new Vector<>();
    private static final Event theEvent = new Event();

    OAuthService service;
    Token accessToken;

    /**
     * Setup the Yelp API OAuth credentials.
     *
     * @param consumerKey Consumer key
     * @param consumerSecret Consumer secret
     * @param token Token
     * @param tokenSecret Token secret
     */


    public YelpAPI(String consumerKey, String consumerSecret, String token, String tokenSecret) {
        this.service =
                new ServiceBuilder().provider(TwoStepOAuth.class).apiKey(consumerKey)
                        .apiSecret(consumerSecret).build();
        this.accessToken = new Token(token, tokenSecret);
    }
    public YelpAPI()
    {
        this.service = new ServiceBuilder().provider(TwoStepOAuth.class).apiKey(CONSUMER_KEY)
                .apiSecret(CONSUMER_SECRET).build();
        this.accessToken = new Token(TOKEN, TOKEN_SECRET);

    }

    /**
     * Creates and sends a request to the Search API by term and location.
     * <p>
     * See <a href="http://www.yelp.com/developers/documentation/v2/search_api">Yelp Search API V2</a>
     * for more info.
     *
     * @param term <tt>String</tt> of the search term to be queried
     * @param location <tt>String</tt> of the location
     * @return <tt>String</tt> JSON Response
     */
    public String searchForBusinessesByLocation(String term, String location) {
        OAuthRequest request = createOAuthRequest(SEARCH_PATH);
        request.addQuerystringParameter("term", term);
        request.addQuerystringParameter("location", location);
        request.addQuerystringParameter("limit", String.valueOf(SEARCH_LIMIT));
        return sendRequestAndGetResponse(request);
    }

    public String searchForBusinessByLongLat(String term, String theCLL)
    {
        OAuthRequest request = createOAuthRequest(SEARCH_PATH);
        request.addQuerystringParameter("term", term);
        request.addQuerystringParameter("ll", theCLL);
        request.addQuerystringParameter("limit", String.valueOf(SEARCH_LIMIT));

        this.service.signRequest(this.accessToken, request);
        Response response = request.send();
        //try {
        while(response.getBody() == null) {
            response = request.send();
        }
        return response.getBody();
        //return theBody;
        //}
        /*catch(OAuthConnectionException e)
        {
            String omega = e.getMessage();
            return "nope";
        }
        */

    }

    /**
     * Creates and sends a request to the Business API by business ID.
     * <p>
     * See <a href="http://www.yelp.com/developers/documentation/v2/business">Yelp Business API V2</a>
     * for more info.
     *
     * @param businessID <tt>String</tt> business ID of the requested business
     * @return <tt>String</tt> JSON Response
     */
    public String searchByBusinessId(String businessID) {
        OAuthRequest request = createOAuthRequest(BUSINESS_PATH + "/" + businessID);
        return sendRequestAndGetResponse(request);
    }

    /**
     * Creates and returns an {@link OAuthRequest} based on the API endpoint specified.
     *
     * @param path API endpoint to be queried
     * @return <tt>OAuthRequest</tt>
     */
    private OAuthRequest createOAuthRequest(String path) {
        OAuthRequest request = new OAuthRequest(Verb.GET, "http://" + API_HOST + path);
        return request;
    }

    /**
     * Sends an {@link OAuthRequest} and returns the {@link Response} body.
     *
     * @param request {@link OAuthRequest} corresponding to the API request
     * @return <tt>String</tt> body of API response
     */
    private String sendRequestAndGetResponse(OAuthRequest request) {
        //System.out.println("Querying " + request.getCompleteUrl() + " ...");
        this.service.signRequest(this.accessToken, request);
        Response response = request.send();
        return response.getBody();
    }

    /**
     * Queries the Search API based on the command line arguments and takes the first result to query
     * the Business API.
     *
     * @param yelpApi <tt>YelpAPI</tt> service instance
     * @param yelpApiCli <tt>YelpAPICLI</tt> command line arguments
     */
    private static void queryAPI(YelpAPI yelpApi, YelpAPICLI yelpApiCli) {
        String searchResponseJSON =
                yelpApi.searchForBusinessesByLocation(yelpApiCli.term, yelpApiCli.location);

        JSONParser parser = new JSONParser();
        JSONObject response = null;
        try {
            response = (JSONObject) parser.parse(searchResponseJSON);
        } catch (ParseException pe) {
            System.out.println("Error: could not parse JSON response:");
            System.out.println(searchResponseJSON);
            System.exit(1);
        }

        JSONArray businesses = (JSONArray) response.get("businesses");
        JSONObject firstBusiness = (JSONObject) businesses.get(0);
        String firstBusinessID = firstBusiness.get("id").toString();
        System.out.println(String.format(
                "%s businesses found, querying business info for the top result \"%s\" ...",
                businesses.size(), firstBusinessID));

        // Select the first business and display business details
        String businessResponseJSON = yelpApi.searchByBusinessId(firstBusinessID.toString());
        System.out.println(String.format("Result for business \"%s\" found:", firstBusinessID));
        System.out.println(businessResponseJSON);
    }
    public static String queryAPIX(YelpAPI yelpApi, String myTerm, String myLocation) {
        String searchResponseJSON =
                yelpApi.searchForBusinessesByLocation(myTerm, myLocation);

        JSONParser parser = new JSONParser();
        JSONObject response = null;
        try {
            response = (JSONObject) parser.parse(searchResponseJSON);
        } catch (ParseException pe) {
            System.out.println("Error: could not parse JSON response:");
            System.out.println(searchResponseJSON);
            System.exit(1);
        }
        try {
            JSONArray businesses = (JSONArray) response.get("businesses");
            JSONObject firstBusiness = (JSONObject) businesses.get(0);
            JSONObject secondBusiness = (JSONObject) businesses.get(1);
            JSONObject thirdBusiness = (JSONObject) businesses.get(2);

            //1st Business Added
            JSONObject firstBusLoc = (JSONObject) firstBusiness.get("location"); //Added
            JSONArray firstDispAdd = (JSONArray) firstBusLoc.get("display_address");
            String address = firstDispAdd.get(0).toString();
            String cityStateZip = firstDispAdd.get(1).toString();
            String firstBusinessID = firstBusiness.get("id").toString();
            String firstAddress = firstBusiness.get("location").toString();    //Added
            Event recommendEventOne = new Event(firstBusinessID, address + " " + cityStateZip, "N/A", "N/A");
            //2nd Business Added
            JSONObject secondBusLoc = (JSONObject) secondBusiness.get("location");
            JSONArray secondDispAdd = (JSONArray) secondBusLoc.get("display_address");
            String address2 = secondDispAdd.get(0).toString();
            String cityStateZip2 = secondDispAdd.get(1).toString();
            String secondBusinessID = secondBusiness.get("id").toString();
            String secondAddress = secondBusiness.get("location").toString();
            Event recommendEventTwo = new Event(secondBusinessID, address2 + " " + cityStateZip2, "N/A", "N/A");
            //3rd Business Added
            JSONObject thirdBusLoc = (JSONObject) thirdBusiness.get("location");
            JSONArray thirdDispAdd = (JSONArray) thirdBusLoc.get("display_address");
            String address3 = thirdDispAdd.get(0).toString();
            String cityStateZip3 = thirdDispAdd.get(1).toString();
            String thirdBusinessID = thirdBusiness.get("id").toString();
            String thirdAddress = thirdBusiness.get("location").toString();
            Event recommendEventThree = new Event(thirdBusinessID, address3 + " " + cityStateZip3, "N/A", "N/A");

            //Store the 3 recommendations in the vector.
            RecommendedEvents.add(recommendEventOne);
            RecommendedEvents.add(recommendEventTwo);
            RecommendedEvents.add(recommendEventThree);
        }
        catch(Exception e)
        {
            Event recommendEventOne = new Event();
            Event recommendEventTwo = new Event();
            Event recommendEventThree = new Event();
            RecommendedEvents.add(recommendEventOne);
            RecommendedEvents.add(recommendEventTwo);
            RecommendedEvents.add(recommendEventThree);
        }

        // Select the first business and display business details
        /*
        String businessResponseJSON = yelpApi.searchByBusinessId(firstBusinessID.toString());
        String firstBusinessName = firstBusiness.get("name").toString();
        myEvent.setEventAddress(address + " " + cityStateZip);
        myEvent.setEventName(firstBusinessName);
        mAuthSingleton myAuthSingleton = new mAuthSingleton();
        String userId = myAuthSingleton.Instance().getmAuth().getCurrentUser().getUid().toString();   //Failing here.
        myEvent.setUniqueId(userId);
        */
        return searchResponseJSON;
    }

    public static String queryAPIZ(YelpAPI yelpApi, String myTerm, String theCLL, Event myEvent) {

        String searchResponseJSON =
                yelpApi.searchForBusinessByLongLat(myTerm, theCLL);

        JSONParser parser = new JSONParser();
        JSONObject response = null;
        try {
            try {
                response = (JSONObject) parser.parse(searchResponseJSON);
            } catch (ParseException pe) {
                System.out.println("Error: could not parse JSON response:");
                System.out.println(searchResponseJSON);
                System.exit(1);
            }
            JSONArray businesses = (JSONArray) response.get("businesses");
            JSONObject firstBusiness = (JSONObject) businesses.get(0);
            JSONObject secondBusiness = (JSONObject) businesses.get(1);
            JSONObject thirdBusiness = (JSONObject) businesses.get(2);

            //1st Business Added
            JSONObject firstBusLoc = (JSONObject) firstBusiness.get("location"); //Added
            JSONArray firstDispAdd = (JSONArray) firstBusLoc.get("display_address");
            String address = firstDispAdd.get(0).toString();
            String cityStateZip = "";
            try {
                cityStateZip = firstDispAdd.get(1).toString();

            } catch (Exception e) {
                cityStateZip = "";
            }
            String firstBusinessID = firstBusiness.get("id").toString();
            String firstAddress = firstBusiness.get("location").toString();    //Added
            Event recommendEventOne = new Event(firstBusinessID, address + " " + cityStateZip, "N/A", "N/A");
            //2nd Business Added
            JSONObject secondBusLoc = (JSONObject) secondBusiness.get("location");
            JSONArray secondDispAdd = (JSONArray) secondBusLoc.get("display_address");
            String address2 = secondDispAdd.get(0).toString();

            String cityStateZip2 = "";
            try {
                cityStateZip2 = secondDispAdd.get(1).toString();

            } catch (Exception e) {
                cityStateZip2 = "";
            }
            String secondBusinessID = secondBusiness.get("id").toString();
            String secondAddress = secondBusiness.get("location").toString();
            Event recommendEventTwo = new Event(secondBusinessID, address2 + " " + cityStateZip2, "N/A", "N/A");
            //3rd Business Added
            JSONObject thirdBusLoc = (JSONObject) thirdBusiness.get("location");
            JSONArray thirdDispAdd = (JSONArray) thirdBusLoc.get("display_address");
            String address3 = thirdDispAdd.get(0).toString();
            String cityStateZip3 = "";
            try {
                cityStateZip3 = thirdDispAdd.get(1).toString();

            } catch (Exception e) {
                cityStateZip3 = "";
            }

            String thirdBusinessID = secondBusiness.get("id").toString();
            String thirdAddress = secondBusiness.get("location").toString();
            Event recommendEventThree = new Event(thirdBusinessID, address3 + " " + cityStateZip3, "N/A", "N/A");

            //Store the 3 recommendations in the vector.
            RecommendedEvents.add(recommendEventOne);
            RecommendedEvents.add(recommendEventTwo);
            RecommendedEvents.add(recommendEventThree);


            // Select the first business and display business details
            String businessResponseJSON = yelpApi.searchByBusinessId(firstBusinessID.toString());
            String firstBusinessName = firstBusiness.get("name").toString();
            myEvent.setEventAddress(address + " " + cityStateZip);
            myEvent.setEventName(firstBusinessName);
            mAuthSingleton myAuthSingleton = new mAuthSingleton();
            String userId = myAuthSingleton.Instance().getmAuth().getCurrentUser().getUid().toString();   //Failing here.
            myEvent.setUniqueId(userId);

            return searchResponseJSON;
        }
        catch (Exception e)
        {
            return searchResponseJSON;
        }
    }
    //This was added to allow the individual to search for demo.
    public static Event queryAPIJSON(String JSONResponseResult, Event myEvent, Vector<Event>busList) {


        String searchResponseJSON = JSONResponseResult;

        JSONParser parser = new JSONParser();
        JSONObject response = null;
        try {
            response = (JSONObject) parser.parse(searchResponseJSON);
        } catch (ParseException pe) {
            System.out.println("Error: could not parse JSON response:");
            System.out.println(searchResponseJSON);
            System.exit(1);
        }
        JSONArray businesses = (JSONArray) response.get("businesses");
        //JSONObject firstBusiness = (JSONObject) businesses.get(0);
        //JSONObject firstBusLoc = (JSONObject) firstBusiness.get("location"); //Added
        //Added
        //JSONArray firstDispAdd = (JSONArray) firstBusLoc.get("display_address");
        //String address = firstDispAdd.get(0).toString();
        //String cityStateZip = firstDispAdd.get(1).toString();

        //String firstBusinessID = firstBusiness.get("id").toString();


        //String firstAddress = firstBusiness.get("location").toString();	//Added
        try {
            JSONObject firstBusiness = (JSONObject) businesses.get(0);
            JSONObject secondBusiness = (JSONObject) businesses.get(1);
            JSONObject thirdBusiness = (JSONObject) businesses.get(2);

            //1st Business Added
            JSONObject firstBusLoc = (JSONObject) firstBusiness.get("location"); //Added
            JSONArray firstDispAdd = (JSONArray) firstBusLoc.get("display_address");
            String address = firstDispAdd.get(0).toString();
            String cityStateZip = "";
            try {
                cityStateZip = firstDispAdd.get(1).toString();
            } catch (Exception e) {
                cityStateZip = "";
            }
            String firstBusinessName = firstBusiness.get("name").toString();
            String firstAddress = firstBusiness.get("location").toString();    //Added
            String firstImageURL = firstBusiness.get("image_url").toString();
            Event recommendEventOne = new Event(firstBusinessName, address + " " + cityStateZip, "N/A", "N/A");
            recommendEventOne.setImageURL(firstImageURL);
            //2nd Business Added
            JSONObject secondBusLoc = (JSONObject) secondBusiness.get("location");
            JSONArray secondDispAdd = (JSONArray) secondBusLoc.get("display_address");
            String address2 = secondDispAdd.get(0).toString();
            String cityStateZip2;
            try {
                cityStateZip2 = secondDispAdd.get(1).toString();
            } catch (Exception e) {
                cityStateZip2 = "";
            }
            String secondBusinessName = secondBusiness.get("name").toString();
            String secondAddress = secondBusiness.get("location").toString();
            String secondImageURL = secondBusiness.get("image_url").toString();
            Event recommendEventTwo = new Event(secondBusinessName, address2 + " " + cityStateZip2, "N/A", "N/A");
            recommendEventTwo.setImageURL(secondImageURL);
            //3rd Business Added

            JSONObject thirdBusLoc = (JSONObject) thirdBusiness.get("location");
            JSONArray thirdDispAdd = (JSONArray) thirdBusLoc.get("display_address");
            String address3 = thirdDispAdd.get(0).toString();
            String cityStateZip3 = "";

            try {
                cityStateZip3 = thirdDispAdd.get(1).toString();

            } catch (Exception e) {
                cityStateZip3 = "";
            }
            String thirdBusinessName = thirdBusiness.get("name").toString();
            String thirdAddress = thirdBusiness.get("location").toString();
            String thirdImageURL = thirdBusiness.get("image_url").toString();
            Event recommendEventThree = new Event(thirdBusinessName, address3 + " " + cityStateZip3, "N/A", "N/A");
            recommendEventThree.setImageURL(thirdImageURL);
            //
            busList.add(0, recommendEventOne);
            busList.add(1, recommendEventTwo);
            busList.add(2, recommendEventThree);
            // Select the first business and display business details
            //String businessResponseJSON = yelpApi.searchByBusinessId(firstBusinessID.toString());
            String firstBusName = firstBusiness.get("name").toString();
            myEvent.setEventAddress(address + " " + cityStateZip);
            myEvent.setEventName(firstBusName);
            mAuthSingleton myAuthSingleton = new mAuthSingleton();
            String userId = myAuthSingleton.Instance().getmAuth().getCurrentUser().getUid().toString();   //Failing here.
            myEvent.setUniqueId(userId);
            return myEvent;
        }
        catch (Exception e)
        {
            Event recommendEventOne = new Event();
            Event recommendEventTwo = new Event();
            Event recommendEventThree = new Event();
            busList.add(0, recommendEventOne);
            busList.add(1, recommendEventTwo);
            busList.add(2, recommendEventThree);
            return myEvent;
        }



    }
    /**
     *
     * @param yelpApi YelpAPI server instance
     * @param yelpApiCli command line arguments
     * @param myLocation myLocation
     * @param myTerm myTerm
     */


    /**
     * Command-line interface for the sample Yelp API runner.
     */
    private static class YelpAPICLI {
        @Parameter(names = {"-q", "--term"}, description = "Search Query Term")
        public String term = DEFAULT_TERM;

        @Parameter(names = {"-l", "--location"}, description = "Location to be Queried")
        public String location = DEFAULT_LOCATION;
    }


    /**
     * Main entry for sample Yelp API requests.
     * <p>
     * After entering your OAuth credentials, execute <tt><b>run.sh</b></tt> to run this example.
     */





}