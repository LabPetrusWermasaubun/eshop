REFLECTION 1 
1. Selama proses pengerjaan fitur2 kemarin, aku ngerasa kodingannya udah cukup bersih dan rapi karena udah
   nerapin prinsip meaningful names, jadi siapa pun yang baca kodenya pasti langsung paham kalau productName
   itu buat nama produk. Penggunaan Thymeleaf juga ngebantu banget buat nerapin prinsip
   DRY (Don't Repeat Yourself), soalnya kita nggak perlu nulis baris tabel berulang-ulang,
   cukup pakai satu perulangan aja. Dari aspek keamanan, aku udah ngerasa lebih aman karena Spring Boot sama
   Thymeleaf udah otomatis ngehandle celah bahaya kayak XSS dan CSRF, plus tambahan konfirmasi hapus buat jaga-jaga
   kalau user salah klik. Tapi, jujur masih ada yang harus diperbaiki, terutama soal keamanan ID produk di URL yang
   masih pakai angka biasa (raw ID) yang rawan ditebak orang. Ke depannya, aku harus lebih teliti buat nerapin
   validasi di sisi backend pakai @Valid biar nggak ada data bodong yang masuk, sekaligus nyoba pakai UUID supaya
   aplikasi jadi lebih solid dan nggak gampang dioprek lewat URL


REFLECTION 2
1. Refleksi Unit Testing dan Code Coverage
   Menulis unit test memberikan rasa aman karna kita punya pengaman saat memodifikasi kode,
   tapi jumlah test yang ideal bukan ditentukan oleh angka pasti, melainkan oleh seberapa banyak
   jalur logika (skenario positif dan negatif) yang berhasil dicakup. Kode coverage memang bantu
   kita memantau berapa banyak baris kode yang udh diuji, tapi penting untuk diingat bahwa coverage 100%
   bukanlah jaminan mutlak bebas bug, karna metrik tersebut hanya mencatat kode yang dijalankan tanpa
   bisa menjamin kebenaran logika atau ketahanan sistem terhadap input yang tidak terduga

2. Analisis Clean Code pada Functional Test
   Membuat class functional test baru dengan menyalin setup dan variabel yang sama dari class sebelumnya
   akan menurunkan kualitas kode karena melanggar prinsip DRY (Don't Repeat Yourself), sehingga menimbulkan
   duplikasi yang menyulitkan pemeliharaan di masa depan. Masalah kode yg duplikat ini bikin kita harus
   mengubah banyak file sekaligus jika ada perubahan konfigurasi, jadinya solusi yang lebih bersih adalah
   dengan menerapkan Base Class untuk menyimpan prosedur setup yang umum atau menggunakan Page Object Model (POM)
   guna memisahkan logika interaksi elemen web dari skenario pengujian agar kode lebih mudah dibaca

REFLECTION 3
1. Selama ngerjain exercise ini, ada beberapa isu code quality yang sempat muncul dan saya perbaiki, salah satunya 
   warning dari static analysi seperti unused import, method yang terlalu panjang, dan duplikasi logic di beberapa 
   class, selain itu ada juga masalah terkait konfigurasi port yang hardcoded di 8080, yang akhirnya 
   saya ubah jadi server.port=${PORT:8080} supaya lebih fleksibel dan sesuai best practice cloud deployment. Strategi 
   saya waktu memperbaiki isu2 tersebut adalah membaca report dari tools quality secara detail, lalu memperbaiki satu
   per satu mulai dari yang paling kritis (bug dan vulnerability), baru kemudian ke code smell dan minor warning. Saya 
   juga mencoba melakukan refactor kecil seperti memecah method besar jadi beberapa method yang lebih spesifik agar lebih 
   sesuai prinsip clean code dan SOLID

2. Kalau dilihat dari workflow CI/CD yang sudah dibuat di GitHub, menurut saya implementasinya sudah cukup memenuhi 
   definisi Continuous Integration dan Continuous Deployment, dimana setiap ada push atau pull request ke branch tertentu, 
   pipeline otomatis menjalankan build dan test, jadi integrasi kode benar-benar dicek secara otomatis tanpa harus 
   dijalankan manual. Selain itu, hasil build juga dianalisis dengan tools code quality sebelum lanjut ke tahap 
   deployment, sehingga deployment tidak akan jalan kalau ada error di tahap sebelumnya. Untuk bagian Continuous 
   Deployment, setiap perubahan di branch utama langsung kedeploy ke environment production, sedangkan branch staging 
   deploy ke environment staging, jadi alurnya sudah otomatis dan konsisten. Walaupun masih bisa ditingkatkan, namun 
   secara konsep dasar CI/CD menurut saya sudah terpenuhi


REFLECTION 3
1. Prinsip SOLID apa saja yang diterapkan pada project ini? 
a) Single Responsibility Principle (SRP) - setiap class hanya memiliki satu tanggung jawab
=> penerapan dalam projek:
- CarController -> hanya menangani HTTP request/response
- CarServiceImpl -> hanya menangani bisnis logic (misalnya validasi dan generate UUID)
- CarRepository -> hanya menangani penyimpanan dan pengambilan data
- InMemoryCarRepository -> hanya implementasi penyimpanan data di memory
=> yang dilakukan:
- menggunakan interface pada service dan repository
- ganti dependency langsung ke implementasi menjadi dependency ke abstraksi, dimana sebelumnya controller 
  langsung menggunakan class konkret seperti CarServiceImpl, diubah jadi controller menggunakan interface CarService
- memisahkan tanggung jawab antara controller, service dan repository
- hapus desain yang ga sesuai seperti pewarisan controller
- ubah metode repository supaya tidak membocorkan detail implementasi, dimana repository sebelumnya balikin iterator, 
  yang indikasikan detail internal struktur data, sekarang repository balikin List<Car> bahkan balikin salinan data (new ArrayList<>(carData)), 
  jadinya struktur penyimpanan internal tetap tersembunyi dan layer lain ga bisa memodifikasi data secara langsung

b) Open/Closed Principle (OCP) - class terbuka untuk dikembangkan, tapi tertutup untuk dimodifikasi
=> penerapan dalam projek:
- pakai interface CarRepository, InMemoryCarRepository implement CarRepository
=> jika suatu saat mau ganti penyimpanan dari memory ke database (misal jpa), tinggal buat public class JpaCarRepository 
   implements CarRepository, tanpa harus ubah CarServiceImpl

