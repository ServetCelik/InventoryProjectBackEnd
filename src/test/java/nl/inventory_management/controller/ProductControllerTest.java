package nl.inventory_management.controller;

import nl.inventory_management.business.domain.Department;
import nl.inventory_management.business.domain.Location;
import nl.inventory_management.business.domain.Product;
import nl.inventory_management.business.exception.*;
import nl.inventory_management.business.interfaces.*;
import nl.inventory_management.controller.dto.DeleteResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CreateProductUseCase createProductUseCaseMock;
    @MockBean
    private GetProductUseCase getProductUseCaseMock;
    @MockBean
    private DeleteProductUseCase deleteProductUseCaseMock;
    @MockBean
    private GetDepartmentUseCase getDepartmentUseCaseMock;
    @MockBean
    private  UpdateProductUseCase updateProductUseCaseMock;

    @Test
    @WithMockUser(roles = "ADMIN")
    void getAllProducts_shouldReturn200ResponseWithGetProductResponseArray() throws Exception {
        // Arrange
        Department responseDep = Department.builder()
                .id(1L)
                .name("Electronic")
                .description("none")
                .build();

        Product response = Product.builder()
                .id(1L)
                .name("Iphone")
                .description("pro max")
                .weight(100)
                .width(50)
                .length(75)
                .height(90)
                .department(responseDep)
                .build();

        Product secondResponse = Product.builder()
                .id(2L)
                .name("Samsung Galaxy")
                .description("14 pro")
                .weight(100)
                .width(50)
                .length(75)
                .height(90)
                .department(responseDep)
                .build();

        when(getProductUseCaseMock.getAllProducts())
                .thenReturn(List.of(response, secondResponse));

        // Act
        mockMvc.perform(get("/product/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                        [{"id":1,"name":"Iphone","description":"pro max","departmentName":"Electronic" ,"weight": 100,"width": 50,"length": 75,"height": 90},
                        {"id":2,"name":"Samsung Galaxy","description":"14 pro","departmentName": "Electronic","weight": 100,"width": 50,"length": 75,"height": 90}]
                        """));

        // Assert
        verify(getProductUseCaseMock).getAllProducts();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getProductById_shouldReturn200ResponseWithProduct() throws Exception {
        // Arrange
        Department responseDep = Department.builder()
                .id(1L)
                .name("Electronic")
                .description("none")
                .build();

        Product response = Product.builder()
                .id(1L)
                .name("Iphone")
                .description("pro max")
                .weight(100)
                .width(50)
                .length(75)
                .height(90)
                .department(responseDep)
                .build();

        when(getProductUseCaseMock.getProductById(1L))
                .thenReturn(response);

        // Act
        mockMvc.perform(get("/product/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                        {"id":1,"name":"Iphone","description":"pro max","departmentName":"Electronic" ,"weight": 100,"width": 50,"length": 75,"height": 90}
                        """));

        // Assert
        verify(getProductUseCaseMock).getProductById(1L);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getProductById_shouldCreateAndReturnError_WhenLProductIdNotExists() throws Exception {
        // Arrange
            when(getProductUseCaseMock.getProductById(1L)).thenThrow(new ProductNotFoundException());

        // Act
            mockMvc.perform(get("/product/1"))
                    .andDo(print())
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ProductNotFoundException));

        // Assert
        verify(getProductUseCaseMock).getProductById(1L);
    }
    @Test
    @WithMockUser(roles = "HR")
    void getProductById_shouldCreateAndReturn401_WhenUnauthenticated() throws Exception {
        // Act
        mockMvc.perform(get("/product/1"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
    @Test
    void getProductById_shouldCreateAndReturn403_WhenUnauthorized() throws Exception {
        // Act
        mockMvc.perform(get("/product/1"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void createProduct_shouldCreateAndReturn201_WhenRequestValid() throws Exception {
        // Arrange
        Department department = Department.builder()
                .id(1L)
                .name("Electronic")
                .description("none")
                .build();

        Product productRequest = Product.builder()
                .name("Iphone")
                .description("pro max")
                .weight(100)
                .width(50)
                .length(75)
                .height(90)
                .department(department)
                .build();

        Product productRespond= Product.builder()
                .id(1L)
                .name("Iphone")
                .description("pro max")
                .weight(100)
                .width(50)
                .length(75)
                .height(90)
                .department(department)
                .build();

        when(createProductUseCaseMock.createProduct(productRequest)).thenReturn(productRespond);
        when(getDepartmentUseCaseMock.findDepartmentByName("Electronic")).thenReturn(department);

        // Act
        mockMvc.perform(post("/product/save")
                .contentType(APPLICATION_JSON_VALUE)
                .content("""
                        {
                        "name": "Iphone",
                        "description": "pro max",
                        "weight": "100",
                        "width": "50",
                        "length": "75",
                        "height": "90",
                        "departmentName": "Electronic"
                        }
                         """))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("Content-Type",
                                APPLICATION_JSON_VALUE))
                        .andExpect(content().json("""
                                    {"id": 1}
                                    """));

        // Assert
        verify(createProductUseCaseMock).createProduct(productRequest);
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void setCreateProductUseCaseMock_shouldCreateAndReturnError_WhenProductNameExists() throws Exception {
        // Arrange
        Department department = Department.builder()
                .id(1L)
                .name("Electronic")
                .description("none")
                .build();

        Product productRequest = Product.builder()
                .name("Iphone")
                .description("pro max")
                .weight(100)
                .width(50)
                .length(75)
                .height(90)
                .department(department)
                .build();

        when(createProductUseCaseMock.createProduct(productRequest)).thenThrow(new ProductNameAlreadyExistsException());
        when(getDepartmentUseCaseMock.findDepartmentByName("Electronic")).thenReturn(department);

        // Act
        mockMvc.perform(post("/product/save")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content("""
                        {
                        "name": "Iphone",
                        "description": "pro max",
                        "weight": "100",
                        "width": "50",
                        "length": "75",
                        "height": "90",
                        "departmentName": "Electronic"
                        }
                         """))
                .andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ProductNameAlreadyExistsException));

        // Assert
        verify(createProductUseCaseMock).createProduct(productRequest);
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void createProduct_shouldCreateAndReturnError_WhenCredentialsAreNotCorrectFormat() throws Exception {
        // Arrange
        Department department = Department.builder()
                .id(1L)
                .name("Electronic")
                .description("none")
                .build();

        Product productRequest = Product.builder()
                .name("I")
                .description("pro max")
                .weight(100)
                .width(50)
                .length(75)
                .height(90)
                .department(department)
                .build();

        when(createProductUseCaseMock.createProduct(productRequest)).thenThrow(new UnsupportedProductFieldsException());
        when(getDepartmentUseCaseMock.findDepartmentByName("Electronic")).thenReturn(department);

        // Act
        mockMvc.perform(post("/product/save")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content("""
                        {
                        "name": "I",
                        "description": "pro max",
                        "weight": "100",
                        "width": "50",
                        "length": "75",
                        "height": "90",
                        "departmentName": "Electronic"
                        }
                         """))
                .andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UnsupportedProductFieldsException));

        // Assert
        verify(createProductUseCaseMock).createProduct(productRequest);
    }
    @Test
    void createLocation_shouldCreateAndReturn401_WhenUnauthenticated() throws Exception {
        // Act
        mockMvc.perform(post("/product/save")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content("""
                        {
                        "name": "Iphone",
                        "description": "pro max",
                        "weight": "100",
                        "width": "50",
                        "length": "75",
                        "height": "90",
                        "departmentName": "Electronic"
                        }
                         """))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
    @Test
    @WithMockUser(roles = "HR")
    void createLocation_shouldCreateAndReturn403_WhenUnauthorized() throws Exception {
        // Act
        mockMvc.perform(post("/product/save")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content("""
                        {
                        "name": "Iphone",
                        "description": "pro max",
                        "weight": "100",
                        "width": "50",
                        "length": "75",
                        "height": "90",
                        "departmentName": "Electronic"
                        }
                         """))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void updateLocation_shouldUpdateAndReturn200_WhenRequestValid() throws Exception {
        // Arrange
        Department department = Department.builder()
                .id(1L)
                .name("Electronic")
                .description("none")
                .build();

        Product productRequest = Product.builder()
                .id(1L)
                .name("Iphone")
                .description("pro max")
                .weight(100)
                .width(50)
                .length(75)
                .height(90)
                .department(department)
                .build();

        Product productRespond= Product.builder()
                .id(1L)
                .name("Iphone")
                .description("pro max")
                .weight(100)
                .width(50)
                .length(75)
                .height(90)
                .department(department)
                .build();

        when(updateProductUseCaseMock.updateProduct(productRequest)).thenReturn(productRespond);
        when(getDepartmentUseCaseMock.findDepartmentByName("Electronic")).thenReturn(department);

        // Act
        mockMvc.perform(put("/product/1")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content("""
                        {
                        "id":"1",
                        "name": "Iphone",
                        "description": "pro max",
                        "weight": "100",
                        "width": "50",
                        "length": "75",
                        "height": "90",
                        "departmentName": "Electronic"
                        }
                         """))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("Content-Type",
                        APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                                    {"id": 1}
                                    """));

        // Assert
        verify(updateProductUseCaseMock).updateProduct(productRequest);
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void updateProduct_shouldReturnError_WhenProductNameExists() throws Exception {
        // Arrange
        Department department = Department.builder()
                .id(1L)
                .name("Electronic")
                .description("none")
                .build();

        Product productRequest = Product.builder()
                .id(1L)
                .name("Iphone")
                .description("pro max")
                .weight(100)
                .width(50)
                .length(75)
                .height(90)
                .department(department)
                .build();

        when(updateProductUseCaseMock.updateProduct(productRequest)).thenThrow(new ProductNameAlreadyExistsException());
        when(getDepartmentUseCaseMock.findDepartmentByName("Electronic")).thenReturn(department);

        // Act
        mockMvc.perform(put("/product/1")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content("""
                        {
                        "id":"1",
                        "name": "Iphone",
                        "description": "pro max",
                        "weight": "100",
                        "width": "50",
                        "length": "75",
                        "height": "90",
                        "departmentName": "Electronic"
                        }
                         """))
                .andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ProductNameAlreadyExistsException));

        // Assert
        verify(updateProductUseCaseMock).updateProduct(productRequest);
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void updateProduct_shouldReturnError_WhenIdNotFound() throws Exception {
        // Arrange
        Department department = Department.builder()
                .id(1L)
                .name("Electronic")
                .description("none")
                .build();

        Product productRequest = Product.builder()
                .id(1L)
                .name("Iphone")
                .description("pro max")
                .weight(100)
                .width(50)
                .length(75)
                .height(90)
                .department(department)
                .build();

        when(updateProductUseCaseMock.updateProduct(productRequest)).thenThrow(new ProductNotFoundException());
        when(getDepartmentUseCaseMock.findDepartmentByName("Electronic")).thenReturn(department);

        // Act
        mockMvc.perform(put("/product/1")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content("""
                        {
                        "id":"1",
                        "name": "Iphone",
                        "description": "pro max",
                        "weight": "100",
                        "width": "50",
                        "length": "75",
                        "height": "90",
                        "departmentName": "Electronic"
                        }
                         """))
                .andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ProductNotFoundException));

        // Assert
        verify(updateProductUseCaseMock).updateProduct(productRequest);
    }
    @Test
    void updateProduct_shouldReturn401_WhenUnauthenticated() throws Exception {
        // Act
        mockMvc.perform(put("/product/1")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content("""
                        {
                        "id":"1",
                        "name": "Iphone",
                        "description": "pro max",
                        "weight": "100",
                        "width": "50",
                        "length": "75",
                        "height": "90",
                        "departmentName": "Electronic"
                        }
                         """))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
    @Test
    @WithMockUser(roles = "HR")
    void updateProduct_shouldCreateAndReturn403_WhenUnauthorized() throws Exception {
        // Act
        mockMvc.perform(put("/product/1")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content("""
                        {
                        "id":"1",
                        "name": "Iphone",
                        "description": "pro max",
                        "weight": "100",
                        "width": "50",
                        "length": "75",
                        "height": "90",
                        "departmentName": "Electronic"
                        }
                         """))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteProduct_shouldCreateAndReturnError_WhenIdNotFound() throws Exception {
        // Arrange
        when(deleteProductUseCaseMock.deleteProduct(1L)).thenThrow(new ProductNotFoundException());

        // Act
        mockMvc.perform(delete("/product/1")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content("""
                        {
                        }
                         """))
                .andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ProductNotFoundException));

        // Assert
        verify(deleteProductUseCaseMock).deleteProduct(1L);
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void delete_ProductShouldReturnError_WhenProductInPallets() throws Exception {
        // Arrange
        when(deleteProductUseCaseMock.deleteProduct(1L)).thenThrow(new ProductExistsInSomePalletsException());

        // Act
        mockMvc.perform(delete("/product/1")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content("""
                        {
                        }
                         """))
                .andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ProductExistsInSomePalletsException));

        // Assert
        verify(deleteProductUseCaseMock).deleteProduct(1L);
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteProduct_shouldReturnOk_WhenProductDeleted() throws Exception {
        // Arrange
        DeleteResponse response = DeleteResponse.builder().result("Product with id:1 is deleted...").build();

        when(deleteProductUseCaseMock.deleteProduct(1L)).thenReturn(response);

        // Act
        mockMvc.perform(delete("/product/1")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content("""
                        {
                        }
                         """))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type",
                        APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                                    {"result": "Product with id:1 is deleted..." }
                                    """));

        // Assert
        verify(deleteProductUseCaseMock).deleteProduct(1L);
    }
}