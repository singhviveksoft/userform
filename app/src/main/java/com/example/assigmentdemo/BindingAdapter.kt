package com.example.assigmentdemo

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.CircleCropTransformation

@BindingAdapter("app:loadImage")
fun ImageView.loadimage(bitmap: Bitmap){
this.load(bitmap){
    crossfade(true)
    crossfade(1500)
    transformations(CircleCropTransformation())
}

}