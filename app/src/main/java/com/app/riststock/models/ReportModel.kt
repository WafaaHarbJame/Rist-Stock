package com.app.riststock.models

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class ReportModel {

    @SerializedName("totalItemTake")
    @Expose
    var totalItemTake = 0

    @SerializedName("totalItemAudit")
    @Expose
    var totalItemAudit = 0

    @SerializedName("totpresct")
    @Expose
    var totpresct = 0

    @SerializedName("totalItemCorrect")
    @Expose
    var totalItemCorrect = 0

    @SerializedName("totalItemUnCorrect")
    @Expose
    var totalItemUnCorrect = 0

    @SerializedName("unCurrectPer")
    @Expose
    var unCurrectPer = 0
}