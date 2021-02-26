package org.antmobile.ah.rijksmuseum.data.remote

import com.google.common.truth.Truth.assertThat
import org.antmobile.ah.rijksmuseum.data.remote.entities.collections.ArtEntity
import org.antmobile.ah.rijksmuseum.data.remote.entities.common.WebImageEntity
import org.antmobile.ah.rijksmuseum.data.remote.entities.details.ArtDetailsEntity
import org.junit.Test

class MapperTest {

    @Test
    fun `when art entity has all data then art should also have it`() {
        val id = "someId"
        val title = "someTile"
        val longTitle = "someLongTitle"
        val principalOrFirstMaker = "somePrincipalOrFirstMaker"
        val webImage = WebImageEntity("someUrl")
        val artEntity = ArtEntity(id, title, longTitle, principalOrFirstMaker, webImage)

        val art = artEntity.toArt()
        assertThat(art.id).isEqualTo(id)
        assertThat(art.title).isEqualTo(title)
        assertThat(art.longTitle).isEqualTo(longTitle)
        assertThat(art.principalOrFirstMaker).isEqualTo(principalOrFirstMaker)
        assertThat(art.imageUrl).isEqualTo(webImage.url)
    }

    @Test
    fun `when art entity with nullable webImage then image url should be empty string`() {
        val artEntity = ArtEntity("", "", "", "", null)
        val art = artEntity.toArt()
        assertThat(art.imageUrl).isEmpty()
    }

    @Test
    fun `when art details entity has all data then art details should also have it`() {
        val id = "someId"
        val title = "someTile"
        val description = "description"
        val webImage = WebImageEntity("someUrl")
        val artDetailsEntity = ArtDetailsEntity(id, title, description, webImage)

        val artDetails = artDetailsEntity.toArtDetails()
        assertThat(artDetails.id).isEqualTo(id)
        assertThat(artDetails.title).isEqualTo(title)
        assertThat(artDetails.description).isEqualTo(description)
        assertThat(artDetails.imageUrl).isEqualTo(webImage.url)
    }

    @Test
    fun `when art details entity with nullable webImage then image url should be empty string`() {
        val artDetailsEntity = ArtDetailsEntity("", "", "", null)
        val artDetails = artDetailsEntity.toArtDetails()
        assertThat(artDetails.imageUrl).isEmpty()
    }

    @Test
    fun `when art details entity with nullable description then description in art details should be empty string`() {
        val artDetailsEntity = ArtDetailsEntity("", "", null, null)
        val artDetails = artDetailsEntity.toArtDetails()
        assertThat(artDetails.description).isEmpty()
    }
}

