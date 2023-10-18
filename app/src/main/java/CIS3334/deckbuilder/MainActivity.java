package CIS3334.deckbuilder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerViewSearchResults;
    ResultAdapter resultsAdapter;
    MainViewModel mainViewModel;
    EditText editTextSearch;
    Button buttonViewInventory;
    Button buttonSearch;
    TextView textViewTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextSearch = findViewById(R.id.editTextSearch);
        buttonViewInventory = findViewById(R.id.buttonViewInventory);
        //buttonSearch = findViewById(R.id.buttonSearch);
        textViewTest = findViewById(R.id.textViewTest);

        setupButtonSearch();
        //setupRecyclerViewResults();

    }

    private void setupButtonSearch() {
        buttonSearch = findViewById(R.id.buttonSearch);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Brain Fart", "Button is clicked");
                String query = editTextSearch.getText().toString();
                Log.d("Brain Fart", "Text of query fetched");
                //textViewTest.setText(query);
                try {
                    Card card = new PokeApi().getCard(query);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("Brain Fart", "Query failed to call.");
                }
                Log.d("Brain Fart", "Query called");
                //textViewTest.setText(card.getName());
            }
        });
    }

    /*
    private void setupRecyclerViewResults() {
        recyclerViewSearchResults = findViewById(R.id.recyclerViewSearchResults);
        resultsAdapter = new ResultAdapter(getApplication(),mainViewModel);
        recyclerViewSearchResults.setAdapter(resultsAdapter);
        recyclerViewSearchResults.setLayoutManager(new LinearLayoutManager(this));
    }
     */
}