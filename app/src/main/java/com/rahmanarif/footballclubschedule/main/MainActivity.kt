package com.rahmanarif.footballclubschedule.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import com.rahmanarif.footballclubschedule.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var fragment: Fragment
    private lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottom_navigation.inflateMenu(R.menu.bottomnavigation_menu);
        fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().replace(R.id.main_container, PreviousFragment()).commit()

        //Memberikan listener saat menu item di bottom navigation diklik
        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            val id = item.getItemId()
            when (id) {
                R.id.prev -> fragment = PreviousFragment()
                R.id.next -> fragment = NextFragment()
            }
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.main_container, fragment).commit()
            true
        }
    }

}
