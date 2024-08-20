package com.example.bitcoinapptesting

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView: TextView = findViewById(R.id.textView)
        val button: Button = findViewById(R.id.button)

        val viewModel = ViewModelProvider(this).get(MyViewModel::class.java)

        button.setOnClickListener {
            viewModel.getData()
        }

        viewModel.uiState.observe(this) { uiState ->
            when(uiState) {
                is MyViewModel.UIState.NoData -> Unit
                is MyViewModel.UIState.Processing -> textView.text = "Proccessing..."
                is MyViewModel.UIState.Result -> {
                    textView.text = uiState.result
                }
                is MyViewModel.UIState.Error -> {
                    textView.text = ""
                    Toast.makeText(this, uiState.description, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}