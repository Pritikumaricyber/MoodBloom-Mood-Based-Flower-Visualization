CREATE DATABASE moodbloom_db;

USE moodbloom_db;

CREATE TABLE mood_history (
    id INT AUTO_INCREMENT PRIMARY KEY,
    mood VARCHAR(100),
    flower_type VARCHAR(100),
    date_time DATETIME
);
