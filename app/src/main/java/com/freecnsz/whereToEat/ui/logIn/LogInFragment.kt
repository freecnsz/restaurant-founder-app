package com.freecnsz.whereToEat.ui.logIn

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.core.content.ContextCompat
import com.freecnsz.whereToEat.HomeActivity
import com.freecnsz.whereToEat.R
import com.freecnsz.whereToEat.databinding.FragmentLogInBinding
import com.freecnsz.whereToEat.ui.help.HelpFragment
import com.freecnsz.whereToEat.ui.signUp.SignUpFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LogInFragment : Fragment() {

    private var _binding: FragmentLogInBinding? = null
    private val binding get() = _binding!!
    private val auth = Firebase.auth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        inflater.inflate(R.layout.fragment_log_in, container, false)


        _binding = FragmentLogInBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonLogin.setOnClickListener { isNull() }

        binding.textCreateOne.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentView, SignUpFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        binding.textHelp.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentView, HelpFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun logIn(email: String, password: String) {

        binding.progressBar.visibility = View.VISIBLE

        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {task ->
            if (task.isSuccessful) {
                Toast.makeText(context,"Successful",Toast.LENGTH_LONG).show()
                val intent = Intent(context,HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            } else {
                binding.progressBar.visibility = View.INVISIBLE
                Toast.makeText(context,"Failed",Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun isNull() {
        val email = binding.editTextEmail.text.toString()
        val password = binding.editTextPassword.text.toString()

        if (email == "" || password == "") {
            binding.editTextEmail.error = "Required"
            binding.editTextPassword.error = "Required"
        }else {
            logIn(email,password)
        }
    }
}