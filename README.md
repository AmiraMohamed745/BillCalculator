# BillCalculator

The **Bill Calculator** is a simple Android app developed using Kotlin and Jetpack Compose. It allows users to calculate the tip amount and the overall bill amount based on the bill amount and tip percentage they input. Additionally, the app provides features to round up the bill and divide it among a specified number of people. Users can also easily share the results with others via text or as a screenshot.

## Screenshots

![Main Screen](https://github.com/AmiraMohamed745/BillCalculator/assets/109589388/23ed1256-3718-482d-bd62-6d6e05c4c26f)

![Share Bill](https://github.com/AmiraMohamed745/BillCalculator/assets/109589388/48cef83a-8eea-4682-a4f5-ab3ae8e01923)

## Features
- Calculate tip and total bill amount.
- Round up the bill to the nearest whole number.
- Split the bill among multiple people.
- Share the bill details via text or as a screenshot.

## What I've Learned
Throughout the development of this app, I've gained valuable knowledge and experience in various aspects of Android development. Here are some of the key learnings:

### State and State Hoisting
I've learned how to manage the state of the app using Jetpack Compose's state management mechanisms, ensuring that the UI reflects the latest data and user interactions.

### Using ViewModels
I've implemented ViewModels to separate the UI logic from the UI components, making the app more maintainable and testable.

### Sharing Data with Other Apps
I've integrated the app with Android's sharing functionality, allowing users to easily share bill details with other apps like WhatsApp.

### LiveData vs. StateFlow
I've explored the differences between LiveData and StateFlow in terms of data flow and how they fit into the Jetpack Compose ecosystem.

### collectAsState() vs. collectAsStateWithLifecycle
I've understood the nuances between `collectAsState()` and `collectAsStateWithLifecycle()` when observing StateFlow data and handling lifecycle events.

### Using Logcat and Debugger
I've utilized Android Studio's debugging tools, including Logcat and the Debugger, to troubleshoot and improve the app's performance and functionality.

Feel free to explore the code and contribute to the development of this app. If you have any questions or suggestions, please don't hesitate to reach out!

## How to Build and Run
To build and run the app, follow these steps:
1. Clone the repository to your local machine.
2. Open the project in Android Studio.
3. Build and run the app on an Android emulator or physical device.

Happy coding!
