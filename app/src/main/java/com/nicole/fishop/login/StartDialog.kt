package com.nicole.fishop.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.firestore.auth.User
import com.nicole.fishop.MainViewModel
import com.nicole.fishop.NavFragmentDirections
import com.nicole.fishop.R
import com.nicole.fishop.data.Users
import com.nicole.fishop.databinding.ActivityStartDialogBinding
import com.nicole.fishop.ext.getVmFactory
import com.nicole.fishop.login.UserManager.accountType
import com.nicole.fishop.login.UserManager.user
import com.nicole.fishop.util.Logger
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class StartDialog() : AppCompatDialogFragment() {

    //    var preferences: SharedPreferences? = null
    private lateinit var binding: ActivityStartDialogBinding

    private val viewModel by viewModels<StartDialogViewModel> {
        getVmFactory(
        )
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.ReviewDialog)
        Logger.i("onCreate")


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Logger.i("onCreateView")

        Logger.i("UserManager.userToken ${UserManager.userToken}")

        binding = ActivityStartDialogBinding.inflate(inflater, container, false)


        binding.constraint.startAnimation(
            AnimationUtils.loadAnimation(
                context,
                R.anim.anim_slide_up
            )
        )



        binding.lifecycleOwner = viewLifecycleOwner
        binding.checkBoxBuyer.setOnCheckedChangeListener { compoundButton, b ->
            if (binding.checkBoxBuyer.isChecked) {
                context?.let {
                    AlertDialog.Builder(it)
                        .setTitle("確定成為買家?")
                        .setPositiveButton("確定") { dialog, _ ->
                            user?.accountType = "buyer"
                            ViewModelProvider(requireActivity()).get(MainViewModel::class.java).apply {
                                newUser.value = true
                            }
                            dialog.dismiss()
//                        findNavController().navigate(NavFragmentDirections.actionStartDialogToFishBuyerFragment())
//                        findNavController().navigate(NavFragmentDirections.actionStartDialogToMainActivity())
                        }
                        .setNeutralButton("取消") { dialog, _ ->
                            dialog.dismiss()
                        }
                        .show()
                }
            }
        }

        binding.checkBoxSaler.setOnCheckedChangeListener { compoundButton, b ->
            if (binding.checkBoxSaler.isChecked) {
                context?.let {
                    AlertDialog.Builder(it)
                        .setTitle("確定成為賣家?")
                        .setPositiveButton("確定") { dialog, _ ->
                            user?.accountType = "saler"
                            ViewModelProvider(requireActivity()).get(MainViewModel::class.java).apply {
                                newUser.value = true
                            }
                            dialog.dismiss()
                        }
                        .setNeutralButton("取消") { dialog, _ ->
                            dialog.dismiss()
                        }
                        .show()
                }
            }
        }

        binding.buttonLoginGoogle.setOnClickListener {
            if (user?.accountType == null) {
                Toast.makeText(activity, "請先勾選角色", Toast.LENGTH_SHORT).show()
            } else {
                signIn()
            }
        }

        //不創id的版本
//        viewModel.userManager.observe(this, Observer {
//            if (it.user?.accountType == "buyer") {
//                dismiss()
//                findNavController().navigate(NavFragmentDirections.actionStartDialogToFishBuyerFragment())
//            } else if (it.user?.accountType == "saler") {
//                dismiss()
//
//                findNavController().navigate(NavFragmentDirections.actionStartDialogToProfileSalerEditFragment())
//            }
//        }
//        )

////      這個是檢查到有id回來配到資料後再配進去給UserManager, 偵測到是甚麼模式就跳什麼分頁
        viewModel.userswithId.observe(this, Observer {
            if (it.id!=null&&it.accountType == "buyer"){
                dismiss()
                UserManager.user?.id=it.id
                Logger.i("viewModel.userswithId UserManager.user ${ UserManager.user}")
                findNavController().navigate(NavFragmentDirections.actionStartDialogToFishBuyerFragment())
            }else if(it.id!=null && it.accountType == "saler" && it.address==null){
                dismiss()
                UserManager.user?.id=it.id
                Logger.i("viewModel.userswithId UserManager.user ${ UserManager.user}")
                findNavController().navigate(NavFragmentDirections.actionStartDialogToProfileSalerEditFragment())
            }

            if (it.accountType == "saler" && it.address!=null){
                UserManager.user?.id =it.id
                findNavController().navigate(NavFragmentDirections.actionStartDialogToProfileSalerFragment())
            }


            if (it.email == null){
                user?.let { viewModel.userSignIn(it) }
            }

            Logger.i(" viewModel.userswithId.observe ${viewModel.userswithId.value}")
        })

        return binding.root
    }

    private fun signIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                //從google service json的client id
            .requestIdToken("44039700708-qg19e235nofihjbsjkrv3efsklst64o8.apps.googleusercontent.com")
            .requestEmail()
            .build()

        val mGoogleSignInClient = context?.let { GoogleSignIn.getClient(it, gso) }
        val signInIntent = mGoogleSignInClient?.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
        //...
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                val email = account?.email
                val token = account?.idToken

                val picture = account?.photoUrl.toString()

                Logger.i("givemepass , email:$email, token:$token")
                UserManager.userToken = token
                UserManager.user?.picture =picture
                UserManager.accountType = user?.accountType
                Logger.i("UserManager.userToken ${UserManager.userToken}")
                user?.email = email
                user?.name = account?.displayName
                //先不要建立資料
                if (UserManager.accountType=="buyer") {
                    user?.email?.let { viewModel.checkBuyerAccount("buyer",it) }
                }

                if (UserManager.accountType=="saler"){
                    user?.email?.let { viewModel.checkSalerAccount("saler",it) }
                }

                //userManager的值分辨買賣家來跳轉頁面
                viewModel.userManager.value = UserManager
                Logger.i(" user.value $user")
                Logger.d("UserManager.userToken ${UserManager.userToken}")
                Logger.d("UserManager.user ${user?.accountType}")
                Logger.d("user?.email ${user?.email}")
                Toast.makeText(context, "登入成功", Toast.LENGTH_SHORT).show()

            } catch (e: ApiException) {
                Logger.i("givemepass , signInResult:failed code=" + e.statusCode)
                Toast.makeText(context, "登入失敗", Toast.LENGTH_SHORT).show()
            }
        } else {
            Logger.i("givemepass login fail")
            Toast.makeText(context, "登入失敗", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val RC_SIGN_IN = 100
    }


    override fun dismiss() {
        binding.constraint.startAnimation(
            AnimationUtils.loadAnimation(
                context,
                R.anim.anim_slide_down
            )
        )
        lifecycleScope.launch {
            delay(200)
            super.dismiss()
        }
    }
}