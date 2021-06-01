package com.example.assigmentdemo.viewmodels

import android.graphics.Bitmap
import androidx.databinding.InverseMethod
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assigmentdemo.db.Address
import com.example.assigmentdemo.db.EducationEnity
import com.example.assigmentdemo.db.Experience
import com.example.assigmentdemo.db.UserEnitity
import com.example.assigmentdemo.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel(val userRepository: UserRepository) : ViewModel() {
    val getUser = userRepository.getUser()
    val fName = MutableLiveData<String>()
    val FirstName:LiveData<String>
    get() = fName


    val lName = MutableLiveData<String>()
    val LastName:LiveData<String>
        get() = lName

    val email_id = MutableLiveData<String>()
    val Email:LiveData<String>
        get() = email_id

    val pwd = MutableLiveData<String>()
    val Password:LiveData<String>
    get() = pwd


    val conf_pwd = MutableLiveData<String>()


    val mob = MutableLiveData<String>()
    val mobile:LiveData<String>
    get() =mob

    val profile_photo = MutableLiveData<Bitmap>()
    val user_photo :LiveData<Bitmap>
    get() = profile_photo


    val gender = MutableLiveData<String>()
    val user_gender :LiveData<String>
        get() = gender

    val Education = MutableLiveData<String>()
    val user_edu :LiveData<String>
        get() = Education

    val YOP = MutableLiveData<String>()
    val user_yop :LiveData<String>
        get() = YOP

    val University = MutableLiveData<String>()
    val user_university :LiveData<String>
        get() = University

    val Grade = MutableLiveData<String>()
    val user_grade :LiveData<String>
        get() = Grade

    val YOE = MutableLiveData<String>()
    val user_yoe :LiveData<String>
        get() = YOE


    val Designation = MutableLiveData<String>()
    val user_designation :LiveData<String>
        get() = Designation


    val Domain = MutableLiveData<String>()

    val user_domain :LiveData<String>
        get() = Domain


    val Address= MutableLiveData<String>()
    val user_address :LiveData<String>
        get() = Address


    val Locality = MutableLiveData<String>()
    val user_locality :LiveData<String>
        get() = Locality

    val Zip_Code = MutableLiveData<String>()
    val user_zipcode :LiveData<String>
        get() = Zip_Code

    val City = MutableLiveData<String>()
    val user_city :LiveData<String>
        get() = City

    val State = MutableLiveData<String>()
    val user_state :LiveData<String>
        get() = State

    fun inserUser() {
        val fname = fName.value!!
        val lName = lName.value!!
        val email_id = email_id.value!!
        val pwd = pwd.value!!
        val conf_pwd = conf_pwd.value!!
        val mob = mob.value!!
        val profile_photo = profile_photo.value!!
        val gender = gender.value!!
        val Education = Education.value!!
        val YOP = YOP.value!!
        val University = University.value!!
        val Grade = Grade.value!!
        val YOE = YOE.value!!
        val Designation = Designation.value!!
        val Domain = Domain.value!!
        val Address = Address.value!!
        val Locality = Locality.value!!
        val Zip_Code = Zip_Code.value!!
        val City = City.value!!
        val State = State.value!!
        val eductation = EducationEnity(Education, YOP, University, Grade)
        val experience = Experience(YOE, Designation, Domain)
        val address = com.example.assigmentdemo.db.Address(Address,Locality,Zip_Code,City,State)


        viewModelScope.launch(Dispatchers.IO) {
            userRepository.insert(
                listOf(
                    UserEnitity(
                        id = 0, fname, lName, email_id, pwd, conf_pwd, mob, profile_photo, gender,
                        eductation, experience,address
                    )
                )
            )
        }

    }

}