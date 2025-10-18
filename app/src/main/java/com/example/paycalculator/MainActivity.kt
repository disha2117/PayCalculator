package com.example.paycalculator

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.NumberFormat
import kotlin.math.max
import kotlin.math.min

class MainActivity : AppCompatActivity() {

    private lateinit var hoursEt: EditText
    private lateinit var rateEt: EditText
    private lateinit var taxEt: EditText
    private lateinit var outPay: TextView
    private lateinit var outOvertime: TextView
    private lateinit var outTotal: TextView
    private lateinit var outTax: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        hoursEt = findViewById(R.id.inputHours)
        rateEt = findViewById(R.id.inputRate)
        taxEt = findViewById(R.id.inputTax)
        outPay = findViewById(R.id.outPay)
        outOvertime = findViewById(R.id.outOvertime)
        outTotal = findViewById(R.id.outTotal)
        outTax = findViewById(R.id.outTax)

        findViewById<Button>(R.id.btnCalc).setOnClickListener { calculateAndShow() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_about -> {
                startActivity(android.content.Intent(this, AboutActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun calculateAndShow() {
        val nf = NumberFormat.getCurrencyInstance()

        val hours = hoursEt.text.toString().toDoubleOrNull()
        val rate = rateEt.text.toString().toDoubleOrNull()
        val taxRate = taxEt.text.toString().toDoubleOrNull()

        if (hours == null || rate == null || taxRate == null) {
            Toast.makeText(this, getString(R.string.err_enter_all_fields), Toast.LENGTH_SHORT).show()
            return
        }

        val baseHours = min(hours, 40.0)
        val overtimeHours = max(0.0, hours - 40.0)

        val pay = baseHours * rate
        val overtimePay = overtimeHours * rate * 1.5
        val totalPay = pay + overtimePay
        val tax = pay * taxRate // per assignment: tax based on base pay

        outPay.text = getString(R.string.pay_format, nf.format(pay))
        outOvertime.text = getString(R.string.overtime_pay_format, nf.format(overtimePay))
        outTotal.text = getString(R.string.total_pay_format, nf.format(totalPay))
        outTax.text = getString(R.string.tax_format, nf.format(tax))
    }
}
