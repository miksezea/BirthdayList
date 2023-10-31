package com.example.birthdaylist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.birthdaylist.databinding.FragmentFriendsBinding
import com.example.birthdaylist.models.PersonsAdapter
import com.example.birthdaylist.models.PersonsViewModel
import android.content.res.Configuration
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar

// TODO:  Sort and filter
class FriendsFragment : Fragment() {
    private var _binding: FragmentFriendsBinding? = null
    private val binding get() = _binding!!

    private val personViewModel: PersonsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFriendsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        personViewModel.personsLiveData.observe(viewLifecycleOwner) { persons ->
            binding.progressbar.visibility = View.GONE
            binding.recyclerView.visibility = if (persons == null) View.GONE else View.VISIBLE
            if (persons != null) {
                val adapter = PersonsAdapter(persons) { position ->
                    val action =
                        FriendsFragmentDirections.
                        actionFriendsFragmentToFriendDetailedFragment(position)
                    findNavController().navigate(action)
                }
                var columns = 2
                val currentOrientation = resources.configuration.orientation
                if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                    columns = 4
                } else if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) {
                    columns = 2
                }
                binding.recyclerView.layoutManager = GridLayoutManager(this.context, columns)

                binding.recyclerView.adapter = adapter
            }
        }

        personViewModel.errorMessageLiveData.observe(viewLifecycleOwner) { errorMessage ->
            binding.textviewMessage.text = errorMessage
        }

        personViewModel.reload()

        binding.swiperefresh.setOnRefreshListener {
            personViewModel.reload()
            binding.swiperefresh.isRefreshing = false // TODO too early
        }

        binding.fabAddFriend.setOnClickListener { view ->
            Snackbar.make(view, "Add friend", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            val action = FriendsFragmentDirections.actionFriendsFragmentToAddFriendFragment()
            findNavController().navigate(action)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}