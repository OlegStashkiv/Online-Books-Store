INSERT INTO books (id,title, author, isbn, price, description, cover_image, is_deleted)
VALUES
    (1,'Test Title First', 'Test Author First', '978-0262033848', 500, 'Test Description for Book 1', 'http://example.com/test.jpg', false),
    (2,'Test Title Second', 'Test Author Second', '978-0-9767736-6-5', 300, 'Test Description for Book 2', 'http://example.com/test.jpg', false);

INSERT INTO categories (id,name, description, is_deleted)
VALUES
    (10,'Test Category First', 'Test Description for Category First', false),
    (20,'Test Category Second', 'Test Description for Category Second', false);


INSERT INTO book_category (book_id, category_id)
VALUES
    (1, 10),
    (2, 20);