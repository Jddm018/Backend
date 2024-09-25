package com.example.demo.controllers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Repositories.IUserRepositories;
import com.example.demo.Services.IProductService;
import com.example.demo.Services.impl.SessionTokenService;
import com.example.demo.controllers.dto.ProductDTO;
import com.example.demo.entities.Product;
import com.example.demo.entities.User;
import com.example.demo.exceptions.FileFormatException;
import com.example.demo.exceptions.InvalidSessionTokenException;
import com.example.demo.exceptions.ProductNotFoundException;
import com.example.demo.exceptions.UnauthorizedAccessException;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Value("${images.storage.path}")
    private String storageFolderPath;

    @Autowired
    private IUserRepositories iUserRepositories;

    @Autowired
    private SessionTokenService sessionTokenService;

    @Autowired
    private IProductService productService;

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            Optional<Product> productOptional = productService.findById(id);

            if (productOptional.isPresent()) {
                Product product = productOptional.get();

                ProductDTO productDTO = ProductDTO.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .price(product.getPrice())
                    .category(product.getCategory())
                    .images(product.getImages())
                    .build();

                return ResponseEntity.ok(productDTO);
            } else {
                throw new ProductNotFoundException("Producto no encontrado");
            }
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {
        List<Product> products = productService.findAll();
        List<ProductDTO> productlist = new ArrayList<>();

        for (Product product : products) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(product.getId());
            productDTO.setName(product.getName());
            productDTO.setPrice(product.getPrice());
            productDTO.setCategory(product.getCategory());
            productDTO.setImages(product.getImages());
            productlist.add(productDTO);
        }

        return ResponseEntity.ok(productlist);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@ModelAttribute ProductDTO productDTO, @RequestParam("image") MultipartFile image, @RequestHeader("Authorization") String token) throws URISyntaxException, IOException {
        if (!sessionTokenService.isValidSessionToken(token)) {
            throw new InvalidSessionTokenException("Token invalido");
        }
        String email = sessionTokenService.getUserEmailFromToken(token);
        User user = iUserRepositories.findByEmail(email);
        boolean isAdmin = user.getRoles().stream().anyMatch(role -> role.getName().equals("ADMIN"));
        if (!isAdmin) {
            throw new UnauthorizedAccessException("No puedes realizar esta accion");
        }
        if (image == null || image.isEmpty()) {
            throw new IllegalArgumentException("Debe proporcionar una imagen para crear el producto");
        }

        // Obtener el nombre de la imagen
        String imageName = image.getOriginalFilename();
        if (imageName == null) {
            throw new IllegalArgumentException("El nombre de la imagen es nulo");
        }

        // Obtener la extensión del archivo
        String fileExtension = imageName.substring(imageName.lastIndexOf(".") + 1).toLowerCase();

        // Verificar si la extensión es válida
        if (!Arrays.asList("jpg", "jpeg", "png").contains(fileExtension)) {
            throw new FileFormatException("Extension de imagen no permitida. Por favor suba una imagen con extension jpg, png o jpeg");
        }

        // Crear un nombre único para cada imagen
        String uniqueFileName = UUID.randomUUID().toString() + "_" + imageName;
        // Ruta donde se guardarán las imágenes en el servidor
        String serverImagePath = "http://localhost:8080/images/" + uniqueFileName;

        // Guardar la imagen en el servidor
        Files.write(Paths.get(storageFolderPath, uniqueFileName), image.getBytes());

        if (productDTO.getName().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        Product productCreated = productService.save(Product.builder()
            .name(productDTO.getName())
            .price(productDTO.getPrice())
            .category(productDTO.getCategory())
            .images(serverImagePath)
            .build());

        return ResponseEntity.status(HttpStatus.CREATED).body(productCreated);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @ModelAttribute ProductDTO productDTO, @RequestParam("image") MultipartFile image, @RequestHeader("Authorization") String token) throws IOException {
        if (!sessionTokenService.isValidSessionToken(token)) {
            throw new InvalidSessionTokenException("Token invalido");
        }
        String email = sessionTokenService.getUserEmailFromToken(token);
        User user = iUserRepositories.findByEmail(email);
        boolean isAdmin = user.getRoles().stream().anyMatch(role -> role.getName().equals("ADMIN"));
        if (!isAdmin) {
            throw new UnauthorizedAccessException("No puedes realizar esta accion");
        }
        if (image == null || image.isEmpty()) {
            throw new IllegalArgumentException("Debe proporcionar una imagen para actualizar el producto");
        }

        // Obtener el nombre de la imagen
        String imageName = image.getOriginalFilename();
        if (imageName == null) {
            throw new IllegalArgumentException("El nombre de la imagen es nulo");
        }

        // Obtener la extensión del archivo
        String fileExtension = imageName.substring(imageName.lastIndexOf(".") + 1).toLowerCase();

        // Verificar si la extensión es válida
        if (!Arrays.asList("jpg", "jpeg", "png").contains(fileExtension)) {
            throw new FileFormatException("Extension de imagen no permitida. Por favor suba una imagen con extension jpg, png o jpeg");
        }

        // Crear un nombre único para cada imagen
        String uniqueFileName = UUID.randomUUID().toString() + "_" + imageName;
        // Ruta donde se guardarán las imágenes en el servidor
        String serverImagePath = "http://localhost:8080/images/" + uniqueFileName;

        // Guardar la imagen en el servidor
        Files.write(Paths.get(storageFolderPath, uniqueFileName), image.getBytes());

        Optional<Product> productOptional = productService.findById(id);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.setName(productDTO.getName());
            product.setPrice(productDTO.getPrice());
            product.setCategory(productDTO.getCategory());
            product.setImages(serverImagePath);
            productService.save(product);
            return ResponseEntity.ok(product);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        if (!sessionTokenService.isValidSessionToken(token)) {
            throw new InvalidSessionTokenException("Token invalido");
        }
        String email = sessionTokenService.getUserEmailFromToken(token);
        User user = iUserRepositories.findByEmail(email);
        boolean isAdmin = user.getRoles().stream().anyMatch(role -> role.getName().equals("ADMIN"));
        if (!isAdmin) {
            throw new UnauthorizedAccessException("No puedes realizar esta accion");
        }
        
        if (id != null) {
            productService.deleteById(id);
            return ResponseEntity.ok("Registro Eliminado");
        }
        return ResponseEntity.badRequest().build();
    }
}
