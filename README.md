# Serafello Chat Application

## Overview
This Android chat application allows users to communicate with their friends in real-time. It offers features such as login with email and password, registration of new accounts, and the ability to maintain and customize user profiles. The application utilizes Firebase for authentication, storage of user data, and real-time messaging.

## Features
* Login with email and password: Users can securely log in using their registered email and password credentials.
* Register new account: New users can create an account by providing their email and desired password.
* Logout: Users can log out of the application to ensure the security of their account.
* Firebase authentication: The application utilizes Firebase Authentication for secure user login and registration.
* Maintain own account: Users can update their profile image to personalize their account.
* View all users: Users can browse through a list of all the users who are using the application and view their profile images.
* Chat with friends: Users can engage in real-time conversations with their friends through the chat feature. They can send photos taken from the camera or gallery, view their friends' last seen status, and check if a friend is currently online.
* Firebase Realtime Database: All user data and chat lists are stored in the Firebase Realtime Database, ensuring real-time updates and synchronization across devices.
* Firebase Storage: Each user's profile picture is stored in Firebase Storage for efficient retrieval and display.
* Firebase Cloud Messaging: The application utilizes Firebase Cloud Messaging to send notifications to users.

## Screens
1. Login screen: Users can enter their credentials to log in.
* ![Screenshot 2024-02-14 231536](https://github.com/SaraSAli/SerafelloChat/assets/17590461/ca81b684-fa99-487b-8c4d-70bdc1b0028e)
2. Register screen: New users can provide their details to create an account.
* ![Screenshot 2024-02-14 232434](https://github.com/SaraSAli/SerafelloChat/assets/17590461/0dbcfeb0-d37b-45a2-8293-c799d9080380)
3. Message activity: The main screen where users can engage in real-time conversations.
* ![Screenshot 2024-02-14 232824](https://github.com/SaraSAli/SerafelloChat/assets/17590461/00deb169-110e-4f7c-bcd8-86539e89b52c)
4. Chats fragment: Displays a list of chat conversations.
* ![Screenshot 2024-02-14 232905](https://github.com/SaraSAli/SerafelloChat/assets/17590461/dffd22ca-4e2b-48a5-a445-3bba2fc95ffc)
5. Users fragment: Shows a list of all the users using the application.
* ![Screenshot 2024-02-14 232953](https://github.com/SaraSAli/SerafelloChat/assets/17590461/6718709a-f69e-4a2d-80cf-94c27a84fab0)
6. Setting activity: Allows users to change their profile picture.
* ![Screenshot 2024-02-14 234104](https://github.com/SaraSAli/SerafelloChat/assets/17590461/c27423d4-3e0b-4529-97e9-750f4a2f024d)

## Technical Details
minSdkVersion: 21

targetSdk: 33

## Used Libraries
```
com.squareup.picasso:picasso:2.71828
com.squareup.retrofit2:retrofit:2.9.0
com.squareup.retrofit2:converter-gson:2.9.0
com.google.firebase:firebase-messaging:23.4.1
com.google.firebase:firebase-analytics:21.5.1
com.google.firebase:firebase-database:20.3.0
com.google.firebase:firebase-core:21.1.1
com.google.firebase:firebase-auth:22.3.1
com.google.firebase:firebase-storage:20.3.0
de.hdodenhof:circleimageview:3.1.0
com.github.bumptech.glide:glide:4.16.0
com.theartofdev.edmodo:android-image-cropper:2.8.0
```
Please refer to the relevant documentation of each library for more information.

## Installation
To run the application, follow these steps:

1. Clone the repository.
2. Open the project in Android Studio.
3. Build and run the application on an emulator or physical device.

**Note: Make sure to add your own Firebase configuration file and replace the necessary placeholders in the code to establish the connection with Firebase services.**

Feel free to contact me if you have any questions or need further assistance.

Enjoy chatting with your friends!
