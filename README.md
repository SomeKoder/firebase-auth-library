# Firebase Auth Library
### Simplified Authentication

[![](https://jitpack.io/v/SomeKoder/firebase-auth-library.svg)](https://jitpack.io/#SomeKoder/firebase-auth-library)

This library uses [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) for it's dependency injection. Make sure to have `@HiltAndroidApp` annotated in your base application class.

Be sure to provide your own `google-services.json` in your application module.

All of the use cases take advantage of coroutines and are suspend functions.

##### Login with Email and Password
`val result = loginEmailPasswordUseCase.execute(email = "email@example.com", password = "example")`

##### Create an account with Email and Password
`val result = createUserEmailPasswordUseCase.execute(email = "email@example.com", password = "example")`

##### Forgot Password
`forgotPasswordUseCase.execute(email = "email@example.com")`

<br/>
Feedback is welcome. 