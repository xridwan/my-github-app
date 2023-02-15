package com.xridwan.mygithub.helper

import androidx.recyclerview.widget.DiffUtil
import com.xridwan.mygithub.data.local.entity.UserData

class AdapterDiffCallback(
    private val mOldDataList: List<UserData>,
    private val mNewDataList: List<UserData>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = mOldDataList.size

    override fun getNewListSize(): Int = mNewDataList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        mOldDataList[oldItemPosition].id == mNewDataList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee = mOldDataList[oldItemPosition]
        val newEmployee = mNewDataList[newItemPosition]
        return oldEmployee.login == newEmployee.login
    }
}