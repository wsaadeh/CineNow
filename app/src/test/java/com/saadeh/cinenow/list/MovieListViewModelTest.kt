package com.saadeh.cinenow.list

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.saadeh.cinenow.common.data.local.MovieCategory
import com.saadeh.cinenow.common.data.model.Movie
import com.saadeh.cinenow.list.data.MovieListRepository
import com.saadeh.cinenow.list.presentation.MovieListViewModel
import com.saadeh.cinenow.list.presentation.ui.MovieListUiState
import com.saadeh.cinenow.list.presentation.ui.MovieUiData
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test


class MovieListViewModelTest {

    private val repository: MovieListRepository = mock()
    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher(TestCoroutineScheduler())

    private val underTest by lazy {
        MovieListViewModel(
            repository,
            testDispatcher
        )
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