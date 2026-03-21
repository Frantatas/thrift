# Thrift Backend - Firebase Configuration Guide

## Overview

The Thrift marketplace application uses a modern, cloud-native backend powered by Firebase. This backend provides secure user authentication, real-time data synchronization, and a scalable NoSQL database using Cloud Firestore.

---

## Firebase Services Used

### 1. Firebase Authentication
- **Purpose**: Secure user registration, login, and password management
- **Provider**: Email/Password authentication
- **Features**:
  - User registration with email and password
  - Email-based login
  - Password reset via email
  - Session management

### 2. Cloud Firestore
- **Purpose**: Primary real-time NoSQL database for all application data
- **Features**:
  - Real-time synchronization
  - Offline support
  - Powerful querying capabilities
  - Automatic indexing

---

## Database Collections Structure

### `users` Collection
Stores user profile information. Each document is keyed by the user's Firebase UID.

```json
{
  "uid": "user_firebase_uid",
  "email": "user@example.com",
  "displayName": "John Doe",
  "profileImageUrl": "https://...",
  "bio": "Thrift enthusiast",
  "rating": 4.5,
  "totalReviews": 12,
  "createdAt": "2024-01-15T10:30:00Z",
  "updatedAt": "2024-01-20T14:45:00Z"
}
```

**Subcollections:**
- `transactions/{transactionId}` - User's purchase history
- `swaps/{swapId}` - User's swap exchange history

---

### `items` Collection
Stores thrift item listings for sale.

```json
{
  "itemId": "item_unique_id",
  "sellerId": "seller_firebase_uid",
  "sellerName": "Seller Name",
  "title": "Vintage Denim Jacket",
  "description": "Blue denim jacket in excellent condition",
  "price": 25.99,
  "condition": "Excellent",
  "category": "Tops",
  "size": "Medium",
  "color": "Blue",
  "imageUrls": ["https://...", "https://..."],
  "isAvailable": true,
  "createdAt": "2024-01-15T10:30:00Z",
  "updatedAt": "2024-01-20T14:45:00Z"
}
```

**Queries:**
- Get all available items: `where isAvailable == true`
- Get items by seller: `where sellerId == "user_id"`

---

### `transactions` Collection
Stores purchase transaction records.

```json
{
  "transactionId": "transaction_unique_id",
  "buyerId": "buyer_firebase_uid",
  "sellerId": "seller_firebase_uid",
  "itemId": "item_unique_id",
  "itemTitle": "Vintage Denim Jacket",
  "price": 25.99,
  "status": "Completed",
  "createdAt": "2024-01-15T10:30:00Z",
  "completedAt": "2024-01-16T14:45:00Z"
}
```

**Statuses:** `Pending`, `Completed`, `Cancelled`

**Queries:**
- Get buyer's transactions: `where buyerId == "user_id" order by createdAt desc`
- Get seller's transactions: `where sellerId == "user_id" order by createdAt desc`

---

### `swapItems` Collection
Stores items available for peer-to-peer swapping (non-monetary exchange).

```json
{
  "swapItemId": "swap_item_unique_id",
  "userId": "user_firebase_uid",
  "userName": "User Name",
  "title": "Designer Handbag",
  "description": "Leather designer handbag",
  "condition": "Good",
  "category": "Accessories",
  "size": "",
  "color": "Black",
  "imageUrls": ["https://...", "https://..."],
  "isAvailable": true,
  "createdAt": "2024-01-15T10:30:00Z",
  "updatedAt": "2024-01-20T14:45:00Z"
}
```

**Queries:**
- Get all available swap items: `where isAvailable == true`
- Get user's swap items: `where userId == "user_id"`

---

### `swapRequests` Collection
Stores requests to exchange items between users.

```json
{
  "requestId": "request_unique_id",
  "requesterId": "requester_firebase_uid",
  "requesterName": "Requester Name",
  "recipientId": "recipient_firebase_uid",
  "recipientName": "Recipient Name",
  "requesterItemId": "swap_item_id",
  "requesterItemTitle": "Designer Handbag",
  "requestedItemId": "swap_item_id",
  "requestedItemTitle": "Vintage Denim Jacket",
  "status": "Pending",
  "message": "Would love to swap for this jacket!",
  "createdAt": "2024-01-15T10:30:00Z",
  "respondedAt": "2024-01-16T14:45:00Z"
}
```

**Statuses:** `Pending`, `Accepted`, `Declined`, `Completed`

**Queries:**
- Get user's sent requests: `where requesterId == "user_id"`
- Get user's received requests: `where recipientId == "user_id"`

---

## Security Rules

All Firestore access is governed by comprehensive security rules in `firestore.rules`. Key principles:

### Authentication
- All operations require `request.auth.uid` to be present
- Unauthenticated users cannot read or write any data

