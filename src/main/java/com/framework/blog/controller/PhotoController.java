package com.framework.blog.controller;

import com.framework.blog.exception.AlbumNotExist;
import com.framework.blog.exception.PermissionDeniedException;
import com.framework.blog.exception.PhotoNotExist;
import com.framework.blog.model.Photo;
import com.framework.blog.service.PhotoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Api(value = "Endpoint of Photo", description = "Endpoint used to make alterations in photo Entity", tags = "Endpoint of Photo")
@RestController
@RequestMapping(value = "/v1/photos")
public class PhotoController {

    private final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @ApiOperation(value = "Upload Photo")
    @PostMapping(value = "/{idAlbum}/upload")
    public ResponseEntity<Photo> uploadPhoto(@PathVariable Long idAlbum,@RequestParam @Valid MultipartFile photo) throws AlbumNotExist, PhotoNotExist, PermissionDeniedException {
        Photo photoSaved = photoService.uploadPhoto(idAlbum, photo);
        photoSaved.add(linkTo(methodOn(PhotoController.class).findById(photoSaved.getId())).withSelfRel());
        return ResponseEntity.ok(photoSaved);
    }

    @ApiOperation(value = "Delete Photo")
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePhoto(@PathVariable long id) throws PhotoNotExist, PermissionDeniedException {
        photoService.deletePhoto(id);
    }

    @ApiOperation(value = "Find Photo by id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Photo> findById(@PathVariable long id) throws PhotoNotExist {
        Photo photoGet = photoService.findById(id);
        photoGet.add(linkTo(methodOn(PhotoController.class).findAll()).withSelfRel());
        return ResponseEntity.ok(photoGet);
    }

    @ApiOperation(value = "Find all Photos of Album")
    @GetMapping(value = "/album/{idAlbum}")
    public ResponseEntity<List<Photo>> findByAlbum(@PathVariable Long idAlbum){
        List<Photo> allPhotos = photoService.findByAlbum(idAlbum);
        allPhotos.forEach(p -> {
            try {
                p.add(linkTo(methodOn(PhotoController.class).findById(p.getId())).withSelfRel());
            } catch (PhotoNotExist photoNotExist) {
                photoNotExist.printStackTrace();
            }
        });
        return ResponseEntity.ok(allPhotos);
    }

    @ApiOperation(value = "Find all Photos")
    @GetMapping
    public ResponseEntity<List<Photo>> findAll(){
        List<Photo> allPhotos = photoService.findAll();
        allPhotos.forEach(p -> {
            try {
                p.add(linkTo(methodOn(PhotoController.class).findById(p.getId())).withSelfRel());
            } catch (PhotoNotExist photoNotExist) {
                photoNotExist.printStackTrace();
            }
        });
        return ResponseEntity.ok(allPhotos);
    }
}
