package venerealulcer

import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.GridLayoutManager
import android.view.Menu
import com.mikepenz.google_material_typeface_library.GoogleMaterial
import douban.Douban
import douban.FilmAdapter
import org.jetbrains.anko.find
import util.*

class MainActivity : BaseActivity() {

    override val mLayout: Int = R.layout.activity_main

    private val mDrawerLayout by lazy { find<DrawerLayout>(R.id.mDrawerLayout) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init_UI()
    }

    private fun init_UI() {
        setToolBarIcon(GoogleMaterial.Icon.gmd_menu)
        mToolBar.setNavigationOnClickListener {
            mDrawerLayout.openDrawer(GravityCompat.START)
        }
        mToolBar.title = getString(R.string.app_name)
        mSwipeLayout.setColorSchemeColors(getPrimaryColor())
        mRecyclerView.layoutManager = GridLayoutManager(this, 1)
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
        setPrimaryColor(R.color.accent_teal)
        recreate()
    }

    private fun searchMenu_Click() {
        mSwipeLayout.ShowRefresh()
        Rx.get {
            Douban.getTheaterFilms("上海")
        }.set { it ->
            mRecyclerView.adapter = FilmAdapter(it, mRecyclerView.context)
            mRecyclerView.adapter.notifyDataSetChanged()
            mSwipeLayout.HideRefresh()
        }.err {
            mSwipeLayout.HideRefresh()
        }
    }

}