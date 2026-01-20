package it.tsm.wiam.pokemon.service;

import it.tsm.wiam.pokemon.entity.PokemonCard;
import it.tsm.wiam.pokemon.exception.PokemonException;
import it.tsm.wiam.pokemon.model.AddPokemonCardRequest;
import it.tsm.wiam.pokemon.repository.PokemonCardRepo;
import it.tsm.wiam.pokemon.util.PokemonCostants;
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
class PokemonCardServiceTest {

    @Mock
    private PokemonCardRepo pokemonCardRepo;

    @Mock
    private PokemonUtil pokemonUtil;

    @InjectMocks
    private PokemonCardService pokemonCardService;

    private AddPokemonCardRequest validRequest;
    private PokemonCard testCard;

    @BeforeEach
    void setUp() {
        validRequest = new AddPokemonCardRequest(
                "Test Card",
                LocalDateTime.now(),
                50.0,
                "Base Set",
                false,
                null,
                null,
                null,
                null
        );

        testCard = new PokemonCard();
        testCard.setId("PKM-CARD-test123");
        testCard.setNome("Test Card");
        testCard.setEspansione("Base Set");
        testCard.setPrezzoAcquisto(50.0);
        testCard.setStato(PokemonCostants.Stati.ACQUISTATO);
    }

    @Test
    void testAggiungiCartaPokemonSuccess() {
        // Arrange
        when(pokemonUtil.createIdPokemonCard()).thenReturn("PKM-CARD-test123");
        when(pokemonCardRepo.save(any(PokemonCard.class))).thenReturn(testCard);

        // Act
        var response = pokemonCardService.aggiungiCartaPokemon(validRequest);

        // Assert
        assertNotNull(response);
        assertEquals("Salvato pokemon card successfully", response.messaggio());
        assertEquals(testCard, response.carta());
        verify(pokemonCardRepo, times(1)).save(any(PokemonCard.class));
    }

    @Test
    void testAggiungiCartaPokemonWithGradazione() {
        // Arrange
        var requestWithGradazione = new AddPokemonCardRequest(
                "Graded Card",
                LocalDateTime.now(),
                100.0,
                "Base Set",
                true,
                "PSA",
                "10",
                "12345",
                null
        );

        testCard.setGradata(true);
        testCard.setCasaGradazione("PSA");
        testCard.setVotoGradazione("10");
        testCard.setCodiceGradazione("12345");

        when(pokemonUtil.createIdPokemonCard()).thenReturn("PKM-CARD-graded123");
        when(pokemonCardRepo.save(any(PokemonCard.class))).thenReturn(testCard);

        // Act
        var response = pokemonCardService.aggiungiCartaPokemon(requestWithGradazione);

        // Assert
        assertNotNull(response);
        assertTrue(response.carta().isGradata());
        assertEquals("PSA", response.carta().getCasaGradazione());
        verify(pokemonCardRepo, times(1)).save(any(PokemonCard.class));
    }

    @Test
    void testAggiungiCartaPokemonMissingParameter() {
        // Arrange
        var invalidRequest = new AddPokemonCardRequest(
                null, // nome mancante
                LocalDateTime.now(),
                50.0,
                "Base Set",
                false,
                null,
                null,
                null,
                null
        );

        // Act & Assert
        assertThrows(PokemonException.class, () -> pokemonCardService.aggiungiCartaPokemon(invalidRequest));
    }

    @Test
    void testCancellaCartaSuccess() {
        // Arrange
        when(pokemonCardRepo.findById("PKM-CARD-test123")).thenReturn(Optional.of(testCard));
        doNothing().when(pokemonCardRepo).delete(testCard);

        // Act & Assert
        assertDoesNotThrow(() -> pokemonCardService.cancellaCarta("PKM-CARD-test123"));
        verify(pokemonCardRepo, times(1)).delete(testCard);
    }

    @Test
    void testCancellaCartaNotFound() {
        // Arrange
        when(pokemonCardRepo.findById("PKM-CARD-notfound")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(PokemonException.class, () -> pokemonCardService.cancellaCarta("PKM-CARD-notfound"));
        verify(pokemonCardRepo, never()).delete(any());
    }

    @Test
    void testGetCartaByIdSuccess() {
        // Arrange
        when(pokemonCardRepo.findById("PKM-CARD-test123")).thenReturn(Optional.of(testCard));

        // Act
        var result = pokemonCardService.getCartaById("PKM-CARD-test123");

        // Assert
        assertNotNull(result);
        assertEquals(testCard.getId(), result.getId());
        assertEquals(testCard.getNome(), result.getNome());
    }

    @Test
    void testGetCartaByIdNotFound() {
        // Arrange
        when(pokemonCardRepo.findById("PKM-CARD-notfound")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(PokemonException.class, () -> pokemonCardService.getCartaById("PKM-CARD-notfound"));
    }

    @Test
    void testGetCartaByStatoSuccess() {
        // Arrange
        List<PokemonCard> cards = Arrays.asList(testCard);
        when(pokemonCardRepo.findByStato(PokemonCostants.Stati.ACQUISTATO)).thenReturn(cards);

        // Act
        var result = pokemonCardService.getCartaByStato(PokemonCostants.Stati.ACQUISTATO);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testCard, result.get(0));
    }

    @Test
    void testGetCartaByStatoEmpty() {
        // Arrange
        when(pokemonCardRepo.findByStato(PokemonCostants.Stati.VENDUTO)).thenReturn(Arrays.asList());

        // Act
        var result = pokemonCardService.getCartaByStato(PokemonCostants.Stati.VENDUTO);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.size());
    }
}
