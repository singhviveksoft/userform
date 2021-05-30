package com.example.assigmentdemo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.assigmentdemo.databinding.FragmentProfessionalInfoBinding
import com.example.assigmentdemo.db.UserDataBase
import com.example.assigmentdemo.repository.UserRepository
import com.example.assigmentdemo.utils.EducationEnum
import com.example.assigmentdemo.utils.Validation
import com.example.assigmentdemo.viewmodelfactorys.UserFactory
import com.example.assigmentdemo.viewmodels.UserViewModel


class ProfessionalInfoFragment : Fragment() {
    private lateinit var binding: FragmentProfessionalInfoBinding
    private lateinit var db: UserDataBase
    private lateinit var userViewModel: UserViewModel
    var education: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfessionalInfoBinding.inflate(inflater, container, false)
        db = UserDataBase.getInstance(activity?.applicationContext!!)
        userViewModel = ViewModelProvider(requireActivity(), UserFactory(UserRepository(db))).get(
            UserViewModel::class.java
        )

        binding.viewModel = userViewModel
        binding.setLifecycleOwner(this)

        binding.nextButton.setOnClickListener {
          //  findNavController().navigate(R.id.action_professionalInfoFragment_to_addressFragment)

            if (check()) {
                findNavController().navigate(R.id.action_professionalInfoFragment_to_addressFragment)
            }
        }
        binding.previousBtn.setOnClickListener {
            findNavController().navigate(R.id.action_professionalInfoFragment_to_basicFragment)

        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val arrayAdapter = ArrayAdapter<EducationEnum>(
            requireContext(),
            R.layout.drop_down,
            EducationEnum.values()
        )
        binding.autoComplete.setAdapter(arrayAdapter)
    }

    fun check(): Boolean {


        education = binding.autoComplete.text.toString()
        if (education.isNullOrBlank()) {
            return Validation.errormsgcheck(requireContext(), "Select your education")

        }
        if (!Validation.isCharValid(binding.yopEdt)) {
            return Validation.errormsg(requireContext(), "Enter your year of passing",binding.yopEdt)
        }
        if (!Validation.isCharactValid(binding.universityEdt)) {
            return Validation.errormsg(requireContext(), "Enter your university",binding.universityEdt)
        }
        if (!Validation.isCharactValid(binding.gradeEdt)) {
            return Validation.errormsg(requireContext(), "Enter your grade",binding.gradeEdt)
        }
        if (!Validation.isCharactValid(binding.yoeEdt)) {
            return Validation.errormsg(requireContext(), "Enter year of experience",binding.yoeEdt)
        }
        if (!Validation.isCharactValid(binding.discriptionEdt)) {
            return Validation.errormsg(requireContext(), "Enter designation",binding.discriptionEdt)
        }
        if (!Validation.isCharactValid(binding.domainEdt)) {
            return Validation.errormsg(requireContext(), "Enter your domain",binding.domainEdt)
        }
        return true

    }
}