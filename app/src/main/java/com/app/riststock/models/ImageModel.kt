package com.app.riststock.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class ImageModel {
    @SerializedName("item_code")
    @Expose
   var itemCode: String? = null

    @SerializedName("imageData")
    @Expose
    var imageData: String? = null
}