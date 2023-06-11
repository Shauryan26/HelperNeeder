package com.example.helper_needer.ModelResponse

import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.replace
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.helper_needer.HomeFragment
import com.example.helper_needer.R
import com.example.helper_needer.SearchFragment
import com.google.android.material.navigation.NavigationBarItemView
import com.google.android.material.navigation.NavigationView

lateinit var toggle: ActionBarDrawerToggle
class Home : AppCompatActivity() {

    public lateinit var sharedpref: SharedPreferences
   lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {


        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
        actionBar?.hide()


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(findViewById(R.id.toolbar))
         drawerLayout =findViewById(R.id.drawerlayout)
         val navview: NavigationView=findViewById(R.id.navview)


        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout,HomeFragment())
            commit()
        }
        toggle= ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        toggle.isDrawerIndicatorEnabled=true
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        sharedpref=getSharedPreferences("myPref", MODE_PRIVATE)
        val x=sharedpref.getString("City",null).toString()
        val y=sharedpref.getString("Type",null).toString()
        navview.setNavigationItemSelectedListener {
            it.isChecked=true
            when(it.itemId){
                R.id.nav_home ->  replaceFragment(HomeFragment())
                R.id.nav_search -> replaceFragment(SearchFragment(x,y))
                R.id.nav_logout -> Toast.makeText(applicationContext,"Clicked Log Out",Toast.LENGTH_SHORT).show()
            }
            true
        }
    }
    private fun replaceFragment(fragment: Fragment){
        val fragmentManager=supportFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()
        drawerLayout.closeDrawers()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}