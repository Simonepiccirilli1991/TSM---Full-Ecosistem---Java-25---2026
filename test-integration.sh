#!/bin/bash

# Script per testare manualmente l'integrazione delle API

BASE_URL="http://localhost:8081"
FRONTEND_URL="http://localhost:8080"

echo "==================================="
echo "TEST INTEGRAZIONE API WIAM"
echo "==================================="

# Test 1: Creazione Pokemon Card
echo ""
echo "Test 1: Creazione Pokemon Card"
echo "POST $BASE_URL/api/v1/pokemon/addcard"

CARD_RESPONSE=$(curl -s -X POST "$BASE_URL/api/v1/pokemon/addcard" \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Charizard",
    "dataInserimentoAcquisto": "2026-01-29T10:00:00",
    "prezzoAcquisto": 150.50,
    "espansione": "Base Set",
    "gradata": true,
    "casaGradazione": "PSA",
    "votoGradazione": "10",
    "codiceGradazione": "12345678"
  }')

echo "$CARD_RESPONSE" | jq '.' 2>/dev/null || echo "$CARD_RESPONSE"
CARD_ID=$(echo "$CARD_RESPONSE" | jq -r '.id' 2>/dev/null)
echo "→ Card ID creata: $CARD_ID"

# Test 2: Recupero Card per Stato
echo ""
echo "Test 2: Recupero Cards per stato DISPONIBILE"
echo "GET $BASE_URL/api/v1/pokemon/getcardsbystatus/DISPONIBILE"

curl -s "$BASE_URL/api/v1/pokemon/getcardsbystatus/DISPONIBILE" | jq '.' 2>/dev/null

# Test 3: Creazione Pokemon Sealed
echo ""
echo "Test 3: Creazione Pokemon Sealed"
echo "POST $BASE_URL/api/v1/pokemon/addsealed"

SEALED_RESPONSE=$(curl -s -X POST "$BASE_URL/api/v1/pokemon/addsealed" \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Booster Box",
    "dataInserimentoAcquisto": "2026-01-29T11:00:00",
    "prezzoAcquisto": 450.00,
    "espansione": "Scarlet & Violet",
    "codiceProdotto": "SV01-BB"
  }')

echo "$SEALED_RESPONSE" | jq '.' 2>/dev/null || echo "$SEALED_RESPONSE"
SEALED_ID=$(echo "$SEALED_RESPONSE" | jq -r '.id' 2>/dev/null)
echo "→ Sealed ID creato: $SEALED_ID"

# Test 4: Aggiunta Vendita
if [ ! -z "$CARD_ID" ] && [ "$CARD_ID" != "null" ]; then
    echo ""
    echo "Test 4: Aggiunta Vendita per Card $CARD_ID"
    echo "POST $BASE_URL/api/v1/pokemon/addvendita"

    VENDITA_RESPONSE=$(curl -s -X POST "$BASE_URL/api/v1/pokemon/addvendita" \
      -H "Content-Type: application/json" \
      -d "{
        \"id\": \"$CARD_ID\",
        \"vendita\": {
          \"dataVendita\": \"2026-01-29\",
          \"prezzoVendita\": 200.00,
          \"costiVendita\": 15.50,
          \"prezzoNetto\": \"184.50\",
          \"piattaformaVendita\": \"eBay\"
        },
        \"tipoProdotto\": \"CARD\"
      }")

    echo "$VENDITA_RESPONSE" | jq '.' 2>/dev/null || echo "$VENDITA_RESPONSE"
fi

# Test 5: OnePiece Card
echo ""
echo "Test 5: Creazione OnePiece Card"
echo "POST $BASE_URL/api/v1/onepiece/addcard"

OP_CARD_RESPONSE=$(curl -s -X POST "$BASE_URL/api/v1/onepiece/addcard" \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Monkey D. Luffy",
    "dataInserimentoAcquisto": "2026-01-29T12:00:00",
    "prezzoAcquisto": 80.00,
    "espansione": "OP01",
    "gradata": false
  }')

echo "$OP_CARD_RESPONSE" | jq '.' 2>/dev/null || echo "$OP_CARD_RESPONSE"

# Test 6: Verifica stato Card venduta
if [ ! -z "$CARD_ID" ] && [ "$CARD_ID" != "null" ]; then
    echo ""
    echo "Test 6: Verifica Card venduta (stato = VENDUTO)"
    echo "GET $BASE_URL/api/v1/pokemon/getcard/$CARD_ID"

    CARD_DETAIL=$(curl -s "$BASE_URL/api/v1/pokemon/getcard/$CARD_ID")
    echo "$CARD_DETAIL" | jq '.' 2>/dev/null

    STATO=$(echo "$CARD_DETAIL" | jq -r '.stato' 2>/dev/null)
    if [ "$STATO" = "VENDUTO" ]; then
        echo "✓ Stato correttamente aggiornato a VENDUTO"
    else
        echo "✗ Stato non aggiornato (attuale: $STATO)"
    fi
fi

# Test 7: Test Frontend
echo ""
echo "Test 7: Verifica integrazione Frontend"
echo "GET $FRONTEND_URL/pokemon/cards"

FRONTEND_STATUS=$(curl -s -o /dev/null -w "%{http_code}" "$FRONTEND_URL/pokemon/cards")
if [ "$FRONTEND_STATUS" = "200" ]; then
    echo "✓ Frontend risponde correttamente (status: $FRONTEND_STATUS)"
    echo "→ Apri nel browser: $FRONTEND_URL"
else
    echo "✗ Frontend non risponde (status: $FRONTEND_STATUS)"
fi

echo ""
echo "==================================="
echo "TEST COMPLETATI"
echo "==================================="
echo ""
echo "URL utili:"
echo "  - Backend API:  $BASE_URL"
echo "  - Frontend Web: $FRONTEND_URL"
echo "  - Pokemon Cards: $FRONTEND_URL/pokemon/cards"
echo "  - Pokemon Sealed: $FRONTEND_URL/pokemon/sealed"
echo "  - OnePiece Cards: $FRONTEND_URL/onepiece/cards"
echo "  - OnePiece Sealed: $FRONTEND_URL/onepiece/sealed"
echo ""
