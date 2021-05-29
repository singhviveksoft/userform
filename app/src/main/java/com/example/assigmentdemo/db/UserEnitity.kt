package com.example.assigmentdemo.db

import android.graphics.Bitmap
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class UserEnitity(
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val FirstName:String,
    val LastName:String,
    val Email:String,
    val Password:String,
    val ConfirmPassword:String,
    val MobileNumber:String,
    val ProfilePhoto:Bitmap,
    val Gender:String,
   @Embedded
    val educationEnity: EducationEnity,
    @Embedded
    val experience: Experience,
    @Embedded
    val address: Address

){

}

data class EducationEnity(
    val Education:String,
    val YearOfPassing:String,
    val University:String,
    val Grade:String,
)

data class  Experience(
    val yearExperience:String,
    val Designation:String,
    val Domain:String,

)

data class  Address(
    val Address :String,
    val Locality :String,
    val ZipCode :String,
    val City :String,
    val State:String

)


