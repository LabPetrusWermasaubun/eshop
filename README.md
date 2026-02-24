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