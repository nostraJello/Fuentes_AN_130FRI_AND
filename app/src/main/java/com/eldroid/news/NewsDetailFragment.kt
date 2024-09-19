package com.eldroid.news

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class NewsDetailFragment : Fragment() {

    companion object {
        private const val ARG_TITLE = "news_title"
        private const val ARG_CONTENT = "news_content"

        fun newInstance(title: String, content: String): NewsDetailFragment {
            val fragment = NewsDetailFragment()
            val args = Bundle()
            args.putString(ARG_TITLE, title)
            args.putString(ARG_CONTENT, content)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val title = arguments?.getString(ARG_TITLE)
        val content = arguments?.getString(ARG_CONTENT)
        val titleTextView = view.findViewById<TextView>(R.id.newsTitle)
        val contentTextView = view.findViewById<TextView>(R.id.newsContent)

        // Check the orientation
        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape, apply smaller text sizes
            titleTextView.textSize = 25f // Set a smaller size
            contentTextView.textSize = 13f
        } else {
            // In portrait, apply default text sizes
            titleTextView.textSize = 30f // Larger size for portrait
            contentTextView.textSize = 16f
        }

        view.findViewById<TextView>(R.id.newsTitle).text = title
        view.findViewById<TextView>(R.id.newsContent).text = content
    }
}