### Data Access Control
- **Users**: Can only read/write their own profile
- **Items**: All users can read available items; only sellers can manage their own listings
- **Transactions**: Only involved buyer/seller can access
- **Swap Items**: All users can read available items; only owner can manage
- **Swap Requests**: Only involved requester/recipient can access

### Write Validation
- Document IDs must match the data payload
- Users can only set their own UID in user documents
- Sellers are automatically verified via their request context

---

## Android Integration

### Dependencies
The app uses the following Firebase SDKs (managed via Firebase BoM):
- `firebase-auth-ktx` - Authentication
- `firebase-firestore-ktx` - Firestore database

See `app/build.gradle.kts` for complete dependency configuration.

### Architecture

The app implements a clean architecture with separation of concerns:

1. **Firebase Helpers** (`firebase/` package)
   - `FirebaseAuthHelper.kt` - Firebase Authentication operations
   - `FirestoreHelper.kt` - Firestore database operations

2. **Repositories** (`repository/` package)
   - `AuthRepository.kt` - Authentication business logic
   - `FirestoreRepository.kt` - Database business logic
   - Wrap Firebase operations with error handling

3. **ViewModels** (`viewmodel/` package)
   - `AuthViewModel.kt` - Authentication state management
   - `ItemViewModel.kt` - Item browsing state management
   - `SwapViewModel.kt` - Swap feature state management
   - Expose UI state via LiveData

4. **Data Models** (`data/models/` package)
   - `User.kt` - User profile model
   - `Item.kt` - Item listing model
   - `Transaction.kt` - Transaction record model
   - `SwapItem.kt` - Swap item model
   - `SwapRequest.kt` - Swap request model

### Typical Data Flow

```
UI (Activity/Fragment)
    ↓
ViewModel (Manages state)
    ↓
Repository (Business logic, error handling)
    ↓
Firebase Helper (SDK operations)
    ↓
Firebase Services (Auth/Firestore)
```

---

## Configuration Instructions

### Prerequisites
1. Firebase Project must be created on Firebase Console
2. Enable Firebase Authentication with Email/Password provider
3. Enable Cloud Firestore in production mode
4. Download `google-services.json` from Firebase Console
5. Place `google-services.json` in the `app/` directory

### Deploying Security Rules

To deploy Firestore security rules:

1. Install Firebase CLI
2. Navigate to the project root
3. Run: `firebase deploy --only firestore:rules`

Or deploy via Firebase Console:
1. Go to Firestore → Rules
2. Replace rules with content from `backend/firestore.rules`
3. Click "Publish"

### Gradle Configuration

The app's `build.gradle.kts` includes:
- Google Services plugin for Firebase integration
- Firebase BoM for dependency management
- Firebase Auth and Firestore SDKs

---

## Real-Time Data Synchronization

### LiveData Observers

ViewModels expose LiveData for UI observation:

```kotlin
viewModel.allItems.observe(this) { items ->
    // Update UI when items change
    adapter.submitList(items)
}
```

### Firestore Snapshots

Firestore automatically syncs changes in real-time:
- When a user purchases an item, `isAvailable` updates immediately
- All other users see the item marked as unavailable
- No manual refresh needed

---

## Error Handling

All Repository methods return `Result<T>` for proper error handling:

```kotlin
val result = repository.login(email, password)
result.onSuccess { user ->
    // Handle successful login
}
result.onFailure { exception ->
    // Display error to user
}
```

ViewModels expose error messages via LiveData:

```kotlin
viewModel.errorMessage.observe(this) { message ->
    if (message != null) {
        showErrorDialog(message)
    }
}
```

---

## Best Practices

1. **Authentication Checks**: Always verify `request.auth.uid` exists
2. **Data Validation**: Validate all writes in security rules
3. **Indexing**: Firestore automatically creates indexes for queries
4. **Offline Support**: Firestore provides offline caching out of the box
5. **Error Handling**: Always handle Result failures gracefully
6. **Separation of Concerns**: Never access Firebase directly from UI; use Repositories
7. **LiveData Observation**: Always unsubscribe LiveData when fragments/activities are destroyed

---

## Troubleshooting

### Common Issues

**"Permission denied" errors in Firestore**
- Check that `google-services.json` is in the correct location (`app/`)
- Verify security rules allow the operation
- Ensure user is authenticated

**Items not syncing in real-time**
- Verify Firestore collection structure matches data models
- Check that `isAvailable` field is present in item documents
- Review Firestore queries match the collection structure

**Firebase not initializing**
- Ensure `google-services.json` is valid
- Check that Google Services plugin is applied in `app/build.gradle.kts`
- Verify Firebase project exists and is active

---

## Next Steps

1. Create Firebase project on Firebase Console
2. Enable Authentication (Email/Password)
3. Create Firestore database in production mode
4. Download and place `google-services.json` in `app/` directory
5. Deploy security rules via Firebase CLI or Console
6. Test authentication flow with LoginActivity
7. Test data sync with DashboardActivity


