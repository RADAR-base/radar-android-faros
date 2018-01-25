# Faros RADAR-pRMT plugin


## Installation

First, add the plugin code to your application:

```gradle
repositories {
    flatDir { dirs 'libs' }
    maven { url  'http://dl.bintray.com/radar-cns/org.radarcns' }
}

dependencies {
    compile 'org.radarcns:radar-android-faros:0.1-alpha.1'
}
```

## Contributing

Code should be formatted using the [Google Java Code Style Guide](https://google.github.io/styleguide/javaguide.html), except using 4 spaces as indentation. Make a pull request once the code is working.
