package it.tsm.wiam.onepiece.service;

import it.tsm.wiam.onepiece.entity.OnePieceCard;
import it.tsm.wiam.onepiece.exception.OnePieceException;
import it.tsm.wiam.onepiece.model.AddOnePieceCardRequest;
import it.tsm.wiam.onepiece.repository.OnePieceCardRepo;
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
class OnePieceCardServiceTest {

    @Mock
    private OnePieceCardRepo onePieceCardRepo;

    @Mock
    private PokemonUtil pokemonUtil;

    @InjectMocks
    private OnePieceCardService onePieceCardService;

    private AddOnePieceCardRequest validRequest;
    private OnePieceCard testCard;

    @BeforeEach
    void setUp() {
        validRequest = new AddOnePieceCardRequest(
                "Test OnePiece Card",
                LocalDateTime.now(),
                75.0,
                "Starter Deck",
                false,
                null,
                null,
                null,
                null
        );

        testCard = new OnePieceCard();
        testCard.setId("OP-CARD-test123");
        testCard.setNome("Test OnePiece Card");
        testCard.setEspansione("Starter Deck");
        testCard.setPrezzoAcquisto(75.0);
        testCard.setStato(OnePieceCostants.Stati.ACQUISTATO);
    }

    @Test
    void testAggiungiCartaOnePieceSuccess() {
        // Arrange
        when(pokemonUtil.createIdOnePieceCard()).thenReturn("OP-CARD-test123");
        when(onePieceCardRepo.save(any(OnePieceCard.class))).thenReturn(testCard);

        // Act
        var response = onePieceCardService.aggiungiCartaOnePiece(validRequest);

        // Assert
        assertNotNull(response);
        assertEquals("Salvato onepiece card successfully", response.messaggio());
        assertEquals(testCard, response.carta());
        verify(onePieceCardRepo, times(1)).save(any(OnePieceCard.class));
    }

    @Test
    void testAggiungiCartaOnePieceWithGradazione() {
        // Arrange
        var requestWithGradazione = new AddOnePieceCardRequest(
                "Graded OnePiece Card",
                LocalDateTime.now(),
                150.0,
                "Starter Deck",
                true,
                "BGS",
                "9",
                "67890",
                null
        );

        testCard.setGradata(true);
        testCard.setCasaGradazione("BGS");
        testCard.setVotoGradazione("9");
        testCard.setCodiceGradazione("67890");

        when(pokemonUtil.createIdOnePieceCard()).thenReturn("OP-CARD-graded123");
        when(onePieceCardRepo.save(any(OnePieceCard.class))).thenReturn(testCard);

        // Act
        var response = onePieceCardService.aggiungiCartaOnePiece(requestWithGradazione);

        // Assert
        assertNotNull(response);
        assertTrue(response.carta().isGradata());
        assertEquals("BGS", response.carta().getCasaGradazione());
        verify(onePieceCardRepo, times(1)).save(any(OnePieceCard.class));
    }

    @Test
    void testAggiungiCartaOnePieceMissingParameter() {
        // Arrange
        var invalidRequest = new AddOnePieceCardRequest(
                null, // nome mancante
                LocalDateTime.now(),
                75.0,
                "Starter Deck",
                false,
                null,
                null,
                null,
                null
        );

        // Act & Assert
        assertThrows(OnePieceException.class, () -> onePieceCardService.aggiungiCartaOnePiece(invalidRequest));
    }

    @Test
    void testCancellaCartaSuccess() {
        // Arrange
        when(onePieceCardRepo.findById("OP-CARD-test123")).thenReturn(Optional.of(testCard));
        doNothing().when(onePieceCardRepo).delete(testCard);

        // Act & Assert
        assertDoesNotThrow(() -> onePieceCardService.cancellaCarta("OP-CARD-test123"));
        verify(onePieceCardRepo, times(1)).delete(testCard);
    }

    @Test
    void testCancellaCartaNotFound() {
        // Arrange
        when(onePieceCardRepo.findById("OP-CARD-notfound")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(OnePieceException.class, () -> onePieceCardService.cancellaCarta("OP-CARD-notfound"));
        verify(onePieceCardRepo, never()).delete(any());
    }

    @Test
    void testGetCartaByIdSuccess() {
        // Arrange
        when(onePieceCardRepo.findById("OP-CARD-test123")).thenReturn(Optional.of(testCard));

        // Act
        var result = onePieceCardService.getCartaById("OP-CARD-test123");

        // Assert
        assertNotNull(result);
        assertEquals(testCard.getId(), result.getId());
        assertEquals(testCard.getNome(), result.getNome());
    }

    @Test
    void testGetCartaByIdNotFound() {
        // Arrange
        when(onePieceCardRepo.findById("OP-CARD-notfound")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(OnePieceException.class, () -> onePieceCardService.getCartaById("OP-CARD-notfound"));
    }

    @Test
    void testGetCartaByStatoSuccess() {
        // Arrange
        List<OnePieceCard> cards = Arrays.asList(testCard);
        when(onePieceCardRepo.findByStato(OnePieceCostants.Stati.ACQUISTATO)).thenReturn(cards);

        // Act
        var result = onePieceCardService.getCartaByStato(OnePieceCostants.Stati.ACQUISTATO);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testCard, result.get(0));
    }

    @Test
    void testGetCartaByStatoEmpty() {
        // Arrange
        when(onePieceCardRepo.findByStato(OnePieceCostants.Stati.VENDUTO)).thenReturn(Arrays.asList());

        // Act
        var result = onePieceCardService.getCartaByStato(OnePieceCostants.Stati.VENDUTO);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.size());
    }
}
