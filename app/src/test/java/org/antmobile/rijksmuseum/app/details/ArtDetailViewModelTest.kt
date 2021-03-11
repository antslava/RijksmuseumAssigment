package org.antmobile.rijksmuseum.app.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jraska.livedata.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.antmobile.rijksmuseum.app.converters.ErrorConverter
import org.antmobile.rijksmuseum.domain.models.ArtDetails
import org.antmobile.rijksmuseum.domain.usecases.GetArtDetailsByIdUseCase
import org.antmobile.rijksmuseum.utils.Result
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

@ExperimentalCoroutinesApi
class ArtDetailViewModelTest {

    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    private val testDispatcher = TestCoroutineDispatcher()

    private var artId = "artId"
    private val artDetails = ArtDetails(
        artId,
        "title",
        "description",
        "imageUrl"
    )

    private lateinit var viewModel: ArtDetailViewModel

    @Mock
    private lateinit var getArtDetailsByIdUseCase: GetArtDetailsByIdUseCase

    @Mock
    private lateinit var errorConverter: ErrorConverter

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = ArtDetailViewModel(
            getArtDetailsByIdUseCase,
            errorConverter
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `verify initial state of VM`() {
        viewModel.showProgress.test()
            .assertNoValue()
        viewModel.uiState.test()
            .assertNoValue()
    }

    @Test
    fun `verify that during successful details loading user will saw loading state`() =
        testDispatcher.runBlockingTest {
            `when`(getArtDetailsByIdUseCase.execute(artId)).thenReturn(Result.Success(artDetails))
            val showProgressObserver = viewModel.showProgress.test()

            viewModel.loadDetails(artId)

            showProgressObserver.assertValueHistory(true, false)
        }

    @Test
    fun `verify that during unsuccessful details loading user will saw loading state`() =
        testDispatcher.runBlockingTest {
            val exception = Exception()
            `when`(getArtDetailsByIdUseCase.execute(artId)).thenReturn(Result.Failure(exception))
            `when`(errorConverter.toErrorMessage(exception)).thenReturn("error message")
            val showProgressObserver = viewModel.showProgress.test()

            viewModel.loadDetails(artId)

            showProgressObserver.assertValueHistory(true, false)
        }

    @Test
    fun `multiple calls of loadDetails should be ignored until there is still active loading`() =
        testDispatcher.runBlockingTest {
            `when`(getArtDetailsByIdUseCase.execute(artId)).thenReturn(Result.Success(artDetails))

            pauseDispatcher()
            viewModel.loadDetails(artId)
            viewModel.loadDetails(artId)
            viewModel.loadDetails(artId)
            resumeDispatcher()

            verify(getArtDetailsByIdUseCase).execute(artId)
        }
}
