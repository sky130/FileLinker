# FileLinker

[![](https://jitpack.io/v/sky130/FileLinker.svg)](https://jitpack.io/#sky130/FileLinker)

## Introduction
FileLinker is a framework designed to simplify reading and writing data in the Android/data directory for devices running Android 11 and above. It provides an easy-to-use API for accessing and manipulating files in the external storage of an Android device.

## Features
- Android 11 support: FileLinker fully supports the scoped storage model introduced in Android 11, allowing you to access app-specific directories without requiring dangerous permissions.
- Read and write operations: FileLinker enables you to perform various file operations such as reading, writing and copying files and directories.
- URI-based access: FileLinker uses URIs to identify and access files, making it compatible with both file paths and content URIs.
- Lightweight and efficient: FileLinker is designed to be lightweight and efficient, minimizing resource usage while providing optimal performance.

## Installation
To use FileLinker in your Android project, follow these steps:

1. Add the JitPack repository to your project's build.gradle file:
```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

2. Add the dependency to your module's build.gradle file:
```groovy
dependencies {
    implementation 'com.github.sky130:FileLinker:1.0.0'
}
```

3. Sync your project with the Gradle files.

## Usage

For detailed usage instructions and API reference, please refer to the [FileLinker Wiki](https://github.com/sky130/FileLinker/wiki).

## License
This project is licensed under the [Apache-2.0 license](LICENSE).
