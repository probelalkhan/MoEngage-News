package net.simplifiedcoding.moengagenews.ui.news;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import net.simplifiedcoding.moengagenews.R;
import net.simplifiedcoding.moengagenews.data.db.DatabaseHelper;
import net.simplifiedcoding.moengagenews.data.models.Article;
import net.simplifiedcoding.moengagenews.data.repository.ArticleContentDownloader;
import net.simplifiedcoding.moengagenews.data.repository.ArticlesRepository;
import net.simplifiedcoding.moengagenews.ui.RecyclerViewItemClickListener;
import net.simplifiedcoding.moengagenews.ui.Utils;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment implements ArticlesRepository.ArticlesCallback,
        RecyclerViewItemClickListener<Article>, ArticleContentDownloader.DownloadCallback {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ArticlesAdapter adapter;

    private DatabaseHelper db = new DatabaseHelper();

    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_articles);
        progressBar = view.findViewById(R.id.progress_bar);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArticlesRepository repository = new ArticlesRepository(this);
        repository.execute();
        adapter = new ArticlesAdapter();
        adapter.setRecyclerViewItemClickListener(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onArticlesFetchStarted() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onArticlesFetchError(String message) {
        progressBar.setVisibility(View.INVISIBLE);
        Utils.toast(message);
    }

    @Override
    public void onArticlesFetched(List<Article> articles) {
        progressBar.setVisibility(View.INVISIBLE);
        adapter.setArticles(articles);
    }

    @Override
    public void onRecyclerViewItemClick(View view, Article item) {
        switch (view.getId()) {
            case R.id.image_view_download:
                new ArticleContentDownloader(item.getTitle(), item.getUrl(), this).execute();
                break;
            case R.id.image_view_share:
                shareURL(item.getUrl());
                break;
            case R.id.layout_article:
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(item.getUrl()));
                startActivity(i);
                break;
        }
    }

    private void shareURL(String url) {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        share.putExtra(Intent.EXTRA_TEXT, url);
        startActivity(Intent.createChooser(share, "Share Article..."));
    }

    @Override
    public void onDownloadStarted() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDownloaded(String articleTitle, String article) {
        progressBar.setVisibility(View.INVISIBLE);
        if (db.addArticle(articleTitle, article)) {
            Utils.toast("Article Saved Offline");
        } else {
            Utils.toast("Something Went Wrong");
        }
    }
}
