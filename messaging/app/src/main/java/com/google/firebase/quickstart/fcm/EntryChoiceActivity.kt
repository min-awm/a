package com.google.firebase.quickstart.fcm

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.firebase.example.internal.BaseEntryChoiceActivity
import com.firebase.example.internal.Choice

class EntryChoiceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Mở luôn Java MainActivity
        startActivity(Intent(this, com.google.firebase.quickstart.fcm.java.MainActivity::class.java))

        // Kết thúc activity hiện tại để không quay lại
        finish()
    }

//    override fun getChoices(): List<Choice> {
//        return listOf(
//            Choice(
//                "Java",
//                "Run the Firebase Cloud Messaging quickstart written in Java.",
//                Intent(this, com.google.firebase.quickstart.fcm.java.MainActivity::class.java),
//            ),
//            Choice(
//                "Kotlin",
//                "Run the Firebase Cloud Messaging written in Kotlin.",
//                Intent(this, com.google.firebase.quickstart.fcm.kotlin.MainActivity::class.java),
//            ),
//        )
//    }
}
