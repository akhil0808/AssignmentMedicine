package com.delight.assignment.database

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.room.Dao
import com.delight.assignment.database.AppDatabase
import com.delight.assignment.database.MedicineDao

class MedicineRepository(application: Application) {
    private var medicineDao: MedicineDao
    private var allCart: LiveData<List<Medicine>>

    init {
        val database: AppDatabase = AppDatabase.getAppDataBase(
            application.applicationContext
        )!!
        medicineDao = database.myDao()
        allCart = medicineDao.getAllMedicine()
    }

    fun getAllMedicine(): LiveData<List<Medicine>> {
        return allCart
    }

    fun getAllMedicineNotLive():List<Medicine>{
       return medicineDao.getAllMedicineNotLive()
    }

    fun getAllMedicinePagination(limit:Int,offset:Int):List<Medicine>{
       return medicineDao.getAllMedicinePagination(limit,offset)
    }

    fun getAllMedicineSearchPagination(search:String):List<Medicine>{
       return medicineDao.getAllMedicineSearchPagination("%$search%")
    }

    fun getAllMedicinePres():List<Medicine>{
       return medicineDao.getAllMedicinePres()
    }


    fun insert(cart: Medicine) {
       InsertMedicineAsyncTask(medicineDao).execute(cart)
    }

    fun update(quantity:Int, pid:Int) {
       UpdateMedicineItemAsyncTask(medicineDao).execute(quantity,pid)
    }


    fun delete(id: Int) {
        DeleteMedicineItemAsyncTask(medicineDao).execute(id)
    }

    fun deleteAll()
    {
        DeleteAllMedicineAsyncTask(medicineDao).execute()
    }

    fun getSingleMedicine(id:Int):Medicine
    {
        return medicineDao.getSingleMedicine(id)
    }

    private class InsertMedicineAsyncTask(val medicineDao: MedicineDao) : AsyncTask<Medicine, Unit, Unit>() {

        override fun doInBackground(vararg p0: Medicine?) {
            medicineDao.insertMedicine(p0[0]!!)
        }
    }

    private class DeleteMedicineItemAsyncTask(val medicineDao: MedicineDao) : AsyncTask<Int, Unit, Unit>() {

        override fun doInBackground(vararg p0: Int?) {
            medicineDao.deleteMedicineItem(p0[0]!!)
        }
    }

    private class UpdateMedicineItemAsyncTask(val medicineDao: MedicineDao) : AsyncTask<Int, Int, Unit>() {

        override fun doInBackground(vararg p0: Int?) {
            medicineDao.updateMedicineQuantity(p0[0]!!,p0[1]!!)
        }

    }

    private class DeleteAllMedicineAsyncTask(val medicineDao: MedicineDao) : AsyncTask<Unit, Unit, Unit>() {

        override fun doInBackground(vararg p0: Unit) {
            medicineDao.deleteAll()
        }

    }

}