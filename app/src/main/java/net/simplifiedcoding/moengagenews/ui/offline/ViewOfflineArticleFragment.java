package net.simplifiedcoding.moengagenews.ui.offline;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import net.simplifiedcoding.moengagenews.R;
import net.simplifiedcoding.moengagenews.data.db.DatabaseHelper;
import net.simplifiedcoding.moengagenews.data.models.OfflineArticle;


public class ViewOfflineArticleFragment extends Fragment {

    private DatabaseHelper db = new DatabaseHelper();
    private WebView webView;

    public ViewOfflineArticleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_offline_article, container, false);
        webView = view.findViewById(R.id.web_view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() == null) return;
        int id = getArguments().getInt(OfflineArticlesFragment.KEY_ARTICLE_ID);

        OfflineArticle article = db.getArticle(id);

        webView.loadData(article.getContent(), "text/html", "UTF-8");


        System.out.println(article.getContent());
    }
}
