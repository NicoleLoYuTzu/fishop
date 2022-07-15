package com.nicole.fishop.chatingroom.chatbox

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.nicole.fishop.FishopApplication
import com.nicole.fishop.NavFragmentDirections
import com.nicole.fishop.data.ChatBoxRecord
import com.nicole.fishop.data.ChatRecord
import com.nicole.fishop.data.Users
import com.nicole.fishop.databinding.FragmentChatBoxBinding
import com.nicole.fishop.ext.getVmFactory
import com.nicole.fishop.login.UserManager
import com.nicole.fishop.util.Logger


class ChatBoxFragment : Fragment() {


    private val viewModel by viewModels<ChatBoxViewModel> {
        getVmFactory(
            ChatBoxFragmentArgs.fromBundle(
                requireArguments()
            ).otherPeopleIdKey

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
        val salerInfo = ChatBoxFragmentArgs.fromBundle(
            requireArguments()
        ).otherPeopleIdKey


        Logger.i("chatbox onCreateView")


        val binding = FragmentChatBoxBinding.inflate(layoutInflater)

        val adapter = ChatBoxAdapter()

        binding.lifecycleOwner = this

        binding.recyclerView.adapter = adapter

        binding.textViewName.text = salerInfo.name
        viewModel.checkHasRoom.observe(viewLifecycleOwner, Observer {
            //如果買家檢查後有房間的話, 就去要對話紀錄
            //有房間的話回true
            if (it != null) {
                if (FishopApplication.instance.isLiveDataDesign()) {
                    viewModel.getLiveChat(it.id)
                } else {
                    if (UserManager.user?.accountType=="buyer") {
                        UserManager.user?.let { it1 ->
                            viewModel.getBuyerChatBoxRecordResult(
                                salerInfo,
                                it1
                            )
                        }
                    }

                    if (UserManager.user?.accountType=="saler"){
                        viewModel._addChatroom.value.let { _addChatroom->

                            val user = Users()
                            user.id = _addChatroom?.buyer

                            viewModel.getSalerChatBoxRecordResult(salerInfo,user)

                        }

                    }

                }
                Logger.i(" //如果買家檢查後有房間的話, 就去要對話紀錄")
                //如果沒有房間的話, 就去開房間
            } else {
                if (UserManager.user?.accountType == "buyer") {
                    viewModel.addChatroom()
                }
                viewModel.checkHasRoom()
                Logger.i("如果沒有房間的話, 就去開房間 viewModel.addChatroom()")
            }
        }
        )

        viewModel.chatRecord.observe(viewLifecycleOwner, Observer {
            viewModel.getLiveChat(it.id)
        })

        viewModel.observeChatItem.observe(viewLifecycleOwner) {
            if (it) {

                viewModel.liveChatItem.observe(viewLifecycleOwner) { ListChat ->
                    val chats = viewModel.toDivide(ListChat)
                    adapter.submitList(chats)
                    binding.recyclerView.smoothScrollToPosition(chats.size)
                }


            }
        }

//        viewModel.observeChatItem.observe(viewLifecycleOwner, Observer {
//            if (it) {
//                val snapshot = viewModel.toDivide(viewModel.liveChatItem)
//                adapter.submitList(snapshot)
//                adapter.notifyDataSetChanged()
//            }
//        })

        //
        viewModel.chatItem.observe(viewLifecycleOwner, Observer
        {
            if (it.isNotEmpty()) {
//            if (it!=null) {
                Logger.i("viewModel.chatItem.observeit != null {{{$it}}}")
                adapter.submitList(it)
                adapter.notifyDataSetChanged()
            }
            Logger.i("viewModel.chatItem.observe $it")

            if (UserManager.user?.accountType == "saler"){
                viewModel._addChatroom.value.let {
                    val user = Users()
                    user.id = it?.buyer
                    Logger.i("salerInfo $salerInfo,user=>$user")

                    viewModel.getSalerChatBoxRecordResult(salerInfo,user)

                }

            }




        })

        val db = Firebase.firestore

        binding.imageViewVideocall.setOnClickListener {
            findNavController().navigate(NavFragmentDirections.actionChatBoxFragmentToRTCFragment())
        }

//        viewModel.liveChatItem.observe(viewLifecycleOwner, Observer {
//            if (it.isNotEmpty()) {
//                val snapshot = viewModel.toDivide(viewModel.liveChatItem)
//                adapter.submitList(snapshot)
//                Logger.i(" viewModel.liveChatItem.observe")
//                adapter.notifyDataSetChanged()
//            }
//        })


        binding.imageViewSend.setOnClickListener {
            Logger.i(" binding.imageViewSend.setOnClickListener ")
            viewModel.checkHasRoom.value.let { openChatRoomReady ->
                //chatRecord?.id是新房間的id
                val chatRoomId = openChatRoomReady?.id

                if (chatRoomId != null) {

                    val chatRecord = ChatRecord()

                    if (UserManager.user?.accountType == "saler") {
                        chatRecord.lastsender = UserManager.user?.id.toString()
                        chatRecord.lastchatTime = System.currentTimeMillis().toString()
                        chatRecord.lastsenderName = UserManager.user?.name.toString()
                        chatRecord.lastchat = binding.editTextTextPersonName.text.toString()
                        chatRecord.salerPhoto = salerInfo.ownPhoto
                        chatRecord.buyerPhoto = viewModel._addChatroom.value?.buyerPhoto.toString()


                        //訊息不是空的才發出去
                        if (binding.editTextTextPersonName.text.isNotEmpty()) {
                            viewModel.sendLastChat(chatRoomId, chatRecord)
                        }

                        val chatBoxRecord = ChatBoxRecord()
                        chatBoxRecord.content = binding.editTextTextPersonName.text.toString()
                        chatBoxRecord.sender = UserManager.user?.id.toString()
                        chatBoxRecord.senderphoto = UserManager.user?.picture.toString()
                        chatBoxRecord.time = System.currentTimeMillis()
                        chatBoxRecord.id = chatRoomId
                        if (binding.editTextTextPersonName.text.isNotEmpty()) {
                            viewModel.sendChat(chatRoomId, chatBoxRecord)
                        }


                        Logger.i("viewModel.sendChat chatRoomId=>${chatRoomId},chatBoxRecord=> ${chatBoxRecord}")
                        Logger.i("viewModel.sendLastChat chatRoomId=>${chatRoomId},chatRecord=> ${chatRecord}")
                        binding.editTextTextPersonName.text.clear()
                    }



                    if (UserManager.user?.accountType == "buyer") {
                        chatRecord.lastsender = UserManager.user?.id.toString()
                        chatRecord.lastchatTime = System.currentTimeMillis().toString()
                        chatRecord.lastsenderName = UserManager.user?.name.toString()
                        chatRecord.lastchat = binding.editTextTextPersonName.text.toString()
                        chatRecord.salerPhoto = salerInfo.ownPhoto
                        chatRecord.buyerPhoto = viewModel._addChatroom.value?.buyerPhoto.toString()


                        //訊息不是空的才發出去
                        if (binding.editTextTextPersonName.text.isNotEmpty()) {
                            viewModel.sendLastChat(chatRoomId, chatRecord)
                        }

                        val chatBoxRecord = ChatBoxRecord()
                        chatBoxRecord.content = binding.editTextTextPersonName.text.toString()
                        chatBoxRecord.sender = UserManager.user?.id.toString()
                        chatBoxRecord.senderphoto = UserManager.user?.picture.toString()
                        chatBoxRecord.time = System.currentTimeMillis()
                        chatBoxRecord.id = chatRoomId
                        if (binding.editTextTextPersonName.text.isNotEmpty()) {
                            viewModel.sendChat(chatRoomId, chatBoxRecord)
                        }


                        Logger.i("viewModel.sendChat chatRoomId=>${chatRoomId},chatBoxRecord=> ${chatBoxRecord}")
                        Logger.i("viewModel.sendLastChat chatRoomId=>${chatRoomId},chatRecord=> ${chatRecord}")
                        binding.editTextTextPersonName.text.clear()
                    }



                }
            }

//            viewModel.FirstCollectionSetUp.observe(viewLifecycleOwner, Observer {
//                viewModel.chatRecord.value.let { openChatRoomReady ->
//                    //chatRecord?.id是新房間的id
//                    val chatRoomId = openChatRoomReady?.id
//                    if (chatRoomId != null) {
//
//                        val chatBoxRecord = ChatBoxRecord()
//                        chatBoxRecord.content = binding.editTextTextPersonName.text.toString()
//                        chatBoxRecord.sender = UserManager.user?.id.toString()
//                        chatBoxRecord.senderphoto = UserManager.user?.picture.toString()
//                        chatBoxRecord.time = System.currentTimeMillis()
//                        chatBoxRecord.id = chatRoomId
//                        viewModel.sendChat(chatRoomId, chatBoxRecord)
//                        Logger.i("viewModel.sendChat chatRoomId=>${chatRoomId},chatBoxRecord=> ${chatBoxRecord}")
//                    }
//                }
//            })
        }


//        if (it == null) {
//            viewModel.addChatroomResult()
//            viewModel.getGroupChatroomResult()
//        }else{
//            if (MainApplication.instance.isLiveDataDesign()) {
//                viewModel.getLiveChatsResult()
//            } else {
//                viewModel.getChatsResult()
//            }
//        }

        binding.imageViewBack.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root

    }

}