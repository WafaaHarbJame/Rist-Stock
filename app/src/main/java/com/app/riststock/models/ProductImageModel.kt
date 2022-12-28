package com.app.riststock.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class ProductImageModel {
    @SerializedName("status")
    @Expose
   var status = 0

    @SerializedName("message")
    @Expose
   var message: String? = null

    @SerializedName("data")
    @Expose
   var imageModel: ImageModel? = null
}