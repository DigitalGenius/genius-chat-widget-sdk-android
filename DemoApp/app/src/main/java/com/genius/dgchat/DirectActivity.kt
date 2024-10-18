package com.genius.dgchat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.digitalgenius.chatwidgetsdk.interactions.DGChatMethods
import com.digitalgenius.chatwidgetsdk.interactions.SimpleIDGChatWidgetListener
import com.digitalgenius.chatwidgetsdk.ui.DGChatView

class DirectActivity : AppCompatActivity() {
    var methods: DGChatMethods? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_straight)

        val dgChatView = findViewById<DGChatView>(R.id.straight_dgchatview)
        dgChatView.chatWidgetListener = object : SimpleIDGChatWidgetListener() {
            override fun onWidgetEmbedded() {
                // Must be run on main thread
                methods?.launchWidget()
            }

        }

        methods = dgChatView.show()
    }
}