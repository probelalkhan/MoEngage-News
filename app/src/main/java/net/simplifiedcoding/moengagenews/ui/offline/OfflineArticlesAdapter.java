package net.simplifiedcoding.moengagenews.ui.offline;

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
import net.simplifiedcoding.moengagenews.data.models.OfflineArticle;
import net.simplifiedcoding.moengagenews.data.utils.ImageLoader;
import net.simplifiedcoding.moengagenews.ui.RecyclerViewItemClickListener;
import net.simplifiedcoding.moengagenews.ui.Utils;

import java.util.List;

public class OfflineArticlesAdapter extends RecyclerView.Adapter<OfflineArticlesAdapter.ArticleViewHolder> {

    private List<OfflineArticle> articles;
    private RecyclerViewItemClickListener<OfflineArticle> listener;

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ArticleViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recycler_view_offline_article, parent, false)
        );
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, final int position) {
        holder.textViewTitle.setText(articles.get(position).getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onRecyclerViewItemClick(v, articles.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        if (articles == null) return 0;
        else return articles.size();
    }

    void setArticles(List<OfflineArticle> articles) {
        this.articles = articles;
        notifyDataSetChanged();
    }

    void setRecyclerViewItemClickListener(RecyclerViewItemClickListener<OfflineArticle> listener) {
        this.listener = listener;
    }

    static class ArticleViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;

        ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
        }
    }

}
