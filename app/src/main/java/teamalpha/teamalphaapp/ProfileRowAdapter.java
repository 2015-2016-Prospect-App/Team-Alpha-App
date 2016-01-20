package teamalpha.teamalphaapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;

import org.w3c.dom.Text;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Daniel on 1/19/2016.
 */
public class ProfileRowAdapter extends ArrayAdapter {
    private int resource;
    private Context context;
    private ArrayList<Object[]> values;

    String[] strings = null;
    public ProfileRowAdapter(Context context, int resource, ArrayList objects) {
        super(context, resource, objects);
        this.context = context;
        this.values = objects;
        this.resource = resource;
    }



    public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater inflater = LayoutInflater.from(context);
        View rowView = inflater.inflate(resource, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.nameView);
//        ImageView imageView = (ImageView) rowView.findViewById(R.id.profileView);
        textView.setText((String)values.get(position)[0]);
//        imageView.setImageBitmap((Bitmap)values.get(position)[1]);
        return rowView;
    }
}
