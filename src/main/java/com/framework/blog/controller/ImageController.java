package com.framework.blog.controller;

import com.framework.blog.exception.ImageNotExist;
import com.framework.blog.exception.PermissionDeniedException;
import com.framework.blog.exception.PhotoNotExist;
import com.framework.blog.exception.PostNotExist;
import com.framework.blog.model.Image;
import com.framework.blog.model.Photo;
import com.framework.blog.service.ImageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Api(value = "Endpoint of Image", description = "Endpoint used to make alterations in image Entity", tags = "Endpoint of Image")
@RestController
@RequestMapping(value = "/v1/images")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @ApiOperation(value = "Upload Image")
    @PostMapping(value = "/{idPost}/upload")
    public ResponseEntity<Image> uploadImage(@PathVariable Long idPost,@RequestParam @Valid MultipartFile image) throws IOException, PostNotExist, ImageNotExist, PermissionDeniedException {
        Image imageSaved = imageService.uploadImage(idPost, image);
        imageSaved.add(linkTo(methodOn(ImageController.class).findById(imageSaved.getId())).withSelfRel());
        return ResponseEntity.ok(imageSaved);
    }

    @ApiOperation(value = "Delete Image")
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteImage(@PathVariable long id) throws ImageNotExist, PermissionDeniedException {
        imageService.deleteImage(id);
    }

    @ApiOperation(value = "Find Image by id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Image> findById(@PathVariable long id) throws ImageNotExist {
        Image imageGet = imageService.findById(id);
        imageGet.add(linkTo(methodOn(ImageController.class).findAll()).withSelfRel());
        return ResponseEntity.ok(imageGet);
    }

    @ApiOperation(value = "Find all Images of Post")
    @GetMapping(value = "/post/{idPost}")
    public ResponseEntity<List<Image>> findByPost(@PathVariable Long idPost){
        List<Image> allImages = imageService.findByPost(idPost);
        allImages.forEach(p -> {
            try {
                p.add(linkTo(methodOn(ImageController.class).findById(p.getId())).withSelfRel());
            } catch (ImageNotExist imageNotExist) {
                imageNotExist.printStackTrace();
            }
        });
        return ResponseEntity.ok(allImages);
    }

    @ApiOperation(value = "Find all Images")
    @GetMapping
    public ResponseEntity<List<Image>> findAll(){
        List<Image> allImages = imageService.findAll();
        allImages.forEach(p -> {
            try {
                p.add(linkTo(methodOn(ImageController.class).findById(p.getId())).withSelfRel());
            } catch (ImageNotExist imageNotExist) {
                imageNotExist.printStackTrace();
            }
        });
        return ResponseEntity.ok(allImages);
    }
}
