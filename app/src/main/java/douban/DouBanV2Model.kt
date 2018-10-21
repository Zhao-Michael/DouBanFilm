package douban


data class TagFilmList(
    val subjects: List<Subject>
)

data class Subject(
    val rate: String,
    val cover_x: Int,
    val title: String,
    val url: String,
    val playable: Boolean,
    val cover: String,
    val id: String,
    val cover_y: Int,
    val is_new: Boolean
)
