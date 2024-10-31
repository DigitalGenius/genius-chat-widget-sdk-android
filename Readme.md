# DGChatSDK

Android SDK for DigitalGenius Chat.

### Requirements

- Android Studio Electric Eel (2022.1.1) or later

## Overview
This SDK enables the DigitalGenius Chat Widget to be embedded anywhere inside an Android app.

The SDK requires minimal setup. Please see the `PublicDemoApp.zip` for an example.

A DigitalGenius Customer Success Manager will provide you with a custom `widgetId`, `env` and `version` before getting started.
Please see the `Integrating SDK to your project` section for details on how to integrate the following settings into an Android app using the SDK.

## Installation
### Manually import aar file
1. Extract the provided `chatwidgetsdk` zip.
2. Add to your build gradle file:
    ```Groovy
    dependencies {
        implementation fileTree(include: ['*.jar', '*.aar'], dir: 'libs')
    }
    ```
3. Put `DGChatSDK.aar` into your `project/module/libs` folder.
4. Click `Sync Project with Gradle files` in Android Studio

### Maven repository
1. Add jitpack.io in your repositories
   ```Groovy
   repositories {
      google()
      mavenCentral()
      maven { url 'https://jitpack.io' }
   }
   ```
2. Add chat sdk to your dependencies
   ```Groovy
   dependencies {
        implementation 'com.github.DigitalGenius:genius-chat-widget-sdk-android:lastest_release'
   }
   ```

## Basic usage
1. Add ``DGChatView`` into your layout
```xml
<com.digitalgenius.chatwidgetsdk.ui.DGChatView
        android:id="@+id/dgchatview"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
```

2. Init the SDK
```Kotlin
        DGChatSdk.init(
            widgetId = "your_widget_id",
            env = "your_env",
            useCache = true, // optional
            crmPlatform = "your_crm", // optional
            crmVersion = "your_crm_version", // optional
            callbacks = object : SimpleIDGChatWidgetListener {
                override fun onChatMinimizeClick() {
                }

                override fun onChatEndClick() {
                }

                override fun onChatLauncherClick() {
                }

                override fun onChatProactiveButtonClick() {
                }

                override fun onCSATPopoverCloseClicked() {
                }

                override fun onChatInitialised() {
                }

               override fun onChatInitialisedError() {
               }
            },
  	    configs = mapOf(
      		"generalSettings" to mapOf("isChatLauncherEnabled" to true)
        	),
	)
```
3. Show chat widget
```Kotlin
	dgChatView.show()
```

And finally, just call ``show(animator: DGChatViewAnimator)`` to present a chat button on top of specified Activity with animation or ``show()`` if you dont wish to have animation.

Methods ``show(animator: DGChatViewAnimator)`` and ``show()`` returned ``DGChatMethods`` which can be used to performed programmatically widget actions

> 🧐 Best user experience with DGChatSDK achieved when using maximum possible view size e.g. - full size UIView or Window itself.

## Jetpack compose

```Kotlin
    class MainActivity : ComponentActivity() { 
        private var dgChatMethods:DGChatMethods? = null
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            DGChatSdk.init(
               widgetId = "your_widget_id",
               env = "your_env",
               useCache = true, // optional
               crmPlatform = "your_crm", // optional
               crmVersion = "your_crm_version", // optional
            )
            setContent {
               DGChatView(
                   onDGChatCreated = { controller: DGChatViewController ->
                       dgChatMethods = controller.showDGChatView()
                   },
                   chatWidgetListener = object : SimpleIDGChatWidgetListener {
                       override fun onChatMinimizeClick() {
                       }
   
                       override fun onChatEndClick() {
                       }
   
                       override fun onChatLauncherClick() {
                       }
   
                       override fun onChatProactiveButtonClick() {
                       }
   
                       override fun onCSATPopoverCloseClicked() {
                       }
   
                       override fun onWidgetEmbedded() {
                           dgChatMethods.launchWidget()
                           dgChatMethods.sendMessage("your message")
                       }
                       override fun onChatInitialised() {
   
                       }
   
                       override fun onChatInitialisedError() {
                       }
                   },
               ) 
            }
        }
    }
```
> Changes in v3.3:
- You no longer need to call `attachDGChatViewToLifecycle()` in your activity's `onCreate()`.
- Instead of using static functions like `showDGChatView()`, use a DGChatViewController instance:
```Kotlin
controller.showDGChatView()
controller.showDGChatViewWith()
controller.hideDGChatView()
```

## Additional custom configs
You can use config to customise your chat widget style. Eg: floating button position, proactive buttons

```Kotlin
   configs = mapOf(
      "proactiveButtonsSettings" to mapOf(
         "isEnabled" to true,
         "questions" to arrayOf("A", "B", "C"),
         "answers" to arrayOf("1", "2", "3"),
      ),
   
      "generalSettings" to mapOf("isChatLauncherEnabled" to true),
      "widgetPosition" to mapOf(
         "mobile" to mapOf(
            "launcher" to mapOf(
               "bottom" to "10px",
               "right" to "10px",
            )
         ),
         "proactive" to mapOf(
            "bottom" to "90px",
            "right" to "20px",
         ),
         "dialog" to mapOf(
            "top" to "0px",
            "right" to "0px",
            "bottom" to "0px",
            "left" to "0px",
         ),
      ),
   )

```

## Additional Methods

You can use a set of additional methods to interact directly with Chat Widget. These methods are lised as a part of ``DGChatMethods`` instance.

The `sendMessage` method allows the customer to programmatically send a message on the user behalf. This method is not available once the user is handed over to a crm:

```Kotlin
fun sendMessage(message: String)
```

The sendSystemMessage method allows the customer to programmatically send a message to system. This method is only available after the chat has been embeded:

