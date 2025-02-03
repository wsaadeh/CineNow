package com.saadeh.cinenow.list

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.saadeh.cinenow.CineNowApplication
import com.saadeh.cinenow.common.data.local.MovieCategory
import com.saadeh.cinenow.common.data.model.Movie
import com.saadeh.cinenow.list.data.MovieListRepository
import com.saadeh.cinenow.list.presentation.MovieListViewModel
import com.saadeh.cinenow.list.presentation.ui.MovieListUiState
import com.saadeh.cinenow.list.presentation.ui.MovieUiData
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.net.UnknownHostException
import kotlin.math.exp


class MovieListViewModelTest {

    private val application: CineNowApplication = mock()
    private lateinit var factory: ViewModelProvider.Factory
    private val repository: MovieListRepository = mock()
    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher(TestCoroutineScheduler())

    private val underTest by lazy {
        MovieListViewModel(
            repository,
            testDispatcher
        )
    }

/*    @Before
    fun `setUp test of cinenow application in companion object`(){

        //Setup the application mock to return the mocked repository
        whenever(application.repository).thenReturn(repository)

        //Create the factory
        factory = MovieListViewModel.Factory

    }*/

    @Test
    fun `Test create viewModel`(){
        val extras = mock<CreationExtras>()
        whenever(extras[APPLICATION_KEY]).thenReturn(application)

        val viewModel = factory.create(MovieListViewModel::class.java, extras)

        assertTrue(viewModel is MovieListViewModel)


        val movieListViewModel = viewModel as MovieListViewModel
        assertNotNull(repository)
        //assertTrue(repository)
    }


    @Test
    fun `Given fresh viewModel when collecting to Upcoming Then assert expected value`() {
        runTest {
            //Given
            val movies = listOf(
                Movie(
                    id = 1,
                    title = "title1",
                    overview = "overview1",
                    image = "image1",
                    category = MovieCategory.Upcoming.name
                )
            )

            whenever(repository.getUpcoming()).thenReturn(Result.success(movies))
            //launch { whenever(repository.getTopRated()).thenReturn(Result.success(movies)) }
            //advanceUntilIdle()

            //When
            underTest.uiUpcoming.test {
                //Then assert expected value
                val expected = MovieListUiState(
                    list = listOf(
                        MovieUiData(
                            id = 1,
                            title = "title1",
                            overview = "overview1",
                            image = "image1",
                        )
                    )
                )

                assertEquals(expected, awaitItem())
            }



        }
    }

    @Test
    fun `Given fresh viewModel when collecting to Popular Then assert expected value`() {
        runTest {
            //Given
            val movies = listOf(
                Movie(
                    id = 1,
                    title = "title1",
                    overview = "overview1",
                    image = "image1",
                    category = MovieCategory.Popular.name
                )
            )

            whenever(repository.getPopular()).thenReturn(Result.success(movies))
            //launch { whenever(repository.getTopRated()).thenReturn(Result.success(movies)) }
            //advanceUntilIdle()

            //When
            underTest.uiPopular.test {
                //Then assert expected value
                val expected = MovieListUiState(
                    list = listOf(
                        MovieUiData(
                            id = 1,
                            title = "title1",
                            overview = "overview1",
                            image = "image1",
                        )
                    )
                )

                assertEquals(expected, awaitItem())
            }



        }
    }

    @Test
    fun `Given fresh viewModel when collection to NowPlaying throw unknown exception`(){
        runTest {
            //Given
            val ex = Result.failure<List<Movie>>(UnknownError("Unknown Error"))
            whenever(repository.getNowPlaying()).thenReturn(ex)

            //Then
            underTest.uiNowPlaying.test {
                val expected = MovieListUiState(
                    isError = true
                )

            //When
            assertEquals(expected,awaitItem())
            }
        }
    }

    @Test
    fun `Given fresh viewModel when collection to Popular throw unknown exception`(){
        runTest {
            //Given
            val ex = Result.failure<List<Movie>>(UnknownError("Unknown Error"))
            whenever(repository.getPopular()).thenReturn(ex)

            //Then
            underTest.uiPopular.test {
                val expected = MovieListUiState(
                    isError = true
                )

                //When
                assertEquals(expected,awaitItem())
            }
        }
    }

    @Test
    fun `Given fresh viewModel when collection to TopRated throw unknown exception`(){
        runTest {
            //Given
            val ex = Result.failure<List<Movie>>(UnknownError("Unknown Error"))
            whenever(repository.getTopRated()).thenReturn(ex)

            //Then
            underTest.uiTopRated.test {
                val expected = MovieListUiState(
                    isError = true
                )

                //When
                assertEquals(expected,awaitItem())
            }
        }
    }

