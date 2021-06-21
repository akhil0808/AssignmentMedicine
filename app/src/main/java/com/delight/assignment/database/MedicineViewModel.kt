package com.rambaanaushadi.rambaan.cartwork.database

import android.app.Application
import androidx.lifecycle.LiveData
import com.delight.assignment.base.BaseViewModel
import com.delight.assignment.database.Medicine
import com.delight.assignment.database.MedicineRepository

class MedicineViewModel(application: Application): BaseViewModel(application)
{
    private var repository: MedicineRepository = MedicineRepository(application)

    private var allMedicine: LiveData<List<Medicine>> = repository.getAllMedicine()

    fun insert(medicine: Medicine) {
        repository.insert(medicine)
    }

    fun update(quantity:Int, pid:Int)
    {
        repository.update(quantity, pid)
    }

    fun deleteMedicine(id:Int) {
        repository.delete(id)
    }

    fun deleteAll(){
        repository.deleteAll()
    }

    fun getAllMedicine(): LiveData<List<Medicine>> {
        return allMedicine
    }

    fun getAllMedicineNotLive():List<Medicine>{
        return repository.getAllMedicineNotLive()
    }

    fun getAllMedicinePagination(limit:Int,offset:Int):List<Medicine>{
        return repository.getAllMedicinePagination(limit,offset)
    }
    fun getAllMedicineSearchPagination(search:String):List<Medicine>{
        return repository.getAllMedicineSearchPagination(search.trim())
    }

    fun getAllMedicinePres():List<Medicine>{
        return repository.getAllMedicinePres()
    }

    fun getSingleMedicine(id:Int):Medicine{
        return repository.getSingleMedicine(id)
    }

}