```Kotlin
fun sendSystemMessage(payload: Map<String, Any>)
```
For example:
```Kotlin
sendSystemMessage(mapOf(
    "message" to "special message",
    "payload" to "some data",
))
```
The `launchWidget` method allows the customer to programmatically launch the widget:

```Kotlin
func launchWidget()
```

The `initProactiveButtons` method allows the customer to programmatically trigger the proactive buttons to display:

```Kotlin
func initProactiveButtons(questions: List<String>, answers: List<String>)
```

The `minimizeWidget` method allows customer to minimise an expanded chat UI to the "launcher" state programmatically:

```Kotlin
func minimizeWidget()
```

See [full methods list](https://docs.digitalgenius.com/docs/methods) for more details.

## Launch chat from an external element

If you prefer to launch the chat widget from your UI element (eg: An “Chat with us“ button) rather than using the default SDK launcher, follow these steps:
1. Hide the launcher with this custome config from chat delegate
You can hide the default launcher button by adding the following configuration inside  ``DGChatSdk.init(configs)``
```
var configs = mapOf(Pair("generalSettings", mapOf(Pair("isChatLauncherEnabled", true))))
```

2. Initialize the Chat SDK
Initialize the chat SDK by calling the DGChatSdk.init() function. Then adding ``methods.launchWidget()`` inside ``onWidgetEmbedded()`` callbacks. 

4. Manually Launch the Chat Widget
After the SDK is successfully embedded in your application, you can manually launch the chat widget by calling the ``methods.launchWidget()`` function.

Below is a complete example of how to configure the SDK to manually launch the chat widget
```Kotlin
class DirectActivity : AppCompatActivity() {
    var methods: DGChatMethods? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_straight)
        DGChatSdk.init(
            "your_widget_id",
            "your_env",
            true,
            configs = mapOf(
                Pair("generalSettings", mapOf(Pair("isChatLauncherEnabled", true))),
            )
        )

        val dgChatView = findViewById<DGChatView>(R.id.straight_dgchatview)
        dgChatView.chatWidgetListener = object : SimpleIDGChatWidgetListener {
            override fun onWidgetEmbedded() {
                methods?.launchWidget()
            }
        }

        methods = dgChatView.show()
    }
}
```

## Authenticating Users
### Generating JWT
Please follow the instruction in this [link](https://docs.digitalgenius.com/docs/passing-authenticated-user-data) to generate authentication token
### Passing the token to the chat widget
Once you have generated the jwt you can pass it to the chat widget as an input via the chat widget config defined when you init the chat:
```Kotlin
        DGChatSdk.init(
            widgetId = "your_widget_id",
            env = "your_env",
            ...
            ,
  	    configs = mapOf(
      		"metadata" to mapOf("auth_token" to "YOUR_AUTH_TOKEN")
        	),
	)
```
If your token is short lived it is possible to send updated tokens by sending a system message like so:
```Kotlin
sendSystemMessage(mapOf(
    "name" to "auth_token",
    "payload" to "YOUR_AUTH_TOKEN",
))
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

## Sample project

The interaction model and example usage can be found in Demo project. Refer to the `MainActivity.kt` file.

Project UI root activity is MainActivity which contains buttons reference to other activities:

`StraightActivity.kt` - can be useful example for those cases, when you need to present Chat Widget during view presentation process.

`DirectActivity.kt` - launch the chat up on openning this screen. 

`EmbeddedActivity.kt` - demonstrates a step-by-step call of DGChatSDK from user-defined UI.

`NavigationActivity.kt` - provides an example of handling fragment navigation with chat widget. 

`FragmentActivityExample.kt` - gives an example of adding chat widget to a Fragment. 


# React-native

## Installation

1. Extract the provided `chatwidgetsdk` zip.
2. Add to your build gradle file:
    ```Groovy
    dependencies {
        implementation fileTree(include: ['*.jar', '*.aar'], dir: 'libs')
        implementation 'androidx.lifecycle:lifecycle-runtime-ktx:2.6.1'
        implementation 'androidx.localbroadcastmanager:localbroadcastmanager:1.0.0'
        implementation(platform("org.jetbrains.kotlin:kotlin-bom:1.8.21"))
    }
    ```
3. Put `chatwidgetsdk.aar` into your `project/android/app/libs` folder.
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
      
    });
    let onChatEndClickEventListener = eventEmitter.addListener('onChatEndClick', event => {
     
    });
    let onChatLauncherClickEventListener = eventEmitter.addListener('onChatLauncherClick', event => {
      
    });
    let onChatProactiveButtonClickEventListener = eventEmitter.addListener('onChatProactiveButtonClick', event => {
      
    });

    let onWidgetEmbedded = eventEmitter.addListener('onWidgetEmbedded', event => {
     
    });
    let onChatInitialised = eventEmitter.addListener('onChatInitialised', event => {
      
    });
    let onChatInitialisedError = eventEmitter.addListener('onChatInitialisedError', event => {
      
    });
    let newConversationStarted = eventEmitter.addListener('newConversationStarted', event => {
      
    });

    return () => {
      onChatMinimizeClickEventListener.remove(); 
	  onChatEndClickEventListener.remove(); 
	  onChatLauncherClickEventListener.remove(); 
	  onChatProactiveButtonClickEventListener.remove(); 
	  onWidgetEmbedded.remove(); 
	  onChatInitialised.remove(); 
	  onChatInitialisedError.remove(); 
	  newConversationStarted.remove(); 
    };
  }, []
);
...
   DGChatModule.showDGChatView(
     "your_widget_id",
     "your_env",
     customConfigs, // optional
     crmPlatform, // optional
     crmVersion, // optional
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

For more detailed example, please refer to `App.tsx`.
