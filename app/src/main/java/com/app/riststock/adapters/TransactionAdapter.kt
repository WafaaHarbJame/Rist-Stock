package com.app.riststock.adapters

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.app.riststock.MainActivity
import com.app.riststock.R
import com.app.riststock.apiHandler.DataCallBack
import com.app.riststock.classes.GlobalData
import com.app.riststock.databinding.RowTransactionItemBinding
import com.app.riststock.models.LoginResultModel
import com.app.riststock.models.PublicModel
import com.app.riststock.models.Transaction
import com.app.riststock.utils.DateHandler
import com.app.riststock.utils.UtilityApp


class TransactionAdapter(

    private val activity: Activity,
    var list: MutableList<Transaction>?,
    var dataFetcherCallBack: DataCallBack
) :
    RecyclerView.Adapter<TransactionAdapter.MyHolder>() {

    var selectedOptionId = 0
    var userId: Int = 0
    var areaId: Int = 0
    var langID: String = ""
    var url: String = ""

    init {
        val user = UtilityApp.userData
        userId = user?.userId ?: 0
        areaId = user?.areaId ?: 0
        langID = user?.langId ?: "ar"
        getUrl()

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyHolder {
        val binding =
            RowTransactionItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return MyHolder(binding)

    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        getUrl()
        if (list != null) {

            val transaction = list?.get(position)
            val user = UtilityApp.userData
            userId = user?.userId ?: 0
            areaId = user?.areaId ?: 0
            langID = user?.langId ?: "ar"
            holder.binding.usernameTv.text = transaction?.userName
            holder.binding.areaTv.text = transaction?.areaId.toString()
            val dateStr= DateHandler.GetTimeFromDateString(DateHandler.convertToLong(transaction?.createdAt, "yyyy-MM-dd HH:mm"))
            holder.binding.timeTv.text = dateStr
            holder.binding.oldQuantityTv.text = transaction?.userQty.toString()
            val adminQau=transaction?.adminQty2?:0
            holder.binding.newQuantityTv.setText(adminQau.toString())


        }
    }

    override fun getItemCount(): Int {
        return if (list != null) {
            list!!.size
        } else 0
    }

    inner class MyHolder(val binding: RowTransactionItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {

            itemView.setOnClickListener {

                val transaction = list?.get(adapterPosition)

                dataFetcherCallBack.Result(transaction, "", true)
            }

            binding.addQuantityBut.setOnClickListener {
                var quantity=0
                var position=adapterPosition
                val transaction = list?.get(adapterPosition)
                if(binding.newQuantityTv.text.toString().isEmpty()){
                    Toast.makeText(activity, activity.getString(R.string.invalid_input), Toast.LENGTH_SHORT).show()
                }
                else{
                    quantity= binding.newQuantityTv.text.toString().toInt()

                    if(quantity>0){
                        addProduct(transaction,quantity,position)
                    }
                    else{
                        binding.newQuantityTv.requestFocus()
                        binding.newQuantityTv.error = activity.getString(R.string.invalid_input)
                        Toast.makeText(activity, activity.getString(R.string.invalid_input), Toast.LENGTH_SHORT).show()

                    }


                }


            }

        }

    }
    fun addProduct(transaction: Transaction?, quantity:Int?,position:Int?) {
        GlobalData.progressDialog(activity, R.string.addQuantity, R.string.please_wait_to_set_quanity)
        AndroidNetworking.get(url.plus("Products/SetAdminQty"))
            .addQueryParameter("admin_id", userId.toString())
            .addQueryParameter("area_id", transaction?.areaId.toString())
            .addQueryParameter("tran_id",transaction?.transId.toString())
            .addQueryParameter("qty", quantity.toString())
            .addQueryParameter("lang_id",langID)
            .setPriority(Priority.LOW)
            .build()
            .getAsObject(PublicModel::class.java, object :
                ParsedRequestListener<PublicModel> {
                override fun onResponse(result: PublicModel) {
                    GlobalData.hideProgressDialog()

                    if(result.status==200){
                        Toast.makeText(activity, activity.getString(R.string.added_sucess), Toast.LENGTH_SHORT).show()
                        list?.get(position!!)?.adminQty2 =quantity
                          notifyItemChanged(position!!)
                        notifyDataSetChanged()

                    }
                    else{
                        var message = activity.getString(R.string.fail_to_add_quanity)
                        message= result.message ?:activity.getString(R.string.fail_to_add_quanity)
                        GlobalData.errorDialog(activity, R.string.addQuantity, message)

                    }

                }

                override fun onError(anError: ANError) {
                    val message = activity.getString(R.string.fail_to_add_quanity)
                    GlobalData.hideProgressDialog()
                    GlobalData.errorDialog(activity, R.string.addQuantity, message)
                    Log.d(javaClass.simpleName, "Log Error Login ${anError.errorCode}")
                    Log.d(javaClass.simpleName, "Log Error Login ${anError.errorDetail}")
                }
            })

    }
    fun getUrl(): Unit {
        url = if (!UtilityApp.appUrl.isNullOrEmpty()) {
            UtilityApp.appUrl!!
        } else {
            GlobalData.ReleaseBaseURL
        }

    }
}