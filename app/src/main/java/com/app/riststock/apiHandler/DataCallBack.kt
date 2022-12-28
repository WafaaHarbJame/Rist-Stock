package com.app.riststock.apiHandler

interface DataCallBack {
    fun Result(
        obj: Any?,
        func: String?,
        otherData: Any?
    ) //    public Object ReturnResult(Object obj, String func, boolean IsSuccess);


}