package com.xridwan.mygithub.ui.follower

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.xridwan.mygithub.databinding.FragmentFollowersBinding
import com.xridwan.mygithub.helper.toast

class FollowersFragment : Fragment() {

    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!
    private val followersViewModel by viewModels<FollowersViewModel>()
    private lateinit var followersAdapter: FollowersAdapter

    companion object {
        var ARG_USERNAME = "username"

        fun newInstance(username: String?): FollowersFragment {
            val fragment = FollowersFragment()
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, username)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            val username = arguments?.getString(ARG_USERNAME)
            setAdapter()
            getData()
            followersViewModel.getFollowers(username.toString())
        }
    }

    private fun setAdapter() {
        followersAdapter = FollowersAdapter()

        binding.apply {
            rvFollowers.layoutManager = LinearLayoutManager(requireContext())
            rvFollowers.setHasFixedSize(true)
            rvFollowers.adapter = followersAdapter
        }
    }

    private fun getData() {
        followersViewModel.followers.observe(viewLifecycleOwner) { items ->
            if (items != null) followersAdapter.setData(items)
        }

        followersViewModel.loading.observe(viewLifecycleOwner) {
            if (it) binding.progressFollowers.visibility = View.VISIBLE
            else binding.progressFollowers.visibility = View.GONE
        }

        followersViewModel.message.observe(viewLifecycleOwner) { response ->
            response.getContentIfNotHandled()?.let {
                toast(it)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}