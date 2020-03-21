package net.simplifiedcoding.moengagenews.data.repository;

import android.os.AsyncTask;
import android.util.Log;

import net.simplifiedcoding.moengagenews.data.network.HTTPRequestHandler;

import java.io.IOException;
import java.lang.ref.WeakReference;

public class ArticleContentDownloader extends AsyncTask<Void, Void, String> {

    private String articleTitle;
    private String articleURL;
    private final WeakReference<DownloadCallback> listener;

    public ArticleContentDownloader(String articleTitle, String articleURL, DownloadCallback listener) {
        this.listener = new WeakReference<>(listener);
        this.articleURL = articleURL;
        this.articleTitle = articleTitle;
    }

    @Override
    protected void onPreExecute() {
        final DownloadCallback listener = this.listener.get();
        if (listener != null) {
            listener.onDownloadStarted();
        }
    }

    @Override
    protected void onPostExecute(String s) {
        final DownloadCallback listener = this.listener.get();
        if (listener == null) return;
        listener.onDownloaded(articleTitle, s);
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            String url = "https" + articleURL.substring(4);
            Log.e("URLX", url);
            return HTTPRequestHandler.get(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public interface DownloadCallback {
        void onDownloadStarted();

        void onDownloaded(String articleTitle, String article);
    }
}
