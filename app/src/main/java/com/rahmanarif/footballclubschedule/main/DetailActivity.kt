package com.rahmanarif.footballclubschedule.main

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.gson.Gson
import com.rahmanarif.footballclubschedule.R
import com.rahmanarif.footballclubschedule.R.id.add_to_favorite
import com.rahmanarif.footballclubschedule.R.menu.detail_menu
import com.rahmanarif.footballclubschedule.api.ApiRepository
import com.rahmanarif.footballclubschedule.db.Favorite
import com.rahmanarif.footballclubschedule.db.database
import com.rahmanarif.footballclubschedule.model.DetailEvent
import com.rahmanarif.footballclubschedule.model.Team
import com.rahmanarif.footballclubschedule.presenter.DetailPresenter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.toast

class DetailActivity : AppCompatActivity(), DetailView {
    private var idEvent: String = ""
    private var idHomeTeam: String = ""
    private var idAwayTeam: String = ""
    private lateinit var presenter: DetailPresenter
    private lateinit var events: DetailEvent

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    override fun getDetailEvent(data: DetailEvent) {
        events = DetailEvent(data.eventId, data.event, data.league, data.homeTeam, data.awayTeam,
                data.homeScore, data.awayScore, data.homeTeamId, data.awayTeamId,data.homeGoalDetail, data.awayGoalDetail, data.homeRedCards,
                data.homeYellowCards, data.awayRedCards, data.awayYellowCards, data.dateEvent)
        idHomeTeam = data.homeTeamId!!
        idAwayTeam = data.awayTeamId.toString()
        Log.d("idHome", idHomeTeam)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(detail_menu, menu)
        menuItem = menu
        favoriteState()
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            add_to_favorite -> {
                if (isFavorite){
                    removeFromFavorite()
                } else {
                    addToFavorite()
                }

                isFavorite = !isFavorite
                setFavorite()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addToFavorite(){
        val TAG: String = "TAG1"
        try {
            database.use {
                insert(Favorite.TABLE_FAVORITE,
                        Favorite.EVENT_ID to events.eventId,
                        Favorite.HOMETEAM_ID to events.homeTeamId,
                        Favorite.HOMETEAM to events.homeTeam,
                        Favorite.AWAYTEAM_ID to events.awayTeamId,
                        Favorite.AWAYTEAM to events.awayTeam,
                        Favorite.HOMESCORE to events.homeScore,
                        Favorite.AWAYSCORE to events.awayScore,
                        Favorite.DATEEVENT to events.dateEvent
                )
            }
            toast("Ditambahkan ke favorite")

            Log.d(TAG, events.homeTeam)
            Log.d(TAG, Favorite.HOMETEAM)
            Log.d(TAG, Favorite.AWAYTEAM)
            Log.d(TAG, idHomeTeam)
            Log.d(TAG, events.homeTeamId)
            Log.d(TAG, "home team id")
        } catch(e: SQLiteConstraintException){
            toast(e.localizedMessage)
        }
    }

    private fun removeFromFavorite(){
        try {
            database.use {
                delete(Favorite.TABLE_FAVORITE, "(EVENT_ID = {eventId})", "eventId" to idEvent)
            }
            toast("Dihapus dari favorite")
        } catch (e: SQLiteConstraintException){
            toast(e.localizedMessage)
        }
    }

    private fun setFavorite(){
        if(isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_star_white_24dp)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_star_border_white_24dp)
    }

    private fun favoriteState(){
        database.use {
            val result = select(Favorite.TABLE_FAVORITE).whereArgs("(EVENT_ID = {id})", "id" to idEvent)
            val favorite = result.parseList(classParser<Favorite>())
            if (!favorite.isEmpty()) isFavorite = true
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        //set toolbar
        setSupportActionBar(toolbar)

        supportActionBar?.title = "Event Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val intent = intent
        idEvent = intent.getStringExtra("idEvent")

        favoriteState()
        val request = ApiRepository()
        val gson = Gson()
        presenter = DetailPresenter(this, request, gson)

        presenter.getDetailEvent(idEvent)
    }
}
