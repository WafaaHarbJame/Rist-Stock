package com.app.riststock.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class ImageModel {
    @SerializedName("image_type")
    @Expose
     val imageType: String? = null

    @SerializedName("image")
    @Expose
     val image: String? = null
}