INSERT INTO user_blog (id, name, username, password, cpf, user_blog_role, locked, enabled) VALUES
    (null, "Carlos", "carlos",  "$2a$10$qS2lc4w67W1HjQD/WAQfiOtE9TzzjA7UEwH/J/T0o6UbaqJ2f1P6y", "95542836011", "ADMIN", false, true);

INSERT INTO user_blog (id, name, username, password, cpf, user_blog_role, locked, enabled) VALUES
    (null, "Pedro", "pedro","$2a$10$.Z9Ehu7Kug5J4Mt1unY3E.gC8.XyDFKIHsVUt6bR5HdvHc/B7YS/e", "53823565052", "USER", false, true);

INSERT INTO user_blog (id, name, username, password, cpf, user_blog_role, locked, enabled) VALUES
    (null, "Pietra", "pietra", "$2a$10$KdfgMa.8n5ky.p8vUb.bGesS52wv4OdoOvMK21fbP7HHJWSMlMXaW", "69778901007", "USER", false, true);


INSERT INTO post (id, description, link, user_blog_id) VALUES
    (null, "Your AI pair programmer With GitHub Copilot, get suggestions for whole lines or entire functions right inside your editor.",
    "https://copilot.github.com/", 3);


INSERT INTO post (id, description, link, user_blog_id) VALUES
    (null, "Nokia XR20: celular 'indestrutível' promete longevidade no Android",
    "https://www.tecmundo.com.br/dispositivos-moveis/221902-xr20-novo-celular-nokia-promete-durabilidade-android.htm", 2);


INSERT INTO post (id, description, link, user_blog_id) VALUES
    (null, "Robô bípede consegue percorrer 5 mil metros; veja o vídeo",
    "https://www.tecmundo.com.br/ciencia/222153-robo-bipede-consegue-percorrer-5-mil-metros-veja-video.htm", 1);

INSERT INTO comment (id, comment, user_blog_id, post_id) VALUES
    (null, "Esse robô parece ser muito interessante!", 1, 3);

INSERT INTO comment (id, comment, user_blog_id, post_id) VALUES
    (null, "Sera que esse celular vai ser caro?", 2, 2);

INSERT INTO comment (id, comment, user_blog_id, post_id) VALUES
    (null, "Bora ver se ele vai realmente ajudar os programadores!", 3, 1);

