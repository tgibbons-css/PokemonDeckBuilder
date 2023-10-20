package CIS3334.deckbuilder;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * PokeApi class to call the Pokemon TCG API and retrieve card data
 */
public class PokeApi {

    private Gson gson;
    Context activityContext;
    Card retrievedCard;
    public Boolean cardHasBeenRetrieved = false;

//    public PokeApi()
//    {
//        gson = new Gson();
//    }

    public PokeApi(Context activityContext)
    {
        gson = new Gson();
        this.activityContext = activityContext;
    }

    /**
     * Method to retrieve a card from the PokeAPI
     * @return matching card
     */
    public Card getCard(String query)
    {
        // Call the PokeAPI and receive a JSON String.
        String url = "https://api.pokemontcg.io/v2/cards?=name:" + query;
        String json = callApi(url);

        //System.out.println("JSON = " + json);
        Card card;

        if(json.startsWith("{"))
        {
            // Parse the JSON into a Pokemon object
            card = gson.fromJson(json, new TypeToken<Card>(){}.getType());
        }
        else
        {
            // String probably contains an error message from the server.
            System.out.println(json);
            card = new Card();
        }

        return card;
    }

    public static String callApi(String rawUrl)
    {
        // Set up variables to call the URL and read the result.
        URL url;
        InputStream inputStream;
        InputStreamReader inputStreamReader;
        BufferedReader reader = null;
        String jsonResult = "";

        try
        {
            // Create the URL with the address to the server.
            url = new URL(rawUrl);

            // Call the url and create a Buffered Reader on the result.
            inputStream = url.openStream();
            inputStreamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();

            // Keep reading lines while more still exist.
            // Append the result to a String.  Should use a StringBuilder if we
            // are expecting a lot of lines.
            while (line != null) {
                jsonResult += line;
                line = reader.readLine();
            }
        }
        catch (MalformedURLException malformedURLException) {
            // URL was bad.
            jsonResult = malformedURLException.getMessage();
        }
        catch (IOException ioException) {
            // An error occurred during the reading process.
            jsonResult = ioException.getMessage();
        }
        finally
        {
            // Close the reader and the underlying objects.
            try
            {
                if (reader != null)
                {
                    reader.close();
                }
            }
            catch (IOException ioException) {
                jsonResult += ioException.getMessage();
            }
        }

        return jsonResult;
    }

    public void getCardWithVolley(String query) {
        // Define URL to use.
        // ---- Remember to add the following permission to the AndroidManifest.xml file
        //      <uses-permission android:name="android.permission.INTERNET" />
        // Call the PokeAPI and receive a JSON String.
        String url = "https://api.pokemontcg.io/v2/cards?=name:" + query;
        // test with fixed URL
        url = "https://api.pokemontcg.io/v2/cards/xy1-1";
        Log.d("Brain Fart","Get card using url =  "+url );

        //String json = callApi(url);
        // Create a Volley web request to receive back a JSON object.
        // This requires two listeners for Async response, onResponse() and onErrorResponse()

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Log.d("Brain Fart","Received Response ");
                        String jsonFact= response.toString();
                        Log.d("Brain Fart","JSON =  "+jsonFact);

                        // Remember to add the following to the module build.gradle file for the gson library for parsing json files
                        // implementation 'com.google.code.gson:gson:2.10.1'
                        Gson gson = new Gson();
                        ApiDataWrapper data = gson.fromJson(jsonFact, ApiDataWrapper.class);
                        //Card newCard = gson.fromJson(jsonFact, Card.class);
                        Card newCard = data.data;
                        Log.d("Brain Fart","Card read with name =  "+newCard.getName() );
                        Log.d("Brain Fart","Card read with name =  "+newCard.getId() );

                        retrievedCard = newCard;
                        cardHasBeenRetrieved = true;
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("Brain Fart","error from JSON Request. VolleyError =  "+error.toString() );
                    }
                });

        // Create a RequestQueue used to send web requests using Volley
        RequestQueue queue = Volley.newRequestQueue(activityContext);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(jsonObjectRequest);
    }

    public void getCardArrayWithVolley(String query) {
        String url = "https://api.pokemontcg.io/v2/cards?=name:" + query;
        JsonArrayRequest jsonArrayRequest  = new JsonArrayRequest (Request.Method.GET, url,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            String jsonFact= response.toString();
                            Log.d("Brain Fart","JSON =  "+jsonFact);
                            Gson gson = new Gson();
                            ApiArrayWrapper dataArray = gson.fromJson(jsonFact, ApiArrayWrapper.class);
                            Log.d("Brain Fart","Number of cards received =  "+dataArray.data.size());
                            // Loop through the array elements
                            for (Card newCard :dataArray.data) {
                                // Remember to add the following to the module build.gradle file for the gson library for parsing json files
                                // implementation 'com.google.code.gson:gson:2.10.1'//Card newCard = gson.fromJson(jsonFact, Card.class);
                                Log.d("Brain Fart","Card read with name =  "+newCard.getName()+" and id =  "+newCard.getId() );
                                retrievedCard = newCard;
                            }
                            cardHasBeenRetrieved = true;
                        } catch (Exception e) {
                            Log.d("Brain Fart", "In getCardArrayWithVolley -- JSONException = "+e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("CIS 3334", "In getSpaceNews -- onErrorResponse = "+error);

                    }
                });

        RequestQueue queue = Volley.newRequestQueue(activityContext);
        // need this for timeout issue
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonArrayRequest);
    }



}
