package com.nicole.fishop

import android.app.Application
import com.nicole.fishop.data.source.FishopRepository
import com.nicole.fishop.util.ServiceLocator
import kotlin.properties.Delegates

/**
 * An application that lazily provides a repository. Note that this Service Locator pattern is
 * used to simplify the sample. Consider a Dependency Injection framework.
 */
class FishopApplication : Application() {

    // Depends on the flavor,
    val repository: FishopRepository
        get() = ServiceLocator.provideRepository(this)

    companion object {
        var instance: FishopApplication by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun isLiveDataDesign() = true
}
