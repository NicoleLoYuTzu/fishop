package com.nicole.fishop.data

sealed class ChatItem {

    abstract val createdTime: Long

    data class MySide(val chatRecord: ChatBoxRecord) : ChatItem() {
        override val createdTime: Long
            get() = chatRecord.time
    }
    data class TheOtherSide(val chatRecord: ChatBoxRecord) : ChatItem() {
        override val createdTime: Long
            get() = chatRecord.time
    }
}
