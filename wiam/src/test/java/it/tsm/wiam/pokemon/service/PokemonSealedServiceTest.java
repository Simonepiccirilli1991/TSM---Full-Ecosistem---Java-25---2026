package it.tsm.wiam.pokemon.service;

import it.tsm.wiam.pokemon.entity.PokemonSealed;
import it.tsm.wiam.pokemon.exception.PokemonException;
import it.tsm.wiam.pokemon.model.AddPokemonSealedRequest;
import it.tsm.wiam.pokemon.repository.PokemonSealedRepo;
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
class PokemonSealedServiceTest {

    @Mock
    private PokemonSealedRepo pokemonSealedRepo;

    @Mock
    private PokemonUtil pokemonUtil;

    @InjectMocks
    private PokemonSealedService pokemonSealedService;

    private AddPokemonSealedRequest validRequest;
    private PokemonSealed testSealed;

    @BeforeEach
    void setUp() {
        validRequest = new AddPokemonSealedRequest(
                "Test Sealed",
                LocalDateTime.now(),
                100.0,
                "Base Set",
                "PKM001",
                null
        );

        testSealed = new PokemonSealed();
        testSealed.setId("PKM-SEALED-test123");
        testSealed.setNome("Test Sealed");
        testSealed.setEspansione("Base Set");
        testSealed.setCodiceProdotto("PKM001");
        testSealed.setPrezzoAcquisto(100.0);
        testSealed.setStato(PokemonCostants.Stati.ACQUISTATO);
    }

    @Test
    void testAddPokemonSealedSuccess() {
        // Arrange
        when(pokemonUtil.createIdPokemonSealed()).thenReturn("PKM-SEALED-test123");
        when(pokemonSealedRepo.save(any(PokemonSealed.class))).thenReturn(testSealed);

        // Act
        var response = pokemonSealedService.addPokemonSealedService(validRequest);

        // Assert
        assertNotNull(response);
        assertEquals("Acquisto salvato con successo", response.messaggio());
        assertEquals(testSealed, response.carta());
        verify(pokemonSealedRepo, times(1)).save(any(PokemonSealed.class));
    }

    @Test
    void testAddPokemonSealedMissingParameter() {
        // Arrange
        var invalidRequest = new AddPokemonSealedRequest(
                null, // nome mancante
                LocalDateTime.now(),
                100.0,
                "Base Set",
                "PKM001",
                null
        );

        // Act & Assert
        assertThrows(PokemonException.class, () -> pokemonSealedService.addPokemonSealedService(invalidRequest));
    }

    @Test
    void testCancellaSealedSuccess() {
        // Arrange
        when(pokemonSealedRepo.findById("PKM-SEALED-test123")).thenReturn(Optional.of(testSealed));
        doNothing().when(pokemonSealedRepo).delete(testSealed);

        // Act & Assert
        assertDoesNotThrow(() -> pokemonSealedService.cancellaSealed("PKM-SEALED-test123"));
        verify(pokemonSealedRepo, times(1)).delete(testSealed);
    }

    @Test
    void testCancellaSealedNotFound() {
        // Arrange
        when(pokemonSealedRepo.findById("PKM-SEALED-notfound")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(PokemonException.class, () -> pokemonSealedService.cancellaSealed("PKM-SEALED-notfound"));
        verify(pokemonSealedRepo, never()).delete(any());
    }

    @Test
    void testGetSealedByIdSuccess() {
        // Arrange
        when(pokemonSealedRepo.findById("PKM-SEALED-test123")).thenReturn(Optional.of(testSealed));

        // Act
        var result = pokemonSealedService.getSealedById("PKM-SEALED-test123");

        // Assert
        assertNotNull(result);
        assertEquals(testSealed.getId(), result.getId());
        assertEquals(testSealed.getNome(), result.getNome());
    }

    @Test
    void testGetSealedByIdNotFound() {
        // Arrange
        when(pokemonSealedRepo.findById("PKM-SEALED-notfound")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(PokemonException.class, () -> pokemonSealedService.getSealedById("PKM-SEALED-notfound"));
    }

    @Test
    void testGetSealedByStatoSuccess() {
        // Arrange
        List<PokemonSealed> sealeds = Arrays.asList(testSealed);
        when(pokemonSealedRepo.findByStato(PokemonCostants.Stati.ACQUISTATO)).thenReturn(sealeds);

        // Act
        var result = pokemonSealedService.getSealedByStato(PokemonCostants.Stati.ACQUISTATO);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testSealed, result.get(0));
    }

    @Test
    void testGetSealedByStatoEmpty() {
        // Arrange
        when(pokemonSealedRepo.findByStato(PokemonCostants.Stati.VENDUTO)).thenReturn(Arrays.asList());

        // Act
        var result = pokemonSealedService.getSealedByStato(PokemonCostants.Stati.VENDUTO);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.size());
    }
}
