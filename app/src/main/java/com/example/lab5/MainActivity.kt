package com.example.lab5

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.jvm.java

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val edtHoTen = findViewById<EditText>(R.id.edtHoTen)
        val edtEmail = findViewById<EditText>(R.id.edtEmail)
        val edtSdt = findViewById<EditText>(R.id.edtSdt)
        val rgGioiTinh = findViewById<RadioGroup>(R.id.rgGioiTinh)
        val cbFacebook = findViewById<CheckBox>(R.id.cbFacebook)
        val cbZalo = findViewById<CheckBox>(R.id.cbZalo)
        val cbTiktok = findViewById<CheckBox>(R.id.cbTiktok)
        val cbInstagram = findViewById<CheckBox>(R.id.cbInstagram)
        val btnDangKy = findViewById<Button>(R.id.btnDangKy)

        val danhSachMxh = listOf(cbFacebook, cbZalo, cbTiktok, cbInstagram)

        // 1. Event EditText: nhập xong, rời khỏi ô thì hiện Toast
        val onFocus = View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                val edt = v as EditText
                if (edt.text.isNotBlank()) {
                    Toast.makeText(this, "Bạn đã nhập: ${edt.text}", Toast.LENGTH_SHORT).show()
                }
            }
        }
        edtHoTen.onFocusChangeListener = onFocus
        edtEmail.onFocusChangeListener = onFocus
        edtSdt.onFocusChangeListener = onFocus

        // 2. Event RadioButton: chọn giới tính thì hiện Toast
        rgGioiTinh.setOnCheckedChangeListener { group, checkedId ->
            val rb = group.findViewById<RadioButton>(checkedId)
            Toast.makeText(this, "Giới tính: ${rb.text}", Toast.LENGTH_SHORT).show()
        }

        // 3. Event CheckBox: tích hoặc bỏ tích thì hiện Toast
        danhSachMxh.forEach { cb ->
            cb.setOnCheckedChangeListener { _, isChecked ->
                val msg = if (isChecked) "Đã chọn ${cb.text}" else "Bỏ chọn ${cb.text}"
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
            }
        }

        // 4. Nút Đăng ký: kiểm tra rồi chuyển màn hình chi tiết
        btnDangKy.setOnClickListener {
            val hoTen = edtHoTen.text.toString().trim()
            val email = edtEmail.text.toString().trim()
            val sdt = edtSdt.text.toString().trim()

            if (hoTen.isEmpty()) {
                edtHoTen.error = "Vui lòng nhập họ tên"
                edtHoTen.requestFocus()
                return@setOnClickListener
            }
            if (email.isEmpty()) {
                edtEmail.error = "Vui lòng nhập email"
                edtEmail.requestFocus()
                return@setOnClickListener
            }
            if (sdt.isEmpty()) {
                edtSdt.error = "Vui lòng nhập số điện thoại"
                edtSdt.requestFocus()
                return@setOnClickListener
            }

            val checkedId = rgGioiTinh.checkedRadioButtonId
            if (checkedId == -1) {
                Toast.makeText(this, "Vui lòng chọn giới tính", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val gioiTinh = findViewById<RadioButton>(checkedId).text.toString()

            val mxh = danhSachMxh.filter { it.isChecked }
                .joinToString(", ") { it.text.toString() }
                .ifEmpty { "Không chọn" }

            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("HOTEN", hoTen)
            intent.putExtra("EMAIL", email)
            intent.putExtra("SDT", sdt)
            intent.putExtra("GIOITINH", gioiTinh)
            intent.putExtra("MXH", mxh)
            startActivity(intent)
        }
    }
}