package com.xridwan.mygithub.ui.following

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.xridwan.mygithub.R
import com.xridwan.mygithub.data.local.entity.UserData
import com.xridwan.mygithub.databinding.UserItemLayoutBinding
import com.xridwan.mygithub.helper.AdapterDiffCallback

class FollowingAdapter : RecyclerView.Adapter<FollowingAdapter.FollowingViewHolder>() {

    private val followingList = ArrayList<UserData>()

    fun setData(followingList: List<UserData>) {
        val diffCallback = AdapterDiffCallback(this.followingList, followingList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.followingList.clear()
        this.followingList.addAll(followingList)
        diffResult.dispatchUpdatesTo(this)
    }

    class FollowingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = UserItemLayoutBinding.bind(itemView)
        fun bind(userData: UserData) {
            binding.apply {
                tvLogin.text = userData.login
                Picasso.get().load(userData.avatarUrl).into(imgAvatar)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.user_item_layout, parent, false)
        return FollowingViewHolder(view)
    }

    override fun onBindViewHolder(holder: FollowingViewHolder, position: Int) {
        holder.bind(followingList[position])
    }

    override fun getItemCount(): Int = followingList.size
}