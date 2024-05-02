package com.freecnsz.whereToEat.ui.signUp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.freecnsz.whereToEat.R
import com.freecnsz.whereToEat.databinding.FragmentSignUpBinding
import com.freecnsz.whereToEat.ui.help.HelpFragment
import com.freecnsz.whereToEat.ui.logIn.LogInFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private val auth: FirebaseAuth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        inflater.inflate(R.layout.fragment_sign_up, container, false)

        _binding = FragmentSignUpBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSignup.setOnClickListener { isNull() }

        binding.backArrow.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.textHelp.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentView, HelpFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }


    private fun signUp(name: String, email: String, password: String) {
        binding.progressBar.visibility = View.VISIBLE
        val changeRequest = UserProfileChangeRequest.Builder().setDisplayName(name).build()

        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                auth.currentUser!!.updateProfile(changeRequest)
                Toast.makeText(context,"Successful",Toast.LENGTH_LONG).show()
                parentFragmentManager.beginTransaction().replace(R.id.fragmentView, LogInFragment()).commit()
            } else {
                Toast.makeText(context,"Failed",Toast.LENGTH_LONG).show()
            }
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

    private fun isNull() {

        val name = binding.editTextName.text.toString()
        val email = binding.editTextEmail.text.toString()
        val password = binding.editTextPassword.text.toString()

        if (email == "" || password == "" || name == "") {
            binding.editTextEmail.error = "Required"
            binding.editTextPassword.error = "Required"
            binding.editTextName.error = "Required"
        }else {
            signUp(name, email, password)
        }
    }
}