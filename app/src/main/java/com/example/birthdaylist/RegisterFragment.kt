package com.example.birthdaylist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.birthdaylist.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater,container,false)
        auth = FirebaseAuth.getInstance()
        return binding.root
    }

    override fun onViewCreated(view:View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonRegister.setOnClickListener {
            val email = binding.edittextRegisterUsername.text.toString()
            val password = binding.edittextRegisterPassword.text.toString()
            val confirmPassword = binding.edittextRegisterPasswordConfirm.text.toString()
            if (email.isEmpty()) {
                binding.edittextRegisterUsername.error = "Please enter an email"
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                binding.edittextRegisterPassword.error = "Please enter a password"
                return@setOnClickListener
            }
            if (confirmPassword.isEmpty()) {
                binding.edittextRegisterPasswordConfirm.error = "Please confirm your password"
                return@setOnClickListener
            }
            if (password != confirmPassword) {
                binding.edittextRegisterPasswordConfirm.error = "Passwords do not match"
                return@setOnClickListener
            }
            auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        Log.d("APPLE", "createUserWithEmail:success")
                        auth.currentUser
                        findNavController().navigate(R.id.action_RegisterFragment_to_FriendsFragment)
                    } else {
                        Log.w("APPLE", "createUserWithEmail:failure", task.exception)
                        binding.textviewRegisterError.text = "Registration failed: " + task.exception?.message
                    }
                }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}