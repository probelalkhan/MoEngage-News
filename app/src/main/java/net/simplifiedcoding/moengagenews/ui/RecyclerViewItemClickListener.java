package net.simplifiedcoding.moengagenews.ui;

import android.view.View;

public interface RecyclerViewItemClickListener<T> {
    void onRecyclerViewItemClick(View view, T item);
}
