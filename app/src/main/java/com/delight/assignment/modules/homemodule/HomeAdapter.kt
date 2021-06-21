package com.delight.assignment.modules.homemodule

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.delight.assignment.R
import com.delight.assignment.database.Medicine
import com.delight.assignment.databinding.RowSingleMedicineBinding

class HomeAdapter(val medicineList:MutableList<Medicine>,val onClick:(quantity:Int,position:Int)->Unit,val onNavigate:(position:Int)->Unit) : RecyclerView.Adapter<HomeAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<RowSingleMedicineBinding>(
                layoutInflater,
                R.layout.row_single_medicine,
                parent,
                false
            )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindView(medicineList[position])
    }

    override fun getItemCount(): Int {
        return  medicineList.size
    }



    inner class MyViewHolder(private val viewBinding: RowSingleMedicineBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bindView(item: Medicine) {
            viewBinding.medicinemodel = item
            val text1=item.type.subSequence(0,1)
            viewBinding.rowSingleType1.text=text1
            if(item.quantity==0){
                viewBinding.rowSingleCartLayout.visibility= View.GONE
                viewBinding.rowSingleCartAdd.visibility= View.VISIBLE
            }
            else{
                viewBinding.rowSingleCartLayout.visibility= View.VISIBLE
                viewBinding.rowSingleCartAdd.visibility= View.GONE
            }
            viewBinding.rowSingleCartDecrease.setOnClickListener {
                val newQuantity=item.quantity-1
                onClick(newQuantity,adapterPosition)
            }
            viewBinding.rowSingleCartIncrease.setOnClickListener {
                val newQuantity=item.quantity+1
                onClick(newQuantity,adapterPosition)
            }
            viewBinding.rowSingleCartAdd.setOnClickListener {
                val newQuantity=item.quantity+1
                onClick(newQuantity,adapterPosition)
            }
            viewBinding.root.setOnClickListener {
                onNavigate(adapterPosition)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return  position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}