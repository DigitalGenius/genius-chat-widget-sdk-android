package com.genius.dgchat

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.digitalgenius.chatwidgetsdk.DGChatSdk
import com.digitalgenius.chatwidgetsdk.ext.attachDGChatViewToLifecycle
import com.digitalgenius.chatwidgetsdk.interactions.DGChatViewAnimator
import com.digitalgenius.chatwidgetsdk.interactions.IDGChatWidgetListener
import com.digitalgenius.chatwidgetsdk.ui.compose.DGChatView
import com.digitalgenius.chatwidgetsdk.ui.compose.showDGChatViewWith
import com.genius.dgchat.ui.theme.DemoAppComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DGChatSdk.init(
            "f0c07546-af4c-4963-9e23-3e9343eaf13b",
            "dev.us",
            true,
            crmPlatform = "sunco",
            crmVersion = "1.0.0",
            callbacks = object : IDGChatWidgetListener {
                override fun onChatMinimizeClick() {
                    Toast.makeText(
                        this@MainActivity,
                        "User callback -> onChatMinimizeClick",
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onCSATPopoverCloseClicked() {
                    Toast.makeText(
                        this@MainActivity,
                        "User callback -> onCSATPopoverCloseClicked",
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onChatEndClick() {
                    Toast.makeText(
                        this@MainActivity,
                        "User callback -> onChatEndClick",
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onChatLauncherClick() {
                    Toast.makeText(
                        this@MainActivity,
                        "User callback -> onChatLauncherClick",
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onChatProactiveButtonClick() {
                    Toast.makeText(
                        this@MainActivity,
                        "User callback -> onChatProactiveButtonClick",
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onChatInitialised() {
                    Toast.makeText(
                        this@MainActivity,
                        "Chat callback -> onChatInitialised",
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onChatInitialisedError() {
                    Toast.makeText(
                        this@MainActivity,
                        "Chat callback -> onWidgetEmbedded",
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onWidgetEmbedded() {
                    Toast.makeText(
                        this@MainActivity,
                        "Chat callback -> onWidgetEmbedded",
                        Toast.LENGTH_LONG
                    ).show()
                }
            },

            configs = mapOf(
                Pair(
                    "proactiveButtonsSettings", mapOf(
                        Pair("isEnabled", true),
                        Pair("questions", arrayOf("A", "B", "C")),
                        Pair("answers", arrayOf("1", "2", "3")),
                    )
                ),
                Pair("generalSettings", mapOf(Pair("isChatLauncherEnabled", true))),
                Pair(
                    "widgetPosition",
                    mapOf(
                        Pair(
                            "mobile", mapOf(
                                Pair(
                                    "launcher", mapOf(
                                        Pair("bottom", "10px"),
                                        Pair("right", "10px")
                                    )
                                ),
                                Pair(
                                    "proactive", mapOf(
                                        Pair("bottom", "90px"),
                                        Pair("right", "20px")
                                    )
                                ),
                                Pair(
                                    "dialog", mapOf(
                                        Pair("top", "0px"),
                                        Pair("right", "0px"),
                                        Pair("bottom", "0px"),
                                        Pair("left", "0px"),
                                    )
                                )
                            )
                        )
                    )
                ),
            ),
        )
        attachDGChatViewToLifecycle()

        setContent {
            DemoAppComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.Bottom,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                text = "Sdk user's screen",
                                textAlign = TextAlign.Center
                            )
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 16.dp),
                                onClick = {
                                    showDGChatViewWith(object : DGChatViewAnimator {
                                        override val animLength: Long
                                            get() = 800

                                        override fun onAnimationStarted(view: View) {
                                            view.alpha = 0f
                                            view.translationX = -700f
                                            view.translationY = 700f
                                        }

                                        override fun onAnimate(view: View, progress: Float) {
                                            view.translationX = -700f - (-700f * progress)
                                            view.translationY = 700f - (700f * progress)
                                            view.scaleX = progress
                                            view.scaleY = progress
                                            view.rotation = 360 * progress
                                            view.alpha = progress
                                        }

                                        override fun onAnimationFinished(view: View) {

                                        }
                                    })
                                }
                            ) {
                                Text(text = "Call SDK")
                            }

                            Text(
                                modifier = Modifier
                                    .clickable { }
                                    .padding(top = 16.dp,
                                        bottom = 16.dp),
                                text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."
                            )
                        }
                        DGChatView(
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DemoAppComposeTheme {
        Greeting("Android")
    }
}