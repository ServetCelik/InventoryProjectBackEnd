package nl.inventory_management.business.impl;

import nl.inventory_management.business.domain.Invoice;
import nl.inventory_management.business.domain.Product;
import nl.inventory_management.repository.InvoiceRepository;
import nl.inventory_management.repository.ProductRepository;
import nl.inventory_management.repository.entity.InvoiceEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetInvoiceUseCaseImplTest {
    @Mock
    private InvoiceRepository invoiceRepositoryMock;
    @InjectMocks
    private GetInvoiceUseCaseImpl getInvoiceUseCase;
    @Test
    void getAllInvoicesShouldReturnList() {
        // Arrange
        InvoiceEntity firstInvoice = InvoiceEntity.builder().id(1L).productId(1L).productName("Iphone").locationName("amsterdam").amount(150).date(LocalDateTime.parse("2020-01-14T20:11:00.455317")).build();
        InvoiceEntity secondInvoice = InvoiceEntity.builder().id(2L).productId(1L).productName("Iphone").locationName("amsterdam").amount(250).date(LocalDateTime.parse("2019-01-14T20:11:00.455317")).build();
        InvoiceEntity thirdInvoice = InvoiceEntity.builder().id(3L).productId(1L).productName("Iphone").locationName("amsterdam").amount(350).date(LocalDateTime.parse("2015-01-14T20:11:00.455317")).build();

        when(invoiceRepositoryMock.findAll()).thenReturn(List.of(firstInvoice,secondInvoice,thirdInvoice));

        // Act
        List<Invoice> actualResult = getInvoiceUseCase.getAllInvoices();
        Integer expectedResult = 3;

        // Assert
        assertEquals(actualResult.size(),expectedResult);
        verify(invoiceRepositoryMock).findAll();

    }

    @Test
    void getStatisticByByProductIdShouldReturnAlist() {
        // Arrange
        InvoiceEntity firstInvoice = InvoiceEntity.builder().id(1L).productId(1L).productName("Iphone").locationName("amsterdam").amount(150).date(LocalDateTime.parse("2020-01-14T20:11:00.455317")).build();
        InvoiceEntity secondInvoice = InvoiceEntity.builder().id(2L).productId(1L).productName("Iphone").locationName("amsterdam").amount(250).date(LocalDateTime.parse("2019-01-14T20:11:00.455317")).build();
        InvoiceEntity thirdInvoice = InvoiceEntity.builder().id(3L).productId(1L).productName("Iphone").locationName("amsterdam").amount(350).date(LocalDateTime.parse("2015-01-14T20:11:00.455317")).build();

        when(invoiceRepositoryMock.findAll()).thenReturn(List.of(firstInvoice,secondInvoice,thirdInvoice));

        // Act
        List<String> actualResult = getInvoiceUseCase.getStatisticByByProductId(1L,"2018-01-14T20:11:00.455317","2021-01-14T20:11:00.455317");
        Integer expectedResult = 4;

        // Assert
        assertEquals(actualResult.size(),expectedResult);
        verify(invoiceRepositoryMock).findAll();

    }
}