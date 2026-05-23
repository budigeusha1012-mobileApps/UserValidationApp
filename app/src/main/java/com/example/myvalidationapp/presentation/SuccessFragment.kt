package com.example.myvalidationapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myvalidationapp.R
import com.example.myvalidationapp.databinding.FragmentSuccessBinding

class SuccessFragment : Fragment() {

    private var _binding: FragmentSuccessBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSuccessBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {

        super.onViewCreated(view, savedInstanceState)

        val name = arguments?.getString("name")

        val email = arguments?.getString("email")

        val phone = arguments?.getString("phone")

        val city = arguments?.getString("city")

        binding.tvName.text = "Name : $name"

        binding.tvEmail.text = "Email : $email"

        binding.tvPhone.text = "Phone : $phone"

        binding.tvCity.text = "City : $city"

        binding.btnBack.setOnClickListener {

            findNavController().navigate(
                R.id.action_successFragment_to_formFragment
            )
        }
    }

    override fun onDestroyView() {

        super.onDestroyView()

        _binding = null
    }
}