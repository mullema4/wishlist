-- client --
INSERT INTO client(id, username)
VALUES(1, 'Vader');

-- wishlist --
INSERT INTO wishlist(id, client_id, wishes_order)
VALUES(10, 1, 0);

-- product --
INSERT INTO product(id, code)
VALUES(100, 'TIE Fighter');
INSERT INTO product(id, code)
VALUES(200, 'Death Star');
INSERT INTO product(id, code)
VALUES(300, 'Star Destroyer');

-- wishlist_products --
INSERT INTO wishlist_products(wishlist_id, products_id, products_order)
VALUES(10, 100, 2);
INSERT INTO wishlist_products(wishlist_id, products_id, products_order)
VALUES(10, 200, 0);
INSERT INTO wishlist_products(wishlist_id, products_id, products_order)
VALUES(10, 300, 1);