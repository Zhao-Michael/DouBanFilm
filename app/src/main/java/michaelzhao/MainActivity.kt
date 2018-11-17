package michaelzhao

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.view.Menu
import com.mikepenz.google_material_typeface_library.GoogleMaterial
import douban.adapter.FilmMainPageAdapter
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import util.*


class MainActivity : BaseActivity() {

    override val mLayout: Int = R.layout.activity_main

    private val mDrawerLayout by lazy { find<DrawerLayout>(R.id.mDrawerLayout) }
    private val mTableLayout by lazy { find<TabLayout>(R.id.mTabLayout) }
    private val mViewPager by lazy { find<ViewPager>(R.id.mViewPager) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init_UI()
    }

    private fun init_UI() {
        setToolBarIcon(GoogleMaterial.Icon.gmd_menu)
        mToolBar.setNavigationOnClickListener { mDrawerLayout.openDrawer(GravityCompat.START) }
        setToolBarTitle(R.string.app_name)
        mTableLayout.setupWithViewPager(mViewPager)
        mTableLayout.tabMode = TabLayout.MODE_SCROLLABLE
        mTableLayout.setSelectedTabIndicatorColor(getPrimaryColor())
        mTableLayout.setTabTextColors(getColorValue(R.color.divider_color), getPrimaryColor())
        mViewPager.adapter = FilmMainPageAdapter(this)
        mTableLayout.setTabStyle()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        run {
            val mMenuSearch = menu?.findItem(R.id.search_action)
            mMenuSearch?.onItemClick { searchMenu_Click() }
            mMenuSearch?.icon = getDrawableIcon(GoogleMaterial.Icon.gmd_search)
        }

        run {
            val mMenuSetting = menu?.findItem(R.id.settings_action)
            mMenuSetting?.icon = getDrawableIcon(GoogleMaterial.Icon.gmd_settings)
            mMenuSetting?.onItemClick { settingMenu_Click() }
        }

        return true
    }

    private fun settingMenu_Click() {
//        Debug()
//        return
//        setPrimaryColor(R.color.md_deep_orange_400)
//        recreate()
    }

    private fun searchMenu_Click() {
        startActivity<SearchActivity>()
    }


    private fun Debug() {
    }

}



