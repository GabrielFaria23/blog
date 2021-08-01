package com.framework.blog.service;

import com.framework.blog.exception.AlbumNotExist;
import com.framework.blog.exception.PhotoNotExist;
import com.framework.blog.exception.PostNotExist;
import com.framework.blog.model.Album;
import com.framework.blog.model.Photo;
import com.framework.blog.model.Post;
import com.framework.blog.repository.AlbumRepository;
import com.framework.blog.repository.PhotoRepository;
import com.framework.blog.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class PhotoService {

    private final PhotoRepository photoRepository;
    private final PostRepository postRepository;
    private final AlbumRepository albumRepository;

    public PhotoService(PhotoRepository photoRepository, PostRepository postRepository, AlbumRepository albumRepository) {
        this.photoRepository = photoRepository;
        this.postRepository = postRepository;
        this.albumRepository = albumRepository;
    }

    public Photo uploadPhoto(Long idAlbum, MultipartFile photo) throws IOException, PostNotExist {
        String folder = "/assets/photo/";
        Path path = Paths.get("D:", folder);
        Path arquivoPath = path.resolve(photo.getOriginalFilename());
        try{
            Files.createDirectories(path);
            photo.transferTo(arquivoPath.toFile());
        }catch (IOException e){
            throw new RuntimeException("Error to save file!");
        }

        return photoRepository.save(new Photo(path.toString()+ arquivoPath, new Album()));
    }

    public void deletePhoto(Long id) throws PhotoNotExist {
        photoRepository.deleteById(id);
    }

    public Page<Photo> findAllPhotos(Pageable pageable){
        return photoRepository.findAll(pageable);
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
