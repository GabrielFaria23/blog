package com.framework.blog.service;

import com.framework.blog.exception.AlbumNotExist;
import com.framework.blog.exception.ImageNotExist;
import com.framework.blog.exception.PostNotExist;
import com.framework.blog.model.Album;
import com.framework.blog.model.Image;
import com.framework.blog.model.Post;
import com.framework.blog.repository.AlbumRepository;
import com.framework.blog.repository.ImageRepository;
import com.framework.blog.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private final PostRepository postRepository;
    private final AlbumRepository albumRepository;

    public ImageService(ImageRepository imageRepository, PostRepository postRepository, AlbumRepository albumRepository) {
        this.imageRepository = imageRepository;
        this.postRepository = postRepository;
        this.albumRepository = albumRepository;
    }

    public Image uploadImage(Long idPost, MultipartFile image) throws IOException, PostNotExist {
        String folder = "/assets/img/";
        Path path = Paths.get("D:", folder);
        Path arquivoPath = path.resolve(image.getOriginalFilename());
        try{
            Files.createDirectories(path);
            image.transferTo(arquivoPath.toFile());
        }catch (IOException e){
            throw new RuntimeException("Error to save file!");
        }

        return imageRepository.save(new Image(path.toString(), checkPostExist(idPost), new Album()));
    }

    public void deleteImage(Long id) throws ImageNotExist {
        imageRepository.deleteById(id);
    }

    public Page<Image> findAllImages(Pageable pageable){
        return imageRepository.findAll(pageable);
    }

    private Post checkPostExist(Long id) throws PostNotExist {
        return postRepository.findById(id)
                .orElseThrow(()-> new PostNotExist("Cannot find post with id: "+ id +" /n" +
                        "Please insert another id and try again!"));
    }

    private Album checkAlbumExist(Long id) throws AlbumNotExist {
        return albumRepository.findById(id)
                .orElseThrow(()-> new AlbumNotExist("Cannot find album with id: "+ id +" /n" +
                        "Please insert another id and try again!"));
    }
}
