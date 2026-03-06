package com.example.thrift

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SelectSwapItemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_swap_item)

        val rbSelectItemOne = findViewById<RadioButton>(R.id.rbSelectItemOne)
        val rbSelectItemTwo = findViewById<RadioButton>(R.id.rbSelectItemTwo)
        val rbSelectItemThree = findViewById<RadioButton>(R.id.rbSelectItemThree)
        val btnSendSwapRequest = findViewById<Button>(R.id.btnSendSwapRequest)

        val navHomeSelectSwap = findViewById<ImageButton>(R.id.navHomeSelectSwap)
        val navSwapSelectSwap = findViewById<ImageButton>(R.id.navSwapSelectSwap)
        val navCartSelectSwap = findViewById<ImageButton>(R.id.navCartSelectSwap)
        val navProfileSelectSwap = findViewById<ImageButton>(R.id.navProfileSelectSwap)

        rbSelectItemOne.setOnClickListener {
            rbSelectItemTwo.isChecked = false
            rbSelectItemThree.isChecked = false
        }

        rbSelectItemTwo.setOnClickListener {
            rbSelectItemOne.isChecked = false
            rbSelectItemThree.isChecked = false
        }

        rbSelectItemThree.setOnClickListener {
            rbSelectItemOne.isChecked = false
            rbSelectItemTwo.isChecked = false
        }

        btnSendSwapRequest.setOnClickListener {
            if (rbSelectItemOne.isChecked || rbSelectItemTwo.isChecked || rbSelectItemThree.isChecked) {
                Toast.makeText(this, "Swap request sent", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please select one of your items first", Toast.LENGTH_SHORT).show()
            }
        }

        navHomeSelectSwap.setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }

        navSwapSelectSwap.setOnClickListener {
            val intent = Intent(this, SwapActivity::class.java)
            startActivity(intent)
        }

        navCartSelectSwap.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }

        navProfileSelectSwap.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }
}