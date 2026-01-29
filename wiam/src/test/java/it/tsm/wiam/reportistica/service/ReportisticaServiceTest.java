package it.tsm.wiam.reportistica.service;

import it.tsm.wiam.pokemon.service.PokemonCardService;
import it.tsm.wiam.pokemon.service.PokemonSealedService;
import it.tsm.wiam.onepiece.service.OnePieceCardService;
import it.tsm.wiam.onepiece.service.OnePieceSealedService;
import it.tsm.wiam.reportistica.model.ReportisticaRequest;
import it.tsm.wiam.reportistica.model.ReportisticaMensileRequest;
import it.tsm.wiam.reportistica.model.ReportisticaResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ReportisticaServiceTest {

    @InjectMocks
    private ReportisticaService reportisticaService;

    @Mock
    private PokemonCardService pokemonCardService;

    @Mock
    private PokemonSealedService pokemonSealedService;

    @Mock
    private OnePieceCardService onePieceCardService;

    @Mock
    private OnePieceSealedService onePieceSealedService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testReportDettaglio() {
        // Arrange
        ReportisticaRequest request = new ReportisticaRequest("Pokemon", "Available");
        when(pokemonCardService.getCartaByStato("Available")).thenReturn(new ArrayList<>());
        when(pokemonSealedService.getSealedByStato("Available")).thenReturn(new ArrayList<>());

        // Act
        ReportisticaResponse response = reportisticaService.reportDettaglio(request);

        // Assert
        assertNotNull(response);
        verify(pokemonCardService, times(1)).getCartaByStato("Available");
        verify(pokemonSealedService, times(1)).getSealedByStato("Available");
    }
}

class ReportisticaMensileServiceTest {

    @InjectMocks
    private ReportisticaMensileService reportisticaMensileService;

    @Mock
    private PokemonCardService pokemonCardService;

    @Mock
    private PokemonSealedService pokemonSealedService;

    @Mock
    private OnePieceCardService onePieceCardService;

    @Mock
    private OnePieceSealedService onePieceSealedService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testReportMensileAcquisti() {
        // Arrange
        ReportisticaMensileRequest request = new ReportisticaMensileRequest("Pokemon", LocalDateTime.now().minusMonths(1), LocalDateTime.now());
        when(pokemonCardService.filteringByStatoAndRangeTimeAcquistoCard(anyString(), anyString(), anyString())).thenReturn(new ArrayList<>());
        when(pokemonSealedService.filteringByStatoAndRangeTimeAcquistoSealed(anyString(), anyString(), anyString())).thenReturn(new ArrayList<>());

        // Act
        ReportisticaResponse response = reportisticaMensileService.reportMensileAcquisti(request);

        // Assert
        assertNotNull(response);
        verify(pokemonCardService, times(1)).filteringByStatoAndRangeTimeAcquistoCard(anyString(), anyString(), anyString());
        verify(pokemonSealedService, times(1)).filteringByStatoAndRangeTimeAcquistoSealed(anyString(), anyString(), anyString());
    }
}
