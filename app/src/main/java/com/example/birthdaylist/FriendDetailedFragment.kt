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
import com.example.birthdaylist.databinding.FragmentFriendDetailedBinding
import com.example.birthdaylist.models.PersonsViewModel
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth

// TODO: Implement picture
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
            binding.textViewFriendDetailedError.text = "Error: Person with id: " + position.toString() + "not found"
            return
        } else {
            binding.textViewFriendDetailedId.text = person.id.toString()
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
                    val newPerson = person.copy(
                        name = binding.editTextFriendDetailedName.text.toString(),
                        birthDayOfMonth = binding.edittextAddDay.text.toString().toInt(),
                        birthMonth = binding.edittextAddMonth.text.toString().toInt(),
                        birthYear = binding.edittextAddYear.text.toString().toInt(),
                        remarks = binding.editTextFriendDetailedRemarks.text.toString()
                    )
                    personViewModel.update(newPerson)

                    binding.textViewFriendDetailedName.text = newPerson.name
                    binding.textViewFriendDetailedBirthdate.text = newPerson.getBirthdayString()
                    binding.textViewFriendDetailedAge.text = newPerson.age.toString()
                    binding.textViewFriendDetailedRemarks.text = newPerson.remarks

                    binding.linearLayoutFriendDetailedUpdate.visibility = View.GONE
                    binding.linearLayoutFriendDetailedView.visibility = View.VISIBLE
                }
            }
            binding.buttonDeleteFriend.setOnClickListener {
                personViewModel.delete(person.id)
                findNavController().popBackStack()
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