package com.example.birthdaylist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.birthdaylist.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        auth = FirebaseAuth.getInstance()

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
                        auth.currentUser
                        findNavController().navigate(R.id.action_LoginFragment_to_FriendsFragment)
                    } else {
                        Log.w("APPLE", "signInWithEmail:failure", task.exception)
                        //binding.textviewMessage.text = "Authentication failed." +
                                //task.exception?.message
                        Toast.makeText(
                            context,
                            "Username or password is incorrect",
                            Toast.LENGTH_LONG
                        ).show()

                    }
                }
        }
        binding.textviewRegister.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}