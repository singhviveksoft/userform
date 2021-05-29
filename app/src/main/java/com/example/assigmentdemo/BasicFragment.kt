package com.example.assigmentdemo

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import coil.ImageLoader
import coil.load
import coil.request.ImageRequest
import coil.request.SuccessResult
import coil.transform.CircleCropTransformation
import com.example.assigmentdemo.databinding.FragmentBasicBinding
import com.example.assigmentdemo.db.UserDataBase
import com.example.assigmentdemo.repository.UserRepository
import com.example.assigmentdemo.utils.Validation
import com.example.assigmentdemo.viewmodelfactorys.UserFactory
import com.example.assigmentdemo.viewmodels.UserViewModel
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.dialogs.SettingsDialog
import kotlinx.android.synthetic.main.fragment_basic.*
import java.io.FileNotFoundException
import java.io.IOException
import java.util.regex.Matcher
import java.util.regex.Pattern


class BasicFragment : Fragment(),EasyPermissions.PermissionCallbacks {
    companion object {
        const val PERMISSION_CODE = 1
        const val CAMERA_REQUEST_CODE = 0
        const val GALLERY_REQUEST_CODE = 2

    }

    var check_rb_id:Int?=null
    private var imageUri: Uri? = null
    private var  bitmap: Bitmap? = null

    private lateinit var binding: FragmentBasicBinding
    private lateinit var db: UserDataBase
    private lateinit var userViewModel: UserViewModel
    private lateinit var userRepository: UserRepository
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_basic, container, false)
        db = UserDataBase.getInstance(activity?.applicationContext!!)
        userViewModel = ViewModelProvider(requireActivity(), UserFactory(UserRepository(db))).get(
            UserViewModel::class.java
        )
        binding.viewModel = userViewModel
        binding.setLifecycleOwner(this)
        binding.button.setOnClickListener {
            if (check()) {
                //  userViewModel.inserUser()
                findNavController().navigate(R.id.action_basicFragment_to_professionalInfoFragment)
            }
        }
       binding.genderRg.setOnCheckedChangeListener { group, checkedId ->
      //  val male_rb_id=  group.checkedRadioButtonId
        val male_rb_id=  2131230952
           check_rb_id  =  checkedId
           if (male_rb_id==checkedId){
               userViewModel.gender.value="Male"
           }
           else{
               userViewModel.gender.value="Female"

           }
          // Toast.makeText(requireContext(),a.toString(),Toast.LENGTH_LONG).show()
       }



        binding.imageView.setOnClickListener {
            requestPermission()
        }
        return binding.root
    }

    fun hasPermission() {
        EasyPermissions.hasPermissions(
            requireContext(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA
        )
    }

    fun requestPermission() {
        EasyPermissions.requestPermissions(
            this, "camera permission needed", PERMISSION_CODE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if (EasyPermissions.somePermissionDenied(this, perms.first())) {
            SettingsDialog.Builder(requireContext()).build().show()
        } else {
            requestPermission()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        alertBox()
        //  findNavController().navigate(R.id.action_basicFragment_to_dialogBottomSheetFragment)
        //  dispatchTakePictureIntent()
        //  Toast.makeText(requireContext(),"permission granted",Toast.LENGTH_LONG).show()
    }

    fun alertBox() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Choose any one")
        builder.setMessage("Upload your photo")
//builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

        builder.setPositiveButton("Camera") { dialog, which ->
            dispatchTakePictureIntent()
        }

        builder.setNegativeButton("Gallery") { dialog, which ->
              openGallery()
        }

        builder.show()

    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, BasicFragment.CAMERA_REQUEST_CODE)
        } catch (e: Exception) {
            // display error state to the user
        }
    }


    private fun openGallery() {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery, GALLERY_REQUEST_CODE)
    }

