package com.rahmanarif.footballclubschedule.fragment

import  android.content.Context
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
import com.google.gson.Gson
import com.rahmanarif.footballclubschedule.R
import com.rahmanarif.footballclubschedule.adapter.EventListAdapter
import com.rahmanarif.footballclubschedule.api.ApiRepository
import com.rahmanarif.footballclubschedule.main.DetailActivity
import com.rahmanarif.footballclubschedule.main.FragmentView
import com.rahmanarif.footballclubschedule.model.Events
import com.rahmanarif.footballclubschedule.presenter.FragmentPresenter
import com.rahmanarif.footballclubschedule.util.invisible
import com.rahmanarif.footballclubschedule.util.visible
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.onRefresh


class NextFragment : Fragment(), FragmentView {
    private var events: MutableList<Events> = mutableListOf()
    private lateinit var presenter: FragmentPresenter
    private lateinit var eventList: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var adapter: EventListAdapter

    private var listener: OnFragmentInteractionListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView: View = inflater.inflate(R.layout.fragment_next, container, false)
        eventList = rootView.findViewById(R.id.nextEventMatch) as RecyclerView
        eventList.layoutManager = LinearLayoutManager(activity)

        adapter = EventListAdapter(events) {
            activity?.startActivity<DetailActivity>("idEvent" to "${it.eventId}")
        }
        eventList.adapter = adapter
        progressBar = rootView.findViewById(R.id.progressBar) as ProgressBar
        swipeRefresh = rootView.findViewById(R.id.swipeRefresh) as SwipeRefreshLayout

        val request = ApiRepository()
        val gson = Gson()
        presenter = FragmentPresenter(this, request, gson)

        presenter.getNextEventsList()

        swipeRefresh.onRefresh {
            presenter.getNextEventsList()
        }

        return rootView
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

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showPastEvents(data: List<Events>) {
        swipeRefresh.isRefreshing = false
        events.clear()
        events.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun showNextEvents(data: List<Events>) {
        swipeRefresh.isRefreshing = false
        events.clear()
        events.addAll(data)
        adapter.notifyDataSetChanged()
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }
}
