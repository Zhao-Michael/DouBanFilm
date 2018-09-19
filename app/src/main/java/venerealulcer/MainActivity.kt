package venerealulcer

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.view.Menu
import com.mikepenz.google_material_typeface_library.GoogleMaterial
import douban.FilmAdapter
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.onRefresh
import util.HideRefresh
import util.OnItemClick
import util.ShowRefresh
import util.setTabStyle


class MainActivity : BaseActivity() {

    override val mLayout: Int = R.layout.activity_main

    private val mDrawerLayout by lazy { find<DrawerLayout>(R.id.mDrawerLayout) }
    private val mTableLayout by lazy { find<TabLayout>(R.id.mRecyclerTabLayout) }
    private val mViewPager by lazy { find<ViewPager>(R.id.mViewPager) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init_UI()
    }

    private fun init_UI() {
        setToolBarIcon(GoogleMaterial.Icon.gmd_menu)
        mToolBar.setNavigationOnClickListener { mDrawerLayout.openDrawer(GravityCompat.START) }
        setToolBarTitle(R.string.app_name)
        mSwipeLayout.setColorSchemeColors(getPrimaryColor())
        mSwipeLayout.onRefresh { onSwipeRefresh() }
        mTableLayout.setupWithViewPager(mViewPager)
        mTableLayout.tabMode = TabLayout.MODE_SCROLLABLE
        mTableLayout.setSelectedTabIndicatorColor(getPrimaryColor())
        mTableLayout.setTabTextColors(getColorValue(R.color.divider_color), getPrimaryColor())
        mViewPager.adapter = FilmPageAdapter(this) { b -> if (b) mSwipeLayout.ShowRefresh() else mSwipeLayout.HideRefresh() }
        mTableLayout.setTabStyle()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        run {
            val mMenuSearch = menu?.findItem(R.id.search_action)
            mMenuSearch?.OnItemClick { searchMenu_Click() }
            mMenuSearch?.icon = getDrawableIcon(GoogleMaterial.Icon.gmd_search)
        }

        run {
            val mMenuSetting = menu?.findItem(R.id.settings_action)
            mMenuSetting?.icon = getDrawableIcon(GoogleMaterial.Icon.gmd_settings)
            mMenuSetting?.OnItemClick { settingMenu_Click() }
        }

        return true
    }

    private fun settingMenu_Click() {
        setPrimaryColor(R.color.md_cyan_600)
        recreate()
    }

    private fun onSwipeRefresh() {
        (mViewPager.adapter as FilmPageAdapter).updatePageFromNet(mTableLayout.selectedTabPosition)
    }

    private fun searchMenu_Click() {
        startActivity<SearchActivity>()
    }


}



