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
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

// TODO: Remove back arrow from toolbar
class FriendsFragment : Fragment() {
    private var _binding: FragmentFriendsBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    private val personViewModel: PersonsViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        auth = FirebaseAuth.getInstance()

        _binding = FragmentFriendsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        personViewModel.reload()

        binding.textviewHelloUser.text = "Hello ${auth.currentUser?.email}"

        personViewModel.personsLiveData.observe(viewLifecycleOwner) { persons ->
            binding.progressbar.visibility = View.GONE
            binding.recyclerView.visibility = if (persons == null) View.GONE else View.VISIBLE
            if (persons != null) {
                val adapter = PersonsAdapter(persons) { position ->
                    val action =
                        FriendsFragmentDirections.actionFriendsFragmentToFriendDetailedFragment(position)
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

        binding.swiperefresh.setOnRefreshListener {
            personViewModel.reload()
            binding.swiperefresh.isRefreshing = false
        }

        binding.fabAddFriend.setOnClickListener {
            Snackbar.make(view, "Add friend", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            val action = FriendsFragmentDirections.actionFriendsFragmentToAddFriendFragment()
            findNavController().navigate(action)
        }

        binding.buttonSortByNameAsc.setOnClickListener {
            personViewModel.sortByName()
        }
        binding.buttonSortByNameDesc.setOnClickListener {
            personViewModel.sortByNameDescending()
        }

        binding.buttonSortByAgeAsc.setOnClickListener {
            personViewModel.sortByAge()
        }
        binding.buttonSortByAgeDesc.setOnClickListener {
            personViewModel.sortByAgeDescending()
        }

        binding.buttonSortByBirthdayAsc.setOnClickListener {
            personViewModel.sortByBirthday()
        }
        binding.buttonSortByBirthdayDesc.setOnClickListener {
            personViewModel.sortByBirthdayDescending()
        }

        binding.buttonFilter.setOnClickListener {
            val name = binding.edittextFilterName.text.toString()
            val ageBelow = binding.edittextFilterAgeBelow.text.toString()
            val ageAbove = binding.edittextFilterAgeAbove.text.toString()

            if (name.isNotBlank()) {
                personViewModel.filterByName(name)
            }
            if (ageAbove.isNotBlank()) {
                personViewModel.filterByAgeAbove(ageAbove.toInt())
            }
            if (ageBelow.isNotBlank()) {
                personViewModel.filterByAgeBelow(ageBelow.toInt())
            }
        }

        binding.buttonResetList.setOnClickListener {
            binding.edittextFilterName.text.clear()
            binding.edittextFilterAgeBelow.text.clear()
            binding.edittextFilterAgeAbove.text.clear()
            personViewModel.reload()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_main, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d("APPLE", item.toString())
        return when (item.itemId) {
            R.id.action_logout -> {
                auth.signOut()
                findNavController().navigate(R.id.action_FriendsFragment_to_LoginFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}