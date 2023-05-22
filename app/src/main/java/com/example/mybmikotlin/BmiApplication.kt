package com.example.mybmikotlin

import android.app.Application
import com.example.mybmikotlin.data.InfoDatabase

class BmiApplication: Application() {
    val database: InfoDatabase by lazy { InfoDatabase.getDatabase(this) }
}