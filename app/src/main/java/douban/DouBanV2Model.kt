package douban


data class TagFilmList(
    val subjects: List<Subject>
)

data class Subject(
    val rate: String,
    val title: String,
    val url: String,
    val playable: Boolean,
    val cover: String,
    val id: String,
    val is_new: Boolean
)
