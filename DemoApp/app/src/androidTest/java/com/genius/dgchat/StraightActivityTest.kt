package com.genius.dgchat

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.digitalgenius.chatwidgetsdk.DGChatSdk
import com.digitalgenius.chatwidgetsdk.interactions.DGChatMethods
import com.digitalgenius.chatwidgetsdk.interactions.SimpleIDGChatWidgetListener
import com.digitalgenius.chatwidgetsdk.ui.DGChatView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


@RunWith(AndroidJUnit4::class)
class StraightActivityTest {
    companion object{
        const val CALL_BACK_TIMEOUT = 30000L
    }

    @get:Rule
    var activityRule = ActivityScenarioRule(StraightActivity::class.java)

    @Test
    fun testShowStraightDgChatView() {
        DGChatSdk.init(
            "f0c07546-af4c-4963-9e23-3e9343eaf13b",
            "dev.us",
            true,
        )
        var dgChatMethods: DGChatMethods? = null

        val latchWidgetEmbedded = CountDownLatch(1)
        val latchChatInitialized = CountDownLatch(1)
        val latchChatMinimize = CountDownLatch(1)

        activityRule.scenario.onActivity { activity ->
            activity.runOnUiThread {
                val straightDGChatView = activity.findViewById<DGChatView>(R.id.straight_dgchatview)
                straightDGChatView.chatWidgetListener = object : SimpleIDGChatWidgetListener() {
                    override fun onWidgetEmbedded() {
                        super.onWidgetEmbedded()
                        latchWidgetEmbedded.countDown()
                    }

                    override fun onChatInitialised() {
                        super.onChatInitialised()
                        latchChatInitialized.countDown()
                    }

                    override fun onChatMinimizeClick() {
                        super.onChatMinimizeClick()
                        latchChatMinimize.countDown()
                    }
                }
                dgChatMethods = straightDGChatView.show()
            }
        }

        // Assert that onWidgetEmbedded was called
        assert(latchWidgetEmbedded.await(CALL_BACK_TIMEOUT, TimeUnit.MILLISECONDS)) {
            "onWidgetEmbedded not called within 30 seconds"
        }

        // Launcher the chat
        activityRule.scenario.onActivity { activity ->
            activity.runOnUiThread {
                dgChatMethods?.launchWidget()
            }
        }

        //Assert that onWidgetEmbedded was called
        assert(latchChatInitialized.await(CALL_BACK_TIMEOUT, TimeUnit.MILLISECONDS)) {
            "onChatInitialised not called within 30 seconds"
        }

        // Minimize click
        activityRule.scenario.onActivity { activity ->
            activity.runOnUiThread {
                dgChatMethods?.minimizeWidget()
            }
        }
    }
}