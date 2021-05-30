package com.example.assigmentdemo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.assigmentdemo.databinding.FragmentAddressBinding
import com.example.assigmentdemo.db.UserDataBase
import com.example.assigmentdemo.repository.UserRepository
import com.example.assigmentdemo.utils.EducationEnum
import com.example.assigmentdemo.utils.StateEnum
import com.example.assigmentdemo.utils.Validation
import com.example.assigmentdemo.viewmodelfactorys.UserFactory
import com.example.assigmentdemo.viewmodels.UserViewModel


class AddressFragment : Fragment() {
    var state:String?=null

private lateinit var binding:FragmentAddressBinding
private lateinit var userViewModel: UserViewModel
    private   lateinit var db:UserDataBase


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         binding= FragmentAddressBinding.inflate(inflater, container, false)
        db= UserDataBase.getInstance(requireActivity())
        userViewModel=ViewModelProvider(requireActivity(),UserFactory(UserRepository(db)))
            .get(UserViewModel::class.java)
        binding.viewModel=userViewModel
        binding.lifecycleOwner=this
        binding.nextBtn.setOnClickListener {
              //  findNavController().navigate(R.id.action_addressFragment_to_profileFragment)
            if (check()){
                userViewModel.inserUser()
                findNavController().navigate(R.id.action_addressFragment_to_userProfileFragment)

            }
        }

        binding.backBtn.setOnClickListener {
            findNavController().navigate(R.id.action_addressFragment_to_professionalInfoFragment)
        }
        return binding.root
    }


        override fun onResume() {
            super.onResume()
            val arrayAdapter=
                ArrayAdapter<StateEnum>(requireContext(),R.layout.drop_down, StateEnum.values())
            binding.autoComplete.setAdapter(arrayAdapter)


    }

    fun check():Boolean{
        state= binding.autoComplete.text.toString()
        if (!Validation.isCharValid(binding.addressEdit))
        {
            return Validation.errormsg(requireContext(),"Enter more than 3  characters address",binding.addressEdit)
        }
        if (!Validation.isCharValid(binding.localityEdt))
        {
            return Validation.errormsg(requireContext(),"Enter more than 3 characters locality",binding.localityEdt)
        }
        if (!Validation.isZipValid(binding.zipCodeEdt))
        {
            return Validation.errormsg(requireContext(),"Enter proper zip code",binding.zipCodeEdt)
        }
        if (!Validation.isCharactValid(binding.cityEdt))
        {
            return Validation.errormsg(requireContext(),"Enter your city",binding.cityEdt)
        }


        if (state.isNullOrBlank()){
            return Validation.errormsgcheck(requireContext(),"Select your state")

        }

        return true
    }


}