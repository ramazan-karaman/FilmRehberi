package com.example.filmrehberi.ui.home

import com.example.filmrehberi.data.local.MovieEntity
import com.example.filmrehberi.data.repository.MovieRepository
import io.mockk.MockKAnnotations
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @RelaxedMockK
    private lateinit var movieRepository: MovieRepository

    private lateinit var homeViewModel: HomeViewModel

    private val testDispatcher= StandardTestDispatcher()

    private val mockMoviesFlow= MutableStateFlow<List<MovieEntity>>(emptyList())

    @Before
    fun setUp(){
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)

        every { movieRepository.getMovies() } returns mockMoviesFlow
        coJustRun { movieRepository.searchMovies(any()) }

        homeViewModel = HomeViewModel(movieRepository)

        testDispatcher.scheduler.runCurrent()
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }

    @Test
    fun `onSearchQueryChanged, uiState'in searchQuery'sini günceller`() {
        runTest(testDispatcher){

            val job = backgroundScope.launch() {
                homeViewModel.uiState.collect()
            }

            testDispatcher.scheduler.runCurrent()
            assertEquals("batman", homeViewModel.uiState.value.searchQuery)

            val testQuery= "superman"

            homeViewModel.onSearchQueryChanged(testQuery)

            testDispatcher.scheduler.runCurrent()

            val guncelState= homeViewModel.uiState.value
            assertEquals(testQuery, guncelState.searchQuery)

            job.cancel()
        }
    }

    @Test
    fun `performSearch çağrıldığında, repository'yi doğru sorguyla çağırır`() {
        runTest(testDispatcher) {
            val job= backgroundScope.launch() {
                homeViewModel.uiState.collect()
            }
            testDispatcher.scheduler.runCurrent()
            val testQuery = "superman"
            homeViewModel.onSearchQueryChanged(testQuery)

            testDispatcher.scheduler.runCurrent()

            homeViewModel.performSearch()

            testDispatcher.scheduler.advanceUntilIdle()

            coVerify(exactly = 1) { movieRepository.searchMovies("superman") }
            coVerify(exactly = 1) { movieRepository.searchMovies("batman") }
            job.cancel()
        }
    }
}