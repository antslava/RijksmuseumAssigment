package org.antmobile.rijksmuseum.app.list

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.antmobile.rijksmuseum.domain.models.Art
import org.junit.Test

@ExperimentalCoroutinesApi
class ArtListUiItemMergerTest {

    private val merger = ArtListUiItemMerger(Dispatchers.Unconfined)
    private val artOne = Art(
        "oneId",
        "oneTitle",
        "oneLongTitle",
        "principalOrFirstMaker"
    )
    private val artTwo = Art(
        "twoId",
        "twoTitle",
        "twoLongTitle",
        artOne.principalOrFirstMaker
    )
    private val artThree = Art(
        "threeId",
        "threeTitle",
        "threeLongTitle",
        "threePrincipalOrFirstMaker"
    )
    private val oldItemsWithLoadMore = arrayListOf(
        ArtListUiItem.Section(artOne.principalOrFirstMaker),
        ArtListUiItem.Item(artOne),
        ArtListUiItem.Item(artTwo),
        ArtListUiItem.LoadMore
    )
    private val additionalItems = arrayListOf(artThree)

    @Test
    fun `when all list are empty and there no more data to load then empty list should be returned`() =
        runBlockingTest {
            val result = merger.merge(
                oldItems = emptyList(),
                additionalItems = emptyList(),
                canLoadMore = false
            )
            assertThat(result).isEmpty()
        }

    @Test
    fun `when can load more is true then last item should be load more`() = runBlockingTest {
        val additionalItemsSectionAmount = 1
        val expectedSize =
            oldItemsWithLoadMore.size + additionalItems.size + additionalItemsSectionAmount
        val result = merger.merge(
            oldItems = oldItemsWithLoadMore,
            additionalItems = additionalItems,
            canLoadMore = true
        )
        assertThat(result).hasSize(expectedSize)
        assertThat(result.last()).isInstanceOf(ArtListUiItem.LoadMore::class.java)
    }

    @Test
    fun `when can load more is true then list should contain one load more item`() =
        runBlockingTest {
            val result = merger.merge(
                oldItems = oldItemsWithLoadMore,
                additionalItems = additionalItems,
                canLoadMore = true
            )
            assertThat(result.filterIsInstance<ArtListUiItem.LoadMore>()).hasSize(1)
            assertThat(result.last()).isInstanceOf(ArtListUiItem.LoadMore::class.java)
        }

    @Test
    fun `when can load more is false then last item should not be load more`() = runBlockingTest {
        val additionalItemsSectionAmount = 1
        val expectedSize =
            oldItemsWithLoadMore.size - 1 + additionalItems.size + additionalItemsSectionAmount
        val result = merger.merge(
            oldItems = oldItemsWithLoadMore,
            additionalItems = additionalItems,
            canLoadMore = false
        )
        assertThat(result).hasSize(expectedSize)
        assertThat(result.last()).isNotInstanceOf(ArtListUiItem.LoadMore::class.java)
    }

    @Test
    fun `when can load more is false then list should not contain load more item`() =
        runBlockingTest {
            val result = merger.merge(
                oldItems = oldItemsWithLoadMore,
                additionalItems = additionalItems,
                canLoadMore = false
            )
            assertThat(result).doesNotContain(ArtListUiItem.LoadMore)
        }

    @Test
    fun `verify that result will contain correct headers`() = runBlockingTest {
        val additionalItems = listOf(artOne, artTwo, artThree)
        val correctAmountOfHeaders = 2
        val firstHeader = ArtListUiItem.Section(artOne.principalOrFirstMaker)
        val secondHeader = ArtListUiItem.Section(artThree.principalOrFirstMaker)

        val result = merger.merge(
            oldItems = oldItemsWithLoadMore,
            additionalItems = additionalItems,
            canLoadMore = false
        )

        val amountOfHeaders = result.filterIsInstance(ArtListUiItem.Section::class.java).size
        assertThat(amountOfHeaders).isEqualTo(correctAmountOfHeaders)
        assertThat(result).containsAtLeast(firstHeader, secondHeader)
    }

    @Test
    fun `verify that result has a correct order`() = runBlockingTest {
        val firstHeader = ArtListUiItem.Section(artOne.principalOrFirstMaker)
        val secondHeader = ArtListUiItem.Section(artThree.principalOrFirstMaker)
        val correctOrder = listOf(
            firstHeader,
            ArtListUiItem.Item(artOne),
            ArtListUiItem.Item(artTwo),
            secondHeader,
            ArtListUiItem.Item(artThree),
            ArtListUiItem.LoadMore
        )

        val result = merger.merge(
            oldItems = oldItemsWithLoadMore,
            additionalItems = additionalItems,
            canLoadMore = true
        )

        assertThat(result).isEqualTo(correctOrder)
    }

}

