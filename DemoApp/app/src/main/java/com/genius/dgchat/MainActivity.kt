package com.genius.dgchat

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.digitalgenius.chatwidgetsdk.DGChatSdk
import com.digitalgenius.chatwidgetsdk.interactions.IDGChatWidgetListener

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDGChatSdk()
//        https://demos.dgwidgetdeployments.com/general/widget-test?env=dev.us&initversion=2.2.0&widgetId=c8f53916-ad17-4be8-8f58-383ea76bc5f8&version=2.4.0
//        https://demos.dgwidgetdeployments.com/general/widget-test?env=eu&initversion=2.2.0&widgetId=b2813082-fe22-40aa-99f9-91f0b974efaa&version=2.4.0

        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.straight_btn).setOnClickListener {
            startActivity(Intent(this, StraightActivity::class.java))
        }
        findViewById<Button>(R.id.direct_btn).setOnClickListener {
            startActivity(Intent(this, DirectActivity::class.java))
        }
        findViewById<Button>(R.id.embedded_btn).setOnClickListener {
            startActivity(Intent(this, EmbeddedActivity::class.java))
        }
        findViewById<Button>(R.id.navigation_btn).setOnClickListener {
            startActivity(Intent(this, NavigationActivity::class.java))
        }
        findViewById<Button>(R.id.fragment_btn).setOnClickListener {
            startActivity(Intent(this, FragmentActivityExample::class.java))
        }
    }

    private fun initDGChatSdk() {
        DGChatSdk.init(
            "f0c07546-af4c-4963-9e23-3e9343eaf13b",
            "dev.us",
            true,
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

                override fun onWidgetEmbedded() {

                }

                override fun onChatInitialised() {

                }

                override fun onChatInitialisedError() {

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
    }
}