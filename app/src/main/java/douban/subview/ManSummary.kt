package douban.subview

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import douban.FilmList
import douban.FilmMan
import douban.adapter.FilmListAdapter
import michaelzhao.BaseActivity
import michaelzhao.R
import org.jetbrains.anko.find
import org.jetbrains.anko.textColor
import util.ReplaceEmpty
import util.setImageUrl

class ManSummary(context: Context, filmMan: FilmMan) : IFilmView(context) {

    override val mLayout: Int = R.layout.film_man_brief_layout
    private val mFilmMan = filmMan
    private val mImageView by lazy { mView.find<ImageView>(R.id.image) }
    private val mTextTitle by lazy { mView.find<TextView>(R.id.title) }
    private val mTextOtherName by lazy { mView.find<TextView>(R.id.other_name) }
    private val mTextNameEn by lazy { mView.find<TextView>(R.id.text_name_en) }
    private val mTextGenres by lazy { mView.find<TextView>(R.id.genres) }
    private val mTextLoc by lazy { mView.find<TextView>(R.id.location) }
    private val mTextWorks by lazy { mView.find<TextView>(R.id.text_works) }

    init {
        initRecyclerView()
        initSummary()
        initFilmList()
    }

    private fun initSummary() {
        mImageView.setImageUrl(mFilmMan.avatars.large)
        mTextTitle.text = mFilmMan.name.ReplaceEmpty()
        val list = mFilmMan.aka.plus(mFilmMan.aka_en)
        mTextOtherName.text = list.joinToString("\n").ReplaceEmpty()
        mTextOtherName.setLines(mTextOtherName.text.split("\n").size)
        mTextNameEn.text = mFilmMan.name_en.ReplaceEmpty()
        mTextGenres.text = mFilmMan.gender.ReplaceEmpty()
        mTextLoc.text = mFilmMan.born_place.ReplaceEmpty()
        mTextWorks.textColor = BaseActivity.getPrimaryColor()
        mTextWorks.text = mFilmMan.name.trim() + mTextWorks.text
    }

    private fun initFilmList() {
        val listFilm = mFilmMan.works.map { it.subject }
        val cnt = listFilm.size
        mRecyclerView.adapter = FilmListAdapter(mContext, FilmList(cnt, 0, cnt, listFilm, mFilmMan.name), this)
    }

}