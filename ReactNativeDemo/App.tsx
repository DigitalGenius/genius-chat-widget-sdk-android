/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 */

import React from 'react';
import type { PropsWithChildren } from 'react';
import {
  SafeAreaView,
  ScrollView,
  StatusBar,
  StyleSheet,
  Text,
  useColorScheme,
  View,
} from 'react-native';

import {
  Colors,
  DebugInstructions,
  Header,
  LearnMoreLinks,
  ReloadInstructions,
} from 'react-native/Libraries/NewAppScreen';

type SectionProps = PropsWithChildren<{
  title: string;
}>;

import { useEffect, useState } from 'react';
import { NativeEventEmitter, NativeModules } from 'react-native';
const { DGChatModule } = NativeModules;


function Section({ children, title }: SectionProps): JSX.Element {
  const isDarkMode = useColorScheme() === 'dark';

  return (
    <View style={styles.sectionContainer}>
      <Text
        style={[
          styles.sectionTitle,
          {
            color: isDarkMode ? Colors.white : Colors.black,
          },
        ]}>
        {title}
      </Text>
      <Text
        style={[
          styles.sectionDescription,
          {
            color: isDarkMode ? Colors.light : Colors.dark,
          },
        ]}>
        {children}
      </Text>
    </View>
  );
}


function App(): JSX.Element {
  const isDarkMode = useColorScheme() === 'dark';
  console.log('123');

  const backgroundStyle = {
    backgroundColor: isDarkMode ? Colors.darker : Colors.lighter,
  };

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
      DGChatModule.launchWidget();
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

  DGChatModule.showDGChatView(
    "f0c07546-af4c-4963-9e23-3e9343eaf13b",
    "dev.us",
    { "generalSettings": { "isChatLauncherEnabled": true }, "locale": "en-US" },
    null,
    null,
  );


  return (
    <SafeAreaView style={backgroundStyle}>
      <StatusBar
        barStyle={isDarkMode ? 'light-content' : 'dark-content'}
        backgroundColor={backgroundStyle.backgroundColor}
      />
      <ScrollView
        contentInsetAdjustmentBehavior="automatic"
        style={backgroundStyle}>
        <Header />
        <View
          style={{
            backgroundColor: isDarkMode ? Colors.black : Colors.white,
          }}>
          <Section title="Step One">
            Edit <Text style={styles.highlight}>App.tsx</Text> to change this
            screen and then come back to see your edits.
          </Section>
          <Section title="See Your Changes">
            <ReloadInstructions />
          </Section>
          <Section title="Debug">
            <DebugInstructions />
          </Section>
          <Section title="Learn More">
            Read the docs to discover what to do next:
          </Section>
          <LearnMoreLinks />
        </View>
      </ScrollView>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  sectionContainer: {
    marginTop: 32,
    paddingHorizontal: 24,
  },
  sectionTitle: {
    fontSize: 24,
    fontWeight: '600',
  },
  sectionDescription: {
    marginTop: 8,
    fontSize: 18,
    fontWeight: '400',
  },
  highlight: {
    fontWeight: '700',
  },
});

export default App;
