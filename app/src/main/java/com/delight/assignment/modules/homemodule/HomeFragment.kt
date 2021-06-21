package com.delight.assignment.modules.homemodule

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.delight.assignment.BR
import com.delight.assignment.R
import com.delight.assignment.ResponseErrorModel
import com.delight.assignment.base.BaseFragment
import com.delight.assignment.database.Medicine
import com.delight.assignment.databinding.FragmentHomeBinding
import com.delight.assignment.databinding.FragmentPrescriptionBinding
import com.delight.assignment.utils.EndlessRecyclerViewScrollListener
import com.rambaanaushadi.rambaan.cartwork.database.MedicineViewModel


class HomeFragment : BaseFragment<FragmentHomeBinding,HomeViewModel>() {
    lateinit var adapter:HomeAdapter
    val homeViewModel: HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    private val medicineViewModel: MedicineViewModel by lazy {
        ViewModelProvider(requireActivity()).get(MedicineViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    lateinit var mBinding: FragmentHomeBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding=getViewDataBinding()
        observeResponse()
        observePrescriptionItems()
        setAdapter()
        var tempList=medicineViewModel.getAllMedicineNotLive()
        if(tempList.isEmpty()){
            showLoading()
            homeViewModel.fetchMedicines()
        }
        else{
            homeViewModel.page=0
            homeViewModel.totalCount=tempList.size
            homeViewModel.medicineList.addAll(medicineViewModel.getAllMedicinePagination(homeViewModel.limit,homeViewModel.page*homeViewModel.limit).toMutableList())
            adapter.notifyDataSetChanged()
        }
        mBinding.homePrescriptionLayout.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.prescriptionFragment)
        }
        mBinding.homeSearchEdit.addTextChangedListener {
            char->
            if(char.toString().isBlank()){
                homeViewModel.medicineList.clear()
                homeViewModel.page=0;
                homeViewModel.totalCount=medicineViewModel.getAllMedicineNotLive().size
                homeViewModel.medicineList.addAll(medicineViewModel.getAllMedicinePagination(homeViewModel.limit,homeViewModel.page*homeViewModel.limit).toMutableList())
                adapter.notifyDataSetChanged()
            }
            else{
                Log.d("Search", char.toString())
                homeViewModel.page=0
                homeViewModel.medicineList.clear()
                homeViewModel.medicineList.addAll(medicineViewModel.getAllMedicineSearchPagination(char.toString()).toMutableList())
               adapter.notifyDataSetChanged()
            }
        }

    }
    fun setAdapter(){
         adapter=HomeAdapter(homeViewModel.medicineList,{
             quantity, position ->
             homeViewModel.medicineList[position].quantity=quantity
             adapter.notifyDataSetChanged()
             medicineViewModel.update(quantity,homeViewModel.medicineList[position].pid)
         },{
             position ->
//             Navigation.findNavController(requireView()).navigate(R.id.prescriptionFragment)

         })
        val layoutManager=LinearLayoutManager(requireContext())
        mBinding.homeRecyclerview.layoutManager =layoutManager
        val endlessRecyclerViewScrollListener: EndlessRecyclerViewScrollListener =
            object : EndlessRecyclerViewScrollListener(layoutManager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int) {
                    if(homeViewModel.medicineList.size<homeViewModel.totalCount){
                        homeViewModel.page=homeViewModel.page+1
                        homeViewModel.medicineList.addAll(medicineViewModel.getAllMedicinePagination(homeViewModel.limit,homeViewModel.page*homeViewModel.limit).toMutableList())
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        mBinding.homeRecyclerview.addOnScrollListener(endlessRecyclerViewScrollListener)
        mBinding.homeRecyclerview.adapter = adapter
    }
    fun observeResponse() {
        homeViewModel.observeResponse.observe(viewLifecycleOwner, Observer {
            when(it){
                is ResponseErrorModel->{
                    hideLoading()
                    homeViewModel.observeResponse.value=null
                    showToast(it.error)
                }
                is List<*>->{
                    hideLoading()
                    homeViewModel.observeResponse.value=null
                    addAllData()
                }
            }
        })
    }

    fun addAllData(){
        for(medicine in homeViewModel.medicineTotalList){
            medicineViewModel.insert(medicine)
        }
        Handler().postDelayed(Runnable {
            homeViewModel.totalCount=homeViewModel.medicineTotalList.size
            homeViewModel.medicineList.addAll(medicineViewModel.getAllMedicinePagination(homeViewModel.limit,homeViewModel.page*homeViewModel.limit).toMutableList())
            adapter.notifyDataSetChanged()
        },200)

    }


    private fun observePrescriptionItems()
    {
        medicineViewModel.getAllMedicine().observe(viewLifecycleOwner, Observer {
                medicineList->
            var num=0
            for (medicine in medicineList)
            {
                if(medicine.quantity>0)
                num+=1
            }
            if(num>0) {
                mBinding.homePrescriptionLayout.visibility = View.VISIBLE
                mBinding.homePrescriptionValue.text="Prescriptopn Items : $num"
            }
            else
                mBinding.homePrescriptionLayout.visibility=View.GONE
        })
    }

    override fun getLayoutId(): Int =R.layout.fragment_home

    override fun getBindingVariable(): Int= BR.homeviewmodel

    override fun getViewModel(): HomeViewModel = homeViewModel


}