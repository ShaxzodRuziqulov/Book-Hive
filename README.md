![Uploading image.png…]()


# BookHive

BookHive - bu onlayn kitob do'koni uchun ishlab chiqilgan backend loyihasi bo'lib, foydalanuvchilarga kitoblarni sotib olish, buyurtma berish va boshqarish imkoniyatini taqdim etadi.

## Texnologiyalar

Loyiha quyidagi texnologiyalar asosida qurilgan:
- **Java 17**
- **Spring Boot** (Spring MVC, Spring Security, Spring Data JPA)
- **PostgreSQL** (ma'lumotlar bazasi sifatida)
- **Hibernate** (ORM sifatida)
- **Swagger** (API hujjatlari uchun)
- **Lombok** (kodni soddalashtirish uchun)

## O'rnatish

1. Loyihani klonlash:
   ```sh
   git clone https://github.com/yourusername/bookhive.git
   cd bookhive
   ```
2. `application.properties` faylida PostgreSQL uchun to'g'ri ma'lumotlarni kiriting.
3. Maven yordamida loyihani qurish:
   ```sh
   mvn clean install
   ```
4. Loyihani ishga tushirish:
   ```sh
   mvn spring-boot:run
   ```

## API Endpoints

Loyiha quyidagi asosiy API'larni taqdim etadi:

### Foydalanuvchilar
- `POST /api/customers/register` - Yangi foydalanuvchi ro'yxatdan o'tkazish
- `POST /api/auth/login` - Tizimga kirish
- `GET /api/customers/{id}` - Foydalanuvchi ma'lumotlarini olish

### Buyurtmalar (Orders)
- `POST /api/order/create` - Yangi buyurtma yaratish
- `PUT /api/order/update/{id}` - Buyurtmani yangilash
- `GET /api/order/all` - Barcha buyurtmalarni olish
- `GET /api/order/{id}` - Ma'lum bir buyurtmani olish
- `DELETE /api/order/delete/{id}` - Buyurtmani o'chirish

### Kitoblar (Books)
- `POST /api/book/create` - Yangi kitob qo'shish
- `PUT /api/book/update/{id}` - Kitobni yangilash
- `GET /api/book/all` - Barcha kitoblarni olish
- `GET /api/book/{id}` - Kitob tafsilotlarini olish
- `DELETE /api/book/delete/{id}` - Kitobni o'chirish

## Xavfsizlik

- Spring Security orqali foydalanuvchilar autentifikatsiya va avtorizatsiya qilinadi.
- Parollar **BCrypt** yordamida shifrlanadi.

## Swagger (API Documentation)

Swagger yordamida API'larni qulay tekshirish mumkin:
```
http://localhost:8080/swagger-ui/index.html
```

## Muallif

Loyiha muallifi: **Shahzod**

Agar sizda taklif yoki savollar bo'lsa, bemalol bog'laning! 🚀

