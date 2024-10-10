package com.genius.dgchat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.digitalgenius.chatwidgetsdk.interactions.DGChatMethods
import com.digitalgenius.chatwidgetsdk.interactions.IDGChatWidgetListener
import com.digitalgenius.chatwidgetsdk.ui.DGChatView

class DirectActivity: AppCompatActivity() {
    var methods: DGChatMethods? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_straight)

        val dgChatView = findViewById<DGChatView>(R.id.straight_dgchatview)
        dgChatView.chatWidgetListener = object : IDGChatWidgetListener{
            override fun onChatInitialised() {
            }

            override fun onWidgetEmbedded() {
                // Must be run on main thread
                runOnUiThread{
                    methods?.launchWidget()
                }
            }

            override fun onCSATPopoverCloseClicked() {
            }

            override fun onChatEndClick() {
            }

            override fun onChatLauncherClick() {
            }

            override fun onChatMinimizeClick() {
            }

            override fun onChatProactiveButtonClick() {
            }

            override fun onChatInitialisedError() {

            }

        }

        methods = dgChatView.show()
    }
}