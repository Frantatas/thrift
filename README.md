# THRIFT! – Thrift Shop Marketplace App

## Overview

THRIFT! is an Android-based retail and community marketplace application designed for buying, selling, and exchanging secondhand clothing items. The application allows users to browse thrifted clothing, purchase items, and swap garments with other users.

The goal of the platform is to create a simple mobile marketplace where users can explore curated thrifted fashion and participate in a community-driven clothing exchange system.

This project was developed using **Android Studio**, **Kotlin**, and **XML-based user interface design**.

---

## Project Objectives

* Develop a mobile retail platform for secondhand clothing
* Simulate core e-commerce features such as browsing items and ordering products
* Implement user authentication and account management
* Introduce a **Community Swap Feature** for peer-to-peer clothing exchange
* Demonstrate Android mobile development using modern UI components

---

## Key Features

### User Authentication

The application includes authentication modules:

* Login
* Registration
* Forgot Password

These features allow users to securely access the application and manage their accounts.

---

### Product Browsing

Users can browse thrifted clothing items that include:

* Product Image
* Item Name
* Price
* Condition Rating
* Add to Cart option

Items are displayed using Android layout components.

---

### Cart and Order List

Users can simulate purchasing items through:

* Cart system
* Order summary screen
* Ordered items list

This replicates the workflow of a retail e-commerce platform.

---

### Community Swap Feature

The **Community Swap Module** allows users to exchange clothing items without monetary transactions.

Users can:

* Upload clothing items for swap
* Browse available swap listings
* Request item exchanges
* Communicate with other users

This feature promotes a community-based marketplace where users can trade clothing items directly.

---

## System Architecture

The application follows a modular Activity-based architecture consisting of:

* XML-based layout files for user interfaces
* Kotlin classes for activity logic
* Separate screens for each functional module

Main components include:

* Welcome Screen
* Login Screen
* Registration Screen
* Dashboard
* Item List
* Order List
* Swap Marketplace

---

## Technologies Used

* Android Studio
* Kotlin Programming Language
* XML Layout Design
* Android Widgets

Key UI components include:

* TextView
* EditText
* Button
* ImageView
* CardView
* RecyclerView

---

## Project Structure

```
THRIFT/
│
├── app/
│   ├── src/main/java/com/example/thrift
│   │   ├── WelcomeActivity.kt
│   │   ├── LoginActivity.kt
│   │   ├── RegisterActivity.kt
│   │   ├── DashboardActivity.kt
│   │   ├── ItemListActivity.kt
│   │   ├── OrderListActivity.kt
│   │   └── SwapActivity.kt
│
├── res/
│   ├── layout/
│   ├── drawable/
│   ├── values/
│
└── AndroidManifest.xml
```

---

## Future Improvements

Possible enhancements for the system include:

* Real-time database integration
* Online payment gateway integration
* User profile management
* Push notifications for new thrift items
* Advanced search and filtering options

---
