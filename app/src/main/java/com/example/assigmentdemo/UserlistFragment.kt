package com.example.assigmentdemo

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.assigmentdemo.databinding.FragmentUserlistBinding
import com.example.assigmentdemo.databinding.UserItemBinding
import com.example.assigmentdemo.db.UserDataBase
import com.example.assigmentdemo.db.UserEnitity
import com.example.assigmentdemo.repository.UserRepository
import com.example.assigmentdemo.viewmodelfactorys.UserFactory
import com.example.assigmentdemo.viewmodels.UserViewModel
import kotlinx.coroutines.delay


class UserlistFragment : Fragment() {
private lateinit var binding: FragmentUserlistBinding
private lateinit var userAdapter :UserAdapter
private lateinit var dataBase: UserDataBase
private lateinit var viewModel: UserViewModel
private  var list= listOf<UserEnitity>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate( inflater,R.layout.fragment_userlist, container, false)
        dataBase= UserDataBase.getInstance(requireContext())
        viewModel= ViewModelProvider(this,UserFactory(UserRepository(dataBase))).get(UserViewModel::class.java)

        userAdapter= UserAdapter()
        binding.userListRv.adapter=userAdapter
        viewModel.getUser.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()){
                    list=it.reversed()
             Handler(Looper.getMainLooper()).postDelayed({
                 binding.shimmer.stopShimmer()
                 binding.shimmer.visibility=View.INVISIBLE
                binding.userListRv.visibility=View.VISIBLE
                 userAdapter.addUser(list)
                Log.i("myThread",Thread.currentThread().name)
             },3000)
            }
        })
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.shimmer.startShimmer()
    }

    override fun onPause() {
        super.onPause()
        binding.shimmer.stopShimmer()
    }
}