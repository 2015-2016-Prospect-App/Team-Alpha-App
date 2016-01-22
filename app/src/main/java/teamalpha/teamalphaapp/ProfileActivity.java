package teamalpha.teamalphaapp;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;

public class ProfileActivity extends AppCompatActivity {

    private RequestQueue queue;
    private final String TAG = "Profile";
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        super.onStart();
        final Context context = this;
        queue = Volley.newRequestQueue(context);
        Intent i = getIntent();
        String userID = i.getStringExtra("userID").toString();
        Log.i("userID", userID);
        String url = getString(R.string.backendIP)+ "/get-user?id=" + userID;
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
                            name.setText("Name: " + JSONResponse.getString("name"));
                            username = (String)JSONResponse.get("name");
                            skill.setText("Skill: " + JSONResponse.getString("skillNumber"));
                            games.setText("Games Played: " + JSONResponse.getString("games"));
                            wins.setText("Wins: " + JSONResponse.getString("wins"));
                            JSONArray JSONcomments = JSONResponse.getJSONArray("comments");
                            Log.i(TAG, "JSONcomments length " + JSONcomments.length());
                            for(int i = 0; i<JSONcomments.length();i++){
                                JSONArray JSONcomment = JSONcomments.getJSONArray(i); //what a terrible variable name :(
                                comments.add(new String[]{JSONcomment.get(0).toString(), JSONcomment.get(1).toString()});

                            }
                            CommentStringAdapter adapter = new CommentStringAdapter(context,R.layout.comment_row,comments);
                            ListView commentList = (ListView)findViewById(R.id.commentList);
                            commentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    TextView commentName = (TextView)view.findViewById(R.id.commentName);
                                    Log.i("Comment", commentName.getText().toString());
                                    String url = getString(R.string.backendIP) + "/get-id-from-name?name=" + commentName.getText().toString();
                                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                                            new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    String userID = response;
                                                    Intent i = new Intent(ProfileActivity.this,ProfileActivity.class).putExtra("userID",userID);
                                                    startActivity(i);
                                                }
                                            }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Log.i("Request",error.toString());
                                        }
                                    });
                                    queue.add(stringRequest);
                                }
                            });
                            commentList.setAdapter(adapter);

                            String url = getString(R.string.backendIP)+ "/valid-friend?token=" + GoogleLoginActivity.acct.getIdToken() + "&friendName=" + username;
                            StringRequest checkFriendRequest = new StringRequest(Request.Method.GET, url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            boolean validFriend = Boolean.parseBoolean(response);
                                            if(!validFriend){
                                                Button addFriend = (Button)findViewById(R.id.addFriend);
                                                addFriend.setVisibility(View.GONE);
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.i("Request",error.toString());
                                }
                            });
                            queue.add(checkFriendRequest);

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
    public void addFriend(View v){

        Button button = (Button) findViewById(R.id.addFriend);
        Animation animSlide = AnimationUtils.loadAnimation(this, R.anim.slide_out);
        animSlide.setFillAfter(true);
        button.startAnimation(animSlide);
        button.setClickable(false);
        String url = getString(R.string.backendIP)+ "/add-friend?token=" + GoogleLoginActivity.acct.getIdToken() + "&friendName=" + username;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Friend","Friend add");
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
