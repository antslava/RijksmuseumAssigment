package org.antmobile.ah.rijksmuseum.domain.usecases

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.antmobile.ah.rijksmuseum.domain.exceptions.WrongArtIdException
import org.antmobile.ah.rijksmuseum.domain.repositories.ArtsRepository
import org.antmobile.ah.rijksmuseum.utils.CoroutinesDispatcherProvider
import org.antmobile.ah.rijksmuseum.utils.Result
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyZeroInteractions

@ExperimentalCoroutinesApi
class GetArtDetailsByIdUseCaseTest {

    private val testDispatcher = TestCoroutineDispatcher()
    private val dispatchers = CoroutinesDispatcherProvider(
        testDispatcher,
        testDispatcher,
        testDispatcher
    )
    private lateinit var useCase: GetArtDetailsByIdUseCase
    private lateinit var fakeRepository: ArtsRepository

    @Before
    fun setUp() {
        fakeRepository = Mockito.mock(ArtsRepository::class.java)
        useCase = GetArtDetailsByIdUseCase(fakeRepository, dispatchers)
    }

    @Test
    fun `when artId is empty string then proper error should be returned`() =
        testDispatcher.runBlockingTest {
            val result = useCase.execute("")

            verifyZeroInteractions(fakeRepository)
            assertThat(result).isInstanceOf(Result.Failure::class.java)
            val failure = result as Result.Failure
            assertThat(failure.exception).isInstanceOf(WrongArtIdException::class.java)
        }

    @Test
    fun `when artId is blank then proper error should be returned`() =
        testDispatcher.runBlockingTest {
            val result = useCase.execute("   ")

            verifyZeroInteractions(fakeRepository)
            assertThat(result).isInstanceOf(Result.Failure::class.java)
            val failure = result as Result.Failure
            assertThat(failure.exception).isInstanceOf(WrongArtIdException::class.java)
        }

    @Test
    fun `when artId is not empty or blank then it should request proper art`() =
        testDispatcher.runBlockingTest {
            val artId = "10"
            useCase.execute(artId)

            verify(fakeRepository).getArt(artId)
        }
}