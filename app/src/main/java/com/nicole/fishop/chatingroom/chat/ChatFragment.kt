package com.nicole.fishop.chatingroom.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.nicole.fishop.NavFragmentDirections
import com.nicole.fishop.chatingroom.chatbox.ChatBoxFragmentArgs
import com.nicole.fishop.chatingroom.chatbox.ChatBoxViewModel
import com.nicole.fishop.data.FishToday
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


        binding.recyclerView.adapter = ChatAdapter( ChatAdapter.OnClickListener {
            if (UserManager.user?.accountType == "buyer"){
                val fishToday = FishToday()
                Logger.i(" binding.recyclerView.adapter it ${it}")
                Logger.i(" binding.recyclerView.adapter it.saler ${it.saler}")
                fishToday.ownerId = it.saler
                fishToday.name = it.salerName
                fishToday.ownPhoto = it.salerPhoto
                findNavController().navigate(NavFragmentDirections.actionToChatBoxFragment(fishToday))
            }else if(UserManager.user?.accountType == "saler"){
                val fishToday = FishToday()
                fishToday.ownerId = UserManager.user!!.id.toString()
                fishToday.name = it.buyerName
                fishToday.buyerId = it.buyer
                fishToday.ownPhoto = it.buyerPhoto
                Logger.i("fishToday ${fishToday}")
                findNavController().navigate(NavFragmentDirections.actionToChatBoxFragment(fishToday))
            }


        })

        binding.textViewNochat.isInvisible

        viewModel.chatRecord.observe(viewLifecycleOwner, Observer {

            if (it.isEmpty()){
                if (UserManager.user?.accountType == "buyer"){
                    binding.textViewNochat.isVisible
                    binding.textViewNochat.text = "您還沒有跟店家聊天哦~"
                }else if(UserManager.user?.accountType == "saler"){
                    binding.textViewNochat.isVisible
                    binding.textViewNochat.text = "目前還沒有客人找您~"
                }
            }

            (binding.recyclerView.adapter as ChatAdapter).submitList(it)
            Logger.i(" viewModel.chatRecord => $it")
        })

        return binding.root
    }


}