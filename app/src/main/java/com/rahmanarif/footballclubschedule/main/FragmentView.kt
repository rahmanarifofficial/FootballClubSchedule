package com.rahmanarif.footballclubschedule.main

import com.rahmanarif.footballclubschedule.model.Events

interface FragmentView {
    fun showLoading()
    fun hideLoading()
    fun showPastEvents(data: List<Events>)
    fun showNextEvents(data: List<Events>)
}