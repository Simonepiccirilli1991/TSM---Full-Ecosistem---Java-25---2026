package it.tsm.wiam.onepiece.service;

import it.tsm.wiam.onepiece.entity.OnePieceSealed;
import it.tsm.wiam.onepiece.exception.OnePieceException;
import it.tsm.wiam.onepiece.model.AddOnePieceSealedRequest;
import it.tsm.wiam.onepiece.repository.OnePieceSealedRepo;
import it.tsm.wiam.onepiece.util.OnePieceCostants;
import it.tsm.wiam.pokemon.util.PokemonUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OnePieceSealedServiceTest {

    @Mock
    private OnePieceSealedRepo onePieceSealedRepo;

    @Mock
    private PokemonUtil pokemonUtil;

    @InjectMocks
    private OnePieceSealedService onePieceSealedService;

    private AddOnePieceSealedRequest validRequest;
    private OnePieceSealed testSealed;

    @BeforeEach
    void setUp() {
        validRequest = new AddOnePieceSealedRequest(
                "Test OnePiece Sealed",
                LocalDateTime.now(),
                150.0,
                "Starter Deck",
                "OP001",
                null
        );

        testSealed = new OnePieceSealed();
        testSealed.setId("OP-SEALED-test123");
        testSealed.setNome("Test OnePiece Sealed");
        testSealed.setEspansione("Starter Deck");
        testSealed.setCodiceProdotto("OP001");
        testSealed.setPrezzoAcquisto(150.0);
        testSealed.setStato(OnePieceCostants.Stati.ACQUISTATO);
    }

    @Test
    void testAddOnePieceSealedSuccess() {
        // Arrange
        when(pokemonUtil.createIdOnePieceSealed()).thenReturn("OP-SEALED-test123");
        when(onePieceSealedRepo.save(any(OnePieceSealed.class))).thenReturn(testSealed);

        // Act
        var response = onePieceSealedService.addOnePieceSealedService(validRequest);

        // Assert
        assertNotNull(response);
        assertEquals("Acquisto salvato con successo", response.messaggio());
        assertEquals(testSealed, response.carta());
        verify(onePieceSealedRepo, times(1)).save(any(OnePieceSealed.class));
    }

    @Test
    void testAddOnePieceSealedMissingParameter() {
        // Arrange
        var invalidRequest = new AddOnePieceSealedRequest(
                null, // nome mancante
                LocalDateTime.now(),
                150.0,
                "Starter Deck",
                "OP001",
                null
        );

        // Act & Assert
        assertThrows(OnePieceException.class, () -> onePieceSealedService.addOnePieceSealedService(invalidRequest));
    }

    @Test
    void testCancellaSealedSuccess() {
        // Arrange
        when(onePieceSealedRepo.findById("OP-SEALED-test123")).thenReturn(Optional.of(testSealed));
        doNothing().when(onePieceSealedRepo).delete(testSealed);

        // Act & Assert
        assertDoesNotThrow(() -> onePieceSealedService.cancellaSealed("OP-SEALED-test123"));
        verify(onePieceSealedRepo, times(1)).delete(testSealed);
    }

    @Test
    void testCancellaSealedNotFound() {
        // Arrange
        when(onePieceSealedRepo.findById("OP-SEALED-notfound")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(OnePieceException.class, () -> onePieceSealedService.cancellaSealed("OP-SEALED-notfound"));
        verify(onePieceSealedRepo, never()).delete(any());
    }

    @Test
    void testGetSealedByIdSuccess() {
        // Arrange
        when(onePieceSealedRepo.findById("OP-SEALED-test123")).thenReturn(Optional.of(testSealed));

        // Act
        var result = onePieceSealedService.getSealedById("OP-SEALED-test123");

        // Assert
        assertNotNull(result);
        assertEquals(testSealed.getId(), result.getId());
        assertEquals(testSealed.getNome(), result.getNome());
    }

    @Test
    void testGetSealedByIdNotFound() {
        // Arrange
        when(onePieceSealedRepo.findById("OP-SEALED-notfound")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(OnePieceException.class, () -> onePieceSealedService.getSealedById("OP-SEALED-notfound"));
    }

    @Test
    void testGetSealedByStatoSuccess() {
        // Arrange
        List<OnePieceSealed> sealeds = Arrays.asList(testSealed);
        when(onePieceSealedRepo.findByStato(OnePieceCostants.Stati.ACQUISTATO)).thenReturn(sealeds);

        // Act
        var result = onePieceSealedService.getSealedByStato(OnePieceCostants.Stati.ACQUISTATO);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testSealed, result.get(0));
    }

    @Test
    void testGetSealedByStatoEmpty() {
        // Arrange
        when(onePieceSealedRepo.findByStato(OnePieceCostants.Stati.VENDUTO)).thenReturn(Arrays.asList());

        // Act
        var result = onePieceSealedService.getSealedByStato(OnePieceCostants.Stati.VENDUTO);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.size());
    }
}
