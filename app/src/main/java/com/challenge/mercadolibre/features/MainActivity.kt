package com.challenge.mercadolibre.features

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.challenge.mercadolibre.R
import com.challenge.mercadolibre.core.utilities.PREFERENCE_NAME
import com.challenge.mercadolibre.core.utilities.getCountry
import com.challenge.mercadolibre.core.utilities.searchdialog.SearchDialog
import com.challenge.mercadolibre.core.utilities.searchdialog.SuggestionProvider
import com.challenge.mercadolibre.databinding.ActivityMainBinding
import com.challenge.mercadolibre.features.list.ListProductsFragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var searchDialog: SearchDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        val navController = navHostFragment.navController
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeFragment), drawerLayout
        )

        //se configura la navegacion del menu lateral
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.search -> {
                    openSearchDialog()
                    drawerLayout.closeDrawer(GravityCompat.START, true)
                }
                R.id.homeFragment -> {
                    navController.navigate(R.id.homeFragment)
                    drawerLayout.closeDrawer(GravityCompat.START, true)
                }
            }
            true
        }

        binding.appBarMain.tvSearch.setOnClickListener {
            openSearchDialog()
        }
        binding.appBarMain.tvSearch.text = intent.getStringExtra(SearchManager.QUERY)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        val navController = navHostFragment.navController
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onNewIntent(intent: Intent) {
        //cuando se crea un nuevo intent significa que se hizo una busqueda
        //por lo que se oculta el dialogo y se validan los argumentos del intent con handleIntent()
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent(intent)
        searchDialog.dismiss()
    }

    private fun openSearchDialog() {
        //antes de abrir el dialogo de busqueda se valida que ya tenga seleccionado un pais de referencia pra la busqueda
        val country = getCountry(getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE))
        if (country.isNotEmpty()) {
            val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
            val searchableInfo = searchManager.getSearchableInfo(componentName)
            searchDialog =
                SearchDialog(binding.appBarMain.tvSearch.text.toString(), searchableInfo, this)
            searchDialog.show()
        } else {
            //en caso de que no tenga seleccionado ningun pais se mustra un mensaje al usuario
            Toast.makeText(this, getString(R.string.select_country), Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                //agregando la busqueda en el hostorial
                SearchRecentSuggestions(this, SuggestionProvider.AUTHORITY, SuggestionProvider.MODE)
                    .saveRecentQuery(query, null)
                //navegando al fragment que lista los productos enviandole el pais y el query de busqueda
                val navHostFragment =
                    supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
                val navController = navHostFragment.navController
                val bundle = bundleOf(
                    ListProductsFragment.ARG_COUNTRY to getCountry(
                        getSharedPreferences(
                            PREFERENCE_NAME,
                            Context.MODE_PRIVATE
                        )
                    ),
                    ListProductsFragment.ARG_QUERY to query
                )
                navController.navigate(R.id.listProductFragment, bundle)
                //colocando en el edittext del actionBar el query para que el usuario pueda ver la busqueda actual
                binding.appBarMain.tvSearch.text = query
            }
        }
    }
}