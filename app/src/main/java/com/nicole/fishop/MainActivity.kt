package com.nicole.fishop

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.nicole.fishop.data.Users
import com.nicole.fishop.databinding.ActivityMainBinding
import com.nicole.fishop.ext.getVmFactory
import com.nicole.fishop.login.StartDialog
import com.nicole.fishop.login.UserManager
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



//        FirebaseFirestore.getInstance()
//            .collectionGroup("Users")
//            .whereEqualTo("email", "a4207486@gmail.com")
//            .get()
//            .addOnCompleteListener { SellerInfo ->
//                Logger.d("SellerInfo.documents ${SellerInfo.result.documents} ")
//                for (oldDocument in SellerInfo.result) {
//                    Logger.i("oldDocument $oldDocument")
//                    Logger.i("SellerInfo.result ${SellerInfo.result}")
//                    val oldUsers = oldDocument.toObject(Users::class.java)
//                    oldUsers.id?.let {
//                        FirebaseFirestore.getInstance()
//                            .collection("Users")
//                            .document(it)
//                            .delete()
//                            .addOnCompleteListener { result ->
//                                Logger.i(" oldUsers.email => ${oldUsers.email}")
//                                if (result.isSuccessful) {
//                                    Logger.i("result.result => ${result.result}")
//                                }
//                            }
//
//                    }
//
//                }
//            }


        val fishop = findViewById<TextView>(R.id.fishop)
        val profileTitle = findViewById<TextView>(R.id.profile_title)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val bottomNavigationView =
            findViewById<BottomNavigationView>(R.id.activity_main_bottom_navigation_view)


        bottomNavigationView.itemIconTintList = null

        val navController: NavController =
            Navigation.findNavController(this, R.id.activity_main_nav_host_fragment)

        Logger.i("MainActivity UserManager.userToken ${UserManager.userToken}")
        Logger.i("UserManager.user?.accountType ${UserManager.user?.accountType}")


        NavigationUI.setupWithNavController(bottomNavigationView, navController)

        viewModel.newUser.observe(this, Observer {
            if (it) {
                if (UserManager.user?.accountType == "buyer") {
                    turnMode(Mode.BUYER.index)
                    Logger.i("UserManager.user ${UserManager.user}")
                } else if (UserManager.user?.accountType == "saler") {
                    turnMode(Mode.SELLER.index)
                    Logger.i("UserManager.user ${UserManager.user}")
                }
            }
        })

        findNavController(R.id.activity_main_nav_host_fragment)
            .addOnDestinationChangedListener { navController: NavController, destination, _ ->
                when (navController.currentDestination?.id) {
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
                    R.id.ChatBoxFragment->{
                        bottomNavigationView.visibility = View.GONE
                        toolbar.visibility = View.GONE
                        profileTitle.visibility = View.GONE
                        fishop.visibility = View.GONE
                    }

                    R.id.ProfileBuyerFragment -> {
                        bottomNavigationView.visibility = View.VISIBLE
                        toolbar.visibility = View.VISIBLE
                        profileTitle.visibility = View.VISIBLE
                        profileTitle.text = "我的檔案"
                        fishop.visibility = View.INVISIBLE
                    }
                    R.id.ProfileSellerFragment -> {
                        bottomNavigationView.visibility = View.VISIBLE
                        toolbar.visibility = View.GONE
                        profileTitle.visibility = View.GONE
                        fishop.visibility = View.GONE
                    }
                    R.id.ChatFragment -> {
                        bottomNavigationView.visibility = View.VISIBLE
                        toolbar.visibility = View.VISIBLE
                        profileTitle.visibility = View.VISIBLE
                        profileTitle.text = "聊天室"
                        fishop.visibility = View.INVISIBLE
                    }
                    R.id.ProfileSalerEditFragment -> {
                        bottomNavigationView.visibility = View.GONE
                        toolbar.visibility = View.VISIBLE
                        profileTitle.visibility = View.INVISIBLE
                        fishop.visibility = View.VISIBLE
                    }
                }
            }


    }

    fun turnMode(mode: Int) {
        if (mode == Mode.BUYER.index) {
            binding.activityMainBottomNavigationView.menu.clear()
            binding.activityMainBottomNavigationView.inflateMenu(R.menu.nav_menu_buyer)
        } else {
            binding.activityMainBottomNavigationView.menu.clear()
            binding.activityMainBottomNavigationView.inflateMenu(R.menu.nav_menu_seller)
        }
    }
}