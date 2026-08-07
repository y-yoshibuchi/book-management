INSERT INTO authors (name, birth_date)
VALUES
    ('夏目漱石', '1867-02-09'),
    ('芥川龍之介', '1892-03-01'),
    ('太宰治', '1909-06-19'),
    ('森鴎外', '1862-02-17');

INSERT INTO books (title, price, publication_status)
VALUES
    ('吾輩は猫である', 700, 'PUBLISHED'),
    ('羅生門', 500, 'PUBLISHED'),
    ('文豪短編集', 1200, 'PUBLISHED'),
    ('未出版作品集', 900, 'UNPUBLISHED');

INSERT INTO book_authors (book_id, author_id)
VALUES
    (1, 1),
    (2, 2),
    (3, 1),
    (3, 2),
    (4, 3),
    (4, 4);
