package teamalpha.teamalphaapp;

import android.app.DialogFragment;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class ProfileActivity extends AppCompatActivity {

    private RequestQueue queue;
    private final String TAG = "Profile";


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
                                        ArrayList<String[]> comments = new ArrayList<String[]>();
                                        try {
                                            JSONObject JSONResponse = new JSONObject(response);
                                            name.setText(JSONResponse.getString("name"));
                                            skill.setText(JSONResponse.getString("skillNumber"));
                                            games.setText(JSONResponse.getString("games"));
                                            wins.setText(JSONResponse.getString("wins"));
                                            JSONArray JSONcomments = JSONResponse.getJSONArray("comments");
                                            Log.i(TAG, "JSONcomments length " + JSONcomments.length());
                                            for(int i = 0; i<JSONcomments.length();i++){
                                                JSONArray JSONcomment = JSONcomments.getJSONArray(i); //what a terrible variable name :(
                                                comments.add(new String[]{JSONcomment.get(0).toString(), JSONcomment.get(1).toString()});

                                            }
                                            CommentStringAdapter adapter = new CommentStringAdapter(context,R.layout.comment_row,comments);
                                            ListView commentList = (ListView)findViewById(R.id.commentList);
                                            commentList.setAdapter(adapter);

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
