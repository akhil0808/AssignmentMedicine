package com.delight.assignment.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MedicineDao {

    @Insert()
    fun insertMedicine(cart: Medicine)

    @Update
    fun updateMedicine(cart: Medicine)


    @Query("SELECT * FROM Medicines WHERE pid==:id")
    fun getSingleMedicine(id: Int): Medicine

    @Query("SELECT * FROM Medicines")
    fun getAllMedicine(): LiveData<List<Medicine>>

    @Query("SELECT * FROM Medicines")
    fun getAllMedicineNotLive(): List<Medicine>


    @Query("SELECT * FROM Medicines LIMIT :limit OFFSET :offset")
    fun getAllMedicinePagination(limit:Int,offset:Int): List<Medicine>

    @Query("SELECT * FROM Medicines WHERE name LIKE :search")
    fun getAllMedicineSearchPagination(search:String): List<Medicine>

    @Query("SELECT * FROM Medicines WHERE quantity>0")
    fun getAllMedicinePres(): List<Medicine>

    @Query("DELETE FROM Medicines")
    fun deleteAll()

    @Query("UPDATE Medicines SET quantity=:qun WHERE pid==:pid")
    fun updateMedicineQuantity(qun: Int, pid: Int)


    @Query("DELETE FROM Medicines WHERE pid==:pid")
    fun deleteMedicineItem(pid: Int)


    @Query("SELECT quantity FROM Medicines WHERE pid==:pid")
    fun getSingleMedicineQuantity(pid: Int): Int


    @Query("SELECT pid FROM Medicines WHERE pid==:pid")
    fun getSingleMedicineId(pid: Int): Int

}