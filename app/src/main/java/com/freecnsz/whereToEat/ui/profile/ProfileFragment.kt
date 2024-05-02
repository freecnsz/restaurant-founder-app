package com.freecnsz.whereToEat.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.freecnsz.whereToEat.R
import com.freecnsz.whereToEat.databinding.FragmentProfileBinding
import com.freecnsz.whereToEat.ui.settings.SettingsFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val auth = Firebase.auth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        inflater.inflate(R.layout.fragment_profile, container, false)

        _binding = FragmentProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textViewName.text = auth.currentUser!!.displayName.toString()
        binding.textFieldName.text = auth.currentUser!!.displayName.toString()
        binding.textFieldEmail.text = auth.currentUser!!.email.toString()

        binding.editButton.setOnClickListener {
            Toast.makeText(context,"This feature will be available soon",Toast.LENGTH_SHORT).show()
        }

        binding.iconSettings.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.frameLayout, SettingsFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        binding.backArrow.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}