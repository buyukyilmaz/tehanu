[![](https://jitpack.io/v/buyukyilmaz/tehanu.svg)](https://jitpack.io/#buyukyilmaz/tehanu)

## Setup

Add jitpack.io to settings.gradle.kts

```gradle
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://jitpack.io") }
    }
}
```
Add dependency to build.gradle.kts

```gradle
implementation("com.github.buyukyilmaz:tehanu:1.0.3")
```

## AppConfig

|                   |        |
|-------------------|--------|
| compileSdk        | 34     |
| buildToolsVersion | 34.0.0 |
| minSdk            | 28     |
| kotlinVersion     | 2.0.0  |
| gradleVersion     | 8.4.1  |
| jvmToolchain      | 17     |

## Dependencies

```sh
implementation("androidx.compose:compose-bom:2024.05.00")
implementation("androidx.compose.ui:ui")
implementation("androidx.compose.foundation:foundation")
implementation("androidx.compose.runtime:runtime-livedata")
implementation("androidx.activity:activity-compose:1.9.0")
implementation("androidx.appcompat:appcompat:1.7.0")
implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")
implementation("com.google.android.material:material:1.12.0")
implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
implementation("com.google.code.gson:gson:2.11.0")
implementation("androidx.datastore:datastore-preferences:1.1.1")
implementation("androidx.navigation:navigation-compose:2.7.7")
implementation("androidx.work:work-runtime-ktx:2.9.0")
```
