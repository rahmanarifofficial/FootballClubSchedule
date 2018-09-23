package com.rahmanarif.footballclubschedule.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.gson.Gson
import com.rahmanarif.footballclubschedule.R
import com.rahmanarif.footballclubschedule.api.ApiRepository
import com.rahmanarif.footballclubschedule.model.DetailEvent
import com.rahmanarif.footballclubschedule.model.Team
import com.rahmanarif.footballclubschedule.presenter.DetailPresenter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity(), DetailView {
    private var idEvent: String = ""
    private var idHomeTeam: String = ""
    private var idAwayTeam: String = ""
    private lateinit var presenter: DetailPresenter

    override fun getDetailEvent(data: DetailEvent) {
        idHomeTeam = data.homeTeamId.toString()
        idAwayTeam = data.awayTeamId.toString()
        Log.d("idHome", idHomeTeam)
        val team = Team(idHomeTeam, idAwayTeam)
        when(data.eventId){
            idEvent -> {
                eventName.text = data.event
                leagueName.text = data.league
                homeTeam.text = data.homeTeam
                awayTeam.text = data.awayTeam
                homeScore.text = data.homeScore
                awayScore.text = data.awayScore
                goalHomeDetail.text = data.homeGoalDetail
                goalAwayDetail.text = data.awayGoalDetail
                homeRedCardDetail.text = data.homeRedCards
                awayRedCardDetail.text = data.awayRedCards
                homeYellowCardDetail.text = data.homeYellowCards
                awayYellowCardDetail.text = data.awayYellowCards
                vs.text = "VS"
            }
        }
        presenter.getTeam(idHomeTeam)
        presenter.getTeam(idAwayTeam)
    }

    override fun showTeam(data: Team) {
        Log.d("idHome2", idHomeTeam)
        when(data.teamId) {
            idHomeTeam -> {
                Picasso.get().load(data.teamBadge).into(homeBadge)
            }
            idAwayTeam -> {
                Picasso.get().load(data.teamBadge).into(awayBadge)
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        //set toolbar
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val intent = intent
        idEvent = intent.getStringExtra("idEvent")

        val request = ApiRepository()
        val gson = Gson()
        presenter = DetailPresenter(this, request, gson)

        presenter.getDetailEvent(idEvent)
    }
}
