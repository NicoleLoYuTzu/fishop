package com.nicole.fishop.util

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.nicole.fishop.data.source.DefaultFishopRepository
import com.nicole.fishop.data.source.FishopRepository
import com.nicole.fishop.data.source.remote.FishopRemoteDataSource

/**
 * Created by Wayne Chen on 2020-01-15.
 *
 * A Service Locator for the [FishopRepository].
 */
object ServiceLocator {

    @Volatile
    var repository: FishopRepository? = null
        @VisibleForTesting set

    fun provideRepository(context: Context): FishopRepository {
        synchronized(this) {
            return repository
                ?: repository
                ?: createFishopRepository(context)
        }
    }

    private fun createFishopRepository(context: Context): FishopRepository {
        return DefaultFishopRepository(
            FishopRemoteDataSource
        )
    }
}
