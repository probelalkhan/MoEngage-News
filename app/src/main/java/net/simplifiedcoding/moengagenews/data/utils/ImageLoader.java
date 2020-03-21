package net.simplifiedcoding.moengagenews.data.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import net.simplifiedcoding.moengagenews.data.models.Article;
import net.simplifiedcoding.moengagenews.data.models.Source;
import net.simplifiedcoding.moengagenews.data.network.ApiException;
import net.simplifiedcoding.moengagenews.data.network.EndPoints;
import net.simplifiedcoding.moengagenews.data.network.HTTPRequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ImageLoader extends AsyncTask<Void, Void, Bitmap> {

    private String imageURL;
    private final WeakReference<ImageView> imageView;

    public ImageLoader(String imageURL, ImageView imageView) {
        this.imageView = new WeakReference<>(imageView);
        this.imageURL = imageURL;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        ImageView imageView = this.imageView.get();
        if (imageView != null && bitmap != null) {
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 525, 300, true);
            imageView.setImageBitmap(scaledBitmap);
        }
    }

    @Override
    protected Bitmap doInBackground(Void... voids) {
        try {
            URL url = new URL(imageURL);
            return BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
