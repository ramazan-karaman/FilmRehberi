package com.example.filmrehberi.data.repository

import com.example.filmrehberi.data.local.MovieDao
import com.example.filmrehberi.data.local.MovieEntity
import com.example.filmrehberi.data.model.MovieSearchResponse
import com.example.filmrehberi.data.model.MovieSearchResult
import com.example.filmrehberi.data.network.ApiService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.slot
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieRepositoryTest {

    @RelaxedMockK
    private lateinit var apiService: ApiService

    @RelaxedMockK
    private lateinit var movieDao: MovieDao

    private lateinit var movieRepository: MovieRepository

    @Before
    fun setUp(){
        MockKAnnotations.init(this)

        movieRepository= MovieRepository(apiService,movieDao)
    }

    @Test
    fun `searchMovies çağrıldığında, api verisini entity'ye dönüştürüp dao'ya kaydeder`() {
        runTest {
            val fakeApiResult = MovieSearchResult(
                imdbID = "tt123",
                Title = "Test Filmi",
                Year = "2025",
                Poster = "url.jpg"
            )
            val fakeResponse = MovieSearchResponse(
                Search = listOf(fakeApiResult),
                totalResults = "1",
                Response = "True"
            )

            coEvery {
                apiService.searchMovies(any(), any())
            } returns fakeResponse

            val entityListSlot = slot<List<MovieEntity>>()

            movieRepository.searchMovies("batman")

            coVerify(exactly = 1) { movieDao.clearMovies() }
            coVerify(exactly = 1) { movieDao.insertMovies(capture(entityListSlot)) }

            val capturedList = entityListSlot.captured
            assertEquals(1, capturedList.size)
            assertEquals("tt123", capturedList[0].imdbID)
            assertEquals("Test Filmi", capturedList[0].title)
            assertEquals("url.jpg", capturedList[0].posterUrl)
        }
    }
}