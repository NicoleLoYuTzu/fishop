package com.nicole.fishop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nicole.fishop.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val a = 123
        val fishop = findViewById<TextView>(R.id.fishop)
        val profileTitle = findViewById<TextView>(R.id.profile_title)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val bottomNavigationView =
            findViewById<BottomNavigationView>(R.id.activity_main_bottom_navigation_view)
//        bottomNavigationView.itemIconTintList
        bottomNavigationView.itemIconTintList = null

        val navController: NavController =
            Navigation.findNavController(this, R.id.activity_main_nav_host_fragment)

        NavigationUI.setupWithNavController(bottomNavigationView, navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.FishSellerFragment -> {
                    bottomNavigationView.visibility = View.VISIBLE
                    toolbar.visibility = View.VISIBLE
                    profileTitle.visibility = View.VISIBLE
                    profileTitle.text = "每日漁貨紀錄"
                    fishop.visibility = View.INVISIBLE
                }

                R.id.FishBuyerFragment -> {
                    bottomNavigationView.visibility = View.VISIBLE
                    toolbar.visibility = View.VISIBLE
                    profileTitle.visibility = View.VISIBLE
                    profileTitle.text = "直播間"
                    fishop.visibility = View.INVISIBLE
                }
                R.id.ProfileBuyerFragment -> {
                    bottomNavigationView.visibility = View.VISIBLE
                    toolbar.visibility = View.VISIBLE
                    profileTitle.visibility = View.VISIBLE
                    profileTitle.text = "聊天室"
                    fishop.visibility = View.INVISIBLE
                }
                R.id.ProfileSellerFragment -> {
                    bottomNavigationView.visibility = View.VISIBLE
                    toolbar.visibility = View.VISIBLE
                    profileTitle.visibility = View.INVISIBLE
                    fishop.visibility = View.VISIBLE
                }

            }
        }

    }
}