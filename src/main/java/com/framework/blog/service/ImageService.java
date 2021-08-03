package com.framework.blog.service;

import com.framework.blog.exception.ImageNotExist;
import com.framework.blog.exception.PermissionDeniedException;
import com.framework.blog.exception.PostNotExist;
import com.framework.blog.model.Image;
import com.framework.blog.model.Post;
import com.framework.blog.model.UserBlog;
import com.framework.blog.repository.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private final PostService postService;
    private final UserBlogService userBlogService;

    public ImageService(ImageRepository imageRepository, PostService postService, UserBlogService userBlogService) {
        this.imageRepository = imageRepository;
        this.postService = postService;
        this.userBlogService = userBlogService;
    }

    public Image uploadImage(Long idPost, MultipartFile image) throws PostNotExist, PermissionDeniedException {
        UserBlog userBlog = userBlogService.userLogged();
        Post post = postService.checkPostExist(idPost);
        if (userBlog.getId() == post.getUserBlog().getId()){
            String folder = "/img/";
            Path path = Paths.get("C:", folder);
            Path arquivoPath = path.resolve(image.getOriginalFilename());
            try{
                Files.createDirectories(path);
                image.transferTo(arquivoPath.toFile());
                return imageRepository.save(new Image(path.toString(), post));
            }catch (IOException e){
                throw new RuntimeException("Error to save file!");
            }
        }else{
            throw new PermissionDeniedException("You don't have permission to add image(s) in this post!");
        }
    }

    public void deleteImage(Long id) throws ImageNotExist, PermissionDeniedException {
        UserBlog userBlog = userBlogService.userLogged();
        Image image = imageRepository.findById(id).get();
        if (userBlog.getId() == image.getPost().getUserBlog().getId()){
            imageRepository.deleteById(id);
        }else{
            throw new PermissionDeniedException("You don't have permission to delete image(s) in this comment!");
        }
    }

    public List<Image> findAll(){
        return imageRepository.findAll();
    }

    public List<Image> findByPost(Long idPost) {
        return imageRepository.findByPost(idPost);
    }

    public Image findById(Long id) throws ImageNotExist {
        return checkImageExist(id);
    }

    private Image checkImageExist(Long id) throws ImageNotExist {
        return imageRepository.findById(id)
                .orElseThrow(()-> new ImageNotExist("Cannot find image with id: "+ id +" /n" +
                        "Please insert another id and try again!"));
    }

}
