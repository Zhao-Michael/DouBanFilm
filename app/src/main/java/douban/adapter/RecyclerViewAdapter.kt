package douban.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

abstract class RecyclerViewAdapter<T : RecyclerView.ViewHolder?> : RecyclerView.Adapter<T>() {

    override fun onBindViewHolder(holder: T, position: Int) {
        if (position == 0) {
            val lp = holder?.itemView?.layoutParams as? ViewGroup.MarginLayoutParams
            if (lp != null) {
                lp.topMargin = lp.topMargin * 2
            }
        } else if (position == itemCount - 1) {
            val lp = holder?.itemView?.layoutParams as? ViewGroup.MarginLayoutParams
            if (lp != null) {
                lp.bottomMargin = lp.bottomMargin * 2
            }
        }
    }

}