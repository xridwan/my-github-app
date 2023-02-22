package com.xridwan.mygithub.presenter.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.xridwan.mygithub.R
import com.xridwan.mygithub.databinding.ActivityMainBinding
import com.xridwan.mygithub.domain.Resource
import com.xridwan.mygithub.domain.model.UserData
import com.xridwan.mygithub.presenter.detail.DetailActivity
import com.xridwan.mygithub.presenter.favorite.FavoriteActivity
import com.xridwan.mygithub.presenter.other.ReminderActivity
import com.xridwan.mygithub.utils.hide
import com.xridwan.mygithub.utils.show
import com.xridwan.mygithub.utils.toast
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), MainAdapter.OnItemClickCallBack {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()
    private val themeViewModel: ThemeViewModel by viewModel()
    private lateinit var mainAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.elevation = 0.0f
        checkTheme()
        setAdapter()
        getUserList("Android")
    }

    private fun checkTheme() {
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

    private fun getUserList(query: String) {
        viewModel.getUsers(query).observe(this@MainActivity) { response ->
            when (response) {
                is Resource.Success -> {
                    val data = response.data
                    if (data.isNullOrEmpty()) {
                        binding.tvNotFound.show()
                        binding.rvUsers.hide()
                    } else {
                        mainAdapter.setData(data as MutableList)
                        binding.tvNotFound.hide()
                        binding.rvUsers.show()
                    }
                    showLoading(false)
                }
                is Resource.Loading -> {
                    showLoading(true)
                }
                is Resource.Error -> {
                    toast(response.message.toString())
                    showLoading(false)
                }
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.option_search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = getString(R.string.label_search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                getUserList(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    binding.tvNotFound.hide()
                    getUserList("Android")
                }
                return false
            }
        })
        return true
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
        if (isLoading) binding.progressMain.show()
        else binding.progressMain.hide()
    }

    override fun onItemClicked(data: UserData) {
        startActivity(
            Intent(
                this,
                DetailActivity::class.java
            ).putExtra(DetailActivity.EXTRA_DATA, data.login)
        )
    }
}