c) Liskov Substitution Principle (LSP)
Subclass harus bisa gantiin superclass tanpa merusak sistem
=> penerapan dalam project:
- CarServiceImpl implement CarService
- semua method di CarServiceImpl sesuai dengan kontrak CarService
=> artinya, kalau kita ganti implementasi service pakai class lain yang juga implement CarService, 
   sistem tetep berjalan dengan benar
d) Interface Segregation Principle (ISP) - Interface ga boleh memaksa class mengimplementasikan method yang tidak dibutuhkan
=> penerapan dalam project:
- interface CarService cuma berisi method yang emang dibutuhi untuk operasi CRUD
- interface CarRepository cuma berisi method yang berkaitan sama data persistence
- tidak ada method yang tidak relevan dipaksakan ke implementasi
e) Dependency Inversion Principle (DIP) - highlevel modul tidak boleh bergantung pada lowlevel modul, keduanya harus bergantung sama abstraksi
=> penerapan dalam project:
- CarController bergantung pada CarService (interface), bukan CarServiceImpl
- CarServiceImpl bergantung pada CarRepository (interface), bukan InMemoryCarRepository
=> contoh yang bener:
- private final CarService carService;

1. Apa keuntungan menerapkan SOLID pada project ini?
=> Menerapkan SOLID memberikan beberapa keuntungan penting:
a) Kode lebih gampang dikembangin
   contoh:
   kalau mau ganti penyimpanan dari inmemory ke database, cukup buat class baru
   JpaCarRepository implements CarRepository, tanpa perlu ubah CarServiceImpl atau CarController, bisa kurangin resiko bug saat pengembangan fitur baru
b) Kode lebih mudah dites (testable)
   karena gunain interface, kita bisa bisa mock, contohnya class MockCarRepository implements CarRepository, 
   jadinya CarServiceImpl bisa diuji tanpa perlu storage yang real, tanpa SOLID, ujianya pasti akan lebih sulit 
   karena class saling bergantung langsung
c) Kode lebih terstruktur dan jelas tanggung jawabnya
karena:
- controller -> cuma HTTP
- service -> cuma business logic
- repository -> cuma data access
struktur jadi lebih rapi dan mudah dipahami
d) Mengurangi coupling (ketergantungan langsung)
   karena pakai interface, perubahan di satu bagian tidak merusak bagian lain, ini sangat penting untuk sistem yang berkembang

3) Apa kerugian tidak menerapkan SOLID?
=> Jika SOLID tidak diterapkan, beberapa masalah bisa muncul:
a) Tight coupling (ketergantungan tinggi)
contoh:
- kalau CarController langsung menggunakan private CarServiceImpl carService, maka saat ingin 
  mengganti implementasi service, kita harus ubah controller juga ini bikin sistem jadi sulit dikembangin
b) Sulit ganti teknologi
- kalau CarServiceImpl langsung bergantung pada private InMemoryCarRepository repository, maka pas mau pindah 
  ke database, kita harus ubah banyak file, tanpa SOLID, sistem jadi ga fleksibel
c) Class jadi terlalu kompleks
- kalau repository juga menangani generate UUID, validasi data, business rules, maka satu class punya banyak tanggung jawab, 
  jadinya sulit dibaca, sulit diuji, mudah kena bug
d) Sulit diuji
- tanpa interface, kita tidak bisa buat mock dengan mudah, testingnya jadi lama, kompleks dan ga terisolasi