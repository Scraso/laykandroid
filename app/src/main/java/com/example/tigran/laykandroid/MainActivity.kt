package com.example.tigran.laykandroid

import android.content.res.Resources
import android.os.Bundle
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

const val TAG = "TIGRAN"

class MainActivity : AppCompatActivity() {

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
            setOf(R.id.nav_home, R.id.nav_shop, R.id.nav_cart, R.id.nav_history, R.id.nav_information ),
            drawerLayout)

        setupActionBar(navController, appBarConfiguration)
        setupNavigationMenu(navController)

        // Set Title of the View
        toolbar.title = "Меню"

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
        // Check if user is logged in and based on status update the item status
        checkUserActivity(menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return item.onNavDestinationSelected(findNavController(R.id.nav_host_fragment))
                || super.onOptionsItemSelected(item)
//        return when (item.itemId) {
//            R.id.action_login -> {
//                if (item.title == "Вход") {
//                    val intent = Intent(this, LoginActivity::class.java)
//                    startActivity(intent)
//                } else {
//                    auth.signOut()
//                }
//                true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
    }


    private fun checkUserActivity(menu: Menu) {
        val menuLoginItemTitle = menu.findItem(R.id.action_login)
        auth.addAuthStateListener { auth  ->
            val user = auth.currentUser
            if (user != null) {
                Log.d(TAG, "User is not null")
                menuLoginItemTitle.title = "Выход"
            } else {
                Log.d(TAG, "User is null")
                menuLoginItemTitle.title = "Вход"
            }
        }
    }

}
