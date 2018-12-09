package util

import android.net.TrafficStats
import android.widget.TextView
import michaelzhao.R
import kotlin.concurrent.thread


class NetStatistics private constructor() {

    companion object {
        val Instance by lazy { NetStatistics() }
        var Old_Data = 0.0
    }

    private val mUID by lazy { android.os.Process.myUid() }
    private val mCurrDownLoadByte get() = TrafficStats.getUidRxBytes(mUID) / 1024.0
    private val mCurrUpLoadByte get() = TrafficStats.getUidTxBytes(mUID) / 1024.0
    private val mTotalNetData: Double get() = (mCurrUpLoadByte + mCurrDownLoadByte) / 1024.0 - Old_Data
    private val mAllNetData: Double get() = mTotalNetData + mSaveNetData
    private var mSaveNetData: Double = 0.0

    var mText_CurrData: TextView? = null
    var mText_NetData: TextView? = null

    private var mFlag_Pause = false //For pause

    private fun saveNetInfo() {
        HawkPut(R.string.preference_net_total_data, mAllNetData)
    }

    private fun loadNetInfo() {
        try {
            mSaveNetData = HawkGet(R.string.preference_net_total_data)
        } catch (e: Exception) {
        }
    }

    fun init(curr: TextView, total: TextView) {
        loadNetInfo()
        mText_CurrData = curr
        mText_NetData = total
        mFlag_Pause = true
        thread {
            while (true) {
                run()
                Thread.sleep(500)
            }
        }
    }

    fun start() {
        mFlag_Pause = false
    }

    fun pause() {
        mFlag_Pause = true
    }

    fun save() {
        saveNetInfo()
    }

    private fun run() {
        try {
            if (mFlag_Pause) return

            val all_data = mAllNetData
            val all_str = if (all_data < 5000) {
                "${all_data.toString().format("0.0").take(6).padStart(6)} MB"
            } else {
                "${(all_data / 1024.0).toString().format("0.0").take(6).padStart(6)} GB"
            }
            val curr_data = "${mTotalNetData.toString().format("0.0").take(6).padStart(6)} MB"

            uiThread {
                mText_CurrData?.text = curr_data
                mText_NetData?.text = all_str
            }
        } catch (e: Exception) {
        }
    }

}