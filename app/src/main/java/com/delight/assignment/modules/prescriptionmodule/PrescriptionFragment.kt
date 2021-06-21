package com.delight.assignment.modules.prescriptionmodule

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.delight.assignment.BR
import com.delight.assignment.R
import com.delight.assignment.base.BaseFragment
import com.delight.assignment.databinding.FragmentPrescriptionBinding
import com.delight.assignment.modules.homemodule.HomeAdapter
import com.delight.assignment.modules.homemodule.HomeViewModel
import com.delight.assignment.utils.EndlessRecyclerViewScrollListener
import com.rambaanaushadi.rambaan.cartwork.database.MedicineViewModel


class PrescriptionFragment : BaseFragment<FragmentPrescriptionBinding,PrescriptionViewModel>() {

    val prescriptionViewModel: PrescriptionViewModel by lazy {
        ViewModelProvider(this).get(PrescriptionViewModel::class.java)
    }

    private val medicineViewModel: MedicineViewModel by lazy {
        ViewModelProvider(requireActivity()).get(MedicineViewModel::class.java)
    }

    lateinit var mBinding: FragmentPrescriptionBinding
    lateinit var adapter:HomeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding=getViewDataBinding()
        prescriptionViewModel.totalCount=medicineViewModel.getAllMedicineNotLive().size
        prescriptionViewModel.medicineList=medicineViewModel.getAllMedicinePres().toMutableList()
        setAdapter()

        mBinding.presSaveBtn.setOnClickListener {
            if(prescriptionViewModel.medicineList.size>0)
            showToast("Save Successfully")
            else
                showToast("Please Select Medicines First")
        }

    }

    fun setAdapter(){
        adapter= HomeAdapter(prescriptionViewModel.medicineList,{
                quantity, position ->
            medicineViewModel.update(quantity,prescriptionViewModel.medicineList[position].pid)
            if(quantity==0)
                prescriptionViewModel.medicineList.removeAt(position)
            else
            prescriptionViewModel.medicineList[position].quantity=quantity
            adapter.notifyDataSetChanged()
        },{
                position ->
//            Navigation.findNavController(requireView()).navigate(R.id.prescriptionFragment)

        })
        val layoutManager= LinearLayoutManager(requireContext())
        mBinding.presRecyclerview.layoutManager =layoutManager
        mBinding.presRecyclerview.adapter = adapter
    }

    override fun getLayoutId(): Int =R.layout.fragment_prescription

    override fun getBindingVariable(): Int= BR.prescriptionviewmodel

    override fun getViewModel(): PrescriptionViewModel = prescriptionViewModel


}