package nl.inventory_management.controller;

import nl.inventory_management.business.domain.Location;
import nl.inventory_management.business.exception.LocationHasSomePalletsException;
import nl.inventory_management.business.exception.LocationNameAlreadyExistsException;
import nl.inventory_management.business.exception.LocationNotFoundException;
import nl.inventory_management.business.exception.UnsupportedLocationFieldsException;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
//@AutoConfigureMockMvc
@AutoConfigureMockMvc(addFilters = false)
class LocationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GetLocationUseCase getLocationUseCaseMock;
    @MockBean
    private CreateLocationUseCase createLocationUseCaseMock;
    @MockBean
    private UpdateLocationUseCase updateLocationUseCaseMock;
    @MockBean
    private DeleteLocationUseCase deleteLocationUseCaseMock;

    @Test
    @WithMockUser(roles = "ADMIN")
    void getAllLocation_shouldReturn200ResponseWithLocationArray() throws Exception {
        // Arrange
        Location response = Location.builder()
                .id(1L)
                .name("Eindhoven")
                .address("Centrum")
                .maxPallet(250)
                .build();

        Location secondResponse = Location.builder()
                .id(2L)
                .name("Arnhem")
                .address("Centrum")
                .maxPallet(250)
                .build();

        when(getLocationUseCaseMock.getAllLocations())
                .thenReturn(List.of(response, secondResponse));

        // Act
        mockMvc.perform(get("/location/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                        [{"id":1,"name":"Eindhoven","address":"Centrum","maxPallet": 250},
                        {"id":2,"name":"Arnhem","address":"Centrum","maxPallet": 250}]
                        """));

        // Assert
        verify(getLocationUseCaseMock).getAllLocations();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getLocation_shouldReturn200ResponseWithLocation() throws Exception {
        // Arrange
        Location response = Location.builder()
                .id(1L)
                .name("Eindhoven")
                .address("Centrum")
                .maxPallet(250)
                .build();

        when(getLocationUseCaseMock.getLocationById(1L))
                .thenReturn(response);

        // Act
        mockMvc.perform(get("/location/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                        {"id":1,"name":"Eindhoven","address":"Centrum","maxPallet": 250}
                        """));

        // Assert
        verify(getLocationUseCaseMock).getLocationById(1L);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createLocation_shouldCreateAndReturn201_WhenRequestValid() throws Exception {
        // Arrange
        Location locRequest = Location
                .builder().name("Eindhoven Depot").address("Eindhoven").maxPallet(888).build();
        Location locRespond = Location
                .builder().id(1L).name("Eindhoven Depot").address("Eindhoven").maxPallet(888).build();

        when(createLocationUseCaseMock.createLocation(locRequest)).thenReturn(locRespond);

        // Act
        mockMvc.perform(post("/location/save")
                .contentType(APPLICATION_JSON_VALUE)
                .content(""" 
                        { 
                        "name": "Eindhoven Depot", 
                        "address": "Eindhoven",
                        "maxPallet": "888"  
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
        verify(createLocationUseCaseMock).createLocation(locRequest);
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void createLocation_shouldCreateAndReturnError_WhenLocationNameExists() throws Exception {
        // Arrange
        Location locRequest = Location
                .builder().name("Eindhoven Depot").address("Eindhoven").maxPallet(888).build();

        when(createLocationUseCaseMock.createLocation(locRequest)).thenThrow(new LocationNameAlreadyExistsException());

        // Act
        mockMvc.perform(post("/location/save")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(""" 
                        { 
                        "name": "Eindhoven Depot", 
                        "address": "Eindhoven",
                        "maxPallet": "888"  
                        }
                         """))
                .andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof LocationNameAlreadyExistsException));

        // Assert
        verify(createLocationUseCaseMock).createLocation(locRequest);
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void createLocation_shouldCreateAndReturnError_WhenCredentialsAreNotCorrectFormat() throws Exception {
        // Arrange
        Location locRequest = Location
                .builder().name("E").address("Eindhoven").maxPallet(888).build();

        when(createLocationUseCaseMock.createLocation(locRequest)).thenThrow(new UnsupportedLocationFieldsException());

        // Act
        mockMvc.perform(post("/location/save")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(""" 
                        { 
                        "name": "E", 
                        "address": "Eindhoven",
                        "maxPallet": "888"  
                        }
                         """))
                .andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UnsupportedLocationFieldsException));

        // Assert
        verify(createLocationUseCaseMock).createLocation(locRequest);
    }
    @Test
    void createLocation_shouldCreateAndReturn401_WhenUnauthenticated() throws Exception {
        // Act
        mockMvc.perform(post("/location/save")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(""" 
                        { 
                        "name": "Eindhoven Depot", 
                        "address": "Eindhoven",
                        "maxPallet": "888"  
                        }
                         """))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
    @Test
    @WithMockUser(roles = "HR")
    void createLocation_shouldCreateAndReturn403_WhenUnauthorized() throws Exception {
        // Act
        mockMvc.perform(post("/location/save")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(""" 
                        { 
                        "name": "Eindhoven Depot", 
                        "address": "Eindhoven",
                        "maxPallet": "888"  
                        }
                         """))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void updateLocation_shouldUpdateAndReturn200_WhenRequestValid() throws Exception {
        // Arrange
        Location locRequest = Location
                .builder().id(1L).name("Eindhoven Depot").address("Eindhoven").maxPallet(888).build();
        Location locRespond = Location
                .builder().id(1L).name("Eindhoven Depot").address("Eindhoven").maxPallet(888).build();

        when(updateLocationUseCaseMock.updateLocation(locRequest)).thenReturn(locRespond);

        // Act
        mockMvc.perform(put("/location/1")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(""" 
                        { 
                        "name": "Eindhoven Depot", 
                        "address": "Eindhoven",
                        "maxPallet": "888"  
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
        verify(updateLocationUseCaseMock).updateLocation(locRequest);
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void updateLocation_shouldCreateAndReturnError_WhenLocationNameExists() throws Exception {
        // Arrange
        Location locRequest = Location
                .builder().id(1L).name("Eindhoven Depot").address("Eindhoven").maxPallet(888).build();

        when(updateLocationUseCaseMock.updateLocation(locRequest)).thenThrow(new LocationNameAlreadyExistsException());

        // Act
        mockMvc.perform(put("/location/1")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(""" 
                        {
                        "id": 1,
                        "name": "Eindhoven Depot", 
                        "address": "Eindhoven",
                        "maxPallet": "888"  
                        }
                         """))
                .andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof LocationNameAlreadyExistsException));

        // Assert
        verify(updateLocationUseCaseMock).updateLocation(locRequest);
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void updateLocation_shouldCreateAndReturnError_WhenIdNotFound() throws Exception {
        // Arrange
        Location locRequest = Location
                .builder().id(1L).name("Eindhoven Depot").address("Eindhoven").maxPallet(888).build();

        when(updateLocationUseCaseMock.updateLocation(locRequest)).thenThrow(new LocationNotFoundException());

        // Act
        mockMvc.perform(put("/location/1")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(""" 
                        {
                        "id": 1,
                        "name": "Eindhoven Depot", 
                        "address": "Eindhoven",
                        "maxPallet": "888"  
                        }
                         """))
                .andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof LocationNotFoundException));

        // Assert
        verify(updateLocationUseCaseMock).updateLocation(locRequest);
    }
    @Test
    void updateLocation_shouldCreateAndReturn401_WhenUnauthenticated() throws Exception {
        // Act
        mockMvc.perform(put("/location/1")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(""" 
                        { 
                        "name": "Eindhoven Depot", 
                        "address": "Eindhoven",
                        "maxPallet": "888"  
                        }
                         """))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
    @Test
    @WithMockUser(roles = "HR")
    void updateLocation_shouldCreateAndReturn403_WhenUnauthorized() throws Exception {
        // Act
        mockMvc.perform(put("/location/1")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(""" 
                        { 
                        "name": "Eindhoven Depot", 
                        "address": "Eindhoven",
                        "maxPallet": "888"  
                        }
                         """))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteLocation_shouldCreateAndReturnError_WhenIdNotFound() throws Exception {
        // Arrange
        when(deleteLocationUseCaseMock.deleteLocation(1L)).thenThrow(new LocationNotFoundException());

        // Act
        mockMvc.perform(delete("/location/1")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(""" 
                        { 
                        }
                         """))
                .andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof LocationNotFoundException));

        // Assert
        verify(deleteLocationUseCaseMock).deleteLocation(1L);
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteLocation_shouldCreateAndReturnError_WhenLocationHasPallets() throws Exception {
        // Arrange
        when(deleteLocationUseCaseMock.deleteLocation(1L)).thenThrow(new LocationHasSomePalletsException());

        // Act
        mockMvc.perform(delete("/location/1")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(""" 
                        { 
                        }
                         """))
                .andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof LocationHasSomePalletsException));

        // Assert
        verify(deleteLocationUseCaseMock).deleteLocation(1L);
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteLocation_shouldCreateAndReturnOk_WhenLocationDeleted() throws Exception {
        // Arrange
        DeleteResponse response = DeleteResponse.builder().result("Location with id:1L is deleted...").build();

        when(deleteLocationUseCaseMock.deleteLocation(1L)).thenReturn(response);

        // Act
        mockMvc.perform(delete("/location/1")
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
                                    {"result": "Location with id:1L is deleted..." }
                                    """));

        // Assert
        verify(deleteLocationUseCaseMock).deleteLocation(1L);
    }
}