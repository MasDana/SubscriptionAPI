package com.dana;
CREATE TABLE `shipping_addresses` (
        `id` INTEGER PRIMARY KEY,
        `customer` INTEGER NOT NULL,
        `title` TEXT NOT NULL, /* nama dari alamat, misalnya: rumah, kantor, rumah 2*/
        `line1` TEXT NOT NULL,
        `line2` TEXT,
        `city` TEXT NOT NULL,
        `province` TEXT NOT NULL,
        `postocde` TEXT NOT NULL
);
public class shippingAddresses {

}
