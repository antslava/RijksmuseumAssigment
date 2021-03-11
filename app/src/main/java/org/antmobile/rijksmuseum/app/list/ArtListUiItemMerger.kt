package org.antmobile.rijksmuseum.app.list

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.antmobile.rijksmuseum.domain.models.Art

class ArtListUiItemMerger(
    private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun merge(
        oldItems: List<ArtListUiItem>,
        additionalItems: List<Art>,
        canLoadMore: Boolean
    ): List<ArtListUiItem> = withContext(ioDispatcher) {
        val cleanedOldItems = removeLoadMoreIfNeeded(oldItems)
        val lastSection = getLastSection(cleanedOldItems)
        val newItems = convertToArtsListItem(additionalItems, lastSection)

        return@withContext if (canLoadMore) {
            cleanedOldItems + newItems + listOf<ArtListUiItem>(ArtListUiItem.LoadMore)
        } else {
            cleanedOldItems + newItems
        }
    }

    private fun getLastSection(oldList: List<ArtListUiItem>): ArtListUiItem.Section? {
        return oldList.asReversed()
            .firstOrNull { it is ArtListUiItem.Section } as ArtListUiItem.Section?
    }

    private fun convertToArtsListItem(
        newItems: List<Art>,
        lastSection: ArtListUiItem.Section?
    ): List<ArtListUiItem> {
        val convertedList = newItems.groupBy { it.principalOrFirstMaker }
            .flatMap { (artist, arts) ->
                val section = listOf<ArtListUiItem>(ArtListUiItem.Section(artist))
                val items = arts.map { ArtListUiItem.Item(it) }
                return@flatMap section + items
            }

        if (lastSection == null) {
            return convertedList
        }

        val section = convertedList.firstOrNull() as? ArtListUiItem.Section
        return if (section == lastSection) {
            convertedList.drop(1)
        } else {
            convertedList
        }
    }


    private fun removeLoadMoreIfNeeded(oldItems: List<ArtListUiItem>): List<ArtListUiItem> {
        if (oldItems.isNotEmpty()) {
            val lastItem = oldItems.last()
            if (lastItem is ArtListUiItem.LoadMore) {
                return oldItems.dropLast(1)
            }
        }
        return oldItems
    }
}
