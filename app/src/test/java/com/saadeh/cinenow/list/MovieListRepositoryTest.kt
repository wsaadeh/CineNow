package com.saadeh.cinenow.list

import android.net.ipsec.ike.exceptions.InvalidKeException
import androidx.compose.ui.text.font.emptyCacheFontFamilyResolver
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.saadeh.cinenow.common.data.local.MovieCategory
import com.saadeh.cinenow.common.data.model.Movie
import com.saadeh.cinenow.list.data.LocalDataSource
import com.saadeh.cinenow.list.data.MovieListRepository
import com.saadeh.cinenow.list.data.local.MovieListLocalDataSource
import com.saadeh.cinenow.list.data.remote.MovieListRemoteDataSource
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.net.UnknownHostException
import java.security.InvalidKeyException


class MovieListRepositoryTest {

    //private val localm: MovieListLocalDataSource = mock()
    private val local = FakeMovieListLocalDataSource()
    private val remote: MovieListRemoteDataSource = mock()

    private val underTest by lazy {
        MovieListRepository(
            local = local,
            remote = remote
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Given no internet connection when getting now playing movies then return local data`() {
        runTest(UnconfinedTestDispatcher()) {
            //Given
            val ex = Result.failure<List<Movie>>(UnknownHostException("No internet connection"))
            val localList = listOf(
                Movie(
                    id = 1,
                    title = "title1",
                    overview = "overview1",
                    image = "image1",
                    category = MovieCategory.NowPlaying.name
                )
            )
            whenever(remote.getNowPlaying()).thenReturn(ex)
            local.nowPlaying = localList
            //whenever(localm.getNowPLayingMovies()).thenReturn(localList)
            //Then
            val result = underTest.getNowPlaying()
            //When
            val expected = Result.success(localList)
            //val expected = ex
            assertEquals(expected, result)

        }
    }

    @Test
    fun `Given no internet connection when getting popular movies then return local data`() {
        runTest {
            //Given
            val localList = listOf(
                Movie(
                    id = 1,
                    title = "title1",
                    overview = "overview1",
                    image = "image1",
                    category = MovieCategory.Popular.name
                )
            )
            whenever(remote.getPopular()).thenReturn(Result.failure(UnknownHostException("No internet connection")))
            local.popular = localList
            //whenever(local.getPopularMovies()).thenReturn(localList)
            //Then
            val result = underTest.getPopular()
            //When
            val expected = Result.success(localList)
            assertEquals(expected, result)
        }
    }

    @Test
    fun `Given no internet connection when getting topRated movies then return local data`() {
        runTest {
            //Given
            val localList = listOf(
                Movie(
                    id = 1,
                    title = "title1",
                    overview = "overview1",
                    image = "image1",
                    category = MovieCategory.TopRated.name
                )
            )
            whenever(remote.getTopRated()).thenReturn(Result.failure(UnknownHostException("No internet connection")))
            local.topRated = localList
            //whenever(local.getTopRatedMovies()).thenReturn(localList)
            //Then
            val result = underTest.getTopRated()
            //When
            val excepted = Result.success(localList)
            assertEquals(excepted, result)
        }
    }

    @Test
    fun `Given no internet connection when getting upComing movies then return local data`() {
        runTest {
            //Given
            val localList = listOf(
                Movie(
                    id = 1,
                    title = "title1",
                    overview = "overview1",
                    image = "image1",
                    category = MovieCategory.Upcoming.name
                )
            )
            whenever(remote.getUpcoming()).thenReturn(Result.failure(UnknownHostException("No internet connection")))
            local.upcoming = localList
            //whenever(local.getUpcomingMovies()).thenReturn(localList)

            //Then
            val result = underTest.getUpcoming()
            //When
            val expected = Result.success(localList)
            assertEquals(expected, result)
        }
    }

    @Test
    fun `Given no internet connection and no local data when getting now playing movies then return remote result`() {
        runTest {
            //Given
            val remoteResult =
                Result.failure<List<Movie>>(UnknownHostException("No internet connection"))
            whenever(remote.getNowPlaying()).thenReturn(remoteResult)
            local.nowPlaying = emptyList()
            //whenever(local.getNowPLayingMovies()).thenReturn(emptyList())
            //Then
            val result = underTest.getNowPlaying()
            //When
            val expected = remoteResult
            assertEquals(expected, result)
        }
    }

    @Test
    fun `Given no internet connection and no local data when getting popular movies then return remote result`() {
        runTest {
            //Given
            val remoteResult =
                Result.failure<List<Movie>>(UnknownHostException("No internet connection"))
            whenever(remote.getPopular()).thenReturn(remoteResult)
            local.popular = emptyList()
            //whenever(local.getPopularMovies()).thenReturn(emptyList())
            //Then
            val result = underTest.getPopular()
            //When
            val expected = remoteResult
            assertEquals(expected, result)
        }
    }

    @Test
    fun `Given no internet connection and no local data when getting topRated movies then result remote result`() {
        runTest {
            //Given
            val remoteResult =
                Result.failure<List<Movie>>(UnknownHostException("No internet connection"))
            whenever(remote.getTopRated()).thenReturn(remoteResult)
            local.topRated = emptyList()
            //whenever(local.getTopRatedMovies()).thenReturn(emptyList())
            //Then
            val result = underTest.getTopRated()
            //When
            val expected = remoteResult
            assertEquals(expected, result)
        }
    }

    @Test
    fun `Given no internet connection and no local data when getting Upcoming movies then result remote result`() {
        runTest {
            //Given
            val remoteResult =
                Result.failure<List<Movie>>(UnknownHostException("No internet connection"))
            whenever(remote.getUpcoming()).thenReturn(remoteResult)
            local.upcoming = emptyList()
            //whenever(local.getUpcomingMovies()).thenReturn(emptyList())
            //Then
            val result = underTest.getUpcoming()
            //When
            val expected = remoteResult
            assertEquals(expected, result)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Given remote success when getting now playing movies then update local data`() {
        runTest(UnconfinedTestDispatcher()) {
            //Given
            val list = listOf(
                Movie(
                    id = 1,
                    title = "title1",
                    overview = "overview1",
                    image = "image1",
                    category = MovieCategory.NowPlaying.name
                )
            )

            val remoteResult = Result.success(list)
            whenever(remote.getNowPlaying()).thenReturn(remoteResult)
            local.nowPlaying = list
            //whenever(local.getNowPLayingMovies()).thenReturn(list)
            //Then
            val result = underTest.getNowPlaying()
            //When
            val expected = Result.success(list)
            assertEquals(expected, result)
            assertEquals(local.updateItems, list)
            //verify(local).updateLocalItems(list)
        }
    }

    @Test
    fun `Given remote success when getting popular movies then update local data`() {
        runTest {
            //Given
            val list = listOf(
                Movie(
                    id = 1,
                    title = "title1",
                    overview = "overview1",
                    image = "image1",
                    category = MovieCategory.Popular.name
                )
            )

            val remoteResult = Result.success(list)
            whenever(remote.getPopular()).thenReturn(remoteResult)
            local.popular = list
            //whenever(local.getPopularMovies()).thenReturn(list)
            //Then
            val result = underTest.getPopular()
            //When
            val expected = Result.success(list)
            assertEquals(expected, result)
            assertEquals(local.updateItems, list)
            //verify(local).updateLocalItems(list)
        }
    }

    @Test
    fun `Given remote success when getting topRated movies then update local data`() {
        runTest {
            //Given
            val list = listOf(
                Movie(
                    id = 1,
                    title = "title1",
                    overview = "overview1",
                    image = "image1",
                    category = MovieCategory.TopRated.name
                )
            )

            val remoteResult = Result.success(list)
            whenever(remote.getTopRated()).thenReturn(remoteResult)
            local.topRated = list
            //whenever(local.getTopRatedMovies()).thenReturn(list)
            //Then
            val result = underTest.getTopRated()
            //When
            val expected = Result.success(list)
            assertEquals(expected, result)
            assertEquals(local.updateItems, list)
            //verify(local).updateLocalItems(list)
        }
    }

    @Test
    fun `Given remote success when getting upComing movies then update local data`() {
        runTest {
            //Given
            val list = listOf(
                Movie(
                    id = 1,
                    title = "title1",
                    overview = "overview1",
                    image = "image1",
                    category = MovieCategory.Upcoming.name
                )
            )

            val remoteResult = Result.success(list)
            whenever(remote.getUpcoming()).thenReturn(remoteResult)
            local.upcoming = list
            //whenever(local.getUpcomingMovies()).thenReturn(list)
            //Then
            val result = underTest.getUpcoming()
            //When
            val expected = Result.success(list)
            assertEquals(expected, result)
            assertEquals(local.updateItems, list)
            //verify(local).updateLocalItems(list)
        }
    }

//    @Test
//    fun `Given no internet connection and no local throw an exception when getting now playing movies then return failure`() {
//        runTest {
//            //Given
//            val remoteResult = Result.failure<List<Movie>>(UnknownHostException("No internet connection"))
//            val exception = UnknownHostException("error")
//            whenever(remote.getNowPlaying()).thenReturn(remoteResult)
//            whenever(local.getNowPLayingMovies()).thenThrow(exception)
//            //Then
//            val result = underTest.getNowPlaying()
//            //When
//            val expected = Result.failure<List<Movie>>(exception)
//            assertEquals(expected, result)
//        }
//    }
}