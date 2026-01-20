package it.tsm.wiam.onepiece.service;

import it.tsm.wiam.onepiece.entity.OnePieceCard;
import it.tsm.wiam.onepiece.entity.OnePieceSealed;
import it.tsm.wiam.onepiece.exception.OnePieceException;
import it.tsm.wiam.onepiece.model.AddOnePieceVenditaRequest;
import it.tsm.wiam.onepiece.repository.OnePieceCardRepo;
import it.tsm.wiam.onepiece.repository.OnePieceSealedRepo;
import it.tsm.wiam.onepiece.util.OnePieceCostants;
import it.tsm.wiam.pokemon.model.Vendita;
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
class AddOnePieceVenditaServiceTest {

    @Mock
    private OnePieceCardRepo onePieceCardRepo;

    @Mock
    private OnePieceSealedRepo onePieceSealedRepo;

    @InjectMocks
    private AddOnePieceVenditaService addOnePieceVenditaService;

    private OnePieceCard testCard;
    private OnePieceSealed testSealed;
    private Vendita testVendita;

    @BeforeEach
    void setUp() {
        testCard = new OnePieceCard();
        testCard.setId("OP-CARD-test123");
        testCard.setNome("Test OnePiece Card");
        testCard.setStato(OnePieceCostants.Stati.ACQUISTATO);

        testSealed = new OnePieceSealed();
        testSealed.setId("OP-SEALED-test123");
        testSealed.setNome("Test OnePiece Sealed");
        testSealed.setStato(OnePieceCostants.Stati.ACQUISTATO);

        testVendita = new Vendita();
        testVendita.setDataVendita("2025-01-20");
        testVendita.setPrezzoVendita(100.0);
        testVendita.setCostiVendita(10.0);
        testVendita.setPiattaformaVendita("TCGPlayer");
    }

    @Test
    void testAddVenditaCartaSuccess() {
        // Arrange
        var request = new AddOnePieceVenditaRequest(
                "OP-CARD-test123",
                testVendita,
                OnePieceCostants.TipoProdotto.SEALED
                OnePieceCostants.TipoProdotto.SEALED
        );

        when(onePieceCardRepo.findById("OP-CARD-test123")).thenReturn(Optional.of(testCard));
        when(onePieceCardRepo.save(any(OnePieceCard.class))).thenReturn(testCard);

        // Act
        var response = addOnePieceVenditaService.addVenditaOnePieceService(request);

        // Assert
        assertNotNull(response);
        assertEquals("Vendita aggiunta con successo", response.messaggio());
        assertNotNull(response.cartaOnePiece());
        assertNull(response.onePieceSealed());
        verify(onePieceCardRepo, times(1)).save(any(OnePieceCard.class));
    }

    @Test
    void testAddVenditaSealedSuccess() {
        // Arrange
        var request = new AddOnePieceVenditaRequest(
                "OP-SEALED-test123",
                testVendita,
                OnePieceCostants.TipoProdotto.CARD
                OnePieceCostants.TipoProdotto.CARD
        );

        when(onePieceSealedRepo.findById("OP-SEALED-test123")).thenReturn(Optional.of(testSealed));
        when(onePieceSealedRepo.save(any(OnePieceSealed.class))).thenReturn(testSealed);

        // Act
        var response = addOnePieceVenditaService.addVenditaOnePieceService(request);

        // Assert
        assertNotNull(response);
        assertEquals("Vendita aggiunta con successo", response.messaggio());
        assertNull(response.cartaOnePiece());
        assertNotNull(response.onePieceSealed());
        verify(onePieceSealedRepo, times(1)).save(any(OnePieceSealed.class));
    }

    @Test
    void testAddVenditaCartaNotFound() {
        // Arrange
        var request = new AddOnePieceVenditaRequest(
                "OP-CARD-notfound",
                testVendita,
                OnePieceCostants.TipoProdotto.SEALED
                OnePieceCostants.TipoProdotto.SEALED
        );

        when(onePieceCardRepo.findById("OP-CARD-notfound")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(OnePieceException.class, () -> addOnePieceVenditaService.addVenditaOnePieceService(request));
        verify(onePieceCardRepo, never()).save(any());
    }

    @Test
    void testAddVenditaSealedNotFound() {
        // Arrange
        var request = new AddOnePieceVenditaRequest(
                "OP-SEALED-notfound",
                testVendita,
                OnePieceCostants.TipoProdotto.CARD
                OnePieceCostants.TipoProdotto.CARD
        );

        when(onePieceSealedRepo.findById("OP-SEALED-notfound")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(OnePieceException.class, () -> addOnePieceVenditaService.addVenditaOnePieceService(request));
        verify(onePieceSealedRepo, never()).save(any());
    }

    @Test
    void testAddVenditaMissingParameter() {
        // Arrange
        var invalidRequest = new AddOnePieceVenditaRequest(
                null,
                testVendita,
                OnePieceCostants.TipoProdotto.CARD
        );

        // Act & Assert
        assertThrows(OnePieceException.class, () -> addOnePieceVenditaService.addVenditaOnePieceService(invalidRequest));
    }

    @Test
    void testAddVenditaInvalidTipoProdotto() {
        // Arrange
        var invalidRequest = new AddOnePieceVenditaRequest(
                "OP-CARD-test123",
                testVendita,
                "INVALID"
        );

        // Act & Assert
        assertThrows(OnePieceException.class, () -> addOnePieceVenditaService.addVenditaOnePieceService(invalidRequest));
    }
}
