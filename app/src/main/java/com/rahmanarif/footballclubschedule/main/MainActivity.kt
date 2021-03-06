package com.rahmanarif.footballclubschedule.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import com.rahmanarif.footballclubschedule.R
import com.rahmanarif.footballclubschedule.R.layout.activity_main
import com.rahmanarif.footballclubschedule.fragment.FavoritesFragment
import com.rahmanarif.footballclubschedule.fragment.NextFragment
import com.rahmanarif.footballclubschedule.fragment.PreviousFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var fragment: Fragment
    private lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_main)

        bottom_navigation.inflateMenu(R.menu.bottomnavigation_menu);
        fragmentManager = supportFragmentManager;

        fragmentManager.beginTransaction().replace(R.id.main_container, PreviousFragment()).commit()

        //Memberikan listener saat menu item di bottom navigation diklik
        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            val id = item.getItemId()
            when (id) {
                R.id.prev -> fragment = PreviousFragment()
                R.id.next -> fragment = NextFragment()
                R.id.favorite -> fragment = FavoritesFragment()
            }
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.main_container, fragment).commit()
            true
        }

    }
}
