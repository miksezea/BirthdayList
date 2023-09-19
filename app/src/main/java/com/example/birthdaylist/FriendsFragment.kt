package com.example.birthdaylist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.birthdaylist.databinding.FragmentFriendsBinding

// TODO: List, detailed view, add, delete, update, sort, filter
class FriendsFragment : Fragment() {

    private var _binding: FragmentFriendsBinding? = null

    private val viewModel: PersonsViewModel by activityViewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFriendsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.persons.observe(viewLifecycleOwner) {
            binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}