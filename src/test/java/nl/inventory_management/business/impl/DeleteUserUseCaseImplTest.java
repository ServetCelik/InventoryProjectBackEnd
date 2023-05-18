package nl.inventory_management.business.impl;

import nl.inventory_management.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeleteUserUseCaseImplTest {
    @Mock
    private UserRepository userRepositoryMock;
    @InjectMocks
    private DeleteUserUseCaseImpl deleteUserUseCaseMock;
    @Test
    void deleteUser() {
        // Arrange
        when(userRepositoryMock.existsById(1L)).thenReturn(true);

        // Act
        deleteUserUseCaseMock.deleteUser(1L);

        // Assert
        verify(userRepositoryMock, times(1)).deleteById(1L);
        verify(userRepositoryMock).existsById(1L);
    }
}