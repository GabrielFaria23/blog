package com.framework.blog.service;

import com.framework.blog.exception.AlbumNotExist;
import com.framework.blog.exception.CommentNotExist;
import com.framework.blog.exception.PermissionDeniedException;
import com.framework.blog.exception.PhotoNotExist;
import com.framework.blog.model.*;
import com.framework.blog.repository.PhotoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class PhotoService {

    private final PhotoRepository photoRepository;
    private final AlbumService albumService;
    private final UserBlogService userBlogService;

    public PhotoService(PhotoRepository photoRepository, AlbumService albumService, UserBlogService userBlogService) {
        this.photoRepository = photoRepository;
        this.albumService = albumService;
        this.userBlogService = userBlogService;
    }

    public Photo uploadPhoto(Long idAlbum, MultipartFile photo) throws AlbumNotExist, PermissionDeniedException {
        UserBlog userBlog = userBlogService.userLogged();
        Album album = albumService.checkAlbumExist(idAlbum);
        if (userBlog.getId() == album.getUserBlog().getId()){
            String folder = "/photo/";
            Path path = Paths.get("C:", folder);
            Path arquivoPath = path.resolve(photo.getOriginalFilename());
            try{
                Files.createDirectories(path);
                photo.transferTo(arquivoPath.toFile());
                return photoRepository.save(new Photo(path.toString(), album));
            }catch (IOException e){
                throw new RuntimeException("Error to save file!");
            }
        }else{
            throw new PermissionDeniedException("You don't have permission to add photo(s) in this album!");
        }

    }

    public void deletePhoto(Long id) throws PhotoNotExist, PermissionDeniedException {
        UserBlog userBlog = userBlogService.userLogged();
        Photo photo = checkPhotoExist(id);
        if (userBlog.getId() == photo.getAlbum().getUserBlog().getId()){
            photoRepository.deleteById(id);
        }else{
            throw new PermissionDeniedException("You don't have permission to delete this photo!");
        }

    }

    public List<Photo> findAll(){
        return photoRepository.findAll();
    }

    public List<Photo> findByAlbum(Long idAlbum) {
        return photoRepository.findByAlbum(idAlbum);
    }

    public Photo findById(Long id) throws PhotoNotExist {
        return checkPhotoExist(id);
    }

    private Photo checkPhotoExist(Long id) throws PhotoNotExist {
        return photoRepository.findById(id)
                .orElseThrow(()-> new PhotoNotExist("Cannot find photo with id: "+ id +" /n" +
                        "Please insert another id and try again!"));
    }
}
