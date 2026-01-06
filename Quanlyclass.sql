-- 1. Xóa Database nếu đã tồn tại
DROP DATABASE IF EXISTS Quanlyclass;
-- 2. Create Database
CREATE DATABASE Quanlyclass;
USE Quanlyclass;

-- Bảng USERS
CREATE TABLE users (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
username VARCHAR(50) NOT NULL UNIQUE,
password VARCHAR(255) NOT NULL,
full_name VARCHAR(100) NOT NULL,
email VARCHAR(100),
phone VARCHAR(20),
role ENUM('TEACHER', 'STUDENT') NOT NULL
);

-- Bảng CHALLENGES
CREATE TABLE challenges (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
hint TEXT NOT NULL,
file_path VARCHAR(255) NOT NULL,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Bảng ASSIGNMENTS
CREATE TABLE assignments (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
title VARCHAR(255) NOT NULL,
description TEXT,
file_path VARCHAR(255),
teacher_id BIGINT NOT NULL,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
FOREIGN KEY (teacher_id) REFERENCES users(id) ON DELETE CASCADE
);

-- 6 Bảng SUBMISSIONS
CREATE TABLE submissions (
 id BIGINT AUTO_INCREMENT PRIMARY KEY,
assignment_id BIGINT NOT NULL,
student_id BIGINT NOT NULL,
file_path VARCHAR(255) NOT NULL,
submitted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
FOREIGN KEY (assignment_id) REFERENCES assignments(id) ON DELETE CASCADE,
FOREIGN KEY (student_id) REFERENCES users(id) ON DELETE CASCADE
);

-- 7. Thêm dữ liệu mẫu vào bảng USERS

INSERT INTO users (username, password, full_name, role)
VALUES ('teacher1', 'teacher123', 'Nguyễn Văn A', 'TEACHER');

INSERT INTO users (username, password, full_name, role)
VALUES ('student1', 'student123', 'Nguyễn Văn B', 'STUDENT');

INSERT INTO assignments (title, description, teacher_id)
VALUES ('Java basic', 'làm bài 3', 1);