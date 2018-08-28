package mainactivity

import android.app.Activity
import android.os.Bundle
import com.google.gson.Gson
import douban.*
import util.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mBtnHello.setOnClickListener {
            buttonClick()
        }
    }

    private fun buttonClick() {

        Douban.getTheaterFilms("%E5%8C%97%E4%BA%AC")
    }


}