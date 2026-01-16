# Fintech Desk UI Kit (Jetpack Compose)

A dark-mode-first **fintech / trading desk UI kit** built with **Jetpack Compose** and **Material 3**.

This project is **UI-first**: it focuses on layout, component design, and realistic screen flows using demo data.  
There is **no backend**, **no real trading**, and **no persistence** by design.

---

## ✨ What this is
- A reusable **UI kit** for fintech, trading, or portfolio apps
- A portfolio/demo project showcasing modern Compose patterns
- A starting point for future real apps

## 🚫 What this is not
- A production trading app
- A data-accurate market client
- A backend-connected system

---

## 🧱 Included Screens

- **Gallery** – UI kit showcase (components + screen links)
- **Portfolio** – Hero portfolio overview
- **Watchlist** – Saved assets list
- **Asset Detail** – Price, chart, stats, holdings, trade entry
- **Trade Ticket** – Buy / Sell ticket (Market & Limit demo)
- **Confirm Trade**
- **Success**
- **Activity** – Demo activity feed

---

## 🧩 Included Components

Located in `ui/components/`:

- `DeskCard` – Standard container card
- `TimeChip` – Timeframe selector chip
- `ChangePill`, `MiniChangeChip`, `PriceChip`
- `LineChart` – Canvas-based demo line chart

All components are **theme-aware**, **dark-mode-first**, and designed for reuse.

---

## 🗂 Project Structure

ui/
├─ components/ // Reusable UI components
├─ screens/ // Screen templates (portfolio, asset, trade, etc.)
├─ navigation/ // Routes + NavHost
├─ theme/ // Material 3 theme
data/
└─ TradeLogStore // Demo-only in-memory store

---

## 🧪 Demo Behaviour

- Prices, charts, and trades use **fake deterministic data**
- Trades are logged to an in-memory store for UI realism
- Navigation simulates a real trading flow

This is intentional to keep the focus on **design**, not logic.

---

## ▶️ Run the project

1. Open in **Android Studio**
2. Sync Gradle
3. Run the `app` module on an emulator or device

Most screens and components include `@Preview` functions for rapid inspection.

---

## 🧭 Recommended Start Point

The app launches into the **Gallery** screen, which acts as the UI kit index:
- Browse components
- Jump directly to any screen
- See how pieces fit together

---

## 📌 Notes

- Built with **Jetpack Compose + Material 3**
- Dark mode is the primary design target
- Code prioritises clarity and reusability over optimisation

---

## 📄 License

Use freely for personal projects, learning, or inspiration.
