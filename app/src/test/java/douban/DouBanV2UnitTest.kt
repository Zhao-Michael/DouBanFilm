package douban

import org.junit.Test
import util.*

class DouBanV2UnitTest {

    @Test
    fun test_getSearchFilmList_fromNet() {
        val film = DouBanV2.getFilmPhoto("26865690", 10, 50)
    }

}