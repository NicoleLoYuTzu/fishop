# fishop
appworks school personnal project

<a href="https://play.google.com/store/apps/details?id=com.nicole.fishshop"><img src="https://camo.githubusercontent.com/9b43e9e7bdf73be90eaee8bf94cf61440638567e/68747470733a2f2f692e696d6775722e636f6d2f49353862574c642e706e67" width="230" height="90"></a>

## Feature
Provide two modes to switch between customers and sellers to suit different user scenarios

<img src="https://github.com/NicoleLoYuTzu/fishop_readme_gif/blob/main/untitled.gif" height=400>  

seller could record everyday purchase volume

<img src="https://github.com/NicoleLoYuTzu/fishop_readme_gif/blob/main/untitled1.gif" height=400>  


# Implementation
### Design Pattern
* MVVM
* Factory
### Android JetPack
* ViewModel
* LiveData
* ViewBinding
* Navigation
### Firebase
* Authentication
* Firestore
* Storage
* Crashlytics
### ThirdParty
*  [<a href="https://github.com/airbnb/lottie-android">Lottie</a>](https://github.com/airbnb/lottie-android)



# Environment
* Android SDK 32

# version
1.1.6 : 2022/07/29

# contact
Nicole,  A4207486@gmail.com

MVVM Design Pattern
簡介
MVVM 是一種設計模式，用於開發基於用戶介面的應用程序。它是 Model-View-ViewModel 的縮寫，這個模式的核心是將 UI 邏輯從 View 中分離出來，並使用 ViewModel 進行管理。

Model
Model 是您的應用程序的核心部分，它代表您的數據層。它可以包含從本地數據庫、網絡服務、文件等各種來源獲取的數據。Model 通常包含以下幾個部分：

實體類：表示您的數據實體。
Repository：用於獲取和存儲數據的類。
DAO：用於與本地數據庫進行交互的類。
View
View 是您的應用程序的用戶介面部分。它是用戶與您的應用程序進行交互的地方，因此它應該是清晰、易於使用的。View 包含以下幾個部分：

XML 佈局：包含所有用於構建界面的佈局元素。
Activity 或 Fragment：用於管理佈局和 UI 狀態。
ViewModel
ViewModel 是您的應用程序的業務邏輯部分。它的作用是處理數據、協調 Model 和 View 之間的交互，以及管理應用程序的狀態。ViewModel 包含以下幾個部分：

ViewModel：用於維護與界面相關的數據和狀態。
MediatorLiveData：用於將 ViewModel 中的數據傳遞到 View 中。
ViewModelFactory：用於創建 ViewModel 實例。
如何使用 MVVM
要使用 MVVM，您需要按照以下步驟操作：

定義您的 Model，包括數據庫表、實體類、Repository 和 DAO。
定義您的 View，包括 XML 佈局、Activity 或 Fragment。
定義您的 ViewModel，包括 ViewModel、MediatorLiveData 和 ViewModelFactory。
在 View 中訂閱 ViewModel 中的 MediatorLiveData，以更新 UI 狀態。
在 ViewModel 中訂閱 Model 中的數據，以更新 ViewModel 中的 MediatorLiveData。
MVVM 的優點
MVVM 的優點包括：

減少代碼耦
