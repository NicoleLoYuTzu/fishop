package com.nicole.fishop.ext

import android.app.Activity
import android.view.Gravity
import android.widget.Toast
import com.nicole.fishop.FishopApplication
import com.nicole.fishop.factory.ViewModelFactory

fun Activity.getVmFactory(): ViewModelFactory {
    val repository = (applicationContext as FishopApplication).repository
    return ViewModelFactory(repository)
}

fun Activity?.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).apply {
        setGravity(Gravity.CENTER, 0, 0)
        show()
    }
}
