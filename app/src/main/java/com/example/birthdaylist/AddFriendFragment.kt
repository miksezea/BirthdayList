package com.example.birthdaylist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.birthdaylist.databinding.FragmentAddFriendBinding
import com.example.birthdaylist.models.PersonsViewModel
import androidx.navigation.fragment.findNavController
import com.example.birthdaylist.models.Person
import com.google.firebase.auth.FirebaseAuth

// TODO: Add friend functionality
class AddFriendFragment : Fragment() {
    private var _binding: FragmentAddFriendBinding? = null
    private val binding get() = _binding!!
    private val personViewModel: PersonsViewModel by activityViewModels()

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddFriendBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonAddFriend.setOnClickListener {
            val email = binding.edittextAddEmail.text.toString()
            val name = binding.edittextAddName.text.toString()
            val day = binding.edittextAddDay.text.toString()
            val month = binding.edittextAddMonth.text.toString()
            val year = binding.edittextAddYear.text.toString()
            val remarks = binding.edittextAddRemarks.text.toString()

            val dayInt = day.toInt()
            val monthInt = month.toInt()
            val yearInt = year.toInt()
            if (name.isEmpty() || email.isEmpty()|| day.isEmpty() || month.isEmpty() || year.isEmpty()) {
                binding.textViewAddFriendError.visibility = View.VISIBLE
                binding.textViewAddFriendError.text = "Error: Please fill in required fields"
                return@setOnClickListener
            }
            if (dayInt < 1 || dayInt > 31 || monthInt < 1 || monthInt > 12 || yearInt < 1900 || yearInt > 2023) {
                binding.textViewAddFriendError.visibility = View.VISIBLE
                binding.textViewAddFriendError.text = "Error: Please fill in a valid date"
                return@setOnClickListener
            }

            val person = Person(userId = email, name = name, birthDayOfMonth = dayInt, birthMonth = monthInt, birthYear = yearInt, remarks = remarks)
            personViewModel.add(person)

            findNavController().navigate(R.id.action_AddFriendFragment_to_FriendsFragment)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_main, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d("APPLE", item.toString())
        when (item.itemId) {
            R.id.action_logout -> {
                auth.signOut()
                findNavController().navigate(R.id.action_AddFriendFragment_to_LoginFragment)
            }
            R.id.action_account -> {
                findNavController().navigate(R.id.action_AddFriendFragment_to_MyAccountFragment)
            }
        }
        return true
    }
}