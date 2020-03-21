package net.simplifiedcoding.moengagenews.ui;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.simplifiedcoding.moengagenews.R;
import net.simplifiedcoding.moengagenews.data.models.Article;
import net.simplifiedcoding.moengagenews.data.utils.ImageLoader;

import java.util.List;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ArticleViewHolder> {

    private List<Article> articles;
    private RecyclerViewItemClickListener<Article> listener;

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ArticleViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recycler_view_article, parent, false)
        );
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, final int position) {
        holder.textViewTitle.setText(articles.get(position).getTitle());
        holder.textViewDesc.setText(articles.get(position).getDescription());
        holder.textViewAuthorAndDate.setText(
                articles.get(position).getAuthor() + ", " +
                        Utils.getFormattedDate(articles.get(position).getPublishedAt())
        );
        new ImageLoader(articles.get(position).getUrlToImage(), holder.imageViewThumb).execute();

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ArticlesAdapter.this.listener != null) {
                    ArticlesAdapter.this.listener.onRecyclerViewItemClick(v, articles.get(position));
                }
            }
        };

        holder.layoutArticle.setOnClickListener(listener);
        holder.imageViewDownload.setOnClickListener(listener);
        holder.imageViewShare.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        if (articles == null) return 0;
        else return articles.size();
    }

    void setArticles(List<Article> articles) {
        this.articles = articles;
        notifyDataSetChanged();
    }

    void setRecyclerViewItemClickListener(RecyclerViewItemClickListener<Article> listener) {
        this.listener = listener;
    }

    static class ArticleViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        TextView textViewAuthorAndDate;
        TextView textViewDesc;
        ImageView imageViewThumb;

        ImageView imageViewShare;
        ImageView imageViewDownload;

        LinearLayout layoutArticle;

        ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewAuthorAndDate = itemView.findViewById(R.id.text_view_author_and_date);
            textViewDesc = itemView.findViewById(R.id.text_view_desc);
            imageViewThumb = itemView.findViewById(R.id.image_view_thumb);
            layoutArticle = itemView.findViewById(R.id.layout_article);

            imageViewShare = itemView.findViewById(R.id.image_view_share);
            imageViewDownload = itemView.findViewById(R.id.image_view_download);
        }
    }

}
