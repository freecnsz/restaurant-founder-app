package com.freecnsz.whereToEat.ui.settings

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.freecnsz.whereToEat.MainActivity
import com.freecnsz.whereToEat.R
import com.freecnsz.whereToEat.databinding.FragmentSettingsBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val auth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        inflater.inflate(R.layout.fragment_settings, container, false)

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backArrow.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.buttonSave.setOnClickListener { isNull() }

        binding.buttonLogOut.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            auth.signOut()
            val intent = Intent(context,MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }

    }

    private fun changePassword(newPassword: String, newPasswordAgain: String) {
        binding.progressBar.visibility = View.VISIBLE

        if (newPassword == newPasswordAgain) {
            auth.currentUser!!.updatePassword(newPassword).addOnCompleteListener { task ->
                if (task.isSuccessful) Toast.makeText(context,"Password successfully changed!",Toast.LENGTH_SHORT).show()
                else Toast.makeText(context,"Failed to change password!",Toast.LENGTH_SHORT).show()
                binding.progressBar.visibility = View.INVISIBLE
            }
        }else {
            binding.progressBar.visibility = View.INVISIBLE
            binding.editTextPassword.error = "Passwords must be matched!"
        }
    }

    private fun isNull() {
        val newPassword = binding.editTextPassword.text.toString()
        val newPasswordAgain = binding.editTextPasswordAgain.text.toString()

        if (newPassword == "" || newPasswordAgain == "") {
            binding.editTextPassword.error = "Required"
            binding.editTextPasswordAgain.error = "Required"
        } else {
            changePassword(newPassword, newPasswordAgain)
        }
    }


}