package com.truxall.everydayanimation

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView

class StartActivity : AppCompatActivity() {

    private var mToolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_activity)

        val navController = Navigation.findNavController(this, R.id.nav_host)

        this.mToolbar = this.findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(this.mToolbar)
        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }

        setupDrawerToggle()

        val navView = this.findViewById<NavigationView>(R.id.nav_view)
        navView.setupWithNavController(navController)
    }

    private fun setupDrawerToggle() {
        val drawerLayout = this.findViewById<DrawerLayout>(R.id.drawer_layout)
        val mDrawerToggle = object : ActionBarDrawerToggle(this, drawerLayout, this.mToolbar, R.string.open, R.string.close) {}
        drawerLayout.addDrawerListener(mDrawerToggle)
        mDrawerToggle.syncState()
    }
}
