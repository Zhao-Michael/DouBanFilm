package douban.subfilmview

import android.content.Context
import android.widget.TextView
import com.daimajia.numberprogressbar.NumberProgressBar
import com.hedgehog.ratingbar.RatingBar
import douban.FilmDetail
import venerealulcer.R
import org.jetbrains.anko.find

class FilmViewSummary(context: Context, filmDetail: FilmDetail) : IFilmView(context) {

    override val mLayout: Int = R.layout.film_summary_layout
    private val mFilmDetail = filmDetail
    private val mTextBrief by lazy { mView.find<TextView>(R.id.mTextBrief) }

    init {
        initPoster(mFilmDetail)
        initFilmBrief(mFilmDetail)
        initFilmRate(mFilmDetail)
    }

    private fun initFilmBrief(film: FilmDetail) {
        getTextView(R.id.org_name).text = film.original_title
        getTextView(R.id.other_name).text = film.aka.joinToString("\n")
        getTextView(R.id.genres).text = film.genres.joinToString("/")
        getTextView(R.id.durations).text = film.durations.joinToString("/")
        getTextView(R.id.year).text = film.year
        getTextView(R.id.pubdate).text = film.pubdates.joinToString("/")
        getTextView(R.id.country).text = film.countries.joinToString("/")
        getTextView(R.id.director).text = film.directors.joinToString("/") { it.name }
        getTextView(R.id.actor).text = film.casts.joinToString("/") { it.name }
        getTextView(R.id.language).text = film.languages.joinToString("/")
    }

    private fun initPoster(film: FilmDetail) {
        mTextBrief.text = film.summary.trim()
    }

    private fun getTextView(id: Int): TextView {
        return mView.find(id)
    }

    private fun initFilmRate(film: FilmDetail) {
        getTextView(R.id.text_rate).text = film.rating.average.toString()
        getTextView(R.id.text_rate_descript).text = "${film.ratings_count}" + " 人评分"
        mView.find<RatingBar>(R.id.rate_bar).setStar((film.rating.stars.toInt() / 10.0).toFloat())
        val sum = film.rating.details.sum()

        fun getProgressBar(id: Int): NumberProgressBar {
            return mView.find(id)
        }
        getProgressBar(R.id.rate_progress_1).progress = (film.rating.details.star1 / sum * 100).toInt()
        getProgressBar(R.id.rate_progress_2).progress = (film.rating.details.star2 / sum * 100).toInt()
        getProgressBar(R.id.rate_progress_3).progress = (film.rating.details.star3 / sum * 100).toInt()
        getProgressBar(R.id.rate_progress_4).progress = (film.rating.details.star4 / sum * 100).toInt()
        getProgressBar(R.id.rate_progress_5).progress = (film.rating.details.star5 / sum * 100).toInt()
    }

}