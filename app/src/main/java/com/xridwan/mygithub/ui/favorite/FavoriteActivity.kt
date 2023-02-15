package com.xridwan.mygithub.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.xridwan.mygithub.R
import com.xridwan.mygithub.data.local.entity.Favorite
import com.xridwan.mygithub.data.local.entity.UserData
import com.xridwan.mygithub.databinding.ActivityFavoriteBinding
import com.xridwan.mygithub.ui.detail.DetailActivity
import com.xridwan.mygithub.ui.main.MainAdapter

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var mainAdapter: MainAdapter
    private val favoriteViewModel by viewModels<FavoriteViewModel>()

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
            ).putExtra(DetailActivity.EXTRA_DATA, UserData(data.id, data.login, data.avatarUrl))
        )
    }

    private fun getfavorite() {
        favoriteViewModel.getFavorite()?.observe(this, {
            if (!it.isNullOrEmpty()) {
                val list = setFavorite(it)
                mainAdapter.setData(list)
                binding.rvFavorite.visibility = View.VISIBLE
                binding.tvNotFound.visibility = View.GONE
            } else {
                binding.rvFavorite.visibility = View.GONE
                binding.tvNotFound.visibility = View.VISIBLE
            }
        })
    }

    private fun setFavorite(favorites: List<Favorite>): ArrayList<UserData> {
        val listFavorite = ArrayList<UserData>()
        for (favorite in favorites) {
            val favoriteMapped = UserData(
                favorite.id,
                favorite.login,
                favorite.avatarUrl
            )
            listFavorite.add(favoriteMapped)
        }
        return listFavorite
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}