    @Test
    fun `Given fresh viewModel when collection to Upcoming throw unknown exception`(){
        runTest {
            //Given
            val ex = Result.failure<List<Movie>>(UnknownError("Unknown Error"))
            whenever(repository.getUpcoming()).thenReturn(ex)

            //Then
            underTest.uiUpcoming.test {
                val expected = MovieListUiState(
                    isError = true
                )

                //When
                assertEquals(expected,awaitItem())
            }
        }
    }


    @Test
    fun `Given fresh viewModel when collection to NowPlaying Then throw a exception`(){
        runTest {
            //Given
            val ex = Result.failure<List<Movie>>(UnknownHostException("No internet connection"))
            whenever(repository.getNowPlaying()).thenReturn(ex)

            //Then

            underTest.uiNowPlaying.test {
                val expected =
                MovieListUiState(
                isError = true,
                errorMessage = "No internet connection"
            )

            //When
            assertEquals(expected,awaitItem())
            }
        }
    }

    @Test
    fun `Given fresh viewModel when collection to Popular Then throw a exception`(){
        runTest {
            //Given
            val ex = Result.failure<List<Movie>>(UnknownHostException("No internet connection"))
            whenever(repository.getPopular()).thenReturn(ex)

            //Then

            underTest.uiPopular.test {
                val expected =
                    MovieListUiState(
                        isError = true,
                        errorMessage = "No internet connection"
                    )

                //When
                assertEquals(expected,awaitItem())
            }
        }
    }

    @Test
    fun `Given fresh viewModel when collection to TopRated Then throw a exception`(){
        runTest {
            //Given
            val ex = Result.failure<List<Movie>>(UnknownHostException("No internet connection"))
            whenever(repository.getTopRated()).thenReturn(ex)

            //Then

            underTest.uiTopRated.test {
                val expected =
                    MovieListUiState(
                        isError = true,
                        errorMessage = "No internet connection"
                    )

                //When
                assertEquals(expected,awaitItem())
            }
        }
    }

    @Test
    fun `Given fresh viewModel when collection to Upcoming Then throw a exception`(){
        runTest {
            //Given
            val ex = Result.failure<List<Movie>>(UnknownHostException("No internet connection"))
            whenever(repository.getUpcoming()).thenReturn(ex)

            //Then

            underTest.uiUpcoming.test {
                val expected =
                    MovieListUiState(
                        isError = true,
                        errorMessage = "No internet connection"
                    )

                //When
                assertEquals(expected,awaitItem())
            }
        }
    }

    @Test
    fun `Given fresh viewModel when collecting to NowPlaying Then assert expected value`() {
        runTest {
            //Given
            val movies = listOf(
                Movie(
                    id = 1,
                    title = "title1",
                    overview = "overview1",
                    image = "image1",
                    category = MovieCategory.NowPlaying.name
                )
            )

            whenever(repository.getNowPlaying()).thenReturn(Result.success(movies))
            //launch { whenever(repository.getTopRated()).thenReturn(Result.success(movies)) }
            //advanceUntilIdle()

            //When
            underTest.uiNowPlaying.test {
                //Then assert expected value
                val expected = MovieListUiState(
                    list = listOf(
                        MovieUiData(
                            id = 1,
                            title = "title1",
                            overview = "overview1",
                            image = "image1",
                        )
                    )
                )

                assertEquals(expected, awaitItem())
            }



        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Given fresh viewModel when collecting to topRated Then assert expected value`() {
        runTest(UnconfinedTestDispatcher()) {
            //Given
            val movies = listOf(
                Movie(
                    id = 1,
                    title = "title1",
                    overview = "overview1",
                    image = "image1",
                    category = MovieCategory.TopRated.name
                )
            )

            whenever(repository.getTopRated()).thenReturn(Result.success(movies))
            //launch { whenever(repository.getTopRated()).thenReturn(Result.success(movies)) }
            //advanceUntilIdle()

            //When
            val result = underTest.uiTopRated.value

            //Then assert expected value
            val expected = MovieListUiState(
                list = listOf(
                    MovieUiData(
                        id = 1,
                        title = "title1",
                        overview = "overview1",
                        image = "image1",
                    )
                )
            )

            assertEquals(expected, result)

        }
    }

    @Test
    fun `Given fresh viewModel when collecting to topRated Then assert loading state`() {
        runTest {
            //Given


            //When
            val result = underTest.uiTopRated.value

            //Then assert expected value
            val expected = MovieListUiState(
                isLoading = true
            )

            assertEquals(expected, result)

        }
    }
}