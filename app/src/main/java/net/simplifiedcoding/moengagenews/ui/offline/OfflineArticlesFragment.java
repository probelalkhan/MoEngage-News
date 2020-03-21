package net.simplifiedcoding.moengagenews.ui.offline;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.simplifiedcoding.moengagenews.R;
import net.simplifiedcoding.moengagenews.data.db.DatabaseHelper;
import net.simplifiedcoding.moengagenews.data.models.OfflineArticle;
import net.simplifiedcoding.moengagenews.ui.RecyclerViewItemClickListener;


public class OfflineArticlesFragment extends Fragment implements RecyclerViewItemClickListener<OfflineArticle> {

    public static final String KEY_ARTICLE_ID = "key_article_id";

    private RecyclerView recyclerView;
    private OfflineArticlesAdapter adapter;
    private DatabaseHelper db = new DatabaseHelper();

    public OfflineArticlesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offline_articles, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_offline_articles);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new OfflineArticlesAdapter();
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        adapter.setArticles(db.getAllArticleTitles());
        adapter.setRecyclerViewItemClickListener(this);
    }

    @Override
    public void onRecyclerViewItemClick(View view, OfflineArticle item) {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_ARTICLE_ID, item.getId());
        Fragment fragment = new ViewOfflineArticleFragment();
        fragment.setArguments(bundle);
        if (getFragmentManager() != null)
            getFragmentManager().beginTransaction()
                    .replace(R.id.main_container, fragment)
                    .commit();
    }
}
