package CIS3334.deckbuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * PokeApi class to call the Pokemon TCG API and retrieve card data
 */
public class PokeApi {

    private Gson gson;

    public PokeApi()
    {
        gson = new Gson();
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

}
