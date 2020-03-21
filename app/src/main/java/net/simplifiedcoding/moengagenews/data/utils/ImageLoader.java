package net.simplifiedcoding.moengagenews.data.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;

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
