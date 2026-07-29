# CashTracker

A personal finance management app for Android — track spending, set budgets and savings goals, scan receipts, and get a clear picture of where your money's going.

📹 [Watch the demo video](https://drive.google.com/file/d/1ts5I-0xYuU8vEsKVz2YKonRUhgzCjQm4/view?usp=sharing)

## Features

- **Dashboard** — at-a-glance overview of your finances
- **Expense Tracking** — log and categorize spending
- **Budgeting** — set budgets, with built-in budgeting tips
- **Savings Goals** — track progress toward specific goals
- **Receipt Scanner** — scan receipts using on-device OCR (Google ML Kit text recognition)
- **Spending Analyzer & Tracker** — visualize spending patterns over time
- **Reports & Export** — generate and export financial reports
- **Currency Converter** — convert between currencies
- **Money Transfer Tracking**
- **Subscription Tracker** — keep tabs on recurring subscriptions
- **Bill Reminders**
- **Stock News** — stay current on market news
- **Personalized Recommendations**
- **Profile & Account Management**

## Tech Stack

- **Language:** Java
- **Platform:** Android (min SDK 24, target SDK 34)
- **Architecture:** Fragment-based navigation from a single `MainActivity`
- **Storage:** SQLite (via `SQLiteOpenHelper`)
- **OCR:** Google ML Kit Text Recognition (receipt scanning)
- **UI:** Material Components for Android, ConstraintLayout
- **Testing:** JUnit, Espresso
- **Build:** Gradle (Kotlin DSL)

## Getting Started

1. Clone the repo:
   ```bash
   git clone https://github.com/hashiributt/cashtracker-android.git
   ```
2. Open the project in **Android Studio**
3. Let Gradle sync and download dependencies
4. Run on an emulator or physical device (min Android 7.0 / API 24)

## Project Structure

```
app/src/main/java/com/example/cashtracker/
├── MainActivity.java              # Entry point, hosts navigation
├── LoginActivity.java             # Authentication
├── AppDatabaseHelper.java         # SQLite database schema/access
├── DashboardFragment.java
├── ExpensesFragment.java
├── BudgetFragment.java / BudgetTipsFragment.java
├── GoalFragment.java
├── ReceiptScannerFragment.java
├── SpendingAnalyzerFragment.java / SpendingTrackerFragment.java
├── ReportsFragment.java / ExportReportFragment.java
├── CurrencyConverterFragment.java
├── MoneyTransferFragment.java
├── SubscriptionTrackerFragment.java
├── RemindersFragment.java
├── StockNewsFragment.java
├── RecommendationsFragment.java
├── ProfileFragment.java
└── HelpFragment.java
```

## License

*Add your preferred license here (e.g. MIT).*
