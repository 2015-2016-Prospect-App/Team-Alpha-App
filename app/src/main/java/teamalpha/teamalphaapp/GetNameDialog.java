package teamalpha.teamalphaapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by Daniel on 12/23/2015.
 */
public class GetNameDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        setCancelable(false);
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final View dialogView = inflater.inflate(R.layout.dialog_name, null);
        builder.setView(dialogView)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText text = (EditText)dialogView.findViewById(R.id.usernameText);
                        String name = text.getText().toString();
                        RequestQueue queue = Volley.newRequestQueue(getActivity());
                        Log.i("Dialog", "Name is " + name);
                        String url = getString(R.string.backendIP)+ "/add-user?token=" + GoogleLoginActivity.acct.getIdToken() + "&name=" + name;
                        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.i("Dialog", response);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.i("Dialog", error.toString());
                                    }
                                });
                        queue.add(stringRequest);
                    }
                });

        return builder.create();
    }
}
