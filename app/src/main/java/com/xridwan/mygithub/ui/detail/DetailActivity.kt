package com.xridwan.mygithub.ui.detail

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso
import com.xridwan.mygithub.R
import com.xridwan.mygithub.data.local.entity.UserData
import com.xridwan.mygithub.databinding.ActivityDetailBinding
import com.xridwan.mygithub.helper.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()

    private val detail = DetailActivity::class.simpleName

    companion object {
        const val EXTRA_DATA = "extra_data"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tabs_followers,
            R.string.tabs_following
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.label_detail)
            elevation = 0.0f
        }

        val data = intent.getParcelableExtra<UserData>(EXTRA_DATA) as UserData
        val id = data.id
        val login = data.login
        val avatar = data.avatarUrl

        var isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = detailViewModel.checkFavorite(id)
            withContext(Dispatchers.Main) {
                if (count != null) {
                    if (count > 0) {
                        binding.tbFavorite.isChecked = true
                        isChecked = true
                    } else {
                        binding.tbFavorite.isChecked = false
                        isChecked = false
                    }
                }
            }
        }

        binding.tbFavorite.setOnClickListener {
            isChecked = !isChecked
            if (isChecked) {
                detailViewModel.addFavorite(id, login, avatar)
                toast("Success added")
            } else {
                detailViewModel.removeFavorite(id)
                toast("Success removed")
            }
            binding.tbFavorite.isChecked = isChecked
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = login
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        detailViewModel.setUser(login)
        getData()
    }

    private fun getData() {
        detailViewModel.detail.observe(this, { items ->
            if (items != null) {
                binding.apply {
                    Picasso.get().load(items.avatarUrl).into(imgAvatarDetail)
                    detailName.text = items.name
                    detailLogin.text = items.login
                    detailCompany.text = "-"
                    detailCompany.text = items.company
                    detailLocation.text = items.location
                    detailRepository.text = getString(R.string.label_repository, items.repository)
                    detailFollowers.text =
                        getString(R.string.label_followers, items.followers.toString())
                    detailFollowing.text =
                        getString(R.string.label_following, items.following.toString())
                }
            }
        })

        detailViewModel.loading.observe(this, { response ->
            if (response) binding.progressDetail.visibility = View.VISIBLE
            else binding.progressDetail.visibility = View.GONE
        })

        detailViewModel.message.observe(this, { response ->
            response.getContentIfNotHandled()?.let {
                toast(it)
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}