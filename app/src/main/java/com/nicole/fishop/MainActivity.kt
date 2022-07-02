package com.nicole.fishop

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nicole.fishop.databinding.ActivityMainBinding
import com.nicole.fishop.databinding.FragmentFishSellerAddTodayBinding
import com.nicole.fishop.ext.getVmFactory
import com.nicole.fishop.fishSeller.AddTodayCategoryViewModel
import com.nicole.fishop.util.Logger

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
//    var preferences: SharedPreferences? = null

    private val viewModel by viewModels<MainViewModel> {
        getVmFactory(
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.i("MainActivity onCreate")
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)



        val fishop = findViewById<TextView>(R.id.fishop)
        val profileTitle = findViewById<TextView>(R.id.profile_title)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val bottomNavigationView =
            findViewById<BottomNavigationView>(R.id.activity_main_bottom_navigation_view)


        bottomNavigationView.itemIconTintList = null




        viewModel.user.observe(this, Observer {
            if (it.accountType=="buyer"){
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
                        R.id.FishSellerFragmentAddToday -> {
                            bottomNavigationView.visibility = View.GONE
                            toolbar.visibility = View.GONE
                            profileTitle.visibility = View.INVISIBLE
                        }

                        R.id.FishBuyerFragment -> {
                            bottomNavigationView.visibility = View.VISIBLE
                            toolbar.visibility = View.VISIBLE
                            profileTitle.visibility = View.VISIBLE
                            profileTitle.text = "今日漁貨"
                            fishop.visibility = View.INVISIBLE
                        }
//                R.id.ProfileBuyerFragment -> {
//                    bottomNavigationView.visibility = View.VISIBLE
//                    toolbar.visibility = View.VISIBLE
//                    profileTitle.visibility = View.VISIBLE
//                    profileTitle.text = "聊天室"
//                    fishop.visibility = View.INVISIBLE
//                }
                        R.id.ProfileSellerFragment -> {
                            bottomNavigationView.visibility = View.VISIBLE
                            toolbar.visibility = View.VISIBLE
                            profileTitle.visibility = View.INVISIBLE
                            fishop.visibility = View.VISIBLE
                        }
                        R.id.BuyerChatFragment -> {
                            bottomNavigationView.visibility = View.VISIBLE
                            toolbar.visibility = View.GONE
                            profileTitle.visibility = View.INVISIBLE
                            fishop.visibility = View.INVISIBLE
                        }
                    }
                }
            }
        })
//        bottomNavigationView.itemIconTintList






    }
}