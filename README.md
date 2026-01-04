# 🎓 Class Management System & CTF Mini-game

Một hệ thống quản lý lớp học trực tuyến đơn giản kết hợp với trò chơi giải đố (CTF Challenge), được xây dựng bằng **Java Servlet, JSP và MySQL**. Dự án này tập trung vào việc xử lý logic thuần (không dùng Framework như Spring) để hiểu rõ cơ chế hoạt động của Web Server.

## 🚀 Tính năng chính

### 1. Phân quyền (Authentication & Authorization)
* **Role:** Hệ thống chia làm 2 quyền: **TEACHER** (Giáo viên) và **STUDENT** (Học sinh).
* **Login:** Cơ chế đăng nhập bảo mật, lưu trạng thái bằng `HttpSession`.
* **Security Filter:** Chặn truy cập trái phép (Học sinh không thể vào trang quản trị của Giáo viên).

### 2. Quản lý Lớp học (Core Features)
* **Giáo viên:**
    * Thêm, sửa, xóa học sinh.
    * Tạo bài tập (Assignments) và upload file đề bài.
    * Xem danh sách bài nộp của học sinh.
* **Học sinh:**
    * Xem danh sách bài tập.
    * Nộp bài làm (Upload file).
    * Cập nhật thông tin cá nhân.

### 3. 🔥 CTF Challenge (Trò chơi giải đố)
Đây là tính năng đặc biệt của dự án:
* **Giáo viên:** Upload một file văn bản (ví dụ: `bai tho.txt`) và đưa ra một gợi ý (Hint). Hệ thống **không** lưu đáp án vào Database mà chỉ lưu đường dẫn file.
* **Học sinh:** Dựa vào gợi ý để đoán tên file.
    * *Logic:* Hệ thống sẽ lấy input của học sinh + đuôi `.txt` để tìm file trên ổ cứng server.
    * Nếu tìm thấy file -> Trả về nội dung (Win).
    * Nếu không tìm thấy -> Báo sai.

---

## 🛠 Công nghệ sử dụng (Tech Stack)

* **Ngôn ngữ:** Java 17+ (Hỗ trợ Java 21/25).
* **Web Core:** Jakarta Servlet, JSP (JavaServer Pages).
* **Frontend:** HTML5, CSS3, Bootstrap 5, JSTL.
* **Database:** MySQL 8.0+.
* **Server:** Apache Tomcat 10.1.x.
* **Build Tool:** Maven.
* **IDE:** IntelliJ IDEA Ultimate.

---

## ⚙️ Hướng dẫn Cài đặt (Installation)

### Bước 1: Clone dự án
```bash
git clone [https://github.com/username/ClassManager.git](https://github.com/username/ClassManager.git)
cd ClassManager
