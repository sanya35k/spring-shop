package com.konstantinov.springshop.controller;

import com.konstantinov.springshop.model.Product;
import com.konstantinov.springshop.repository.ProductRepository;
import com.konstantinov.springshop.storage.FileSystemStorageService;
import com.konstantinov.springshop.storage.StorageFileNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.stream.Collectors;

@Controller
public class ProductController {
    private static Logger LOG = LoggerFactory.getLogger(ProductController.class);

    private ProductRepository productRepository;
    private FileSystemStorageService storageService;

    @Autowired
    public void setStorageService(FileSystemStorageService storageService) {
        this.storageService = storageService;
    }

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping(value = "/products")
    public String list(@PageableDefault(size = 8, direction = Sort.Direction.ASC, sort = "price") Pageable pageable, Model model) {
        model.addAttribute("products", productRepository.findAll(pageable));
        model.addAttribute("files", storageService
                .loadAll()
                .map(path ->
                        MvcUriComponentsBuilder
                                .fromMethodName(ProductController.class, "serveFile", path.getFileName().toString())
                                .build().toString())
                .collect(Collectors.toList()));
        LOG.info("All products");
        return "product/products";
    }

    @PostMapping(value = "product")
    public String saveProduct(Product product, MultipartFile file,
                              RedirectAttributes redirectAttributes) {
        storageService.store(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");
        product.setPhotoName(file.getOriginalFilename());
        productRepository.save(product);

        LOG.info("Save product: " + product);
        return "redirect:/product/" + product.getId();
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

    @RequestMapping("product/{id}")
    public String showProduct(@PathVariable Long id, Model model) {
        model.addAttribute("product", productRepository.findOne(id));
        LOG.info("Product id: " + id);
        return "product/productshow";
    }

    @RequestMapping("product/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("product", productRepository.findOne(id));
        LOG.info("Edited product id: " + id);
        return "product/productform";
    }

    @RequestMapping("product/new")
    public String newProduct(Model model) {
        model.addAttribute("product", new Product());
        return "product/productform";
    }

    @RequestMapping("product/delete/{id}")
    public String delete(@PathVariable Long id) {
        productRepository.delete(id);
        LOG.info("Deleted product id : " + id);
        return "redirect:/products";
    }
}