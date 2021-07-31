CREATE TABLE user_blog(
    id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    cpf VARCHAR(11) NOT NULL,
    password VARCHAR(50) NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE post(
    id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    description VARCHAR(200) NOT NULL,
    image VARCHAR(200) NOT NULL,
    link VARCHAR(200) NOT NULL,
    user_blog_id BIGINT(20) NOT NULL,
    FOREIGN KEY (user_blog_id) REFERENCES user_blog(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE comment(
    id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    comment VARCHAR(200) NOT NULL,
    user_blog_id BIGINT(20) NOT NULL,
    FOREIGN KEY (user_blog_id) REFERENCES user_blog(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE album(
    id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    user_blog_id BIGINT(20) NOT NULL,
    FOREIGN KEY (user_blog_id) REFERENCES user_blog(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;