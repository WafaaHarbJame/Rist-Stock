package com.app.riststock.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class ProductsModel {

    @SerializedName("item_ref")
    @Expose
   var itemRef: String? = null

    @SerializedName("item_code")
    @Expose
   var itemCode: String? = null

    @SerializedName("description")
    @Expose
   var description: String? = null

    @SerializedName("packId")
    @Expose
   var packId: String? = null

    @SerializedName("packing")
    @Expose
   var packing: String? = null

    @SerializedName("price")
    @Expose
   var price = 0.0

    @SerializedName("cost")
    @Expose
   var cost = 0.0

    @SerializedName("image")
    @Expose
   var image: String? = null

    @SerializedName("quantity")
    @Expose
   var quantity = 0
}