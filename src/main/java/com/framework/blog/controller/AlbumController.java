package com.framework.blog.controller;

import com.framework.blog.exception.AlbumNotExist;
import com.framework.blog.model.Album;
import com.framework.blog.service.AlbumService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "Endpoint of Album", description = "Endpoint used to make alterations in album Entity", tags = "Endpoint of Album")
@RestController
@RequestMapping(value = "/v1/albums")
public class AlbumController {

    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @ApiOperation(value = "Create Album")
    @PostMapping
    public ResponseEntity<Album> createAlbum(@RequestBody @Valid Album album){
        return ResponseEntity.ok(albumService.createAlbum(album));
    }

    @ApiOperation(value = "Delete Album")
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlbum(@PathVariable long id) throws AlbumNotExist {
        albumService.deleteAlbum(id);
    }

    @ApiOperation(value = "Find all Albums")
    @GetMapping
    public ResponseEntity<Page<Album>> findAll(Pageable pageable){
        return ResponseEntity.ok(albumService.findAllAlbums(pageable));
    }

    @ApiOperation(value = "Find Album by id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Album> findById(@PathVariable long id) throws AlbumNotExist {
        return ResponseEntity.ok(albumService.findById(id));
    }

    @ApiOperation(value = "Update Album")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Album> updateAlbum(@PathVariable long id, @RequestBody @Valid Album album) throws AlbumNotExist {
        return ResponseEntity.ok(albumService.updateAlbum(id, album));
    }
}
