package com.example.myvalidationapp.presentation

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.myvalidationapp.R
import com.example.myvalidationapp.databinding.FragmentFormBinding
import androidx.navigation.fragment.findNavController
import com.example.myvalidationapp.data.User
import com.example.myvalidationapp.presentation.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint

class FormFragment : Fragment() {

    private var _binding: FragmentFormBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UserViewModel by viewModels()

    private val cities = listOf(
        "Dubai",
        "Abu Dhabi",
        "Sharjah",
        "Riyadh"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFormBinding.inflate(
            inflater,
            container,
            false
        )

        binding.etName.doAfterTextChanged {
            binding.btnSubmit.isEnabled = isFormValid()
        }

        binding.etEmail.doAfterTextChanged {
            binding.btnSubmit.isEnabled = isFormValid()
        }

        binding.etPhone.doAfterTextChanged {
            binding.btnSubmit.isEnabled = isFormValid()
        }

        binding.actvCity.setOnItemClickListener { _, _, _, _ ->
            binding.btnSubmit.isEnabled = isFormValid()
        }

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        setupCityDropdown()
        binding.etName.setOnFocusChangeListener { _, hasFocus ->

            if (!hasFocus) {

                val name =
                    binding.etName.text.toString().trim()

                if (name.length < 2 ||
                    !name.matches(Regex("^[a-zA-Z ]+$"))
                ) {

                    binding.etName.error =
                        "Enter valid name"

                } else {

                    binding.etName.error = null
                }
            }
        }
        binding.etEmail.setOnFocusChangeListener { _, hasFocus ->

            if (!hasFocus) {

                val email =
                    binding.etEmail.text.toString().trim()

                if (!android.util.Patterns.EMAIL_ADDRESS
                        .matcher(email)
                        .matches()
                ) {

                    binding.etEmail.error =
                        "Enter valid email"

                } else {

                    binding.etEmail.error = null
                }
            }
        }
        binding.etPhone.setOnFocusChangeListener { _, hasFocus ->

            if (!hasFocus) {

                val phone =
                    binding.etPhone.text.toString().trim()

                if (!phone.matches(Regex("^\\d{7,15}$"))) {

                    binding.etPhone.error =
                        "Enter valid phone number"

                } else {

                    binding.etPhone.error = null
                }
            }
        }
        binding.btnSubmit.isEnabled = isFormValid()
            binding.btnSubmit.setOnClickListener {

                val user = User(
                    name = binding.etName.text.toString().trim(),
                    email = binding.etEmail.text.toString().trim(),
                    phone = binding.etPhone.text.toString().trim(),
                    city = binding.actvCity.text.toString().trim()
                )

                viewModel.createUser(user)

                viewModel.createUserResponse.observe(viewLifecycleOwner) {

                    if (it.isSuccessful) {

                        val bundle = Bundle().apply {

                            putString("name", user.name)

                            putString("email", user.email)

                            putString("phone", user.phone)

                            putString("city", user.city)
                        }

                        findNavController().navigate(
                            R.id.action_formFragment_to_successFragment,
                            bundle
                        )
                    }
                }

        }

        viewModel.isLoading.observe(viewLifecycleOwner) {

            binding.progressBar.visibility =

                if (it) View.VISIBLE
                else View.GONE
        }
        viewModel.errorMessage.observe(viewLifecycleOwner) {

            Toast.makeText(
                requireContext(),
                it,
                Toast.LENGTH_SHORT
            ).show()
        }


    }

    private fun setupCityDropdown() {

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            cities
        )

        binding.actvCity.setAdapter(adapter)
    }
    private fun isFormValid(): Boolean {

        val name = binding.etName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val phone = binding.etPhone.text.toString().trim()
        val city = binding.actvCity.text.toString().trim()

        val isNameValid =
            name.length >= 2 && name.matches(Regex("^[a-zA-Z ]+$"))

        val isEmailValid =
            android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

        val isPhoneValid =
            phone.matches(Regex("^\\d{7,15}$"))

        val isCityValid =
            city != "Select City" && city.isNotEmpty()

        return isNameValid &&
                isEmailValid &&
                isPhoneValid &&
                isCityValid
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}