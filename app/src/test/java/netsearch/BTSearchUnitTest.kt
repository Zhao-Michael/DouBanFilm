package netsearch


import douban.DouBanV2
import douban.DouBanV2.getTagTV
import org.junit.Assert
import org.junit.Test

class BTSearchUnitTest {

    @Test
    fun test_searchBT_fromNet() {
        val result = BTSearch.searchBT("La La Land")
        Assert.assertTrue(result.item.isNotEmpty())
        Assert.assertTrue(result.item[0].title.isNotBlank())
        Assert.assertTrue(result.item[0].url.isNotBlank())
        Assert.assertTrue(result.item[0].date.isNotBlank())
        Assert.assertTrue(result.item[0].length.isNotBlank())


        val detail = BTSearch.getBTDetail(result.item[0].url)
        Assert.assertTrue(detail.title.isNotBlank())
        Assert.assertTrue(detail.length.isNotBlank())
        Assert.assertTrue(detail.date.isNotBlank())
        Assert.assertTrue(detail.link.isNotBlank())
    }


}