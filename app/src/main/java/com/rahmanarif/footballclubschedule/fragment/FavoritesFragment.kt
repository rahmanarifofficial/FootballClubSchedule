package com.rahmanarif.footballclubschedule.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.rahmanarif.footballclubschedule.R
import com.rahmanarif.footballclubschedule.adapter.FavoriteListAdapter
import com.rahmanarif.footballclubschedule.db.Favorite
import com.rahmanarif.footballclubschedule.db.database
import com.rahmanarif.footballclubschedule.main.DetailActivity
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.startActivity

class FavoritesFragment : Fragment() {
    private var favorites: MutableList<Favorite> = mutableListOf()
    private lateinit var favoriteList: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var adapter: FavoriteListAdapter

    private var listener: OnFragmentInteractionListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView:View = inflater.inflate(R.layout.fragment_favorites, container, false)
        favoriteList = rootView.findViewById(R.id.favorite_match) as RecyclerView
        favoriteList.layoutManager = LinearLayoutManager(activity)

        adapter = FavoriteListAdapter(favorites) {
            activity?.startActivity<DetailActivity>("idEvent" to "${it.eventId}")
        }

        favoriteList.adapter = adapter
        showFavorite()
        return rootView
    }

    private fun showFavorite(){
        context?.database?.use {
            val result = select(Favorite.TABLE_FAVORITE)
            val favorite = result.parseList(classParser<Favorite>())
            favorites.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }
}
