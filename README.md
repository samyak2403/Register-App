# RegisterApp

**RegisterApp** is an Android application that provides user registration, login, and password reset functionality. Users can sign up, log in, and reset their password using email verification. This app sends a verification link to the user's email to complete the registration or password reset process.

---

## ✨ Features
- **User Registration:** Sign up with an email address and password.
- **Email Verification:** Receive an email verification link that must be confirmed before logging in.
- **Login:** Log in with registered email and password.
- **Forgot Password:** Request a password reset link sent to the registered email.
- **Secure Authentication:** Ensure secure authentication flow using Firebase Authentication.

---

## 🛠️ Technologies Used
- **Android (Java)**
- **Firebase Authentication**
- **Email Verification**
- **Material Design UI**

---

## 🔧 Firebase Setup

1. Go to the [Firebase Console](https://console.firebase.google.com/) and create a new project.
2. Enable **Firebase Authentication** and the **Email/Password** provider.
3. Download the `google-services.json` file and place it in your project's `app/` directory.
4. Add the necessary Firebase dependencies to your `build.gradle` files.

---

## 📝 How It Works

### 1️⃣ Registration
1. Provide your **email** and **password** on the registration screen.
2. A verification email is sent to the provided email address.
3. Verify your email by clicking the link in the email.

### 2️⃣ Login
1. After verifying your email, log in with your **email** and **password**.
2. Upon successful login, you will be redirected to the home screen.

### 3️⃣ Forgot Password
1. If you forget your password, request a password reset link.
2. A reset link will be sent to your registered email.

---

## 🚀 Usage

### Register
1. Open the app and navigate to the **registration screen**.
2. Enter your **email** and **password**, then submit.
3. Check your inbox for the verification link, and click to verify your email.

### Login
1. After verification, return to the **login screen**.
2. Enter your **credentials** and log in.

### Forgot Password
1. Go to the **Forgot Password** screen.
2. Enter your registered email, and a reset link will be sent to your email.
3. Use the link to reset your password.

---

## 📥 Installation

1. Clone this repository:

   ```bash
   git clone https://github.com/samyak2403/RegisterApp.git
