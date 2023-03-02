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

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Introduction to MVVM</title>
	<style>
		body {
			font-family: Arial, sans-serif;
			background-color: #f2f2f2;
			padding: 20px;
		}
		
		h1 {
			font-size: 36px;
			margin-bottom: 20px;
			color: #005daa;
			text-align: center;
		}
		
		h2 {
			font-size: 24px;
			margin-bottom: 10px;
			color: #005daa;
		}
		
		p {
			font-size: 18px;
			line-height: 1.5;
			margin-bottom: 20px;
		}
		
		code {
			background-color: #f2f2f2;
			padding: 5px;
			font-family: monospace;
			font-size: 16px;
		}
		
		ul {
			list-style-type: disc;
			margin-bottom: 20px;
			margin-left: 40px;
		}
		
		li {
			font-size: 18px;
			margin-bottom: 10px;
			line-height: 1.5;
		}
		
		.blockquote {
			background-color: #f7f7f7;
			border-left: 10px solid #005daa;
			padding: 10px 20px;
			margin: 20px 0;
			font-size: 18px;
			line-height: 1.5;
			color: #666666;
			quotes: "\201C" "\201D" "\2018" "\2019";
		}
		
		.blockquote:before {
			content: open-quote;
			font-size: 40px;
			line-height: 1;
			margin-right: 5px;
			vertical-align: middle;
		}
		
		.blockquote:after {
			content: close-quote;
			font-size: 40px;
			line-height: 1;
			margin-left: 5px;
			vertical-align: middle;
		}
	</style>
</head>
<body>
	<h1>Introduction to MVVM</h1>
	
	<h2>What is MVVM?</h2>
	<p>MVVM stands for Model-View-ViewModel. It is a design pattern that separates an application into three main components:</p>
	
	<ul>
		<li>The <code>Model</code>, which represents the data and business logic of the application.</li>
		<li>The <code>View</code>, which represents the user interface.</li>
		<li>The <code>ViewModel</code>, which acts as a mediator between the Model and the View. It exposes the data from the Model to the View in a way that is easy to bind to, and it also handles user input from the View and updates the Model accordingly.</li>
	</ul>
	
	<p>The MVVM pattern is commonly used in modern web and mobile applications, especially those that utilize a reactive programming approach.</p>
	
	<h2>Why use MVVM?</h2>
	<p>Using MVVM can provide a number of benefits for an application:</p>
	
	<ul>
		<li><strong>Separation of concerns:</strong> By separating
