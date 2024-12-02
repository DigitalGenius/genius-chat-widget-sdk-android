package com.genius.dgchat

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.digitalgenius.chatwidgetsdk.DGChatSdk
import com.digitalgenius.chatwidgetsdk.interactions.SimpleIDGChatWidgetListener
import com.digitalgenius.chatwidgetsdk.ui.DGChatView
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StraightActivityTest {

    @get:Rule
    var activityRule = ActivityScenarioRule(StraightActivity::class.java)

    @Test
    fun testShowStraightDgChatView() {
        DGChatSdk.init(
            "f0c07546-af4c-4963-9e23-3e9343eaf13b",
            "dev.us",
            true,
        )
        var isWidgetEmbedded = false

        activityRule.scenario.onActivity { activity ->
            activity.runOnUiThread {
                val straightDGChatView = activity.findViewById<DGChatView>(R.id.straight_dgchatview)
                straightDGChatView.chatWidgetListener = object : SimpleIDGChatWidgetListener() {
                    override fun onWidgetEmbedded() {
                        super.onWidgetEmbedded()
                        isWidgetEmbedded = true
                    }
                }
                straightDGChatView.show()
            }
        }
        Thread.sleep(30000)

        // Assert that onWidgetEmbedded was called
        assert(isWidgetEmbedded) { "onWidgetEmbedded not called within 30 seconds" }

    }

}