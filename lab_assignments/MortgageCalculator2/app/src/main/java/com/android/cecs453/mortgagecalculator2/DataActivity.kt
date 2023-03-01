package com.android.cecs453.mortgagecalculator2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.android.cecs453.mortgagecalculator2.MainActivity.Companion.mortgage
import com.android.cecs453.mortgagecalculator2.databinding.ActivityDataBinding

class DataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDataBinding
    val mortgage = MainActivity.mortgage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonDone.setOnClickListener { view: View ->
            goBack(view)
        }
    }

    override fun onStart() {
        super.onStart()
        updateView()
    }

    fun updateView()
    {
        val rb10 = binding.ten
        val rb15 = binding.fifteen
        if( mortgage.getYears( ) == 10 ) {
            rb10.isChecked = true
        } else if( mortgage.getYears() == 15) {
            rb15.isChecked = true
        } // else do nothing (default is 30)
        val rateET = binding.dataRate
        rateET.setText(mortgage.getRate().toString())

        val amountET = binding.dataAmount
        amountET.setText(mortgage.getAmount().toString())
    }

    fun updateMortgageObject()
    {   val p = Prefs(this)

        val amountET = binding.dataAmount
        val rb10 = binding.ten
        val rb15 = binding.fifteen
        var years = 30
        if (rb10.isChecked)
            years = 10
        else if (rb15.isChecked)
            years = 15
        mortgage.setYears(years)
        val rateET = binding.dataRate
        val rateString:String = rateET.getText().toString()
        val amountString = amountET.text.toString()
        try {
            val amount = amountString.toFloat()
            mortgage.setAmount(amount)
            val rate: Float = rateString.toFloat()
            mortgage.setRate(rate)
            p.setPreferences(mortgage)

        } catch (nfe: NumberFormatException) {
            mortgage.setAmount(100000.0f)
            mortgage.setRate(.035f)
        }

    }
    fun goBack(v: View?) {
        updateMortgageObject()
        this.finish()
        overridePendingTransition(R.anim.fade_in_and_scale, 0)
    }

}