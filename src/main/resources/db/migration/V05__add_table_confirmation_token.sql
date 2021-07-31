CREATE TABLE confirmation_token(
    id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    token VARCHAR(255) NOT NULL,
    created_at DATETIME NOT NULL,
    expires_at DATETIME NOT NULL,
    confirmed_at DATETIME,
    user_blog_id BIGINT(20) NOT NULL,
    FOREIGN KEY (user_blog_id) REFERENCES user_blog(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;