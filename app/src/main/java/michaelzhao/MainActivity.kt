package michaelzhao

import android.content.res.ColorStateList
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.view.Gravity
import android.view.Menu
import android.view.View
import com.mikepenz.google_material_typeface_library.GoogleMaterial
import douban.adapter.FilmMainPageAdapter
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import util.*
import util.Util.DisableWarningDialog


class MainActivity : BaseActivity() {
    companion object {
        lateinit var Instance: MainActivity
    }

    override val mLayout: Int = R.layout.activity_main

    private val mDrawerLayout by lazy { find<DrawerLayout>(R.id.mDrawerLayout) }
    private val mTableLayout by lazy { find<TabLayout>(R.id.mTabLayout) }
    private val mViewPager by lazy { find<ViewPager>(R.id.mViewPager) }
    private val mNavigationView by lazy { find<NavigationView>(R.id.home_navigation_drawer) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Instance = this
        init_UI()
        DisableWarningDialog()
    }

    private fun init_UI() {
        init_MainMenu()
        setToolBarIcon(GoogleMaterial.Icon.gmd_menu)
        mToolBar.setNavigationOnClickListener { mDrawerLayout.openDrawer(GravityCompat.START) }
        setToolBarTitle(R.string.app_name)
        mTableLayout.setupWithViewPager(mViewPager)
        mTableLayout.tabMode = TabLayout.MODE_SCROLLABLE
        mTableLayout.setSelectedTabIndicatorColor(getPrimaryColor())
        mTableLayout.setTabTextColors(getColorValue(R.color.divider_color), getPrimaryColor())
        mViewPager.adapter = FilmMainPageAdapter(this)
        mTableLayout.setTabStyle()
        initStatistics()
    }

    private fun init_MainMenu() {
        mNavigationView.itemIconTintList = ColorStateList.valueOf(getPrimaryColor())
        mNavigationView.itemTextColor = ColorStateList.valueOf(resources.getColor(R.color.black,null))
        mNavigationView.menu.getItem(0).icon = getDrawableIcon(GoogleMaterial.Icon.gmd_home)
        mNavigationView.menu.getItem(1).icon = getDrawableIcon(GoogleMaterial.Icon.gmd_favorite)
        mNavigationView.menu.getItem(2).icon = getDrawableIcon(GoogleMaterial.Icon.gmd_search)

        mNavigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_openhome -> {
                }
                R.id.action_favorite -> {
                    startActivity<FavoriteActivity>()
                }
                R.id.action_btsearch -> {
                    startActivity<BTSearchActivity>()
                }
            }
            mDrawerLayout.closeDrawer(Gravity.START)
            return@setNavigationItemSelectedListener true
        }
    }

    private fun initStatistics() {
        uiThread(1500) {
            NetStatistics.Instance.init(find(R.id.mText_CurrData), find(R.id.mText_NetData))
            mDrawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
                override fun onDrawerStateChanged(newState: Int) = Unit

                override fun onDrawerSlide(drawerView: View, slideOffset: Float) = Unit

                override fun onDrawerClosed(drawerView: View) {
                    NetStatistics.Instance.pause()
                    NetStatistics.Instance.save()
                }

                override fun onDrawerOpened(drawerView: View) {
                    NetStatistics.Instance.start()
                }

            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        run {
            val mMenuSearch = menu?.findItem(R.id.search_action)
            mMenuSearch?.onItemClick { startActivity<FilmSearchActivity>() }
            mMenuSearch?.icon = getDrawableIcon(GoogleMaterial.Icon.gmd_search)
        }

        run {
            val mMenuSetting = menu?.findItem(R.id.settings_action)
            mMenuSetting?.icon = getDrawableIcon(GoogleMaterial.Icon.gmd_settings)
            mMenuSetting?.onItemClick { }
        }
        return true
    }

}



