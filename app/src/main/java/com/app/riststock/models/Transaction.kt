package com.app.riststock.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class Transaction {
    @SerializedName("barcode")
    @Expose
   var barcode: String? = null

    @SerializedName("created_at")
    @Expose
   var createdAt: String? = null

    @SerializedName("trans_id")
    @Expose
   var transId = 0

    @SerializedName("area_id")
    @Expose
   var areaId = 0

    @SerializedName("user_qty")
    @Expose
   var userQty = 0

    @SerializedName("admin_qty2")
    @Expose
   var adminQty2: Any? = null

    @SerializedName("user_name")
    @Expose
   var userName: String? = null
}