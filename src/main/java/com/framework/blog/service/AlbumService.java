package com.framework.blog.service;

import com.framework.blog.exception.AlbumNotExist;
import com.framework.blog.exception.PermissionDeniedException;
import com.framework.blog.model.Album;
import com.framework.blog.model.UserBlog;
import com.framework.blog.repository.AlbumRepository;
import com.framework.blog.repository.UserBlogRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final UserBlogService userBlogService;

    public AlbumService(AlbumRepository albumRepository, UserBlogService userBlogService) {
        this.albumRepository = albumRepository;
        this.userBlogService = userBlogService;
    }

    public Album createAlbum(){
        UserBlog userBlog = userBlogService.userLogged();
        return albumRepository.save(new Album(userBlog));
    }

    public void deleteAlbum(Long id) throws AlbumNotExist, PermissionDeniedException {
        UserBlog userBlog = userBlogService.userLogged();
        Album album = albumRepository.getById(id);
        if (userBlog.getId() == album.getUserBlog().getId()){
            albumRepository.deleteById(checkAlbumExist(id).getId());
        }else{
            throw new PermissionDeniedException("You don't have permission to delete this Album!");
        }
    }

    public Album findById(Long id) throws AlbumNotExist {
        return checkAlbumExist(id);
    }

    public List<Album> findAll(){
        return albumRepository.findAll();
    }

    public Album checkAlbumExist(Long id) throws AlbumNotExist {
        return albumRepository.findById(id)
                .orElseThrow(()-> new AlbumNotExist("Cannot find album with id: "+ id +" /n" +
                        "Please insert another id and try again!"));
    }

}
