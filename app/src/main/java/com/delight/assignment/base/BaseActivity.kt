package com.delight.assignment

import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.delight.assignment.base.BaseViewModel
import com.delight.assignment.utils.UtilityHelper


abstract class BaseActivity : AppCompatActivity() {

    private lateinit var mProgressDialog: Dialog
   // private lateinit var toolbarViewModel: ToolbarViewModel
    private lateinit var baseViewModel: BaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseViewModel = ViewModelProvider(this).get(BaseViewModel::class.java)
     //   toolbarViewModel = ViewModelProviders.of(this).get(ToolbarViewModel::class.java)
    }

    /**
     * For show loader
     */
    fun showLoading() {
        if (::mProgressDialog.isInitialized) {
            if (!mProgressDialog.isShowing) {
                mProgressDialog.show()
            }
        } else {
            mProgressDialog = UtilityHelper.showDialog(this)
        }
    }

    /**
     * For hide loader
     */
    fun hideLoading() {
        if (::mProgressDialog.isInitialized && mProgressDialog.isShowing) {
            mProgressDialog.dismiss()
        }
    }

    fun showToast(message:String,lenght:Int=Toast.LENGTH_SHORT){
        Toast.makeText(this,message,lenght).show()
    }
}