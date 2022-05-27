package com.example.first.news;
/**
 * 加载news_content_frag布局
 */

import android.os.Bundle;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.first.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class NewsContentFragment extends Fragment {
    private View view;

    /**
     * 为碎片创建视图（加载布局时调用onCreateView()）
     *加载news_content_frag布局
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // return super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.news_content_frag, container, false);
        return view;
    }

    public void refresh(String newTitle, String newConstent) {
        View visibilityLayout = view.findViewById(R.id.visibility_layout);//获取news_content_frag.xml 的线性布局
        visibilityLayout.setVisibility(View.VISIBLE);
        TextView newT = (TextView) view.findViewById(R.id.news_title);
        TextView newC = (TextView) view.findViewById(R.id.news_content);
        //刷新新闻
        newT.setText(newTitle);
        newC.setText(newConstent);

    }

}
