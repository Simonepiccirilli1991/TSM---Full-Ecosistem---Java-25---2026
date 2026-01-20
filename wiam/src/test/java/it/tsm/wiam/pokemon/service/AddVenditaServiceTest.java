package it.tsm.wiam.pokemon.service;

import it.tsm.wiam.pokemon.entity.PokemonCard;
import it.tsm.wiam.pokemon.entity.PokemonSealed;
import it.tsm.wiam.pokemon.exception.PokemonException;
import it.tsm.wiam.pokemon.model.AddVenditaRequest;
import it.tsm.wiam.pokemon.model.Vendita;
import it.tsm.wiam.pokemon.repository.PokemonCardRepo;
import it.tsm.wiam.pokemon.repository.PokemonSealedRepo;
import it.tsm.wiam.pokemon.util.PokemonCostants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddVenditaServiceTest {

    @Mock
    private PokemonCardRepo pokemonCardRepo;

    @Mock
    private PokemonSealedRepo pokemonSealedRepo;

    @InjectMocks
    private AddVenditaService addVenditaService;

    private PokemonCard testCard;
    private PokemonSealed testSealed;
    private Vendita testVendita;

    @BeforeEach
    void setUp() {
        testCard = new PokemonCard();
        testCard.setId("PKM-CARD-test123");
        testCard.setNome("Test Card");
        testCard.setStato(PokemonCostants.Stati.ACQUISTATO);

        testSealed = new PokemonSealed();
        testSealed.setId("PKM-SEALED-test123");
        testSealed.setNome("Test Sealed");
        testSealed.setStato(PokemonCostants.Stati.ACQUISTATO);

        testVendita = new Vendita();
        testVendita.setDataVendita("2025-01-20");
        testVendita.setPrezzoVendita(75.0);
        testVendita.setCostiVendita(5.0);
        testVendita.setPiattaformaVendita("eBay");
    }

    @Test
    void testAddVenditaCartaSuccess() {
        // Arrange
        var request = new AddVenditaRequest(
                "PKM-CARD-test123",
                testVendita,
                PokemonCostants.TipoProdotto.CARD
        );

        when(pokemonCardRepo.findById("PKM-CARD-test123")).thenReturn(Optional.of(testCard));
        when(pokemonCardRepo.save(any(PokemonCard.class))).thenReturn(testCard);

        // Act
        var response = addVenditaService.addVenditaPokemonService(request);

        // Assert
        assertNotNull(response);
        assertEquals("Vendita aggiunta con successo", response.messaggio());
        assertNotNull(response.cartaPokemon());
        assertNull(response.pokemonSealed());
        verify(pokemonCardRepo, times(1)).save(any(PokemonCard.class));
    }

    @Test
    void testAddVenditaSealedSuccess() {
        // Arrange
        var request = new AddVenditaRequest(
                "PKM-SEALED-test123",
                testVendita,
                PokemonCostants.TipoProdotto.SEALED
        );

        when(pokemonSealedRepo.findById(any(String.class))).thenReturn(Optional.of(testSealed));
        when(pokemonSealedRepo.save(any(PokemonSealed.class))).thenReturn(testSealed);

        // Act
        var response = addVenditaService.addVenditaPokemonService(request);

        // Assert
        assertNotNull(response);
        assertEquals("Vendita aggiunta con successo", response.messaggio());
        assertNull(response.cartaPokemon());
        assertNotNull(response.pokemonSealed());
        verify(pokemonSealedRepo, times(1)).save(any(PokemonSealed.class));
    }

    @Test
    void testAddVenditaCartaNotFound() {
        // Arrange
        var request = new AddVenditaRequest(
                "PKM-CARD-notfound",
                testVendita,
                PokemonCostants.TipoProdotto.CARD
        );

        when(pokemonCardRepo.findById("PKM-CARD-notfound")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(PokemonException.class, () -> addVenditaService.addVenditaPokemonService(request));
        verify(pokemonCardRepo, never()).save(any());
    }

    @Test
    void testAddVenditaSealedNotFound() {
        // Arrange
        var request = new AddVenditaRequest(
                "PKM-SEALED-notfound",
                testVendita,
                PokemonCostants.TipoProdotto.SEALED
        );

        when(pokemonSealedRepo.findById("PKM-SEALED-notfound")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(PokemonException.class, () -> addVenditaService.addVenditaPokemonService(request));
        verify(pokemonSealedRepo, never()).save(any());
    }

    @Test
    void testAddVenditaMissingParameter() {
        // Arrange
        var invalidRequest = new AddVenditaRequest(
                null,
                testVendita,
                PokemonCostants.TipoProdotto.CARD
        );

        // Act & Assert
        assertThrows(PokemonException.class, () -> addVenditaService.addVenditaPokemonService(invalidRequest));
    }

    @Test
    void testAddVenditaInvalidTipoProdotto() {
        // Arrange
        var invalidRequest = new AddVenditaRequest(
                "PKM-CARD-test123",
                testVendita,
                "INVALID"
        );

        // Act & Assert
        assertThrows(PokemonException.class, () -> addVenditaService.addVenditaPokemonService(invalidRequest));
    }
}
