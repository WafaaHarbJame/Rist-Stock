package com.app.riststock.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ameer on 6/22/2017.
 */

public class ErrorModel {

    final public static String CHECK_VALID_CODE = "check_valid_code";
    @Expose
    public int status;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("error")
    @Expose
    public String error;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("details")
    @Expose
    public String[] details = null;

}
