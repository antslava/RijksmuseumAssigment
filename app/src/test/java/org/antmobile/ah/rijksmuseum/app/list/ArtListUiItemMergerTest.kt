package org.antmobile.ah.rijksmuseum.app.list

import com.google.common.truth.Truth.assertThat
import org.antmobile.ah.rijksmuseum.domain.models.Art
import org.junit.Test

class ArtListUiItemMergerTest {

    private val merger = ArtListUiItemMerger()
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
    private val threeTwo = Art(
        "threeId",
        "threeTitle",
        "threeLongTitle",
        "threePrincipalOrFirstMaker"
    )
    private val oldItems = arrayListOf(
        ArtListUiItem.Section(artOne.principalOrFirstMaker),
        ArtListUiItem.Item(artOne),
        ArtListUiItem.Item(artTwo)
    )
    private val additionalItems = arrayListOf(threeTwo)

    @Test
    fun `when all list are empty and there no more data to load then empty list should be returned`() {
        val result = merger.merge(
            oldItems = emptyList(),
            additionalItems = emptyList(),
            canLoadMore = false
        )
        assertThat(result).isEmpty()
    }

    @Test
    fun `when can load more is true then last item should be load more`() {
        val result = merger.merge(
            oldItems = oldItems,
            additionalItems = additionalItems,
            canLoadMore = true
        )
        assertThat(result).hasSize(oldItems.size + additionalItems.size + 2)
        assertThat(result.last()).isInstanceOf(ArtListUiItem.LoadMore::class.java)
    }

    @Test
    fun `when can load more is true then list should contain one load more item`() {
        val result = merger.merge(
            oldItems = oldItems,
            additionalItems = additionalItems,
            canLoadMore = true
        )
        assertThat(result.filterIsInstance<ArtListUiItem.LoadMore>()).hasSize(1)
        assertThat(result.last()).isInstanceOf(ArtListUiItem.LoadMore::class.java)
    }

    @Test
    fun `when can load more false then last item should not be load more`() {
        val result = merger.merge(
            oldItems = oldItems,
            additionalItems = additionalItems,
            canLoadMore = false
        )
        assertThat(result).hasSize(oldItems.size + additionalItems.size + 1)
        assertThat(result.last()).isNotInstanceOf(ArtListUiItem.LoadMore::class.java)
    }

    @Test
    fun `when can load more false then list should not contain load more item`() {
        val result = merger.merge(
            oldItems = oldItems,
            additionalItems = additionalItems,
            canLoadMore = false
        )
        assertThat(result).doesNotContain(ArtListUiItem.LoadMore)
    }
}

