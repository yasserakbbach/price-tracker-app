# price-tracker-app

# Description
**PriceTrackerApp** is a sleek, responsive Android application built to track live stock prices in real-time. Developed leveraging modern Android standards with Kotlin and Jetpack Compose, the project emphasizes a smooth user experience and maintainable architecture.

**Key Features:**
- **Real-Time Data:** Live-updating stock list powered by `WebSockets`.
- **Modern UI:** Built entirely using Jetpack Compose with optimized lists (`LazyColumn`).
- **Resilient State Management:** Smooth handling of loading and error states on detailed stock screens.
- **Deep Linking:** Open directly to specific stocks using `stocks://symbol/{symbol}`.
- **Tested & Reliable:** Confirmed behavior backed by Compose UI and Unit tests.

# Demo
https://github.com/user-attachments/assets/f1877647-2cc8-487c-899c-a5c27fc30ea0


# Demo deeplink - `stocks://symbol/{symbol}`
https://github.com/user-attachments/assets/2e1a66c4-87f6-46f1-b388-9e8592cfa80d

# Stock Details - Error and Loading
| Loading | Error |
| :--- | :--- |
| <img width="1344" height="2992" alt="image" src="https://github.com/user-attachments/assets/845ca19b-1ee7-4271-a68d-d68f21da606a" /> | <img width="1344" height="2992" alt="image" src="https://github.com/user-attachments/assets/1a9e391a-1b50-48b6-aa94-9feb43ebac0f" /> |

# Bonus
- Compose UI tests or unit tests ✅
- Deep link: `stocks://symbol/{symbol}` that opens the details screen ✅
- Error and loading for stock details screen ✅
