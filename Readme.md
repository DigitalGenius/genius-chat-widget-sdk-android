# Module chatwidgetsdk

# ``DGChatSDK``

Android SDK for DigitalGenius Chat.

## Overview
This SDK enables the DigitalGenius Chat Widget to be embedded anywhere inside an Android app.
The SDK requires minimal setup. Please see the `PublicDemoApp.zip` for an example.

A DigitalGenius Customer Success Manager will provide you with a custom `widgetId`, `env` and `version` before getting started.
Please see the `Integrating SDK to your project` section for details on how to integrate the following settings into an Android app using the SDK.

## Installation

1. Extract the provided `chatwidgetsdk` zip.
2. Add to your build gradle file:
    ```Groovy
    dependencies {
        implementation fileTree(include: ['*.jar', '*.aar'], dir: 'libs')
    }
    ```
3. Put `DGChatSDK.aar` into your `project/module/libs` folder.
4. Click `Sync Project with Gradle files` in Android Studio

## Basic usage example

```Kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DGChatSdk.init(
            widgetId = "your_widget_id",
            env = "your_env",
            version = "your_version",
            useCache = true,
            crmPlatform = "your_crm", // optional
            crmVersion = "your_crm_version", // optional
            callbacks = object : IDGChatWidgetListener {
                override fun onChatMinimizeClick() {
                    Toast.makeText(
                        this@MainActivity,
                        "User callback -> onChatMinimizeClick",
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

                override fun onCSATPopoverCloseClicked() {
                    Toast.makeText(
                        this@MainActivity,
                        "User callback -> onCSATPopoverCloseClicked",
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
            },
            metadata = """
                metadata: {
                    "currentPage": "some-random-string",
                    "currentPageTitle": "another-random-string"
                }
            """.trimIndent()
        )
        attachDGChatViewToLifecycle()

        /*
            ....
         */

       val methods = showDGChatView()
       methods.minimizeWidget()
       methods.sendMessage("your message")
       methods.launchWidget()
       methods.initProactiveButtons(
          listOf("question1", "question2", "question3"),
          listOf("answer1", "answer2", "answer3")
       )
    }
}
```
⚠️ It is highly important to provide ``version`` as a [Semantic versioning three-part version number](https://en.wikipedia.org/wiki/Software_versioning). Otherwise, you'll encounter runtime error.
And finally, just call ``showDGChatViewWith(animator: DGChatViewAnimator)`` to present a chat button on top of specified Activity with animation or ``showDGChatView()`` without.

Methods ``showDGChatViewWith(animator: DGChatViewAnimator)`` and ``showDGChatView()`` returned ``DGChatMethods`` which can be used to performed programmatically widget actions

See [full methods list](https://docs.digitalgenius.com/docs/methods) for more details.

## Sample project

The interaction model and example usage can be found in Demo project. Refer to the `MainActivity.kt` file.



# React-native

## Installation

1. Extract the provided `chatwidgetsdk` zip.
2. Add to your build gradle file:
    ```Groovy
    dependencies {
        implementation fileTree(include: ['*.jar', '*.aar'], dir: 'libs')
        implementation(platform("org.jetbrains.kotlin:kotlin-bom:1.8.0"))
        implementation("androidx.compose.runtime:runtime:1.4.3")
    }
    ```
3. Put `DGChatSDK.aar` into your `project/android/app/libs` folder.
4. Click `Sync Project with Gradle files` in Android Studio

## Basic usage example
Add to your react-native application class:

```Java
public class MainApplication extends Application implements ReactApplication {
        ...
        @Override
        protected List<ReactPackage> getPackages() {
          @SuppressWarnings("UnnecessaryLocalVariable")
          List<ReactPackage> packages = new PackageList(this).getPackages();
          // Packages that cannot be autolinked yet can be added manually here, for example:
           packages.add(new DGChatSdkPackage());
          return packages;
        }
         ...
```

Add to your react-native activity class:
```Java
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DGChatSdkModule.Companion.initDgChatSdkModule(this);
    }
```

Add to your react-native App.tsx:
```JavaScript
import {useEffect, useState} from 'react';
import {NativeEventEmitter, NativeModules} from 'react-native';
const {DGChatModule} = NativeModules;

...
useEffect(() => {
    const eventEmitter = new NativeEventEmitter(NativeModules.DGChatModule);
    let onChatMinimizeClickEventListener = eventEmitter.addListener('OnChatMinimizeClick', event => {
      DGChatModule.showToast("OnChatMinimizeClick")
    });
    let onChatEndClickEventListener = eventEmitter.addListener('onChatEndClick', event => {
      DGChatModule.showToast("onChatEndClick")
    });
    let onChatLauncherClickEventListener = eventEmitter.addListener('onChatLauncherClick', event => {
      DGChatModule.showToast("onChatLauncherClick")
    });
    let onChatProactiveButtonClickEventListener = eventEmitter.addListener('onChatProactiveButtonClick', event => {
      DGChatModule.showToast("onChatProactiveButtonClick")
    });
    let onCSATPopoverCloseClickedEventListener = eventEmitter.addListener('onCSATPopoverCloseClicked', event => {
      DGChatModule.showToast("onCSATPopoverCloseClicked")
    });


    return () => {
      onChatMinimizeClickEventListener.remove(); 
	  onChatEndClickEventListener.remove(); 
	  onChatLauncherClickEventListener.remove(); 
	  onChatProactiveButtonClickEventListener.remove();
	  onCSATPopoverCloseClickedEventListener.remove();
    };
  }, []
);
...
    DGChatModule.showDGChatView(
       "your_widget_id",
       "your_env",
       "your_version",
       true, 
       crmPlatform, // optional
       crmVersion, // optional
       "metadata: { \"currentPage\": \"some-random-string\", \"currentPageTitle\": \"another-random-string\"}"
    );
...

       DGChatModule.sendMessage("your message")
       DGChatModule.launchWidget()
       DGChatModule.initProactiveButtons(
          ["question1", "question2", "question3"],
          ["answer1", "answer2", "answer3"]
       )
...
```

# Full screen support
There are two methods to display your chat in full-screen mode:
### 1. Customize Activity Styles via xml config

```xml
    <resources>
       <style name="Theme.MyApplication" parent="android:Theme.Material.Light.NoActionBar">
           <item name="android:windowFullscreen">true</item>
       </style>
    </resources>
```
### 2. Set Full Screen Programmatically

```Kotlin
    window.setFlags(
       WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
       WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
    )
```
   After setting the flags, you can also hide the status bar using the following code:
   
```Kotlin
    WindowCompat.getInsetsController(window, window.decorView).apply {
        hide(WindowInsetsCompat.Type.systemBars())
    }
```

Screenshot:

![Screenshot](Screenshot.png)

## Full Screen with Bottom Navigation Tabs
If you wish to implement full-screen mode with bottom navigation tabs, follow these steps:

### 1. Enable drawing over the system bar in your activity

```Kotlin
    enableEdgeToEdge()
```

### 2. Hide the status bar when the Genius Chat tab is displayed

```Kotlin
   WindowCompat.getInsetsController(activity.window, activity.window.decorView).apply {
        if (selectedItem == 2) {
            hide(WindowInsetsCompat.Type.statusBars())
        } else {
            show(WindowInsetsCompat.Type.statusBars())
        }
   }
```

Screenshot:

![Screenshot](Screenshot2.png)


## Customise your chat widget
You can use config to customise your chat widget style. Eg: floating button position, proactive buttons

```Kotlin
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
            )

```

## Sample project
The interaction model and example usage can be found in Demo project.
