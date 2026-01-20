package it.tsm.wiam.onepiece.model;

import it.tsm.wiam.onepiece.entity.OnePieceSealed;

public record AddOnePieceSealedResponse
        (String messaggio, OnePieceSealed carta) {
}
