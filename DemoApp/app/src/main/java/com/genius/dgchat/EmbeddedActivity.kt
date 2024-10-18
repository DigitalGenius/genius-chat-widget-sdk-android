package com.genius.dgchat

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.digitalgenius.chatwidgetsdk.DGChatSdk
import com.digitalgenius.chatwidgetsdk.interactions.DGChatMethods
import com.digitalgenius.chatwidgetsdk.interactions.IDGChatWidgetListener
import com.digitalgenius.chatwidgetsdk.interactions.SimpleIDGChatWidgetListener
import com.digitalgenius.chatwidgetsdk.ui.DGChatView

class EmbeddedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_embedded)

        var methods: DGChatMethods? = null
        val dgChatView = findViewById<DGChatView>(R.id.embedded_dgchat_view)

        findViewById<Button>(R.id.embedded_show_btn).setOnClickListener {
            methods = dgChatView.show()
        }
        findViewById<Button>(R.id.embedded_hide_btn).setOnClickListener {
            dgChatView.hide()
        }
        findViewById<Button>(R.id.embedded_send_txt_msg).setOnClickListener {
            methods?.sendMessage("Test message")
        }
        findViewById<Button>(R.id.embedded_reset_chat).setOnClickListener {
            DGChatSdk.resetChat(applicationContext)
        }
    }
}