package com.pareksha.myproject;


import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

import static com.pareksha.myproject.R.id.text;
import static com.pareksha.myproject.R.id.textView;
import static com.pareksha.myproject.R.layout.activity_screen_one;


public class ScreenOne extends AppCompatActivity {
    private static final String TAG = ScreenOne.class.getSimpleName();

    private MaterialSearchView searchView;
    String roll_number;
    String URL = "http://pu-uiet.atspace.cc/waitinglist.php?roll_no=";
    TextView textView, textView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "testingOncreateCalled");
        super.onCreate(savedInstanceState);
        setContentView(activity_screen_one);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        textView = (TextView) findViewById(R.id.textView);
        textView1 = (TextView) findViewById(R.id.textView1);

        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setVoiceSearch(false);
        searchView.setCursorDrawable(R.drawable.custom_cursor);
        searchView.setEllipsize(true);
        searchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do some magic
                roll_number = query.toUpperCase();
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL + roll_number, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String waiting = null;
//
                            try {
                                waiting = response.getString("waiting");
                            } catch (JSONException e) {

                                e.printStackTrace();

                            }
                            if(waiting.equals("No Information Available")){
                                Toast.makeText(ScreenOne.this, "Roll Number not found", Toast.LENGTH_LONG).show();
                            }else {
//

                                textView1.setText(roll_number);
                                textView.setText(waiting);
                                Log.v("VolleyResponse", response.toString());
                            }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        textView.setText(error.toString());
                        Log.v("VolleyResponse", error.toString());

                    }
                });
                RequestQueue requestQueue = Volley.newRequestQueue(ScreenOne.this);
                requestQueue.add(jsonObjectRequest);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        Log.v(TAG, "testingCreateOptionsMenuCalled");

        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        Log.v("testingMenuItemValue: ", item.toString());
        searchView.setMenuItem(item);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.v(TAG, "testingOptionsMenuCalled");

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    searchView.setQuery(searchWrd, false);
                }
            }

            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}