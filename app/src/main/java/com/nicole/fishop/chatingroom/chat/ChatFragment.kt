package com.nicole.fishop.chatingroom.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.nicole.fishop.databinding.FragmentChatBuyerBinding
import com.nicole.fishop.ext.getVmFactory
import com.nicole.fishop.login.UserManager
import com.nicole.fishop.util.Logger


class ChatFragment : Fragment() {

    private val viewModel by viewModels<ChatViewModel> {
        getVmFactory(
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentChatBuyerBinding.inflate(layoutInflater)


//        if (UserManager.user?.accountType == "saler"){
//            UserManager.user?.let { viewModel.getSalerChatRecordResult(it) }
//        }else if (UserManager.user?.accountType == "buyer"){
//            UserManager.user?.let { viewModel.getBuyerChatRecordResult(it) }
//        }


        binding.recyclerView.adapter = ChatAdapter()

        viewModel.chatRecord.observe(viewLifecycleOwner, Observer {

            (binding.recyclerView.adapter as ChatAdapter).submitList(it)
            Logger.i(" viewModel.chatRecord => $it")
        })

        return binding.root
    }


}