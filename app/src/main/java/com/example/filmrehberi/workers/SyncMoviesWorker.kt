package com.example.filmrehberi.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.filmrehberi.data.repository.MovieRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class SyncMoviesWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val movieRepository: MovieRepository
) : CoroutineWorker(appContext,workerParams){
    override suspend fun doWork(): Result{
        return try {
            val defaultQuery= "batman"

            movieRepository.searchMovies(defaultQuery)

            Result.success()
        }catch (e: Exception){
            e.printStackTrace()
            Result.retry()
        }
    }
}