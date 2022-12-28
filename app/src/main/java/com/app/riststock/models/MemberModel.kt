package com.app.riststock.models

import com.app.riststock.utils.UtilityApp
import com.app.riststock.classes.Constants
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class MemberModel : Serializable {

    @SerializedName("user_id")
    @Expose
     var userId = 0

    @SerializedName("user_name")
    @Expose
     var userName: String? = null

    @SerializedName("branch_id")
    @Expose
     var branchId: String? = null

    @SerializedName("branch_name")
    @Expose
     var branchName: String? = null

    @SerializedName("group_id")
    @Expose
     var groupId: String? = null

    @SerializedName("group_name")
    @Expose
     var groupName: String? = null

    @SerializedName("area_id")
    @Expose
     var areaId = 0

    @SerializedName("lang_id")
    @Expose
     var langId: String? = null

    @SerializedName("user_group_id")
    @Expose
     var userGroupId = 0
    var password: String? = null


}