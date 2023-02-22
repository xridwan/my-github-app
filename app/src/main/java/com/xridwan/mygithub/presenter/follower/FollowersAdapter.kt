package com.xridwan.mygithub.presenter.follower

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.xridwan.mygithub.R
import com.xridwan.mygithub.databinding.UserItemLayoutBinding
import com.xridwan.mygithub.domain.model.FollowerData
import com.xridwan.mygithub.utils.FollowAdapterDiffCallback

class FollowersAdapter : RecyclerView.Adapter<FollowersAdapter.FollowersViewHolder>() {

    private val followerList = ArrayList<FollowerData>()

    fun setData(followerList: List<FollowerData>) {
        val diffCallback = FollowAdapterDiffCallback(this.followerList, followerList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.followerList.clear()
        this.followerList.addAll(followerList)
        diffResult.dispatchUpdatesTo(this)
    }

    class FollowersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = UserItemLayoutBinding.bind(itemView)
        fun bind(userResponse: FollowerData) {
            binding.apply {
                tvLogin.text = userResponse.login
                Picasso.get().load(userResponse.avatarUrl).into(imgAvatar)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowersViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.user_item_layout, parent, false)
        return FollowersViewHolder(view)
    }

    override fun onBindViewHolder(holder: FollowersViewHolder, position: Int) {
        holder.bind(followerList[position])
    }

    override fun getItemCount(): Int = followerList.size
}