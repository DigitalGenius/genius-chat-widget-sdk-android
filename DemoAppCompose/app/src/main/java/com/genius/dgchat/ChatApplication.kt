package com.genius.dgchat

import android.app.Application
import com.digitalgenius.chatwidgetsdk.DGChatSdk

class ChatApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        DGChatSdk.init(
            widgetId = "f0c07546-af4c-4963-9e23-3e9343eaf13b",
            env = "dev.us",
            crmPlatform = "sunco",
            configs = mapOf(
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