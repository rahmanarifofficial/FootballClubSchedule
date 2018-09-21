package com.rahmanarif.footballclubschedule.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.gson.Gson
import com.rahmanarif.footballclubschedule.R
import com.rahmanarif.footballclubschedule.api.ApiRepository
import com.rahmanarif.footballclubschedule.model.Team
import com.rahmanarif.footballclubschedule.presenter.DetailPresenter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity(), DetailView {
    private var teams: MutableList<Team> = mutableListOf()
    private var idHomeTeam: String = ""
    private var idAwayTeam: String = ""
    private lateinit var presenter: DetailPresenter

    override fun showTeam(data: Team) {
        when(data.teamId) {
            idHomeTeam -> {
                Picasso.get().load(data.teamBadge).into(homeBadge)
                homeTeam.text = data.teamName
            }
            idAwayTeam -> {
                Picasso.get().load(data.teamBadge).into(awayBadge)
                awayTeam.text = data.teamName
            }
        }
        vs.text = "vs"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        //set toolbar
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val intent = intent
        idHomeTeam = intent.getStringExtra("idHomeTeam")
        idAwayTeam = intent.getStringExtra("idAwayTeam")

        val request = ApiRepository()
        val gson = Gson()
        presenter = DetailPresenter(this, request, gson)


        presenter.getTeam(idHomeTeam)
        presenter.getTeam(idAwayTeam)

    }
}
