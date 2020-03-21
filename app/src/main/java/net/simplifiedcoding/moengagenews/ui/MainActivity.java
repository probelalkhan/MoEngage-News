package net.simplifiedcoding.moengagenews.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import net.simplifiedcoding.moengagenews.R;
import net.simplifiedcoding.moengagenews.data.models.Article;
import net.simplifiedcoding.moengagenews.data.repository.ArticlesRepository;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ArticlesRepository.ArticlesCallback, RecyclerViewItemClickListener<Article> {

    private ProgressBar progressBar;
    private ArticlesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_articles);
        progressBar = findViewById(R.id.progress_bar);

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
}
