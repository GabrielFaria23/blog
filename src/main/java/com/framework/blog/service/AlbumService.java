package com.framework.blog.service;

import com.framework.blog.exception.AlbumNotExist;
import com.framework.blog.model.Album;
import com.framework.blog.repository.AlbumRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AlbumService {

    private final AlbumRepository albumRepository;

    public AlbumService(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    public Album createAlbum(Album album){
        return albumRepository.save(album);
    }

    public void deleteAlbum(Long id) throws AlbumNotExist {
        albumRepository.deleteById(checkAlbumExist(id).getId());
    }

    public Album findById(Long id) throws AlbumNotExist {
        return checkAlbumExist(id);
    }

    public Album updateAlbum(Long id, Album album) throws AlbumNotExist {
        Album albumToBeUpdated = checkAlbumExist(id);

        //verificar campos

        return albumRepository.save(albumToBeUpdated);
    }

    public Page<Album> findAllAlbums(Pageable pageable){
        return albumRepository.findAll(pageable);
    }

    private Album checkAlbumExist(Long id) throws AlbumNotExist {
        return albumRepository.findById(id)
                .orElseThrow(()-> new AlbumNotExist("Cannot find album with id: "+ id +" /n" +
                        "Please insert another id and try again!"));
    }
}
