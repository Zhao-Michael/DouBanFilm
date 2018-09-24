package douban.subfilmview

import android.content.Context
import android.view.LayoutInflater
import android.view.View

abstract class IFilmView(context: Context) {

    protected abstract val mLayout: Int

    protected val mView: View by lazy { LayoutInflater.from(context).inflate(mLayout, null) }

    private val mContext = context

    fun getView(): View {
        return if (mLayout == 0)
            View(mContext)
        else
            mView
    }

}

class FilmView(context: Context) : IFilmView(context) {
    override val mLayout: Int = 0
}