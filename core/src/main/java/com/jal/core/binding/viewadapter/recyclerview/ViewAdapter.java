package com.jal.core.binding.viewadapter.recyclerview;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import me.goldze.mvvmhabit.binding.command.BindingCommand;

public class ViewAdapter {
    @BindingAdapter({"horizontalSpace"})
    public static void setHorizontalSpace(RecyclerView recyclerView, final float space) {
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
                if (layoutManager instanceof GridLayoutManager) {
                    int count = ((GridLayoutManager) layoutManager).getSpanCount();
                    if (parent.getChildLayoutPosition(view) % count != 0) {
                        outRect.left = (int) space;
                    }
                }
            }
        });
    }

    @BindingAdapter({"verticalSpace"})
    public static void setVerticalSpace(RecyclerView recyclerView, final float space) {
        if (recyclerView.getItemDecorationCount() > 0) {
            recyclerView.removeItemDecorationAt(0);
        }
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = (int) space;
            }
        }, 0);
    }

    @BindingAdapter({"setSpanCount"})
    public static void setSpanCount(RecyclerView recyclerView, int spanCount) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            ((GridLayoutManager) layoutManager).setSpanCount(spanCount);
        }
    }

    @BindingAdapter({"firstVisibleChange"})
    public static void addOnScrollListener(RecyclerView recyclerView, final BindingCommand<Integer> firstVisibleChange) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    firstVisibleChange.execute(layoutManager.findFirstVisibleItemPosition());
                }
            }
        });
    }

    @BindingAdapter({"scrollToPosition"})
    public static void scrollToPosition(RecyclerView recyclerView, int position) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        layoutManager.scrollToPositionWithOffset(position, 0);
    }
    @BindingAdapter("layoutManager")
    public static void setLayoutManager(RecyclerView recyclerView, RecyclerView.LayoutManager layoutManager) {
        recyclerView.setLayoutManager(layoutManager);
    }
}
