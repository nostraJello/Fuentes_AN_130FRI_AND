package com.eldroid.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NewsListFragment : Fragment() {
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)

        val newsList = listOf(
            NewsItem(
                title = getString(R.string.news_1_title),
                content = getString(R.string.news_1_content)
            ),
            NewsItem(
                title = getString(R.string.news_2_title),
                content = getString(R.string.news_2_content)
            ),
            NewsItem(
                title = getString(R.string.news_3_title),
                content = getString(R.string.news_3_content)
            ),
            NewsItem(
                title = getString(R.string.news_4_title),
                content = getString(R.string.news_4_content)
            ),
            NewsItem(
                title = getString(R.string.news_5_title),
                content = getString(R.string.news_5_content)
            )
        )

        newsAdapter = NewsAdapter(newsList) { newsItem ->
            (activity as MainActivity).showNewsDetails(newsItem.title, newsItem.content)
        }
        recyclerView.adapter = newsAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)
    }
}
