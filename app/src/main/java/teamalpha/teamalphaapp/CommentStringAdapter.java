package teamalpha.teamalphaapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.Response;

import java.util.ArrayList;

/**
 * Created by Daniel on 1/1/2016.
 */
public class CommentStringAdapter extends ArrayAdapter {
    private int resource;
    private Context context;
    private ArrayList<String[]> values;

    String[] strings = null;
    public CommentStringAdapter(Context context, int resource, ArrayList objects) {
        super(context, resource, objects);
        this.context = context;
        this.values = objects;
        this.resource = resource;
    }



    public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater inflater = LayoutInflater.from(context);
        View rowView = inflater.inflate(resource, parent, false);

        TextView name = (TextView)rowView.findViewById(R.id.commentName);
        TextView text =(TextView)rowView.findViewById(R.id.commentText);
        name.setText(values.get(position)[0]);
        text.setText(values.get(position)[1]);
        return rowView;

    }
}
