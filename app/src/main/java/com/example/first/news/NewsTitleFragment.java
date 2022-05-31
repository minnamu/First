package com.example.first.news;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.first.NewsContentActivity;
import com.example.first.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NewsTitleFragment extends Fragment {
    private boolean isTwoPane;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //根据是否能找到这个view来判断是否是双页模式
//        if (getActivity().findViewById(R.id.news_content_layout) != null) {
//            isTwoPane = true;
//        } else {
//            isTwoPane = false;
//        }
    }

    /**
     * 新建内部类adapter，展示新闻列表
     * 内部类可以直接访问变量isTwoPane
     */
    class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
        private List<News> mNewsList;

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView newsTitleText;

            public ViewHolder(View view) {
                super(view);
                newsTitleText = (TextView) view.findViewById(R.id.news_title);

            }
        }

        public NewsAdapter(List<News> newsList) {
            mNewsList = newsList;
        }

        @NonNull
        @Override
        public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
            final ViewHolder holder = new ViewHolder(view);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    News news = mNewsList.get(holder.getAdapterPosition());
//                    if (isTwoPane) {
//                        //双页模式
//                        NewsContentFragment newsContentFragment = (NewsContentFragment) getFragmentManager().findFragmentById(R.id.news_content_fragment);
//                        newsContentFragment.refresh(news.getTitle(), news.getContent());
//                    } else {
//                        //单页模式，直接启动NewsContentActivity
//                        NewsContentActivity.actionStart(getActivity(), news.getTitle(), news.getContent());
//
//                    }
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder holder, int position) {
            News news = mNewsList.get(position);
            holder.newsTitleText.setText(news.getTitle());
        }

        @Override
        public int getItemCount() {
            return mNewsList.size();
        }

        /**
         * 在其中使用adapter填充数据
         *
         * @param inflater
         * @param container
         * @param savedInstanceState
         * @return
         */
        @Nullable
        //@Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            //
            View view = inflater.inflate(R.layout.news_title_frag, container, false);


            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.news_title_recycler_view);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(linearLayoutManager);
            NewsAdapter newsAdapter = new NewsAdapter(getNews());
            recyclerView.setAdapter(newsAdapter);
            return view;

        }

        private List<News> getNews() {
            List<News> list = new ArrayList<>();
            for (int i = 0; i < 50; i++) {
                News news = new News();
                news.setTitle("新闻标题 " + i);
                news.setContent(getRandomLength("新闻内容" + i));
                mNewsList.add(news);

            }
            return mNewsList;
        }

        //随机
        private String getRandomLength(String context) {
            Random random = new Random();
            int length = random.nextInt(20) + 1;
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < length; i++) {
                stringBuilder.append(context);
            }
            return stringBuilder.toString();
        }


    }
}
