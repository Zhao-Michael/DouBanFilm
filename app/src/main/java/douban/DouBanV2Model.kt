package douban


data class TagFilmList(
        val subjects: List<Subject>
)

data class Subject(
        val rate: String,
        val title: String,
        val url: String,        //Not use
        val playable: Boolean,  //Not use
        val cover: String,
        val id: String,
        val is_new: Boolean     //Not use
)

data class CommonInfoList(
        val infoList: List<CommonInfo>
)

data class CommonInfo(
        val id: String,
        val cover: String,
        val isfilm: Boolean,
        val topright: String,
        val title: String
)

data class PhotoComment(val thumb: String, val author: String, val content: String, val time: String)