/*
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            userViewModel.profile_photo.value = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(userViewModel.profile_photo.value)
        }
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
          //  imageView.setImageURI(imageUri)
//            val bitmap = MediaStore.Images.Media.getBitmap(ContentResolver(this), imageUri)
            userViewModel.profile_photo.value = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(userViewModel.profile_photo.value)

        }
    }
*/


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {

            when (requestCode) {

                CAMERA_REQUEST_CODE -> {
                try {
                     bitmap = data?.extras?.get("data") as Bitmap
                    userViewModel.profile_photo.value=bitmap
                    //we are using coroutine image loader (coil)
                    binding.imageView.load(bitmap) {
                        crossfade(true)
                        crossfade(1000)
                        transformations(CircleCropTransformation())
                    }
                }catch (ex:Exception){}

                }

                GALLERY_REQUEST_CODE -> {
                    imageUri = data?.data
            try {

                 bitmap= MediaStore.Images.Media.getBitmap(requireActivity().contentResolver,imageUri)
                userViewModel.profile_photo.value=bitmap
                binding.imageView.load(data?.data) {
                    crossfade(true)
                    crossfade(1000)
                    transformations(CircleCropTransformation())
                }
            }catch (ex:Exception){}


                }
            }

        }


    }



    fun check():Boolean{
        var flag:Boolean=true
        flag=Validation.isValidPassword(binding.pwdEditText)
        var    flag2=Validation.isValidPassword(binding.confPwdEditText)

     if (!Validation.isCharValid(binding.fNameEditText))
     {
         return Validation.errormsg(requireContext(),"Enter more than 3 characters first name")
     }
        else if (!Validation.isCharValid(binding.lNameEditText))
        {
            return Validation.errormsg(requireContext(),"Enter more than 3 characters  last name")
        }
       else if (!Validation.isEmailId(binding.emailEditText))
        {
            return Validation.errormsg(requireContext(),"Enter your proper email id")
        }
      else  if (!Validation.isValidPassword(binding.pwdEditText))
        {
            return Validation.errormsg(requireContext(),"Enter your password")
        }
     else  if (!Validation.isValidPassword(binding.confPwdEditText))
     {
         return Validation.errormsg(requireContext(),"Confirm your password")
     }

     else  if (flag!=flag2)
     {
         return Validation.errormsg(requireContext(),"Enter password does not match")
     }
     else  if (!Validation.isMobileValid(binding.mobEditText))
     {
         return Validation.errormsg(requireContext(),"Enter your correct mobile number")
     }
        else if (check_rb_id==null){
         return Validation.errormsg(requireContext(),"Select your gender")

     }
        else if (bitmap==null){
         return Validation.errormsg(requireContext(),"Choose your photo")

     }
        return true

    }


fun isValid():Boolean {

    val fName = binding.fNameEditText.text.toString().trim()
    val lName = binding.lNameEditText.text.toString().trim()
    val email = binding.emailEditText.text.toString().trim()
    val pwd = binding.pwdEditText.text.toString().trim()
    val conf_pwd = binding.confPwdEditText.text.toString().trim()
    val mob = binding.mobEditText.text.toString().trim()

    if (fName.isNullOrEmpty() || fName.length < 3) {
        return false
    }else if (lName.isNullOrEmpty()|| lName.length<3){
        return false
    }
    else if (!email.isNullOrEmpty()){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    else if (mob.isNullOrEmpty()){
        return Patterns.PHONE.matcher(mob).matches()

    }
    else if (pwd.isNullOrEmpty()){
        return isValidPassword(pwd)
    }

    return true

}

    private fun isValidPassword(password: String): Boolean {
        val pattern: Pattern
        val matcher: Matcher
        val specialCharacters = "-@%\\[\\}+'!/#$^?:;,\\(\"\\)~`.*=&\\{>\\]<_"
        val PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[$specialCharacters])(?=\\S+$).{8,20}$"
        pattern = Pattern.compile(PASSWORD_REGEX)
        matcher = pattern.matcher(password)
        return matcher.matches()
    }
}