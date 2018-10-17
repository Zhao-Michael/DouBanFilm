package douban.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

abstract class RecyclerViewAdapter<T : RecyclerView.ViewHolder?> : RecyclerView.Adapter<T>() {

    private var mMargin: Int? = null

    override fun onBindViewHolder(holder: T, position: Int) {
        if (holder != null) {
            val position = holder.adapterPosition
            if (position == 0) {
                val lp = holder.itemView?.layoutParams as? ViewGroup.MarginLayoutParams
                if (lp != null) {
                    if (mMargin == null) mMargin = lp.topMargin * 2
                    lp.topMargin = mMargin!!
                }
            } else if (position == itemCount - 1) {
                val lp = holder.itemView?.layoutParams as? ViewGroup.MarginLayoutParams
                if (lp != null) {
                    if (mMargin == null) mMargin = lp.topMargin * 2
                    lp.bottomMargin = mMargin!!
                }
            }
        }
    }

}