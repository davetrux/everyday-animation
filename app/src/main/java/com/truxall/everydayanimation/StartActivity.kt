package com.truxall.everydayanimation

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.ActionBarDrawerToggle
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI

import kotlinx.android.synthetic.main.start_activity.*

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_activity)

        val navController = Navigation.findNavController(this, R.id.nav_host)

        setSupportActionBar(toolbar)
        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }

        setupDrawerToggle()

        NavigationUI.setupWithNavController(nav_view, navController)
    }

    private fun setupDrawerToggle() {
        val mDrawerToggle = object : ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.open, R.string.close) {}
        drawer_layout.addDrawerListener(mDrawerToggle)
        mDrawerToggle.syncState()

    }


}
