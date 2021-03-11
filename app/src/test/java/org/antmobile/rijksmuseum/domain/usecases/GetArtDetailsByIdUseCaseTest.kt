package org.antmobile.rijksmuseum.domain.usecases

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.antmobile.rijksmuseum.domain.exceptions.WrongArtIdException
import org.antmobile.rijksmuseum.domain.repositories.ArtsRepository
import org.antmobile.rijksmuseum.utils.Result
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoInteractions

@ExperimentalCoroutinesApi
class GetArtDetailsByIdUseCaseTest {

    private val testDispatcher = TestCoroutineDispatcher()
    private lateinit var useCase: GetArtDetailsByIdUseCase
    private lateinit var fakeRepository: ArtsRepository

    @Before
    fun setUp() {
        fakeRepository = Mockito.mock(ArtsRepository::class.java)
        useCase = GetArtDetailsByIdUseCase(fakeRepository, testDispatcher)
    }

    @Test
    fun `when artId is empty string then proper error should be returned`() =
        testDispatcher.runBlockingTest {
            val result = useCase.execute("")

            verifyNoInteractions(fakeRepository)
            assertThat(result).isInstanceOf(Result.Failure::class.java)
            val failure = result as Result.Failure
            assertThat(failure.exception).isInstanceOf(WrongArtIdException::class.java)
        }

    @Test
    fun `when artId is blank then proper error should be returned`() =
        testDispatcher.runBlockingTest {
            val result = useCase.execute("   ")

            verifyNoInteractions(fakeRepository)
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
