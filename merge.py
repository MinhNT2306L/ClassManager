import os

# 1. Cấu hình các định dạng file muốn lấy
EXTENSIONS = {'.java', '.jsp', '.sql'}
# Tên file đầu ra
OUTPUT_FILE = "Tong_Hop_Source_Code.txt"
# Thư mục chứa code của bạn (để '.' nếu chạy script ngay tại thư mục dự án)
PROJECT_DIR = "."

def merge_code_files():
    with open(OUTPUT_FILE, "w", encoding="utf-8") as outfile:
        # Duyệt qua tất cả thư mục và file
        for root, dirs, files in os.walk(PROJECT_DIR):
            # Bỏ qua các thư mục không cần thiết để file nhẹ hơn (tùy chọn)
            if 'target' in root or '.git' in root or '.idea' in root:
                continue
                
            for file in files:
                # Kiểm tra định dạng file
                if any(file.endswith(ext) for ext in EXTENSIONS):
                    file_path = os.path.join(root, file)
                    
                    # Ghi tiêu đề file để dễ phân biệt trong TXT
                    outfile.write(f"\n{'='*50}\n")
                    outfile.write(f"FILE: {file_path}\n")
                    outfile.write(f"{'='*50}\n\n")
                    
                    try:
                        # Đọc nội dung file và ghi vào file tổng
                        with open(file_path, "r", encoding="utf-8", errors="ignore") as infile:
                            outfile.write(infile.read())
                            outfile.write("\n")
                    except Exception as e:
                        outfile.write(f"[LỖI ĐỌC FILE: {e}]\n")
                        
    print(f"Đã hoàn thành! Toàn bộ code đã được gộp vào: {OUTPUT_FILE}")

if __name__ == "__main__":
    merge_code_files()
