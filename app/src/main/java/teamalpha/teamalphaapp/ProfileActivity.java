package teamalpha.teamalphaapp;

import android.app.DialogFragment;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {

    private RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        super.onStart();
        final Context context = this;
        queue = Volley.newRequestQueue(context);
        String url = getString(R.string.backendIP)+ "/get-id?token=" + GoogleLoginActivity.acct.getIdToken();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        queue = Volley.newRequestQueue(context);
                        String url = getString(R.string.backendIP)+ "/get-user?id=" + response;
                        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        TextView name = (TextView)findViewById(R.id.name);
                                        TextView skill = (TextView)findViewById(R.id.skill);
                                        TextView games = (TextView)findViewById(R.id.games);
                                        TextView wins = (TextView)findViewById(R.id.wins);
                                        try {
                                            JSONObject JSONResponse = new JSONObject(response);
                                            name.setText(JSONResponse.getString("name"));
                                            skill.setText(JSONResponse.getString("skillNumber"));
                                            games.setText(JSONResponse.getString("games"));
                                            wins.setText(JSONResponse.getString("wins"));

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
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Request",error.toString());
            }
        });
        queue.add(stringRequest);
    }
}
