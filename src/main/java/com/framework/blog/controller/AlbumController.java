package com.framework.blog.controller;

import com.framework.blog.exception.AlbumNotExist;
import com.framework.blog.exception.PermissionDeniedException;
import com.framework.blog.model.Album;
import com.framework.blog.service.AlbumService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
    public ResponseEntity<Album> createAlbum() throws AlbumNotExist {
        Album albumSaved = albumService.createAlbum();
        albumSaved.add(linkTo(methodOn(AlbumController.class).findById(albumSaved.getId())).withSelfRel());
        return ResponseEntity.ok(albumSaved);
    }

    @ApiOperation(value = "Delete Album")
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlbum(@PathVariable long id) throws AlbumNotExist, PermissionDeniedException {
        albumService.deleteAlbum(id);
    }

    @ApiOperation(value = "Find all Albums")
    @GetMapping
    public ResponseEntity<List<Album>> findAll(){
        List<Album> allAlbums = albumService.findAll();
        allAlbums.forEach(a -> {
            try {
                a.add(linkTo(methodOn(AlbumController.class).findById(a.getId())).withSelfRel());
            } catch (AlbumNotExist albumNotExist) {
                albumNotExist.printStackTrace();
            }
        });
        return ResponseEntity.ok(allAlbums);
    }

    @ApiOperation(value = "Find Album by id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Album> findById(@PathVariable long id) throws AlbumNotExist {
        Album albumGet = albumService.findById(id);
        albumGet.add(linkTo(methodOn(AlbumController.class).findAll()).withSelfRel());
        return ResponseEntity.ok(albumGet);
    }
}
