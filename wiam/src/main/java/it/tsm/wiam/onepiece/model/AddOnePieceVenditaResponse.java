package it.tsm.wiam.onepiece.model;

import it.tsm.wiam.onepiece.entity.OnePieceCard;
import it.tsm.wiam.onepiece.entity.OnePieceSealed;

public record AddOnePieceVenditaResponse(

        String messaggio,
        OnePieceCard cartaOnePiece,
        OnePieceSealed onePieceSealed
) {
}
