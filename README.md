# Tugas Kecil 1 IF2211 2024/2025

### Tentang Projek

IQ Puzzle Pro Solver adalah aplikasi Java yang dirancang untuk menyelesaikan permainan IQ Puzzle Pro menggunakan metode brute force. Projek ini disusun untuk memenuhi tugas  mata kuliah IF2211 Strategi Algoritma pada Semester 2 Tahun Akademik 2024/2025

---
### Persyaratan
- **JDK 23**: Pastikan kamu sudah menginstal JDK 23. Kamu bisa mengunduhnya di [situs resmi OpenJDK](https://jdk.java.net/23/).
- **JavaFX**: Program ini menggunakan JavaFX untuk tampilan antarmuka grafis. Pastikan JavaFX sudah terpasang dan dikonfigurasi dengan benar.

### Instalasi
1. **Instal JDK 23**
    - Unduh JDK 23 dari [situs resmi OpenJDK](https://jdk.java.net/23/).
    - Ikuti petunjuk instalasi sesuai dengan sistem operasi yang digunakan.

2. **Instal JavaFX**
    - Jika menggunakan JDK 23, JavaFX biasanya tidak terintegrasi secara default, jadi kamu perlu menginstalnya terpisah.
    - Kamu bisa mengunduh JavaFX dari [situs resmi Gluon](https://gluonhq.com/products/javafx/).

3. **Set Path JavaFX (jika diperlukan)**:
    - Jika JavaFX tidak terdeteksi secara otomatis, pastikan untuk mengonfigurasi *classpath* agar JavaFX bisa digunakan oleh aplikasi. Kamu bisa melakukannya dengan menambahkan *library* JavaFX ke dalam *module path* atau *classpath* sesuai dengan pengaturan di IDE atau terminal.

### Menjalankan Program
1. **Clone repository ini**
2. **Pergi ke direktori program di terminal**
```bash
cd ./tulisPathDisini
```
3. **Jalankan program**
   - Pastikan kamu sudah mengganti "path/to/javafx-sdk-23/lib" menjadi path JavaFX
```bash
java --module-path "path/to/javafx-sdk-23/lib" --add-modules javafx.controls,javafx.fxml -jar bin/IQPuzzleProSolver.jar
