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
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.birthdaylist.databinding.FragmentFriendDetailedBinding
import com.example.birthdaylist.models.PersonsViewModel
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth

class FriendDetailedFragment : Fragment() {
    private var _binding: FragmentFriendDetailedBinding? = null
    private val binding get() = _binding!!
    private val personViewModel: PersonsViewModel by activityViewModels()
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        auth = FirebaseAuth.getInstance()

        _binding = FragmentFriendDetailedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = requireArguments()
        val friendDetailedFragmentArgs: FriendDetailedFragmentArgs = FriendDetailedFragmentArgs.fromBundle(bundle)
        val position = friendDetailedFragmentArgs.position
        val person = personViewModel[position]
        if (person == null) {
            binding.linearLayoutFriendDetailedView.visibility = View.GONE
            binding.linearLayoutFriendDetailedUpdate.visibility = View.GONE
            binding.textViewFriendDetailedError.visibility = View.VISIBLE
            binding.textViewFriendDetailedError.text = "Error: Person with id: ${position} not found"
            return
        } else {
            binding.textViewFriendDetailedName.text = person.name
            binding.textViewFriendDetailedBirthdate.text = person.getBirthdayString()
            binding.textViewFriendDetailedAge.text = person.age.toString()
            binding.textViewFriendDetailedRemarks.text = person.remarks

            binding.buttonUpdateFriend.setOnClickListener {
                binding.linearLayoutFriendDetailedView.visibility = View.GONE
                binding.editTextFriendDetailedName.setText(person.name)
                binding.edittextAddDay.setText(person.birthDayOfMonth.toString())
                binding.edittextAddMonth.setText(person.birthMonth.toString())
                binding.edittextAddYear.setText(person.birthYear.toString())
                binding.editTextFriendDetailedRemarks.setText(person.remarks)
                binding.linearLayoutFriendDetailedUpdate.visibility = View.VISIBLE

                binding.buttonCancelUpdateFriend.setOnClickListener {
                    binding.linearLayoutFriendDetailedUpdate.visibility = View.GONE
                    binding.linearLayoutFriendDetailedView.visibility = View.VISIBLE
                }

                binding.buttonConfirmUpdateFriend.setOnClickListener {
                    val updates = person.copy(
                        name = binding.editTextFriendDetailedName.text.toString(),
                        birthDayOfMonth = binding.edittextAddDay.text.toString().toInt(),
                        birthMonth = binding.edittextAddMonth.text.toString().toInt(),
                        birthYear = binding.edittextAddYear.text.toString().toInt(),
                        remarks = binding.editTextFriendDetailedRemarks.text.toString()
                    )
                    try {
                        personViewModel.update(updates)
                        Log.d("APPLE", "Updated person with id: " + person.id.toString())
                        binding.textViewFriendDetailedName.text = updates.name
                        binding.textViewFriendDetailedBirthdate.text = updates.getBirthdayString()
                        binding.textViewFriendDetailedAge.text = personViewModel[person.id]!!.age.toString()
                        binding.textViewFriendDetailedRemarks.text = updates.remarks
                        Toast.makeText(
                            context,
                            "Friend details updated successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                    } catch (e: Exception) {
                        Log.d("APPLE", "Error while updating: $e")
                        Toast.makeText(
                            context,
                            "Unable to update friend details: $e",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    binding.linearLayoutFriendDetailedUpdate.visibility = View.GONE
                    binding.linearLayoutFriendDetailedView.visibility = View.VISIBLE
                }

                binding.buttonDeleteFriend.setOnClickListener {
                    try {
                        personViewModel.delete(person.id)
                        Log.d("APPLE", "Deleted person with id: " + person.id.toString())
                        Toast.makeText(
                            context,
                            "Friend was successfully removed",
                            Toast.LENGTH_SHORT
                        ).show()
                        findNavController().popBackStack()
                    } catch (e: Exception) {
                        Log.d("APPLE", e.toString())
                        Toast.makeText(
                            context,
                            "Unable to delete friend: $e",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
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
        return when (item.itemId) {
            R.id.action_logout -> {
                auth.signOut()
                findNavController().navigate(R.id.action_FriendsFragment_to_LoginFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}