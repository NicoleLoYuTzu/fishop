package com.nicole.fishop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nicole.fishop.databinding.ActivityMainBinding
import com.nicole.fishop.ext.getVmFactory
import com.nicole.fishop.util.Logger

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
//    var preferences: SharedPreferences? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.i("MainActivity onCreate")
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)



        val fishop = findViewById<TextView>(R.id.fishop)
        val profileTitle = findViewById<TextView>(R.id.profile_title)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val bottomNavigationView_Saler =
            findViewById<BottomNavigationView>(R.id.activity_main_bottom_navigation_view_salermode)

//        val bottomNavigationView_Buyer =
//            findViewById<BottomNavigationView>(R.id.activity_main_bottom_navigation_view_buyermode)

        bottomNavigationView_Saler.itemIconTintList = null
//        bottomNavigationView_Buyer.itemIconTintList = null




//        viewModel.user.observe(this, Observer {
//            if (it.accountType=="saler"){
                val navController: NavController =
                    Navigation.findNavController(this, R.id.activity_main_nav_host_fragment)

                NavigationUI.setupWithNavController(bottomNavigationView_Saler, navController)

                navController.addOnDestinationChangedListener { _, destination, _ ->
                    when (destination.id) {
                        R.id.FishSellerFragment -> {
                            bottomNavigationView_Saler.visibility = View.VISIBLE
                            toolbar.visibility = View.VISIBLE
                            profileTitle.visibility = View.VISIBLE
                            profileTitle.text = "每日漁貨紀錄"
                            fishop.visibility = View.INVISIBLE
                        }
                        R.id.FishSellerFragmentAddToday -> {
                            bottomNavigationView_Saler.visibility = View.GONE
                            toolbar.visibility = View.GONE
                            profileTitle.visibility = View.INVISIBLE
                        }

                        R.id.FishBuyerFragment -> {
                            bottomNavigationView_Saler.visibility = View.VISIBLE
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
                            bottomNavigationView_Saler.visibility = View.VISIBLE
                            toolbar.visibility = View.VISIBLE
                            profileTitle.visibility = View.INVISIBLE
                            fishop.visibility = View.VISIBLE
                        }
                        R.id.BuyerChatFragment -> {
                            bottomNavigationView_Saler.visibility = View.VISIBLE
                            toolbar.visibility = View.GONE
                            profileTitle.visibility = View.INVISIBLE
                            fishop.visibility = View.INVISIBLE
                        }
                    }
                }
//            }else if (it.accountType=="buyer"){
//
//                val navController: NavController =
//                    Navigation.findNavController(this, R.id.activity_main_nav_host_fragment)
//
//                NavigationUI.setupWithNavController(bottomNavigationView_Buyer, navController)
//
//                navController.addOnDestinationChangedListener { _, destination, _ ->
//                    when (destination.id) {
//                        R.id.FishSellerFragment -> {
//                            bottomNavigationView_Buyer.visibility = View.VISIBLE
//                            toolbar.visibility = View.VISIBLE
//                            profileTitle.visibility = View.VISIBLE
//                            profileTitle.text = "每日漁貨紀錄"
//                            fishop.visibility = View.INVISIBLE
//                        }
//                        R.id.FishSellerFragmentAddToday -> {
//                            bottomNavigationView_Buyer.visibility = View.GONE
//                            toolbar.visibility = View.GONE
//                            profileTitle.visibility = View.INVISIBLE
//                        }
//
//                        R.id.FishBuyerFragment -> {
//                            bottomNavigationView_Buyer.visibility = View.VISIBLE
//                            toolbar.visibility = View.VISIBLE
//                            profileTitle.visibility = View.VISIBLE
//                            profileTitle.text = "今日漁貨"
//                            fishop.visibility = View.INVISIBLE
//                        }
////                R.id.ProfileBuyerFragment -> {
////                    bottomNavigationView.visibility = View.VISIBLE
////                    toolbar.visibility = View.VISIBLE
////                    profileTitle.visibility = View.VISIBLE
////                    profileTitle.text = "聊天室"
////                    fishop.visibility = View.INVISIBLE
////                }
//                        R.id.ProfileSalerFragment -> {
//                            bottomNavigationView_Buyer.visibility = View.VISIBLE
//                            toolbar.visibility = View.VISIBLE
//                            profileTitle.visibility = View.INVISIBLE
//                            fishop.visibility = View.VISIBLE
//                        }
//                        R.id.BuyerChatFragment -> {
//                            bottomNavigationView_Buyer.visibility = View.VISIBLE
//                            toolbar.visibility = View.GONE
//                            profileTitle.visibility = View.INVISIBLE
//                            fishop.visibility = View.INVISIBLE
//                        }
//                    }
//                }
//
//
//            }
//        })
//        bottomNavigationView.itemIconTintList






    }
}