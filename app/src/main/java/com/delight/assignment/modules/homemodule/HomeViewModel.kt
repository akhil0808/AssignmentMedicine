package com.delight.assignment.modules.homemodule

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.delight.assignment.ResponseErrorModel
import com.delight.assignment.api.RetrofitFactory
import com.delight.assignment.base.BaseViewModel
import com.delight.assignment.database.Medicine
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(application: Application): BaseViewModel(application) {
    var medicineList= mutableListOf<Medicine>()
    var medicineTotalList= mutableListOf<Medicine>()
    val observeResponse= MutableLiveData<Any>()
    val service = RetrofitFactory.getApiService()
    var page=0
    var limit=10
    var totalCount=0

    public fun fetchMedicines(){
       var request= service.getAllMedicines()
        request.enqueue(object :Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.body()!=null){
                    parseResponse(response.body()!!.string())
                }
                else{
                    observeResponse.value=ResponseErrorModel("Something went wrong")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                observeResponse.value=ResponseErrorModel(t.message!!)
            }
        })
    }

    fun parseResponse(response: String){
        try {
            medicineTotalList.clear()
            val jsonArray=JSONArray(response)
            for (i in 0 until jsonArray.length()) {
                val jsonObject=jsonArray.getJSONObject(i)
                medicineTotalList.add(
                    Medicine(
                    jsonObject.getInt("id"),
                    jsonObject.getString("name"),
                    jsonObject.getString("type"),
                    jsonObject.getString("company"),
                    jsonObject.getString("strength"),
                    jsonObject.getString("strengthtype"),
                        0)
                )
            }
            observeResponse.value=medicineTotalList
        }
        catch (e: JSONException){
            observeResponse.value=e.message
        }
    }

}