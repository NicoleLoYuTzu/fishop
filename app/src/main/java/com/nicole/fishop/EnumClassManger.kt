package com.nicole.fishop

enum class LoadApiStatus {
    LOADING,
    ERROR,
    DONE
}


enum class Mode(val index: Int) {
    BUYER(0), SELLER(1),
}

