package it.tsm.wiam.pokemon.util;

public class PokemonCostants {


    public static class Stati{

        public static final String ACQUISTATO = "ACQUISTATO";
        public static final String VENDUTO = "VENDUTO";
        public static final String DISPONIBILE = "DISPONIBILE";
        public static final String NON_DISPONIBILE = "NON DISPONIBILE";
    }

    public static class CodiciErrori{

        public static final String NOT_FOUND = "PKM-404";
        public static final String BAD_REQUEST = "PKM-400";
        public static final String CONFLICT = "PKM-403";
    }

    public static class TipoProdotto{

        public static final String SEALED = "Sealed";
        public static final String CARD = "Carta";
    }
}
