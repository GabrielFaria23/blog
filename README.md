# Aplicação Blog

Esse projeto foi gerado utilizando o [Spring Initializr](https://start.spring.io/).


# Execução

Para seu desenvolvimento foi Utilizada a Ferramenta [Intellij IDEA Community Edition](https://www.jetbrains.com/pt-br/idea/download/#section=windows) .
Para execução é necessário ter o Banco de Dados MySQL instalado e em execução na maquina.
Após isso basta abrir o projeto em alguma IDE que suporta o Spring Boot, esperar a IDE fazer o dowload das dependências e executar o arquivo BlogApplication.

Para testar as requisições foi utilizada a ferramenta Postman. O processo de execução vai ser descrito a seguir com imagens das requisições.

O primeiro a se fazer é cadastrar um novo usuário utilizando o endpoint: http://localhost:8080/v1/usersBlog

![PostUser](https://user-images.githubusercontent.com/47676471/127918219-7d848ccc-4208-4c39-b8df-bde7f9aab846.png)

A requisição tem que ser do tipo POST e deve ser enviado no corpo da requisição um JSON como na imagem. 


> **Note:** EX:{
						    "name": "Felipe",
						    "username": "felipe",
						    "password": "123",
						    "cpf": "95542836011",
						    "userBlogRole": "ADMIN"
						}
						
Depois de cadastrar o Usuário é possível realizar seguintes requisições:
1. (GET) Recuperar usuário por ID: http://localhost:8080/v1/usersBlog/{id} 
2. (GET) Recuperar todos os usuários: http://localhost:8080/v1/usersBlog/ 


Depois de criar o usuário o proximo passo é fazer o login para ter acesso a algumas funcionalidades restritas. Para fazer o login faça uma requisição post para a url: http://localhost:8080/v1/login

![Login](https://user-images.githubusercontent.com/47676471/127918646-a233b8a0-b622-45d6-9377-794813b3aadc.png)

Utilizando postman selecione o radio button x-www-form-urlencoded e adicione as keys username e password passando seus respectivos valores, caso ambos estejam corretos a resposta será um token parecido com este:

> eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjYXJsb3MiLCJyb2xlcyI6W10sImlzcyI6Imh0dHA6Ly9
> sb2NhbGhvc3Q6ODA4MC92MS9sb2dpbiIsImV4cCI6MTYyNzkzMzMxMH0.4TnThl06zILOkabZtqKDoPO3l5S
> qwNHqB4-WHs5NOso

Depois de fazer o login você pode fazer o cadastro de posts. Para isso é necessário inserir a palavra "Bearer " + o token recebido na requisição anterior no parâmetro Authorization do Header, da seguinte maneira:

![headerToken](https://user-images.githubusercontent.com/47676471/127918675-27f629f7-89c2-4d3a-b6a1-b4e5976584b2.png)

com isso o sistema permite o cadastro de posts utilizando o caminho: http://localhost:8080/v1/posts enviando um body como este:

![postPosts](https://user-images.githubusercontent.com/47676471/127918708-af88b95c-f16c-4c27-b397-c58f9bbe977f.png)

>{
    "description": "Noticias x",
    "link": "www.google.com/..."
}

Utilizando a mesma URL porém fazendo uma requisição do tipo GET será retornado todos os posts publicados.
É possível deletar um post, caso ele seja do usuário logado, fazendo uma requisição DELETE para a url: http://localhost:8080/v1/posts/{idPost}

Qualquer usuário pode comentar em qualquer post enviando um POST na url: http://localhost:8080/v1/comments e passando o corpo da requisição com a seguir:

![postComment](https://user-images.githubusercontent.com/47676471/127918745-f4b3e090-06f6-462f-8306-09f30c497a10.png)

>{
    "comment": "comment by felipe!",
    "post": {
        "id": 2
    }
}

Obs: Usuario logado vai comentar no post 2

Caso o criador do comentario queira deletar seu comentario basta enviar uma requisição DELETE para a url: http://localhost:8080/v1/comments/{idComment} .

Também é possível cadastrar imagens no Post : (POST) http://localhost:8080/v1/images/{idPost}/upload 

#### Note que para inserir imagens o header da requisição não pode ter content-type

![imagePost](https://user-images.githubusercontent.com/47676471/127919175-44cb40dd-027e-4a2d-9484-f9c1342cc9ba.png)

Para finalizar, qualquer usuário também pode cadastrar seu próprio álbum de fotos e para isso ele deve Enviar uma requisição POST para: http://localhost:8080/v1/albums
>Essa requisição não precisa de body.

Para inserir Fotos dentro do álbum o dono do mesmo deve  enviar uma requisição (POST) para http://localhost:8080/v1/photos/{idAlbum}/upload da seguinte maneira: 
#### Note que para inserir fotos, assim como nas imagens, o header da requisição não pode ter content-type

![post photo](https://user-images.githubusercontent.com/47676471/127919201-f5bd8dd1-3d1f-43d8-b060-8fc202442404.png)

# Dados pre-cadastrados para ficilitar
1. 3 users -> {username:carlos, password: 123}, {username:pedro, password: 321}, {username:pietra, password: 123321}
2. 3 albuns um para cada usuário
3. 3 comentarios, um de cada usuario.
4. 3 posts, um de cada usuario.
 
# Requisições que precisam de authenticação
1. POST -> Todas menos /v1/usersBlog e /v1/login
2. PUT -> Todas
3. DELETE -> Todas
4. GET -> nenhuma requisição GET precisa de autenticação. Além dessa, as requisições http://localhost:8080/swagger-ui.html e http://localhost:8080/v2/api-docs também não precisam de autenticação.
 
# Todas as requisições do projeto

### Login
1. Logar no sistema: http://localhost:8080/v1/login

![Login](https://user-images.githubusercontent.com/47676471/127918815-4dfee961-fb85-4620-861b-43603af6591d.png)

### UserBlog
1. Cadastrar usuário: (POST) http://localhost:8080/v1/usersBlog

>exBody(JSON): {
    "name": "Felipe",
    "username": "felipe",
    "password": "felipe123",
    "cpf": "95542836011",
    "userBlogRole": "ADMIN"
}

2. Atualizar usuário: (PUT) http://localhost:8080/v1/usersBlog/{id}

>exBody(JSON): {
>"id": 1,
    "name": "Felipe",
    "username": "felipe",
    "password": "felipe123",
    "cpf": "95542836011",
    "userBlogRole": "ADMIN"
}

3. Buscar usuários : (GET) http://localhost:8080/v1/usersBlog
4. Buscar usuário por id : (GET) http://localhost:8080/v1/usersBlog/{id}
5. Deletar usuário por id : (DELETE) http://localhost:8080/v1/usersBlog/{id}

### Post
1. Cadastrar post: (POST) http://localhost:8080/v1/posts

>exBody(JSON): {
    "description": "Noticias x",
    "link": "www.google.com/..."
}

2. Atualizar post: (PUT) http://localhost:8080/v1/posts/{id}
>exBody(JSON):{
    "id": 4,
    "description": "Your AI aa ",
    "link": "https://copilot.github.com/",
    "userBlog": {
    	"id": 1
    }
}

3. Buscar posts: (GET) http://localhost:8080/v1/posts
4. Buscar post por id : (GET) http://localhost:8080/v1/posts/{id}
5. Deletar post por id : (DELETE) http://localhost:8080/v1/posts/{id}

### Comment
1. Cadastrar Comentário: (POST) http://localhost:8080/v1/comments
>exBody(JSON): {
    "comment": "comment by felipe!",
    "post": {
        "id": 2
    }
}
2. Atualizar Comentário: (PUT) http://localhost:8080/v1/comments/{id}

>exBody(JSON):{
>"id": 1,
    "comment": "comment by felipe!"
}

3. Buscar Comentários: (GET) http://localhost:8080/v1/comments
4. Buscar Comentário por id : (GET) http://localhost:8080/v1/comments/{id}
5. Deletar Comentário por id : (DELETE) http://localhost:8080/v1/comments/{id}

### Albums
1. Cadastrar álbum: (POST) http://localhost:8080/v1/albums
2. Buscar álbums: (GET) http://localhost:8080/v1/albums
3. Buscar álbum por id : (GET) http://localhost:8080/v1/albums/{id}
4. Deletar álbum por id : (DELETE) http://localhost:8080/v1/albums/{id}

### Image
1. Cadastrar imagem em post: (POST) http://localhost:8080/v1/images/{idPost}/upload
2. Buscar imagens: (GET) http://localhost:8080/v1/images
3. Buscar imagens por post: (GET)http://localhost:8080/v1/photos/album/{idPost}
4. Buscar imagem por id : (GET) http://localhost:8080/v1/images/{id}
5. Deletar imagem por id : (DELETE) http://localhost:8080/v1/images/{id}

### Photos
1. Cadastrar foto em album: (POST) http://localhost:8080/v1/photos/{idAlbum}/upload
2. Buscar fotos: (GET) http://localhost:8080/v1/photos
3. Buscar fotos por álbum: (GET)http://localhost:8080/v1/photos/album/{idAlbum}
4. Buscar foto por id : (GET) http://localhost:8080/v1/photos/{id}
5. Deletar foto por id : (DELETE) http://localhost:8080/v1/photos/{id}
