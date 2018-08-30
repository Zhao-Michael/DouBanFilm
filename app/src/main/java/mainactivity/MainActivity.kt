package mainactivity

import android.app.Activity
import android.os.Bundle
import com.bumptech.glide.Glide
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
        Glide.with(this)
                .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535654895479&di=dc196c57645d387b37d81dee874c077a&imgtype=0&src=http%3A%2F%2Fimg.mp.itc.cn%2Fupload%2F20161218%2Fef76c702ecb948558d1e3d6381a5fc5f_th.gif")
                .into(mPhotoView)
    }


}