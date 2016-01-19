package teamalpha.teamalphaapp;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MenuActivity extends AppCompatActivity {

    private RequestQueue queue;
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        queue = Volley.newRequestQueue(this);
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.i("request", "started");
        String url = getString(R.string.backendIP) + "/user-exists?token=" + GoogleLoginActivity.acct.getIdToken();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Boolean exists = Boolean.parseBoolean(response);
                        if(!exists){
                            DialogFragment newFragment = new GetNameDialog();
                            newFragment.show(getFragmentManager(),"name");
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

    public void openFindAMatch(View v) {
        startActivity(new Intent(MenuActivity.this, MapsActivity.class));
    }
    public void openFriendsList(View v) {
        startActivity(new Intent(MenuActivity.this, FriendsListActivity.class));
    }
    public void openProfile(View v){

        String url = getString(R.string.backendIP) + "/get-id?token=" + GoogleLoginActivity.acct.getIdToken();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        userID = response;
                        Intent i = new Intent(MenuActivity.this,ProfileActivity.class).putExtra("userID",userID);
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
    public void openSearch(View v){
        startActivity(new Intent(MenuActivity.this,SearchActivity.class));
    }

}
