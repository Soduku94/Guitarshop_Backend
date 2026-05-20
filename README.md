# GuitarShop Backend 

Đây là dự án Backend cho hệ thống quản lý cửa hàng đàn Guitar, được xây dựng trên nền tảng **Java Spring Boot**. Dự án cung cấp các API để giao tiếp với cơ sở dữ liệu và quản lý thông tin các loại đàn guitar trong kho.

## 🛠 Công nghệ sử dụng
Dự án được xây dựng với các công nghệ và thư viện sau:
- **Java 21**: Phiên bản Java mới nhất, mang lại hiệu suất cao.
- **Spring Boot**: Framework chính để phát triển ứng dụng (Sử dụng WebMVC).
- **Spring Data JPA / Hibernate**: Công cụ ORM để tương tác với cơ sở dữ liệu thay vì viết SQL thuần.
- **PostgreSQL**: Hệ quản trị cơ sở dữ liệu quan hệ mạnh mẽ.
- **Lombok**: Thư viện giúp giảm thiểu code boilerplate (tự động tạo getter, setter, constructor,...).
- **Maven**: Công cụ quản lý dự án và các thư viện (dependencies).

##  Tính năng chính (Dự kiến)
- Quản lý danh sách các loại đàn Guitar (Thêm, Xem, Sửa, Xóa).
- Mỗi cây đàn (`Guitar`) sẽ có các thông tin cơ bản:
  - `id`: Mã định danh (Tự động tăng)
  - `name`: Tên đàn
  - `brand`: Thương hiệu (Yamaha, Taylor, Fender,...)
  - `price`: Giá tiền
  - `quantity`: Số lượng tồn kho

## ⚙ Hướng dẫn cài đặt và chạy dự án

### 1. Yêu cầu hệ thống
- Máy tính đã cài đặt **Java 21** (JDK 21).
- Máy tính đã cài đặt **PostgreSQL** và đang chạy dịch vụ (Service).
- IDE khuyên dùng: IntelliJ IDEA, Eclipse, hoặc VS Code.

### 2. Cài đặt Cơ sở dữ liệu (Database)
1. Mở công cụ quản lý PostgreSQL (pgAdmin hoặc DBeaver, DataGrip).
2. Tạo một cơ sở dữ liệu trống có tên là: `guitar_shop`
3. Ứng dụng sẽ tự động tạo bảng (tables) khi chạy nhờ vào cấu hình `spring.jpa.hibernate.ddl-auto=update`.

### 3. Cấu hình ứng dụng
Mở file `src/main/resources/application.properties` và đảm bảo các thông số kết nối Database khớp với máy của bạn:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/guitar_shop
spring.datasource.username=postgres
spring.datasource.password=postgres # Thay bằng mật khẩu PostgreSQL của bạn
```

### 4. Chạy dự án
Bạn có thể chạy dự án bằng cách:
1. Mở file `GuitarShopBackendApplication.java` và ấn nút **Run** trong IDE của bạn.
2. Hoặc sử dụng Maven qua Terminal (Command Line):
   ```bash
   ./mvnw spring-boot:run
   ```
   *(Trên Windows có thể dùng `mvnw.cmd spring-boot:run`)*

Mặc định ứng dụng sẽ chạy ở cổng `8080`.

##  Cấu trúc thư mục chính
```text
src/main/java/com/example/guitarshop_backend/
 ├── entity/          # Chứa các lớp ánh xạ với CSDL (Ví dụ: Guitar.java)
 ├── repository/      # Chứa các Interface giao tiếp với Database (Ví dụ: GuitarRepository.java)
 ├── (Tương lai) controller/ # Chứa các REST APIs nhận request từ Frontend
 ├── (Tương lai) service/    # Chứa logic nghiệp vụ xử lý dữ liệu
 └── GuitarShopBackendApplication.java # File chạy chính
```

---
*Dự án đang trong quá trình nghiên cứu và phát triển.*
