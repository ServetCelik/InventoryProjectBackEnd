package nl.inventory_management.controller;

import nl.inventory_management.business.domain.Department;
import nl.inventory_management.business.exception.DepartmentAlreadyExistsException;
import nl.inventory_management.business.exception.DepartmentDescriptionTooShortException;
import nl.inventory_management.business.exception.DepartmentHasSomeProductsException;
import nl.inventory_management.business.exception.DepartmentNotFoundException;
import nl.inventory_management.business.interfaces.CreateDepartmentUseCase;
import nl.inventory_management.business.interfaces.DeleteDepartmentUseCase;
import nl.inventory_management.business.interfaces.GetDepartmentUseCase;
import nl.inventory_management.business.interfaces.UpdateDepartmentUseCase;
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
@AutoConfigureMockMvc()
class DepartmentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GetDepartmentUseCase getDepartmentUseCaseMock;
    @MockBean
    private CreateDepartmentUseCase createDepartmentUseCaseMock;
    @MockBean
    private UpdateDepartmentUseCase updateDepartmentUseCaseMock;
    @MockBean
    private DeleteDepartmentUseCase deleteDepartmentUseCaseMock;


    @Test
    @WithMockUser(roles = "ADMIN")
    void getAllDepartments_shouldReturn200ResponseWithLDepartmenArray() throws Exception {
        // Arrange
        List<Department> departmentList = List.of(Department.builder()
                       .id(1L).name("Electronics").description("none").build()
               , Department.builder().id(2L).name("Sport").description("none").build());

        when(getDepartmentUseCaseMock.getAllDepartments())
                .thenReturn(departmentList);

        // Act
        mockMvc.perform(get("/department/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                        [{"id":1,"name":"Electronics","description":"none"},
                        {"id":2,"name":"Sport","description":"none"}]
                        """));

        // Assert
        verify(getDepartmentUseCaseMock).getAllDepartments();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getDepartment_shouldReturn200ResponseWithDepartment() throws Exception {
        // Arrange
        Department response = Department.builder()
                .id(1L)
                .name("Electronic")
                .description("none")
                .build();

        when(getDepartmentUseCaseMock.getDepartmentById(1L))
                .thenReturn(response);

        // Act
        mockMvc.perform(get("/department/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                        {"id":1,"name":"Electronic","description":"none"}
                        """));

        // Assert
        verify(getDepartmentUseCaseMock).getDepartmentById(1L);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createDepartment_shouldCreateAndReturn201_WhenRequestValid() throws Exception {
        // Arrange
        Department depRequest = Department
                .builder().name("Electronic").description("none").build();
        Department depRespond = Department
                .builder().id(1L).name("Electronic").description("none").build();

        when(createDepartmentUseCaseMock.createDepartment(depRequest)).thenReturn(depRespond);

        // Act
        mockMvc.perform(post("/department/save")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content("""
                        {
                        "name": "Electronic",
                        "description": "none"
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
        verify(createDepartmentUseCaseMock).createDepartment(depRequest);
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void createDepartment_shouldCreateAndReturnError_WhenDepartmentNameExists() throws Exception {
        // Arrange
        Department depRequest = Department
                .builder().name("Electronic").description("none").build();

        when(createDepartmentUseCaseMock.createDepartment(depRequest)).thenThrow(new DepartmentAlreadyExistsException());

        // Act
        mockMvc.perform(post("/department/save")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content("""
                        {
                        "name": "Electronic",
                        "description": "none"
                        }
                         """))
                .andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof DepartmentAlreadyExistsException));

        // Assert
        verify(createDepartmentUseCaseMock).createDepartment(depRequest);
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void createDepartment_shouldCreateAndReturnError_WhenCredentialsAreNotCorrectFormat() throws Exception {
        // Arrange
        Department depRequest = Department
                .builder().name("Electronic").description("no").build();

        when(createDepartmentUseCaseMock.createDepartment(depRequest)).thenThrow(new DepartmentDescriptionTooShortException());

        // Act
        mockMvc.perform(post("/department/save")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content("""
                        {
                        "name": "Electronic",
                        "description": "no"
                        }
                         """))
                .andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof DepartmentDescriptionTooShortException));

        // Assert
        verify(createDepartmentUseCaseMock).createDepartment(depRequest);
    }
    @Test
    void createDepartment_shouldCreateAndReturn401_WhenUnauthenticated() throws Exception {
        // Act
        mockMvc.perform(post("/department/save")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content("""
                        {
                        "name": "Electronic",
                        "description": "none"
                        }
                         """))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
    @Test
    @WithMockUser(roles = "HR")
    void createDepartment_shouldCreateAndReturn403_WhenUnauthorized() throws Exception {
        // Act
        mockMvc.perform(post("/department/save")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content("""
                        {
                        "name": "Electronic",
                        "description": "none"
                        }
                         """))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void updateDepartment_shouldUpdateAndReturn200_WhenRequestValid() throws Exception {
        // Arrange
        Department depRequest = Department
                .builder().id(1L).name("Electronic").description("none").build();
        Department depRespond = Department
                .builder().id(1L).name("Electronic").description("none").build();

        when(updateDepartmentUseCaseMock.updateDepartment(depRequest)).thenReturn(depRespond);

        // Act
        mockMvc.perform(put("/department/1")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content("""
                        {
                        "name": "Electronic",
                        "description": "none"
                        }
                         """))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type",
                        APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                                    {"id": 1}
                                    """));

        // Assert
        verify(updateDepartmentUseCaseMock).updateDepartment(depRequest);
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void updateDepartment_shouldReturnError_WhenDepartmentNameExists() throws Exception {
        // Arrange
        Department depRequest = Department
                .builder().id(1L).name("Electronic").description("none").build();

        when(updateDepartmentUseCaseMock.updateDepartment(depRequest)).thenThrow(new DepartmentAlreadyExistsException());

        // Act
        mockMvc.perform(put("/department/1")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content("""
                        {
                        "name": "Electronic",
                        "description": "none"
                        }
                         """))
                .andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof DepartmentAlreadyExistsException));

        // Assert
        verify(updateDepartmentUseCaseMock).updateDepartment(depRequest);
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void updateDepartment_shouldReturnError_WhenIdNotFound() throws Exception {
        // Arrange
        Department depRequest = Department
                .builder().id(1L).name("Electronic").description("none").build();

        when(updateDepartmentUseCaseMock.updateDepartment(depRequest)).thenThrow(new DepartmentNotFoundException());

        // Act
        mockMvc.perform(put("/department/1")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content("""
                        {
                        "name": "Electronic",
                        "description": "none"
                        }
                         """))
                .andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof DepartmentNotFoundException));

        // Assert
        verify(updateDepartmentUseCaseMock).updateDepartment(depRequest);
    }
    @Test
    void updateDepartment_shouldReturn401_WhenUnauthenticated() throws Exception {
        // Act
        mockMvc.perform(put("/department/1")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content("""
                        {
                        "name": "Electronic",
                        "description": "none"
                        }
                         """))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
    @Test
    @WithMockUser(roles = "HR")
    void updateDepartment_shouldReturn403_WhenUnauthorized() throws Exception {
        // Act
        mockMvc.perform(put("/department/1")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content("""
                        {
                        "name": "Electronic",
                        "description": "none"
                        }
                         """))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteDepartment_shouldReturnError_WhenIdNotFound() throws Exception {
        // Arrange
        when(deleteDepartmentUseCaseMock.deleteDepartment(1L)).thenThrow(new DepartmentNotFoundException());

        // Act
        mockMvc.perform(delete("/department/1")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content("""
                        {
                        }
                         """))
                .andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof DepartmentNotFoundException));

        // Assert
        verify(deleteDepartmentUseCaseMock).deleteDepartment(1L);
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteDepartment_shouldReturnError_WhenLocationHasProducts() throws Exception {
        // Arrange
        when(deleteDepartmentUseCaseMock.deleteDepartment(1L)).thenThrow(new DepartmentHasSomeProductsException());

        // Act
        mockMvc.perform(delete("/department/1")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content("""
                        {
                        }
                         """))
                .andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof DepartmentHasSomeProductsException));

        // Assert
        verify(deleteDepartmentUseCaseMock).deleteDepartment(1L);
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteDepartment_shouldCreateAndReturnOk_WhenDepartmentDeleted() throws Exception {
        // Arrange
        DeleteResponse response = DeleteResponse.builder().result("Department with id:1L is deleted...").build();

        when(deleteDepartmentUseCaseMock.deleteDepartment(1L)).thenReturn(response);

        // Act
        mockMvc.perform(delete("/department/1")
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
                                    {"result": "Department with id:1L is deleted..." }
                                    """));

        // Assert
        verify(deleteDepartmentUseCaseMock).deleteDepartment(1L);
    }

}