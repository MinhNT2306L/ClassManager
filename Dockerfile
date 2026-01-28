# --- Giai đoạn 1: Build file WAR bằng Maven ---
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app

# Copy file cấu hình maven trước để tận dụng cache
COPY pom.xml .
# (Tùy chọn) Tải dependency về trước để build nhanh hơn cho các lần sau
# RUN mvn dependency:go-offline

# Copy toàn bộ code nguồn vào
COPY . .

# Chạy lệnh build (tạo ra file WAR trong thư mục target)
RUN mvn clean package -DskipTests

# --- Giai đoạn 2: Chạy web bằng Tomcat ---
FROM tomcat:10.1-jdk17

# Xóa ứng dụng mặc định của Tomcat
RUN rm -rf /usr/local/tomcat/webapps/*

# COPY file WAR từ giai đoạn 1 sang giai đoạn 2
# Lưu ý: Sửa 'ClassManager-1.0-SNAPSHOT.war' đúng với tên file trong pom.xml của bạn
COPY --from=build /app/target/*.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
CMD ["catalina.sh", "run"]