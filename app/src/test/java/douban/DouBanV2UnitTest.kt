package douban

import org.junit.Assert
import org.junit.Test

class DouBanV2UnitTest {

    @Test
    fun test_getTagFilmList_fromNet() {
        val film = DouBanV2.getTagFilm(DouBanV2.FilmTagType.Action)
        Assert.assertNotNull(film)
        Assert.assertNotNull(film.subjects)
        Assert.assertTrue(film.subjects.isNotEmpty())
        Assert.assertNotNull(film.subjects[0].id)
        Assert.assertNotNull(film.subjects[0].url)
    }

}