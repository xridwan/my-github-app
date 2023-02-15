package com.xridwan.mygithub.ui.main

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

class MainAdapter : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    private val mainList = ArrayList<UserData>()
    private lateinit var onItemClickCallBack: OnItemClickCallBack

    fun setOnItemCLickCallBack(onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack
    }

    fun setData(mainList: MutableList<UserData>) {
        val diffCallback = AdapterDiffCallback(this.mainList, mainList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.mainList.clear()
        this.mainList.addAll(mainList)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = UserItemLayoutBinding.bind(itemView)
        fun bind(userData: UserData) {
            binding.apply {
                tvLogin.text = userData.login
                Picasso.get().load(userData.avatarUrl).into(imgAvatar)
            }

            itemView.setOnClickListener {
                onItemClickCallBack.onItemClicked(userData)
            }
        }
    }

    interface OnItemClickCallBack {
        fun onItemClicked(data: UserData)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.user_item_layout, parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(mainList[position])
    }

    override fun getItemCount(): Int = mainList.size
}