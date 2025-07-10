# 🌸 MoodBloom – Mood-Based Flower Visualization using JavaFX

MoodBloom is a JavaFX-based desktop application that lets users select a mood and see a uniquely coded flower bloom on screen, styled to visually reflect that emotion. Each mood is mapped to a custom flower with a specific shape, color, and layout. User mood selections are also saved to a MySQL database for mood history tracking.

---

## ✨ Features

- 🎨 Custom flowers drawn using JavaFX shapes for each mood:
  - **Happy** – Bright rose-style flower 🌹
  - **Sad** – Drooping petals with soft colors 😢
  - **Angry** – Sharp, edgy petals with intense colors 😡
  - **Calm** – Balanced, soft tulip-style design 🧘
  - **Excited** – Multi-layered, glowing lavender flower 💜

- 💾 MySQL integration to store mood history with timestamp.
- 🧼 Option to clear mood history directly from within the app.
- 💻 Built using Java 23 and JavaFX SDK 24.0.1.

---

## 📁 Folder Structure

moodbloom/
├── lib/ # External libraries (JavaFX SDK, MySQL connector)
├── src/
│ ├── main/
│ │ └── Main.java # JavaFX main application class
│ ├── controllers/
│ │ └── MoodController.java # Mood display and DB logic
│ └── database/
│ └── DBConnection.java # MySQL DB connection setup
├── out/ # Compiled .class files
└── README.md # You're reading it!


---

## 🛠️ Requirements

- Java JDK 17+ (recommended: Java 23)
- JavaFX SDK 21+ (recommended: 24.0.1)
- MySQL server
- MySQL Connector/J (`mysql-connector-j-<version>.jar`)

---

## 🧩 Database Setup

```sql
CREATE DATABASE moodbloom_db;

USE moodbloom_db;

CREATE TABLE mood_history (
    id INT AUTO_INCREMENT PRIMARY KEY,
    mood VARCHAR(100),
    flower_type VARCHAR(100),
    date_time DATETIME
);





