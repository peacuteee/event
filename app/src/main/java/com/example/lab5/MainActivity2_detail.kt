package com.example.lab5

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity2_detail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_activity2_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val tvChiTiet = findViewById<TextView>(R.id.tvChiTiet)

        tvChiTiet.text = """
            Họ tên: ${intent.getStringExtra("HOTEN")}
            Email: ${intent.getStringExtra("EMAIL")}
            Số điện thoại: ${intent.getStringExtra("SDT")}
            Giới tính: ${intent.getStringExtra("GIOITINH")}
            Mạng xã hội: ${intent.getStringExtra("MXH")}
        """.trimIndent()

        findViewById<Button>(R.id.btnQuayLai).setOnClickListener {
            finish()
        }
    }
}