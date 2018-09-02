package mainactivity

import android.os.Bundle
import com.bumptech.glide.Glide
import com.mikepenz.google_material_typeface_library.GoogleMaterial
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.activity_main.*
import org.horaapps.liz.ThemeHelper.getToolbarIcon


class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(mToolBar)
        setToolBarIcon(GoogleMaterial.Icon.gmd_arrow_back)
        setStatusBarColor()
        setNavBarColor()

    }


}