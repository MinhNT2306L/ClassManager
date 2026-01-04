# 🏫 HỆ THỐNG QUẢN LÝ LỚP HỌC

## 1. Introduction
Ứng dụng web quản lý lớp học trực tuyến, cho phép Giáo viên quản lý sinh viên, giao bài tập và tổ chức các Challenge dựa trên thao tác với file hệ thống. 
Dự án được xây dựng theo mô hình MVC sử dụng Java.

---

## 2. Functional Requirements

Hệ thống gồm 2 vai trò chính: **Giáo viên (TEACHER)** và **Sinh viên (STUDENT)**.

### 👤 User Management
Thông tin người dùng gồm: `username`, `password`, `fullname`, `email`, `phone`.

| Chức năng | Giáo viên | Sinh viên | Ghi chú |
| :--- | :---: | :---: | :--- |
| **Đăng nhập/Đăng xuất** | ✅ | ✅ | Sử dụng Session để lưu phiên làm việc. |
| **Xem danh sách User** | ✅ | ✅ | Xem danh sách tất cả thành viên trong hệ thống (Ẩn password). |
| **Xem chi tiết User** | ✅ | ✅ | Xem thông tin cụ thể của 1 người khác. |
| **Thêm/Sửa/Xóa SV** | ✅ | ❌ | Giáo viên có toàn quyền quản lý sinh viên. |
| **Cập nhật bản thân** | ✅ | ⚠️ | SV chỉ sửa được `email`, `phone`. **Không** được sửa `username`, `fullname`. |

### 📚 Assignments
| Chức năng | Giáo viên | Sinh viên | Ghi chú |
| :--- | :---: | :---: | :--- |
| **Giao bài tập** | ✅ | ❌ | Upload file đề bài (PDF/DOCX...). |
| **Xem list bài tập** | ✅ | ✅ | Sinh viên xem và tải file đề bài về. |
| **Nộp bài (Submit)** | ❌ | ✅ | Sinh viên upload file bài làm lên hệ thống. |
| **Xem bài đã nộp** | ✅ | ⚠️ | Giáo viên xem được bài của tất cả SV. SV chỉ xem được bài của mình. |

### 🧩Features
Mô phỏng một mini-game CTF (Capture The Flag) dạng Web/Misc.

* **Logic:**
    1.  **Giáo viên:**
        * Chuẩn bị 1 file `.txt` (nội dung là thơ, văn...).
        * Đặt tên file là đáp án (viết không dấu, cách nhau bởi khoảng trắng). Ví dụ: `bai tho.txt`.
        * Nhập "Gợi ý" (Hint) và Upload file lên.
        * **Yêu cầu hệ thống:** Chỉ lưu đường dẫn file và gợi ý vào Database. **KHÔNG lưu đáp án (tên file) vào Database.**
    2.  **Sinh viên:**
        * Xem gợi ý.
        * Nhập đáp án vào ô Input (Ví dụ nhập: `bai tho`).
        * Hệ thống kiểm tra: Lấy input ghép với đuôi `.txt` -> Kiểm tra xem file có tồn tại trên ổ cứng server không.
        * **Kết quả:**
            * Đúng: Đọc nội dung file `.txt` và hiển thị lên màn hình.
            * Sai: Báo lỗi "Sai đáp án".

---
