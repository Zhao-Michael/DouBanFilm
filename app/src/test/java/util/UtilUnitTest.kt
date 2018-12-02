package util

import douban.DouBanV1UnitTest
import org.junit.Assert
import org.junit.Test

class UtilUnitTest {


    @Test
    fun test_util_compress() {

        val str = DouBanV1UnitTest().get_strFilmDetail()

        val zip = Util.Compress(str.toByteArray())

        val re = Util.UnCompress(zip)

        val str1 = String(re)

        println("Compress percent : ${zip.size * 100f / str.length} %")

        Assert.assertTrue(str == str1)
    }


}