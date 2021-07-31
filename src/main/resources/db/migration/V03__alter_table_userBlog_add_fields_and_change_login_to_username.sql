ALTER TABLE user_blog RENAME COLUMN login TO username;
ALTER TABLE user_blog ADD COLUMN `user_blog_role` ENUM('USER','ADMIN') AFTER `username`,
                      ADD COLUMN `locked` BOOLEAN NOT NULL DEFAULT false AFTER `user_blog_role`,
                      ADD COLUMN `enabled` BOOLEAN NOT NULL DEFAULT true AFTER `locked`;
