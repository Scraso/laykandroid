package com.example.tigran.laykandroid

import android.os.Bundle
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import java.util.*
import kotlin.concurrent.schedule

const val TAG = "TIGRAN"

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var auth: FirebaseAuth
    private lateinit var appBarConfiguration : AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment? ?: return

        // Set up Action Bar
        val navController = host.navController

        // Define top-level destination
        val drawerLayout : DrawerLayout? = findViewById(R.id.drawer_layout)
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_home, R.id.nav_shop, R.id.nav_cart, R.id.nav_history, R.id.nav_information, R.id.nav_login),
            drawerLayout)

        setupActionBar(navController, appBarConfiguration)
        setupNavigationMenu(navController)

        // Set Title of the View
        toolbar.title = "Меню"

        checkUserActivity()

    }


    // Display navigation view
    private fun setupNavigationMenu(navController: NavController) {
        val sideNavView = findViewById<NavigationView>(R.id.nav_view)
        sideNavView?.setupWithNavController(navController)

    }

    private fun setupActionBar(navController: NavController,
                               appBarConfig : AppBarConfiguration) {
        setupActionBarWithNavController(navController, appBarConfig)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp(appBarConfiguration)
    }

    override fun onBackPressed() {
        when {
            drawer_layout.isDrawerOpen(GravityCompat.START) -> drawer_layout.closeDrawer(GravityCompat.START)
            supportFragmentManager.backStackEntryCount > 0 -> supportFragmentManager.popBackStack()
            else -> super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val drawerLayout : DrawerLayout? = findViewById(R.id.drawer_layout)
        return when (item.itemId) {
            R.id.action_cart -> {
                drawerLayout?.openDrawer(GravityCompat.START)
                findNavController(R.id.nav_host_fragment).navigate(R.id.nav_cart)
                Timer().schedule(500) {
                    drawerLayout?.closeDrawer(GravityCompat.START)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.nav_home) {
            if (item.isChecked) {
                Log.d(TAG, "Currently in this state")
            } else {
                findNavController(R.id.nav_host_fragment).navigate(R.id.nav_home)
            }
        }
        if (id == R.id.nav_shop) {
            if (item.isChecked) {
                Log.d(TAG, "Currently in this state")
            } else {
                findNavController(R.id.nav_host_fragment).navigate(R.id.nav_shop)
            }
        }
        if (id == R.id.nav_history) {
            if (item.isChecked) {
                Log.d(TAG, "Currently in this state")
            } else {
                findNavController(R.id.nav_host_fragment).navigate(R.id.nav_history)
            }
        }
        if (id == R.id.nav_cart) {
            if (item.isChecked) {
                Log.d(TAG, "Currently in this state")
            } else {
                findNavController(R.id.nav_host_fragment).navigate(R.id.nav_cart)
            }
        }
        if (id == R.id.nav_information) {
            if (item.isChecked) {
                Log.d(TAG, "Currently in this state")
            } else {
                findNavController(R.id.nav_host_fragment).navigate(R.id.nav_information)
            }
        }
        if (id == R.id.nav_login) {
            findNavController(R.id.nav_host_fragment).navigate(R.id.nav_login)
        }
        if (id == R.id.nav_logout) {
            auth.signOut()
        }
        drawer_layout.closeDrawers()
        return true
    }



    private fun checkUserActivity() {
        // Get Navigation header TextView
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        // Set Navigation View on Click Listener so we can check what is the status of the user
        // and handle sign out in onNavigationItemSelected method.
        navigationView?.setNavigationItemSelectedListener(this)

        val headerView = navigationView.getHeaderView(0)
        val navUserEmail = headerView.findViewById<TextView>(R.id.header_userTextView)

        // Get Navigation User Status menu item
        val menu = navigationView.menu
        val navUserLogin = menu.findItem(R.id.nav_login)
        val navUserLogout = menu.findItem(R.id.nav_logout)


        auth.addAuthStateListener { auth  ->
            val user = auth.currentUser
            if (user != null) {
                Log.d(TAG, "User is not null")
                navUserEmail.text = user.email
                navUserLogin.isVisible = false
                navUserLogout.isVisible = true
            } else {
                Log.d(TAG, "User is null")
                navUserEmail.text = "Войдите или зарегистрируйтесь"
                navUserLogin.isVisible = true
                navUserLogout.isVisible = false
            }
        }
    }

}
