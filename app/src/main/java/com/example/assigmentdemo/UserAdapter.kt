package com.example.assigmentdemo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.assigmentdemo.databinding.UserItemBinding
import com.example.assigmentdemo.db.UserEnitity

class UserAdapter:RecyclerView.Adapter<UserAdapter.userViewHolder>() {
val list= arrayListOf<UserEnitity>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): userViewHolder {
        val view=UserItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return userViewHolder(view)

    }

    override fun onBindViewHolder(holder: userViewHolder, position: Int) {
        holder.bind(list[position])

    }

    override fun getItemCount(): Int {
       return list.size
    }


    fun addUser(list:List<UserEnitity>){
        this.list.addAll(list)
        notifyDataSetChanged()
    }


    inner class userViewHolder(val binding:UserItemBinding) : RecyclerView.ViewHolder (binding.root){


        fun bind(list: UserEnitity) {
            binding.model=list

        }

    }

}