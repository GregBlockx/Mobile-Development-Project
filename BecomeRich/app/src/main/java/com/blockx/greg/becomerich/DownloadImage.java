package com.blockx.greg.becomerich;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Bryan on 20/10/2017.
 * Deze klasse was bedoelt om je facebook profielfoto in te laden.
 * Maar door de verschillende redirects bij de URL kreeg ik het niet werkende.
 */

public class DownloadImage extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

    public DownloadImage(ImageView bmImage){
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls){
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = 2;
        try{
            HttpURLConnection con = (HttpURLConnection) (new URL(urldisplay).openConnection());
            con.setInstanceFollowRedirects(true);
            con.connect();
            InputStream in = con.getInputStream();//new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in,null,opts);
        }catch (Exception e){
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result){
        bmImage.setImageBitmap(result);
    }

}
