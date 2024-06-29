<h1 align="center"><b>CypherX</b></h1>
<p align="center">
  A secure and user-friendly password manager app built with Jetpack Compose that allows users to store and manage their passwords in a secure and organized manner.
</p><br>

<p align="center">
  <img src="https://github.com/amanverma-765/CypherX/assets/46085882/f51711c0-cc8b-4747-8674-a8a4a0a2f442" height="300"/>
 &nbsp;&nbsp;&nbsp;
 <img src="https://github.com/amanverma-765/CypherX/assets/46085882/5626d8ee-404a-485e-bd19-3df750a92a96" height="300"/>
  &nbsp;&nbsp;&nbsp;
  <img src="https://github.com/amanverma-765/CypherX/assets/46085882/c5d99d5d-15ed-43b3-928a-7a1cbc5ecf4e" height="300"/>
&nbsp;&nbsp;&nbsp;
<img src="https://github.com/amanverma-765/CypherX/assets/46085882/bb37018e-bfb9-49e3-8fc6-5e78188df9ee" height="300"/>
</p>

## Functional Requirements

- [x] **Add Password**: Users can securely add new passwords by providing details such as the account type (e.g., Gmail, Facebook, Instagram), username/email, and password.
- [x] **View/Edit Password**: Users can view and edit existing passwords, including account details like username/email and password.
- [x] **Show List of Passwords on Home Screen**: The home screen of the application displays a list of all saved passwords, showing essential details for each entry.
- [x] **Delete Password**: Users can delete passwords.

## Technical Requirements 

- [ ] **Encryption**: Implement strong encryption algorithms (e.g., AES, RSA) to secure password data.
- [x] **Database**: Use a secure database (e.g., SQLite, Room) to manage encrypted passwords locally on the device.
- [x] **User Interface**: Design a clean and intuitive user interface for managing passwords.
- [x] **Input Validation**: Implement validation to ensure that mandatory fields are not empty.
- [x] **Error Handling**: Properly handle errors and edge cases to ensure a smooth user experience.

## Bonus Features (Optional)

- [x] **Security Feature**: When the user opens the app, prompt them to authenticate using biometric (e.g., fingerprint, face ID) or a PIN screen for added security.
- [ ] **Password Strength Meter**: Provide a visual indicator of password strength to help users create strong passwords.
- [ ] **Password Generation**: Provide a feature to generate strong, random passwords for new accounts.

## Tech Stack Used

- Minimum SDK level 24.
- [Kotlin](https://kotlinlang.org/) based, utilizing [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) for asynchronous operations.
- Jetpack Libraries:
  - Jetpack Compose: Android’s modern toolkit for declarative UI development.
  - Lifecycle: Observes Android lifecycles and manages UI states upon lifecycle changes.
  - ViewModel: Manages UI-related data and is lifecycle-aware, ensuring data survival through configuration changes.
  - [Koin](https://insert-koin.io/): Facilitates dependency injection.
- Architecture:
  - MVI Architecture (Model - View - Intent): Facilitates separation of concerns and promotes maintainability.
  - Repository Pattern: Acts as a mediator between different data sources and the application's business logic.
- [Kotlinx-Serialization](https://github.com/Kotlin/kotlinx.serialization): A modern JSON parsing library.
- [Voyager](https://voyager.adriel.cafe/): A modern and robust navigation library.

## Compiling and Running an App from Android Studio

### Prerequisites

- Android Studio installed
- Basic knowledge of Android development
- Some patience

### Setting Up Android Studio

1. **Install Android Studio**
    - Download Android Studio from the official [Android Developer website](https://developer.android.com/studio).
    - Follow the installation instructions for your operating system.

2. **Downloading this project**
    - Open Android Studio.
    - Click on `Get from VCS`.
    - Copy the url of this github repo.
    - Place the url inside URL field and hit `Clone`.

### Running the App

1. **Connect a Device or Start an Emulator**
    - Connect your Android device via USB and enable USB debugging in the developer options.
    - Alternatively, you can use an emulator:
        - Click on `AVD Manager` (Device Manager) in Android Studio.
        - Create a new virtual device and start it.

2. **Run the App**
    - Click on the green `Run` button in the toolbar or press `Shift + F10`.
    - Select the device/emulator from the list and click `OK`.
    - The app will build and install on the selected device/emulator.
  
## Conclusion

