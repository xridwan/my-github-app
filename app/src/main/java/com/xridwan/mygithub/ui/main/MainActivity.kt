package com.xridwan.mygithub.ui.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CompoundButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.xridwan.mygithub.R
import com.xridwan.mygithub.data.local.entity.UserData
import com.xridwan.mygithub.data.local.preference.ThemePreferences
import com.xridwan.mygithub.databinding.ActivityMainBinding
import com.xridwan.mygithub.helper.toast
import com.xridwan.mygithub.ui.detail.DetailActivity
import com.xridwan.mygithub.ui.favorite.FavoriteActivity
import com.xridwan.mygithub.ui.other.ReminderActivity

class MainActivity : AppCompatActivity(), MainAdapter.OnItemClickCallBack {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var mainAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.elevation = 0.0f
        checkTheme()
        setAdapter()
        getUserList()
    }

    private fun checkTheme() {
        val pref = ThemePreferences.getInstance(dataStore)
        val themeViewModel = ViewModelProvider(
            this,
            ViewModelFactory(pref)
        )[ThemeViewModel::class.java]

        themeViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.switchTheme.isChecked = false
            }
        }

        binding.switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            themeViewModel.saveThemeSettings(isChecked)
        }
    }

    private fun setAdapter() {
        mainAdapter = MainAdapter()

        binding.apply {
            rvUsers.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUsers.setHasFixedSize(true)
            rvUsers.adapter = mainAdapter
        }

        mainAdapter.setOnItemCLickCallBack(this)
    }

    private fun getUserList() {
        viewModel.userList.observe(this, {
            mainAdapter.setData(it)
        })

        viewModel.loading.observe(this, {
            showLoading(it)
        })

        viewModel.message.observe(this, { response ->
            response.getContentIfNotHandled()?.let {
                toast(it)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.option_search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = getString(R.string.label_search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.userSearch(query)
                viewModel.userList.observe(this@MainActivity, { items ->
                    if (items.size == 0) {
                        binding.tvNotFound.visibility = View.VISIBLE
                        binding.rvUsers.visibility = View.GONE
                    } else {
                        mainAdapter.setData(items)
                        binding.tvNotFound.visibility = View.GONE
                        binding.rvUsers.visibility = View.VISIBLE
                    }
                })
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    binding.tvNotFound.visibility = View.GONE
                    viewModel.userSearch("Android")
                    viewModel.userList.observe(this@MainActivity, { items ->
                        if (items.size == 0) {
                            binding.tvNotFound.visibility = View.VISIBLE
                            binding.rvUsers.visibility = View.GONE
                        } else {
                            mainAdapter.setData(items)
                            binding.tvNotFound.visibility = View.GONE
                            binding.rvUsers.visibility = View.VISIBLE
                        }
                    })
                }
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.option_language_setting -> startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            R.id.option_favorite -> startActivity(Intent(this, FavoriteActivity::class.java))
            R.id.option_reminder -> startActivity(Intent(this, ReminderActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressMain.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onItemClicked(data: UserData) {
        startActivity(
            Intent(
                this,
                DetailActivity::class.java
            ).putExtra(DetailActivity.EXTRA_DATA, UserData(data.id, data.login, data.avatarUrl))
        )
    }
}