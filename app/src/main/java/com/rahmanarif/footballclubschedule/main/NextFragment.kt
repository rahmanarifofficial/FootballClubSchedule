package com.rahmanarif.footballclubschedule.main

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.rahmanarif.footballclubschedule.R
import com.rahmanarif.footballclubschedule.api.ApiRepository
import com.rahmanarif.footballclubschedule.model.Events


class NextFragment : Fragment(), FragmentView {
    // TODO: Rename and change types of parameters
    private var events: MutableList<Events> = mutableListOf()
    private lateinit var presenter: FragmentPresenter
    private lateinit var eventList: RecyclerView

    private var listener: OnFragmentInteractionListener? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView: View = inflater.inflate(R.layout.fragment_next, container, false)
        eventList = rootView.findViewById(R.id.nextEventMatch) as RecyclerView
        eventList.layoutManager = LinearLayoutManager(activity)
        eventList.adapter = EventListAdapter(events)

        val request = ApiRepository()
        val gson = Gson()
        presenter = FragmentPresenter(this, request, gson)

        presenter.getNextEventsList()

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
//        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
//        progressBar.visibility = View.INVISIBLE
    }

    override fun showPastEvents(data: List<Events>) {
//        swipeRefresh.isRefreshing = false
//        events.clear()
//        events.addAll(data)
//        adapter.notifyDataSetChanged()
    }

    override fun showNextEvents(data: List<Events>) {
//        swipeRefresh.isRefreshing = false
        events.clear()
        events.addAll(data)
//        adapter.notifyDataSetChanged()
    }

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }
}
