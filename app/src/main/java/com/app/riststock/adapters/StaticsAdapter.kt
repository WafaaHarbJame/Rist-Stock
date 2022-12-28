package com.app.riststock.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.riststock.apiHandler.DataCallBack
import com.app.riststock.databinding.RowStaticsItemBinding
import com.app.riststock.models.StaticsModel


class StaticsAdapter(
    private val activity: Activity,
    var list: MutableList<StaticsModel>?,
    var dataFetcherCallBack: DataCallBack
) :
    RecyclerView.Adapter<StaticsAdapter.MyHolder>() {

    var selectedOptionId = 0

    init {

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyHolder {
        val binding =
            RowStaticsItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return MyHolder(binding)

    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        if (list != null) {

            val staticsModel = list?.get(position)

            holder.binding.staticNameTv.text = staticsModel?.title
            holder.binding.staticCountTv.text = staticsModel?.count.toString()


        }
    }

    override fun getItemCount(): Int {
        return if (list != null) {
            list!!.size
        } else 0
    }

    inner class MyHolder(val binding: RowStaticsItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {



        }

    }
}