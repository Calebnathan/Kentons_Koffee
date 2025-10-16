import android.R
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var drawerLayout: DrawerLayout? = null
    private var navigationView: NavigationView? = null
    private var toolbar: Toolbar? = null
    private var toggle: ActionBarDrawerToggle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        drawerLayout = findViewById<DrawerLayout?>(R.id.drawer_layout)
        navigationView = findViewById<NavigationView?>(R.id.nav_view)
        toolbar = findViewById<Toolbar?>(R.id.toolbar)

        // Set up toolbar
        setSupportActionBar(toolbar)

        // Create drawer toggle
        toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawerLayout!!.addDrawerListener(toggle!!)
        toggle!!.syncState()

        // Set navigation view listener
        navigationView!!.setNavigationItemSelectedListener(this)

        // Load default fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, HomeFragment())
                .commit()
            navigationView.setCheckedItem(R.id.nav_home)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment: Fragment? = null
        val itemId = item.getItemId()

        if (itemId == R.id.nav_home) {
            fragment = HomeFragment()
        } else if (itemId == R.id.nav_menu) {
            fragment = MenuFragment()
        } else if (itemId == R.id.nav_orders) {
            fragment = OrdersFragment()
        } else if (itemId == R.id.nav_settings) {
            fragment = SettingsFragment()
        }

        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragment)
                .addToBackStack(null)
                .commit()
        }

        drawerLayout!!.closeDrawers()
        return true
    }

    override fun onBackPressed() {
        if (drawerLayout!!.isDrawerOpen(GravityCompat.START)) {
            drawerLayout!!.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}