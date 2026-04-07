package com.pixgateway.backend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;

import com.pixgateway.backend.domain.Cobranca;
import com.pixgateway.backend.repository.CobrancaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CobrancaServiceTest {

    @Mock
    private CobrancaRepository repository; // "Fingimos" o banco

    @InjectMocks
    private CobrancaService service; // Onde os mocks serão injetados

    @Test
    @DisplayName("Deve confirmar pagamento com sucesso quando ID existe")
    void pagarCobrancaSucesso() {
        // Arrange (Preparação)
        UUID id = UUID.randomUUID();
        Cobranca cobranca = new Cobranca();
        cobranca.setId(id);
        cobranca.setStatus("PENDENTE");

        when(repository.findById(id)).thenReturn(Optional.of(cobranca));
        when(repository.save(any())).thenReturn(cobranca);

        // Act (Ação)
        Cobranca resultado = service.pagarCobranca(id);

        // Assert (Verificação)
        assertEquals("PAGO", resultado.getStatus());
        assertNotNull(resultado.getDataPagamento());
        verify(repository, times(1)).save(cobranca); // Garante que salvou no banco
    }

    @Test
    @DisplayName("Deve lançar exceção quando a cobrança não for encontrada")
    void pagarCobrancaNaoEncontrada() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            service.pagarCobranca(id);
        });

        assertEquals("Cobrança não encontrada!", exception.getMessage());
    }
}