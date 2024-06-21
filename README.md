# Back End API Sistem Pembayaran Subscriptions

Dibuat oleh 
Putu Devasya Aditya Widyadana - 2205551070 - PBO E

## Pengenalan

Program **API Sistem Pembayaran Subscriptions** adalah program yang memungkinkan pengguna untuk terhubung dan memanipulasi basis data dari **subscriptions**.


## Batasan Program

-   Hanya _request method_ GET, POST, PUT, dan DELETE yang dizinkan.
-   Tidak diperkenankan kesalahan dalam penulisan nama tabel, berikut daftar tabel dengan penulisan yang tepat:

```
cards
customer
item
shippingAddresses
subscriptionItem
subscriptions
```
-   ID merupakan bilangan bulat dari 1 sampai batas kolom pada tabel tertentu.

- Parameter pada URL harus sesuai dengan ketentuan




## Penggunaan

Berikut adalah langkah-langkah untuk menjalankan program ini:
#### Perisapan
Untuk menggunakan API ini silahkan jalankan com.dana.data.Main. Selanjutnya API sudah bisa diakses pada :

```
localhost:9071
```

#### Otorisasi

Program ini menggunakan API _Key_ untuk otorisasi atau _authorization_. Berikut adalah API _Key_ yang digunakan untuk otorisasi:

```
key : api-key
value : dana_240024200571
```

Jika menggunakan aplikasi Postman, mohon masukkan key dan value tersebut pada bagian authorization dengan ketentuan sebagai berikut:

![alt text](<img/Screenshot (450).png>)

Berikut pesan yang dikirimkan jika pengguna tidak menggunakan _API Key_.

![alt text](<img/Screenshot (445).png>)

## Tes API Menggunakan Postman

#### GET

Metode GET digunakan untuk mendapatkan data dari server.

```
GET /customer 
```
Perintah GET untuk mendapatkan seluruh info dalam tabel **customer**

![alt text](<img/Screenshot (418).png>)

```
GET /customer/{id} 
```
Perintah GET untuk mendapatkan seluruh info salah satu id dalam tabel **customer**

![alt text](<img/Screenshot (419).png>)

```
GET /customers/{id}/cards 
```
Perintah GET untuk mendapatkan seluruh info salah satu customer berdasarkan id dengan detail cards yang dimiliki

![alt text](<img/Screenshot (422).png>)

```
GET /customers/{id}/subscriptions 
```
Perintah GET untuk mendapatkan seluruh info salah satu customer berdasarkan id dengan detail subscriptions yang dimiliki

![alt text](<img/Screenshot (421).png>)

```
GET /customers/{id}/subscriptions?subscriptions_status={active, cancelled,
non-renewing}
```
Perintah GET untuk mendapatkan seluruh info salah satu customer berdasarkan id dengan detail subscriptions yang dimiliki, difilter berdasarkan subscriptions_status (active, cancelled, non-renewing)

![alt text](<img/Screenshot (424).png>)

```
GET /subscriptions
```
Perintah GET untuk mendapatkan seluruh info dalam tabel subscriptions

![alt text](<img/Screenshot (426).png>)

```
GET /subscriptions?sort_by=current_term_end&sort_type=desc 
```
Perintah GET untuk mendapatkan seluruh info dalam tabel subscriptions dengan pengurutan berdasarkan current_term_end dan tipe pengurutan desc

![alt text](<img/Screenshot (427).png>)

```
GET /subscriptions/{id} 
```
Perintah GET untuk mendapatkan info salah satu subscription berdasarkan id

![alt text](<img/Screenshot (428).png>)

![alt text](<img/Screenshot (429).png>)
```
GET /items 
```
Perintah GET untuk mendapatkan seluruh info dalam tabel items

![alt text](<img/Screenshot (430).png>)
```
GET /items?is_active=true 
```
Perintah GET untuk mendapatkan seluruh info dalam tabel items yang memiliki is_active bernilai true

![alt text](<img/Screenshot (431).png>)

```
GET /items/{id}
```
Perintah GET untuk mendapatkan info salah satu item berdasarkan id

![alt text](<img/Screenshot (432).png>)

#### POST

Metode POST digunakan untuk mengirimkan data ke server.
```
POST /customers
```
Perintah POST untuk membuat customer baru

![alt text](<img/Screenshot (434).png>)

![alt text](<img/Screenshot (435).png>)
```
POST /subscriptions 
```
Perintah POST untuk membuat subscription baru

![alt text](<img/Screenshot (437).png>)

![alt text](<img/Screenshot (438).png>)


```
POST /items
```
Perintah POST untuk membuat item baru

![alt text](<img/Screenshot (439).png>)

![alt text](<img/Screenshot (440).png>)



#### PUT

Metode PUT digunakan untuk mengubah data pada server.
```
PUT /customers/{id}
```
Perintah PUT untuk mengubah data customer berdasarkan id

![alt text](<img/Screenshot (446).png>)

![alt text](<img/Screenshot (449).png>)

```
PUT /item/{id}
```
Perintah PUT untuk mengubah data item berdasarkan id

![alt text](<img/Screenshot (447).png>)

![alt text](<img/Screenshot (448).png>)

#### DELETE

Metode DELETE digunakan untuk menghapus data pada server.

```
DELETE /items/{id}
```
Perintah DELETE untuk mengubah status item dalam tabel menjadi tidak aktif (isActive=false) berdasarkan id

![alt text](<img/Screenshot (441).png>)

![alt text](<img/Screenshot (442).png>)


```
DELETE /customers/{id}/cards/{id}
```
Perintah DELETE untuk menghapus informasi kartu kredit pelanggan berdasarkan customer id dan card id jika isPrimary bernilai false

![alt text](<img/Screenshot (443).png>)

![alt text](<img/Screenshot (444).png>)

#### ERROR RESPONSE
Terdapat beberapa error respon yang sudah dibuat dalam server ini yaitu :

- Error response saat id entitas tidak tersedia ketika dicari

![alt text](<img/Screenshot (451).png>)

- Error response saat memperbarui data, namun data yang ingin ditambahkan tidak sesuai / tidak lengkap

![alt text](<img/Screenshot (452).png>)

- Error respon saat akan menambah data, namun data yang ditambahkan tidak lengkap / tidak sesuai

![alt text](<img/Screenshot (454).png>)

- Error response satt metode yang digunakan tidak sesuai dengan metode yang tersedia

![alt text](<img/Screenshot (455).png>)




