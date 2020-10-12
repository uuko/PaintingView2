package com.example.paintingview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.paintingview2.PPView
import com.example.paintingview2.PaintItActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var it=Intent(this,PaintItActivity::class.java)
        startActivity(it);
    }
}
