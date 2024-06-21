# Back End API Sistem Pembayaran Subscriptions

Dibuat oleh 
Putu Devasya Aditya Widyadana - 2205551070 - PBO E

## Pengenalan

Program **API Sistem Pembayaran Subscriptions** adalah program yang memungkinkan pengguna untuk terhubung dan memanipulasi basis data dari **subscriptions**.


## Batasan Program

-   Hanya _request method_ GET, POST, PUT, dan DELETE yang dizinkan.

![Request method not allowed](img/Validate-1.png "Request method not allowed")

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

![Postman API Key Configuration](img/Postman-API-Key-Configuration.png "Postman API Key Configuration")

Berikut pesan yang dikirimkan jika pengguna tidak menggunakan _API Key_.

![Select users](img/Authorization.png "Select users")



#### GET

Metode GET digunakan untuk mendapatkan data dari server.

• GET /customers => daftar semua pelanggan
• GET /customers/{id} => informasi pelanggan dan alamatnya
• GET /customers/{id}/cards => daftar kartu kredit/debit milik pelanggan
• GET /customers/{id}/subscriptions => daftar semua subscriptions milik
pelanggan
• GET /customers/{id}/subscriptions?subscriptions_status={active, cancelled,
non-renewing} => daftar semua subscriptions milik pelanggan yg berstatus
aktif / cancelled / non-renewing

• GET /subscriptions => daftar semua subscriptions
• GET /subscriptions?sort_by=current_term_end&sort_type=desc => daftar
semua subscriptions diurutkan berdasarkan current_term_end secara
descending
• GET /subscriptions/{id} =>
• informasi subscription,
• customer: id, first_name, last_name,
• subscription_items: quantity, amount,
• item: id, name, price, type

• GET /items => daftar semua produk
• GET /items?is_active=true => daftar semua produk yg memiliki status aktif
• GET /items/{id} => informasi produk

#### POST

Metode POST digunakan untuk mengirimkan data ke server.
```
POST /customers => buat pelanggan baru
```
```
POST /subscriptions => buat subscription baru
```
```
POST /items => buat item baru
```

#### PUT

Metode PUT digunakan untuk mengubah data pada server.



```
PUT /customers/{id}
```

```
PUT /items/{id}
```

#### DELETE

Metode DELETE digunakan untuk menghapus data pada server.

```
DELETE /items/{id}
```
DELETE untuk mengubah status dalam tabel item yaitu status dalam kolom isActive menjadi **false**

```
DELETE /customers/{id}/cards/{id}
```
DELETE untuk menghapus informasi kartu kredit pelanggan jika isPrimary bernilai false
