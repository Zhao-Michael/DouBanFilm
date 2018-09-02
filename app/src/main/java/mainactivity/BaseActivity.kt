package mainactivity

import android.annotation.SuppressLint
import android.support.v7.widget.Toolbar
import com.mikepenz.google_material_typeface_library.GoogleMaterial
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.IIcon
import org.horaapps.liz.ThemedActivity
import org.jetbrains.anko.find

@SuppressLint("Registered")
open class BaseActivity : ThemedActivity() {

    val mToolBar by lazy { find<Toolbar>(R.id.toolbar) }

    fun setToolBarIcon(icon: IIcon) {
        mToolBar.navigationIcon = getToolbarIcon(icon)
    }

}