# CoinConverter (Android App) :money_with_wings:
## Project description (First version)
<p align="center">Application consuming the [HG FINANCE API](https://hgbrasil.com/status/finance), accessing the quotation of some currencies to the real. In addition, bitcoin price on some brokers, and the stock exchange index.</p>

![BG](https://user-images.githubusercontent.com/90936908/153720186-bebec2a9-a971-4451-897d-5377ffad94e0.jpg)

## MVVM Architecture.

## Some development tools:

- [Kotlin](https://kotlinlang.org/)
- [Retrofit](https://square.github.io/retrofit/)
- [Gson](https://github.com/google/gson)
- [Room Database](https://developer.android.com/reference/android/arch/persistence/room/RoomDatabase)
- [Coroutines](https://developer.android.com/kotlin/coroutines)
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
- [ViewBinding](https://developer.android.com/topic/libraries/view-binding)
- [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
- [ViewPager](https://developer.android.com/jetpack/androidx/releases/viewpager2)
- [Navigation](https://developer.android.com/guide/navigation)
- [DataStore](https://developer.android.com/topic/libraries/architecture/datastore)

## Features (first version):

- [x] Custom conversion.
- [x] Using the application even without internet connection (with the last data received, on the last connection). Using Room, data is stored in the database whenever the request is made.
- [x] Some stock market indices.
- [x] Bitcoin quote on some brokers.

## Setup:

- Go to https://hgbrasil.com/, then create an account, and in the dashboard, generate a key.
- Access the project with Android Studio, in the "api" package, in the "ServiceResult" class, replace the value of the constant "API_KEY" with your key.
- After that, just run the application. Enjoy!:heart: