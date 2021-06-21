package com.delight.assignment.modules.prescriptionmodule

import android.app.Application
import com.delight.assignment.base.BaseViewModel
import com.delight.assignment.database.Medicine

class PrescriptionViewModel(application: Application): BaseViewModel(application) {

    var totalCount=0
    var medicineList= mutableListOf<Medicine>()
    var page=0
    var limit=10
}