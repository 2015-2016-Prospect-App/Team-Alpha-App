package teamalpha.teamalphaapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Daniel on 1/19/2016.
 */
public class AsyncURLDownload extends AsyncTask<ArrayList<Object[]>,Void,ArrayList<Object[]>>{

    FriendsListActivity activity;

    public AsyncURLDownload(FriendsListActivity activity){
        this.activity = activity;
    }

    @Override
    protected ArrayList<Object[]> doInBackground(ArrayList<Object[]>...arrayLists) {
        for(Object[] objectArray :arrayLists[0]){
            URL tempURL = (URL)objectArray[1];
            try {
                objectArray[1] = BitmapFactory.decodeStream(tempURL.openStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return arrayLists[0];
    }

    @Override
    protected void onPostExecute(ArrayList<Object[]> arrayList) {
        super.onPostExecute(arrayList);
        activity.updateList(arrayList);

    }
}
