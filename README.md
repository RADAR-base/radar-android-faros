# Faros RADAR-pRMT plugin

Plugin for the eMotion Faros 90/180/360 devices. The source code for device interaction is
closed and maintained by The Hyve. For more information, please contact The Hyve.

## Installation

First, add the plugin code to your application:

```gradle
repositories {
    maven { url  'http://dl.bintray.com/radar-cns/org.radarcns' }
}

dependencies {
    compile 'org.radarcns:radar-android-faros:0.1-alpha.1'
}
```

## Configuration

This plugin takes the following Firebase configuration parameters:

| Name | Type | Default | Description |
| ---- | ---- | ------- | ----------- |
| `emotion_faros_acceleration_rate` | int (Hz) | `25` | How frequently acceleration values are collected. Use `0` to disable acceleration data. |
| `emotion_faros_acceleration_resolution` | float (g) | `0.001` | Resolution of acceleration values. A higher resolution will result in lower range. |
| `emotion_faros_ecg_rate` | int (Hz) | `125` | How frequently ECG data is collected. Use `0` to disable ECG data. |
| `emotion_faros_ecg_resolution` | float (µV) | `1.0` | Resolution of ECG values. A higher resolution will result in lower range. |
| `emotion_faros_ecg_channels` | int | `1` | Number of ECG channels to activate. Faros 360 supports 3 channels, other devices support 1. |
| `emotion_faros_ecg_filter_frequency` | float (Hz) | `0.05` | High pass filter frequency for the ECG channel. |
| `emotion_faros_temperature_enable` | boolean | `true` | Whether to send temperature readings. Only the Faros 360 supports temperature readings. |
| `emotion_faros_inter_beat_interval_enable` | boolean | `true` | Whether to send inter-beat-interval readings. |
| `emotion_faros_battery_interval` | int (s) | `60` = 1 minute | How often to send the battery level. Use `0` to disable precise battery level collection and opt for approximate battery level instead. |

This plugin produces data for the following topics:

| Topic | Type | Description |
| ----- | ---- | ----------- |
| `android_emotion_faros_acceleration` | `org.radarcns.passive.emotion.EmotionFarosAcceleration` | Acceleration values. |
| `android_emotion_faros_ecg` | `org.radarcns.passive.emotion.EmotionFarosEcg` | ECG signal. |
| `android_emotion_faros_inter_beat_interval` | `org.radarcns.passive.emotion.EmotionFarosInterBeatInterval` | Inter-beat-interval derived from the ECG signal. |
| `android_emotion_faros_temperature` | `org.radarcns.passive.emotion.EmotionFarosTemperature` | Temperature. |
| `android_emotion_faros_battery_level` | `org.radarcns.passive.emotion.EmotionFarosBatteryLevel` | Battery level. If `battery_level_interval` is set to `0`, this is an approximation. |

## Contributing

Code should be formatted using the [Google Java Code Style Guide](https://google.github.io/styleguide/javaguide.html), except using 4 spaces as indentation. Make a pull request once the code is working.
