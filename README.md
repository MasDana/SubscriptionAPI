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

![Table name not found](img/Validate-2.png "Table name not found")

-   ID merupakan bilangan bulat dari 1 sampai batas kolom pada tabel tertentu.

![Wrong ID format](img/Validate-3.png "Wrong ID format")

-   Parameter pada URL harus sesuai dengan ketentuan, untuk lebih detailnya pada [GET](#get) bagian **`localhost:8070/{table}?{params}`**

![Wrong ID format](img/Validate-4.png "Wrong ID format")

-   Format _request body_ yang diterima pada _request method_ POST dan PUT hanyalah JSON atau _JavaScript Object Notation_

![Wrong request body format](img/Validate-5.png "Wrong request body format")

## Penggunaan

Berikut adalah langkah-langkah untuk menjalankan program ini:

#### Persiapan



#### Otorisasi

Program ini menggunakan API _Key_ untuk otorisasi atau _authorization_. Berikut adalah API _Key_ yang digunakan untuk otorisasi:

```

```

Mohon masukkan _key_ tersebut pada bagian _request header_ dengan ketentuan sebagai berikut:

```

```

Atau pada aplikasi Postman, Anda dapat melakukan konfigurasi sebagai berikut:

!

Berikut pesan yang dikirimkan jika pengguna tidak menggunakan _API Key_.


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

```
PUT /items/{id}
```
Perintah PUT untuk mengubah data item berdasarkan id

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

