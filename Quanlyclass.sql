-- 1. Xóa database cũ đi để làm lại cho sạch (Reset)
DROP DATABASE IF EXISTS Quanlyclass;

-- 2. Tạo lại Database
CREATE DATABASE Quanlyclass;
USE Quanlyclass;

-- 3. Tạo bảng USERS
CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       full_name VARCHAR(100) NOT NULL,
                       email VARCHAR(100),
                       phone VARCHAR(20),
                       role ENUM('TEACHER', 'STUDENT') NOT NULL
);

-- 4. Tạo bảng CHALLENGES (Trò chơi giải đố)
-- Lưu ý: Mình đã giữ lại cột file_path để code Java không bị lỗi
CREATE TABLE challenges (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            hint TEXT NOT NULL,
                            file_path VARCHAR(255) NOT NULL,
                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 5. Tạo bảng ASSIGNMENTS (Bài tập - MỚI)
CREATE TABLE assignments (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             title VARCHAR(255) NOT NULL,
                             description TEXT,
                             file_path VARCHAR(255), -- File đề bài giáo viên up lên
                             teacher_id BIGINT NOT NULL,
                             created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             FOREIGN KEY (teacher_id) REFERENCES users(id) ON DELETE CASCADE
);

-- 6. Tạo bảng SUBMISSIONS (Nộp bài - MỚI)
CREATE TABLE submissions (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             assignment_id BIGINT NOT NULL,
                             student_id BIGINT NOT NULL,
                             file_path VARCHAR(255) NOT NULL, -- File bài làm sinh viên nộp
                             submitted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             FOREIGN KEY (assignment_id) REFERENCES assignments(id) ON DELETE CASCADE,
                             FOREIGN KEY (student_id) REFERENCES users(id) ON DELETE CASCADE
);

-- 7. Thêm dữ liệu mẫu (Fake Data)
-- Tạo 1 Giáo viên
INSERT INTO users (username, password, full_name, role)
VALUES ('teacher1', 'teacher123', 'Nguyễn Văn A', 'TEACHER');

-- Tạo 1 Học sinh
INSERT INTO users (username, password, full_name, role)
VALUES ('student1', 'student123', 'Nguyễn Văn B', 'STUDENT');

-- Tạo thử 1 Bài tập mẫu
INSERT INTO assignments (title, description, teacher_id)
VALUES ('Java basic', 'làm bài 3', 1);