INSERT INTO books (id,title, author, isbn, price, description, cover_image, is_deleted)
VALUES
    (1,'Test Title 1', 'Test Author 1', '978-0262033848', 500, 'Test Description for Book 1', 'TestImage1.png', false),
    (2,'Test Title 2', 'Test Author 2', '978-0-9767736-6-5', 300, 'Test Description for Book 2', 'TestImage2.png', false);

INSERT INTO categories (id,name, description, is_deleted)
VALUES
    (1,'Test Category 1', 'Test Description for Category 1', false),
    (2,'Test Category 2', 'Test Description for Category 2', false);


INSERT INTO book_category (book_id, category_id)
VALUES
    (1, 1),
    (2, 2);