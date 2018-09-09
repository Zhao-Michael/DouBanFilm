package douban

import com.google.gson.annotations.SerializedName
import java.util.*


data class FilmList(
        val count: Int,
        val start: Int,
        val total: Int,
        val subjects: List<FilmItem>,
        val title: String
)

data class FilmItem(
        val rating: Rating,
        val genres: List<String>,
        val title: String,
        val casts: List<Celebrity>,
        val collect_count: Int,
        val original_title: String,
        val subtype: String,
        val directors: List<Celebrity>,
        val year: String,
        val images: Images,
        val alt: String,
        val id: String
)


data class Avatars(
        val small: String,
        val large: String,
        val medium: String
)

data class Images(
        val small: String,
        val large: String,
        val medium: String
)

data class Rating(
        val max: Int,
        val average: Double,
        val stars: String,
        val min: Int,
        val details: Details
)

data class FilmDetail(
        val rating: Rating,
        val reviews_count: Int,
        val videos: List<Video>,
        val wish_count: Int,
        val original_title: String,
        val blooper_urls: List<Any>,
        val collect_count: Int,
        val images: Images,
        val douban_site: String,
        val year: String,
        val popular_comments: List<PopularComment>,
        val alt: String,
        val id: String,
        val mobile_url: String,
        val photos_count: Int,
        val pubdate: String,
        val title: String,
        val do_count: Any,
        val has_video: Boolean,
        val share_url: String,
        val seasons_count: Any,
        val languages: List<String>,
        val schedule_url: String,
        val writers: List<Writer>,
        val pubdates: List<String>,
        val website: String,
        val tags: List<String>,
        val has_schedule: Boolean,
        val durations: List<String>,
        val genres: List<String>,
        val collection: Any,
        val trailers: List<Trailer>,
        val episodes_count: Any,
        val trailer_urls: List<String>,
        val has_ticket: Boolean,
        val bloopers: List<Any>,
        val clip_urls: List<Any>,
        val current_season: Any,
        val casts: List<Celebrity>,
        val countries: List<String>,
        val mainland_pubdate: String,
        val photos: List<Photo>,
        val summary: String,
        val clips: List<Any>,
        val subtype: String,
        val directors: List<Celebrity>,
        val comments_count: Int,
        val popular_reviews: List<PopularReview>,
        val ratings_count: Int,
        val aka: List<String>
)

data class PopularComment(
        val rating: Rating,
        val useful_count: Int,
        val author: Author,
        val subject_id: String,
        val content: String,
        val created_at: String,
        val id: String
)

data class Author(
        val uid: String,
        val avatar: String,
        val signature: String,
        val alt: String,
        val id: String,
        val name: String
)

data class Writer(
        val avatars: Avatars,
        val name_en: String,
        val name: String,
        val alt: String,
        val id: String
)

class Details {
    @SerializedName("1")
    var star1: Double = 0.0

    @SerializedName("2")
    var star2: Double = 0.0

    @SerializedName("3")
    var star3: Double = 0.0

    @SerializedName("4")
    var star4: Double = 0.0

    @SerializedName("5")
    var star5: Double = 0.0
}

data class Trailer(
        val medium: String,
        val title: String,
        val subject_id: String,
        val alt: String,
        val small: String,
        val resource_url: String,
        val id: String
)

data class Video(
        val source: Source,
        val sample_link: String,
        val video_id: String,
        val need_pay: Boolean
)

data class Source(
        val literal: String,
        val pic: String,
        val name: String
)

data class PopularReview(
        val rating: Rating,
        val title: String,
        val subject_id: String,
        val author: Author,
        val summary: String,
        val alt: String,
        val id: String
)


data class FilmPhoto(
        val count: Int,
        val photos: List<Photo>,
        val total: Int,
        val start: Int,
        val subject: FilmDetail
)

data class Photo(
        val photos_count: Int,
        val thumb: String,
        val icon: String,
        val author: Author,
        val created_at: String,
        val album_id: String,
        val cover: String,
        val id: String,
        val prev_photo: String,
        val album_url: String,
        val comments_count: Int,
        val image: String,
        val recs_count: Int,
        val position: Int,
        val alt: String,
        val album_title: String,
        val next_photo: String,
        val subject_id: String,
        val desc: String
)


data class FilmComment(
        val count: Int,
        val comments: List<Comment>,
        val start: Int,
        val total: Int,
        val next_start: Int,
        val subject: FilmDetail
)

data class Comment(
        val rating: Rating,
        val useful_count: Int,
        val author: Author,
        val subject_id: String,
        val content: String,
        val created_at: String,
        val id: String
)


data class FilmReview(
        val count: Int,
        val reviews: List<Review>,
        val start: Int,
        val total: Int,
        val next_start: Int,
        val subject: FilmDetail
)

data class Review(
        val rating: Rating,
        val useful_count: Int,
        val author: Author,
        val created_at: String,
        val title: String,
        val updated_at: String,
        val share_url: String,
        val summary: String,
        val content: String,
        val useless_count: Int,
        val comments_count: Int,
        val alt: String,
        val id: String,
        val subject_id: String
)


data class SearchBrief(
        val episode: String,
        val img: String,
        val title: String,
        val url: String,
        val type: String,
        val year: String,
        val sub_title: String,
        val id: String
)


data class CityList(
        val count: Int,
        val start: Int,
        val total: Int,
        val locs: List<Location>
)

data class Location(
        val parent: String,
        val habitable: String,
        val id: String,
        val name: String,
        val uid: String
)


data class FilmMan(
        val mobile_url: String,
        val aka_en: List<String>,
        val name: String,
        val works: List<Work>,
        val gender: String,
        val avatars: Avatars,
        val id: String,
        val aka: List<String>,
        val name_en: String,
        val born_place: String,
        val alt: String
)

data class Work(
        val roles: List<String>,
        val subject: FilmDetail
)


data class FilmManWork(
        val count: Int,
        val start: Int,
        val celebrity: Celebrity,
        val total: Int,
        val works: List<Work>
)

data class Celebrity(
        val avatars: Avatars,
        val name_en: String,
        val name: String,
        val alt: String,
        val id: String
)


data class FilmManPhoto(
        val count: Int,
        val photos: List<Photo>,
        val celebrity: Celebrity,
        val total: Int,
        val start: Int
)


data class RankFilm(
        val date: String,
        val subjects: List<RankFilmItem>,
        val title: String
)

data class RankFilmItem(
        val box: Int,
        val new: Boolean,
        val rank: Int,
        val subject: FilmItem,
        val delta: Int
)

