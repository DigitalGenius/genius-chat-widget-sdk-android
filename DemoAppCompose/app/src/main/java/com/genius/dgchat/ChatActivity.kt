package com.genius.dgchat

import android.os.Bundle
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.digitalgenius.chatwidgetsdk.interactions.DGChatMethods
import com.digitalgenius.chatwidgetsdk.interactions.SimpleIDGChatWidgetListener
import com.digitalgenius.chatwidgetsdk.ui.compose.DGChatView
import com.digitalgenius.chatwidgetsdk.ui.compose.DGChatViewController
import com.genius.dgchat.ui.theme.DemoAppComposeTheme

class ChatActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WebView.setWebContentsDebuggingEnabled(true)
        setContent {
            DemoAppComposeTheme {
                ChatBotComposable(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    finish()
                }
            }
        }
    }
}

@Composable
fun ChatBotComposable(
    modifier: Modifier = Modifier,
    closeChatBot: () -> Unit,
) {
    val methods = remember { mutableStateOf<DGChatMethods?>(null) }
    val chatController = remember { mutableStateOf<DGChatViewController?>(null) }
    DGChatView(
        modifier = modifier.testTag("DGChatView"),
        onDGChatCreated = { controller ->
            chatController.value = controller
            methods.value = controller.showDGChatView()
        },
        chatWidgetListener = object : SimpleIDGChatWidgetListener() {
            override fun onChatEndClick() {
                // Send callback to finish activity when the chatbot is end
                closeChatBot()
            }

            override fun onChatMinimizeClick() {
                // Send callback to finish activity when the chatbot is minimised
                closeChatBot()
            }

            override fun onWidgetEmbedded() {
                // Launch chat directly when it's initialized
                methods.value?.launchWidget()
            }

        },
    )
}