package net.simplifiedcoding.moengagenews.data.repository;

import android.os.AsyncTask;

import net.simplifiedcoding.moengagenews.data.models.Article;
import net.simplifiedcoding.moengagenews.data.models.Source;
import net.simplifiedcoding.moengagenews.data.network.EndPoints;
import net.simplifiedcoding.moengagenews.data.network.HTTPRequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class ArticlesRepository extends AsyncTask<Void, Void, String> {

    private final WeakReference<ArticlesCallback> listener;

    public ArticlesRepository(ArticlesCallback listener) {
        this.listener = new WeakReference<>(listener);
    }

    @Override
    protected void onPreExecute() {
        final ArticlesCallback listener = this.listener.get();
        if (listener != null) {
            listener.onArticlesFetchStarted();
        }
    }

    @Override
    protected void onPostExecute(String s) {
        final ArticlesCallback listener = this.listener.get();
        if (listener == null) return;

        if (s == null) {
            listener.onArticlesFetchError("Something went wrong, try later");
            return;
        }

        try {
            JSONObject object = new JSONObject(s);
            if (object.has("articles")) {
                JSONArray articlesJSON = object.getJSONArray("articles");
                List<Article> articles = new ArrayList<>();
                for (int i = 0; i < articlesJSON.length(); i++) {
                    JSONObject articleObj = articlesJSON.getJSONObject(i);
                    JSONObject sourceObj = articleObj.getJSONObject("source");
                    Source source = new Source(sourceObj.getString("id"), sourceObj.getString("name"));
                    Article article = new Article(
                            source,
                            articleObj.getString("author"),
                            articleObj.getString("title"),
                            articleObj.getString("description"),
                            articleObj.getString("url"),
                            articleObj.getString("urlToImage"),
                            articleObj.getString("publishedAt"),
                            articleObj.getString("content")
                    );
                    articles.add(article);
                }
                listener.onArticlesFetched(articles);
            }
        } catch (JSONException e) {
            listener.onArticlesFetchError(e.getMessage());
        }
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            return HTTPRequestHandler.get(EndPoints.ARTICLES);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public interface ArticlesCallback {
        void onArticlesFetchStarted();

        void onArticlesFetchError(String message);

        void onArticlesFetched(List<Article> articles);
    }
}
