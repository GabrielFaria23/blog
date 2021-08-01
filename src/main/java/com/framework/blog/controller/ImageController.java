package com.framework.blog.controller;

import com.framework.blog.exception.ImageNotExist;
import com.framework.blog.exception.PostNotExist;
import com.framework.blog.model.Image;
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
    public ResponseEntity<Image> uploadImage(@PathVariable Long idPost,@RequestParam @Valid MultipartFile image) throws IOException, PostNotExist {
        return ResponseEntity.ok(imageService.uploadImage(idPost, image));
    }

    @ApiOperation(value = "Delete Image")
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteImage(@PathVariable long id) throws ImageNotExist {
        imageService.deleteImage(id);
    }

    @ApiOperation(value = "Find all Images")
    @GetMapping
    public ResponseEntity<Page<Image>> findAll(Pageable pageable){
        return ResponseEntity.ok(imageService.findAllImages(pageable));
    }
}
