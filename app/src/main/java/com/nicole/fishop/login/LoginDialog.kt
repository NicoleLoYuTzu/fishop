package com.nicole.fishop.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import app.appworks.school.stylish.login.LoginViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.nicole.fishop.MainViewModel
import com.nicole.fishop.R
import com.nicole.fishop.databinding.DialogLoginBinding
import com.nicole.fishop.ext.getVmFactory
import com.nicole.fishop.ext.setTouchDelegate
import com.nicole.fishop.util.Logger

/**
 * Created by Wayne Chen in Jul. 2019.
 */
class LoginDialog : AppCompatDialogFragment() {

    /**
     * Lazily initialize our [LoginViewModel].
     */
    private val viewModel by viewModels<LoginViewModel> { getVmFactory() }
    private lateinit var binding: DialogLoginBinding



    private fun signIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("google-service.json 內的 client_id")
            .requestEmail()
            .build()

        val mGoogleSignInClient = context?.let { GoogleSignIn.getClient(it, gso) }
        val signInIntent = mGoogleSignInClient?.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
        //...
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                val email  = account?.email
                val token = account?.idToken
                Logger.i("givemepass , email:$email, token:$token")
                Toast.makeText(context, "登入成功", Toast.LENGTH_SHORT).show()
            } catch (e: ApiException) {
                Logger.i("givemepass , signInResult:failed code=" + e.statusCode)
                Toast.makeText(context, "登入失敗", Toast.LENGTH_SHORT).show()
            }
        } else{
            Logger.i("givemepass login fail")
            Toast.makeText(context, "登入失敗", Toast.LENGTH_SHORT).show()
        }
    }
    companion object {
        const val RC_SIGN_IN = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.LoginDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        binding = DialogLoginBinding.inflate(inflater, container, false)
        binding.layoutLogin.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_slide_up))

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.buttonLoginClose.setTouchDelegate()

        binding.buttonLoginGoogle.setOnClickListener {
            signIn()
        }

        binding.buttonLoginClose.setOnClickListener {
            findNavController().popBackStack()
        }

//        val mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

//        viewModel.user.observe(
//            viewLifecycleOwner,
//            Observer {
//                it?.let {
//                    mainViewModel.setupUser(it)
//                }
//            }
//        )
//
//        viewModel.navigateToLoginSuccess.observe(
//            viewLifecycleOwner,
//            Observer {
//                it?.let {
//                    mainViewModel.navigateToLoginSuccess(it)
//                    dismiss()
//                }
//            }
//        )
//
//        viewModel.leave.observe(
//            viewLifecycleOwner,
//            Observer {
//                it?.let {
//                    dismiss()
//                    viewModel.onLeaveCompleted()
//                }
//            }
//        )
//
//        viewModel.loginFacebook.observe(
//            viewLifecycleOwner,
//            Observer {
//                it?.let {
////                    LoginManager.getInstance().logInWithReadPermissions(this, listOf("email"))
//                    viewModel.onLoginFacebookCompleted()
//                }
//            }
//        )

        return binding.root
    }

    override fun dismiss() {
        binding.layoutLogin.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_slide_down))
        Handler().postDelayed({ super.dismiss() }, 200)
    }




}
