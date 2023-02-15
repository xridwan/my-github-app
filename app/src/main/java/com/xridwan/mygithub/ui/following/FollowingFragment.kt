package com.xridwan.mygithub.ui.following

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.xridwan.mygithub.databinding.FragmentFollowingBinding
import com.xridwan.mygithub.helper.Helper.toast

class FollowingFragment : Fragment() {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private val followingViewModel by viewModels<FollowingViewModel>()
    private lateinit var followingAdapter: FollowingAdapter

    companion object {
        var ARG_USERNAME = "username"

        fun newInstance(username: String?): FollowingFragment {
            val fragment = FollowingFragment()
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, username)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            val username = arguments?.getString(ARG_USERNAME)
            setAdapter()
            getData()
            followingViewModel.getFollowing(username.toString())
        }
    }

    private fun setAdapter() {
        followingAdapter = FollowingAdapter()

        binding.apply {
            rvFollowing.layoutManager = LinearLayoutManager(requireContext())
            rvFollowing.setHasFixedSize(true)
            rvFollowing.adapter = followingAdapter
        }
    }

    private fun getData() {
        followingViewModel.following.observe(viewLifecycleOwner, { items ->
            if (items != null) followingAdapter.setData(items)
        })

        followingViewModel.loading.observe(viewLifecycleOwner, {
            if (it) binding.progressFollowing.visibility = View.VISIBLE
            else binding.progressFollowing.visibility = View.GONE
        })

        followingViewModel.message.observe(viewLifecycleOwner, { response ->
            response.getContentIfNotHandled()?.let {
                toast(it)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}