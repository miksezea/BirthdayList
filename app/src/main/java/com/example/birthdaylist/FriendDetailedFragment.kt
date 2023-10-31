package com.example.birthdaylist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.birthdaylist.databinding.FragmentFriendDetailedBinding
import com.example.birthdaylist.models.PersonsViewModel
import androidx.navigation.fragment.findNavController

// TODO: Implement picture
class FriendDetailedFragment : Fragment() {
    private var _binding: FragmentFriendDetailedBinding? = null
    private val binding get() = _binding!!
    private val personViewModel: PersonsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
            binding.textViewFriendDetailedEmail.text = person.userId
            binding.textViewFriendDetailedName.text = person.name
            binding.textViewFriendDetailedBirthdate.text = person.getBirthdayString()
            binding.textViewFriendDetailedAge.text = person.age.toString()
            binding.textViewFriendDetailedRemarks.text = person.remarks

            binding.buttonUpdateFriend.setOnClickListener {
                binding.linearLayoutFriendDetailedView.visibility = View.GONE
                binding.editTextFriendDetailedEmail.setText(person.userId)
                binding.editTextFriendDetailedName.setText(person.name)
                // TODO: birthdate
                binding.editTextFriendDetailedRemarks.setText(person.remarks)
                binding.linearLayoutFriendDetailedUpdate.visibility = View.VISIBLE

                binding.buttonCancelUpdateFriend.setOnClickListener {
                    binding.linearLayoutFriendDetailedUpdate.visibility = View.GONE
                    binding.linearLayoutFriendDetailedView.visibility = View.VISIBLE
                }

                binding.buttonConfirmUpdateFriend.setOnClickListener {
                    val newPerson = person.copy(
                        userId = binding.editTextFriendDetailedEmail.text.toString(),
                        name = binding.editTextFriendDetailedName.text.toString(),
                        // TODO: birthdate
                        remarks = binding.editTextFriendDetailedRemarks.text.toString()
                    )
                    personViewModel.update(newPerson)
                    binding.textViewFriendDetailedEmail.text = newPerson.userId
                    binding.textViewFriendDetailedName.text = newPerson.name
                    // TODO: birthdate
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
}