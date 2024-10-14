package com.genius.dgchat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.digitalgenius.chatwidgetsdk.interactions.DGChatMethods
import com.digitalgenius.chatwidgetsdk.ui.DGChatView

class StraightActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_straight)

        val dgChatView = findViewById<DGChatView>(R.id.straight_dgchatview)

        dgChatView.show()
    }
}