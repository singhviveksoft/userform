package com.example.assigmentdemo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.assigmentdemo.databinding.FragmentAddressBinding
import com.example.assigmentdemo.databinding.FragmentUserProfileBinding
import com.example.assigmentdemo.db.UserDataBase
import com.example.assigmentdemo.repository.UserRepository
import com.example.assigmentdemo.viewmodelfactorys.UserFactory
import com.example.assigmentdemo.viewmodels.UserViewModel


class UserProfileFragment : Fragment() {
    private lateinit var binding: FragmentUserProfileBinding
    private lateinit var userViewModel: UserViewModel
    private   lateinit var db: UserDataBase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentUserProfileBinding.inflate(inflater, container, false)
        db= UserDataBase.getInstance(requireActivity())
        userViewModel= ViewModelProvider(requireActivity(), UserFactory(UserRepository(db)))
            .get(UserViewModel::class.java)
        binding.viewModel=userViewModel
        binding.lifecycleOwner=this
        displayUser()
        return binding.root
    }
    fun displayUser(){
        userViewModel.getUser.observe(viewLifecycleOwner, Observer {
            try {
                Log.i("check",it.toString())
            }catch (e:Exception){
                Log.i("check",e.message.toString())

            }
        })
    }

}