import os

# 1. Cấu hình các định dạng file muốn lấy (Đã thêm .yml, .xml, .txt)
EXTENSIONS = {'.java', '.jsp', '.sql', '.yml', '.xml', '.txt'}

# Tên file đầu ra
OUTPUT_FILE = "Tong_Hop_Source_Code.txt"
# Thư mục chứa code của bạn
PROJECT_DIR = "."

def merge_code_files():
    print(f"Đang quét các file có đuôi: {EXTENSIONS} ...")
    
    with open(OUTPUT_FILE, "w", encoding="utf-8") as outfile:
        # Duyệt qua tất cả thư mục và file
        for root, dirs, files in os.walk(PROJECT_DIR):
            # Bỏ qua các thư mục rác
            if 'target' in root or '.git' in root or '.idea' in root or 'node_modules' in root:
                continue
                
            for file in files:
                # QUAN TRỌNG: Bỏ qua chính file đầu ra để không bị đọc lặp
                if file == OUTPUT_FILE:
                    continue

                # Kiểm tra định dạng file
                if any(file.endswith(ext) for ext in EXTENSIONS):
                    file_path = os.path.join(root, file)
                    
                    # Ghi tiêu đề file
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
                        
    print(f"Xong! File đã được lưu tại: {os.path.abspath(OUTPUT_FILE)}")

if __name__ == "__main__":
    merge_code_files()