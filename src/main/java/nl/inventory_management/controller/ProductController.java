package nl.inventory_management.controller;

import lombok.RequiredArgsConstructor;
import nl.inventory_management.business.domain.Department;
import nl.inventory_management.business.domain.Product;
import nl.inventory_management.business.interfaces.*;
import nl.inventory_management.configuration.security.isauthenticated.IsAuthenticated;
import nl.inventory_management.controller.converter.ProductConverter;
import nl.inventory_management.controller.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
public class ProductController {
    private final CreateProductUseCase createProductUseCase;
    private final GetProductUseCase getProductUseCase;
    private final DeleteProductUseCase deleteProductUseCase;
    private final GetDepartmentUseCase getDepartmentUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final GetFilteredProductsUseCase getFilteredProductsUseCase;
    @IsAuthenticated
    @RolesAllowed({"ROLE_DEPOT_WORKER","ROLE_ADMIN"})
    @PostMapping("/save")
    public ResponseEntity<CreateProductResponse> createProduct(@RequestBody @Valid CreateProductRequest createProductRequest) {

        Department dep = getDepartmentUseCase.findDepartmentByName(createProductRequest.getDepartmentName());
        Product product = Product.builder()
                .name(createProductRequest.getName())
                .description(createProductRequest.getDescription())
                .weight(createProductRequest.getWeight())
                .width(createProductRequest.getWidth())
                .length(createProductRequest.getLength())
                .height(createProductRequest.getHeight())
                .department(dep)
                .build();
        Product responseEntity = createProductUseCase.createProduct(product);
        CreateProductResponse response = ProductConverter.productToCreateProductResponse(responseEntity);

        return  ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @IsAuthenticated
    @RolesAllowed({"ROLE_DEPOT_WORKER","ROLE_ADMIN"})
    @PutMapping("/{id}")
    public ResponseEntity<CreateProductResponse> updateProduct(@PathVariable("id") long id,@RequestBody @Valid UpdateProductRequest updateProductRequest) {

        Department dep = getDepartmentUseCase.findDepartmentByName(updateProductRequest.getDepartmentName());
        Product product = Product.builder()
                .id(id)
                .name(updateProductRequest.getName())
                .description(updateProductRequest.getDescription())
                .weight(updateProductRequest.getWeight())
                .width(updateProductRequest.getWidth())
                .length(updateProductRequest.getLength())
                .height(updateProductRequest.getHeight())
                .department(dep)
                .build();
        Product responseEntity = updateProductUseCase.updateProduct(product);
        CreateProductResponse response = ProductConverter.productToCreateProductResponse(responseEntity);

        return  ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @IsAuthenticated
    @RolesAllowed({"ROLE_DEPOT_WORKER","ROLE_ADMIN"})
    @GetMapping("/")
    public ResponseEntity<List<GetProductResponse>> getAllProducts(){


        List<Product> responseEntityList = getProductUseCase.getAllProducts();
        List<GetProductResponse> response = responseEntityList.stream().map(ProductConverter::productToGetProductResponse).toList();

        return ResponseEntity.ok(response);
    }
    @IsAuthenticated
    @RolesAllowed({"ROLE_DEPOT_WORKER","ROLE_ADMIN"})
    @GetMapping("/filter/{name}/{description}/{department}")
    public ResponseEntity<List<GetProductResponse>> getFilteredProducts(@PathVariable String name, @PathVariable String description, @PathVariable String department  ){

        List<Product> responseEntityList = getFilteredProductsUseCase.getFilteredProducts(name,description,department);
        List<GetProductResponse> response = responseEntityList.stream().map(ProductConverter::productToGetProductResponse).toList();

        return ResponseEntity.ok(response);
    }


    @IsAuthenticated
    @RolesAllowed({"ROLE_DEPOT_WORKER","ROLE_ADMIN"})
    @GetMapping("/{id}")
    public ResponseEntity<GetProductResponse> getProduct(@PathVariable Long id) {

        Product responseEntity = getProductUseCase.getProductById(id);
        GetProductResponse response = ProductConverter.productToGetProductResponse(responseEntity);
        return ResponseEntity.ok(response);
    }
    @IsAuthenticated
    @RolesAllowed({"ROLE_DEPOT_WORKER","ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteResponse> deleteProduct(@PathVariable Long id) {
        DeleteResponse response = deleteProductUseCase.deleteProduct(id);

        return ResponseEntity.ok(response);
    }
}