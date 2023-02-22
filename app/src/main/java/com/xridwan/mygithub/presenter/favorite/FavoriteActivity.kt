package com.xridwan.mygithub.presenter.favorite

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.xridwan.mygithub.R
import com.xridwan.mygithub.databinding.ActivityFavoriteBinding
import com.xridwan.mygithub.domain.model.UserData
import com.xridwan.mygithub.presenter.detail.DetailActivity
import com.xridwan.mygithub.presenter.main.MainAdapter
import com.xridwan.mygithub.utils.hide
import com.xridwan.mygithub.utils.show
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var mainAdapter: MainAdapter
    private val favoriteViewModel: FavoriteViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.label_favorite)
        }
        recyclerView()
        getfavorite()
    }

    override fun onResume() {
        super.onResume()
        getfavorite()
    }

    private fun recyclerView() {
        mainAdapter = MainAdapter()
        binding.apply {
            rvFavorite.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvFavorite.setHasFixedSize(true)
            rvFavorite.adapter = mainAdapter
        }

        mainAdapter.setOnItemCLickCallBack(object : MainAdapter.OnItemClickCallBack {
            override fun onItemClicked(data: UserData) {
                selectedItem(data)
            }
        })
    }

    private fun selectedItem(data: UserData) {
        startActivity(
            Intent(
                this,
                DetailActivity::class.java
            ).putExtra(DetailActivity.EXTRA_DATA, data.login)
        )
    }

    private fun getfavorite() {
        favoriteViewModel.getFavorite().observe(this) {
            if (!it.isNullOrEmpty()) {
//                val list = setFavorite(it)
                mainAdapter.setData(it)
                binding.rvFavorite.show()
                binding.tvNotFound.hide()
            } else {
                binding.rvFavorite.hide()
                binding.tvNotFound.show()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}