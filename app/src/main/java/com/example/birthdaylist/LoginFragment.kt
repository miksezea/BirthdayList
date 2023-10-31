package com.example.birthdaylist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.birthdaylist.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth

// TODO: Add login functionality
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonLogin.setOnClickListener {
            val email = binding.edittextEmail.text.trim().toString()
            if (email.isEmpty()) {
                binding.edittextEmail.error = "Please enter an email"
                return@setOnClickListener
            }
            val password = binding.edittextPassword.text.trim().toString()
            if (password.isEmpty()) {
                binding.edittextPassword.error = "Please enter a password"
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        Log.d("APPLE", "signInWithEmail:success")
                        val user = auth.currentUser
                        findNavController().navigate(R.id.action_LoginFragment_to_FriendsFragment)
                    } else {
                        Log.w("APPLE", "signInWithEmail:failure", task.exception)
                        binding.textviewMessage.text = "Authentication failed." +
                                task.exception?.message
                    }
                }
        }

        binding.textviewRegister.setOnClickListener {
            findNavController().navigate(R.id.action_LoginFragment_to_RegisterFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}