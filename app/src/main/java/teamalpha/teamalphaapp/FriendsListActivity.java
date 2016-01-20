package teamalpha.teamalphaapp;


import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class FriendsListActivity extends AppCompatActivity {
    ArrayList<Object[]> friends;
    RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);
        super.onStart();
        queue = Volley.newRequestQueue(this);
        friends = new ArrayList<Object[]>();
        Log.i("request", "started");
        final Context context = this;
        String url = getString(R.string.backendIP) + "/get-friends?token=" + GoogleLoginActivity.acct.getIdToken();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Friends", response);
                        try {
                            JSONArray JSONResponse = new JSONArray(response);
                            for(int i = 0; i<JSONResponse.length();i++){
                                friends.add(new Object[] {JSONResponse.get(i)});
                            }
                            ProfileRowAdapter adapter = new ProfileRowAdapter(context, R.layout.profile_row,friends);
                            ListView listView = (ListView)findViewById(R.id.listView);
                            listView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Request",error.toString());
            }
        });
        queue.add(stringRequest);

    }

    public void updateList(ArrayList<Object[]> newList){
        friends = newList;
    }
}


