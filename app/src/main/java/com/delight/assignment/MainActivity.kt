package com.delight.assignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.delight.assignment.databinding.ActivityMainBinding
import com.delight.assignment.utils.UtilityHelper.isConnectionAvailable

class MainActivity : BaseActivity() {

    lateinit var navController: NavController
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        navController = findNavController(R.id.main_fragments_container)

        binding.mainToolbarBack.setOnClickListener {
            onBackPressed()
        }


        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.homeFragment -> {

                    binding.mainToolbarBack.visibility= View.GONE
                    binding.mainToolbarText.text= resources.getString(R.string.home_list)

                }
                R.id.prescriptionFragment->{
                    binding.mainToolbarBack.visibility= View.VISIBLE
                    binding.mainToolbarText.text=  resources.getString(R.string.home_prescription_pad)


                }
            }
        }


    }
}