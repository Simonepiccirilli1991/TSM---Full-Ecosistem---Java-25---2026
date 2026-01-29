#!/bin/bash

# Script per avviare e testare l'integrazione wiam + wiam-frontend

echo "==================================="
echo "AVVIO MICROSERVIZI WIAM"
echo "==================================="

# 1. Verifica e avvio MongoDB
echo ""
echo "1. Verifica MongoDB..."
if pgrep -x "mongod" > /dev/null; then
    echo "✓ MongoDB è già in esecuzione"
else
    echo "→ Avvio MongoDB..."
    brew services start mongodb-community@7.0
    sleep 3
fi

# 2. Compila e avvia wiam backend
echo ""
echo "2. Compilazione e avvio wiam backend (porta 8081)..."
cd "wiam"
mvn clean package -DskipTests &
WIAM_PID=$!
wait $WIAM_PID

if [ $? -eq 0 ]; then
    echo "✓ wiam backend compilato con successo"
    java -jar target/wiam-*.jar &
    WIAM_BACKEND_PID=$!
    echo "→ wiam backend avviato (PID: $WIAM_BACKEND_PID)"
    sleep 10
else
    echo "✗ Errore nella compilazione di wiam backend"
    exit 1
fi

# 3. Compila e avvia wiam-frontend
echo ""
echo "3. Compilazione e avvio wiam-frontend (porta 8080)..."
cd "../wiam-frontend"
mvn clean package -DskipTests &
FRONTEND_PID=$!
wait $FRONTEND_PID

if [ $? -eq 0 ]; then
    echo "✓ wiam-frontend compilato con successo"
    java -jar target/wiam-frontend-*.jar &
    WIAM_FRONTEND_PID=$!
    echo "→ wiam-frontend avviato (PID: $WIAM_FRONTEND_PID)"
    sleep 10
else
    echo "✗ Errore nella compilazione di wiam-frontend"
    kill $WIAM_BACKEND_PID
    exit 1
fi

# 4. Test di integrazione
echo ""
echo "==================================="
echo "TEST INTEGRAZIONE"
echo "==================================="

# Test health check backend
echo ""
echo "Test 1: Health check wiam backend (8081)..."
BACKEND_STATUS=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8081/actuator/health 2>/dev/null || echo "000")
if [ "$BACKEND_STATUS" = "200" ]; then
    echo "✓ wiam backend risponde correttamente"
else
    echo "✗ wiam backend non risponde (status: $BACKEND_STATUS)"
fi

# Test health check frontend
echo ""
echo "Test 2: Health check wiam-frontend (8080)..."
FRONTEND_STATUS=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8080/ 2>/dev/null || echo "000")
if [ "$FRONTEND_STATUS" = "200" ]; then
    echo "✓ wiam-frontend risponde correttamente"
else
    echo "✗ wiam-frontend non risponde (status: $FRONTEND_STATUS)"
fi

# Test API Pokemon Cards
echo ""
echo "Test 3: API Pokemon Cards (GET /api/v1/pokemon/getcardsbystatus/DISPONIBILE)..."
CARDS_STATUS=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8081/api/v1/pokemon/getcardsbystatus/DISPONIBILE)
if [ "$CARDS_STATUS" = "200" ]; then
    echo "✓ API Pokemon Cards funzionante"
    CARDS_COUNT=$(curl -s http://localhost:8081/api/v1/pokemon/getcardsbystatus/DISPONIBILE | jq '. | length' 2>/dev/null || echo "N/A")
    echo "  → Carte disponibili: $CARDS_COUNT"
else
    echo "✗ API Pokemon Cards non funzionante (status: $CARDS_STATUS)"
fi

# Test integrazione frontend -> backend
echo ""
echo "Test 4: Integrazione Frontend -> Backend..."
FRONTEND_CARDS=$(curl -s http://localhost:8080/pokemon/cards 2>/dev/null | grep -c "card" || echo "0")
if [ "$FRONTEND_CARDS" -gt 0 ]; then
    echo "✓ Frontend carica correttamente i dati dal backend"
else
    echo "⚠ Verifica manuale necessaria"
fi

# Riepilogo
echo ""
echo "==================================="
echo "RIEPILOGO"
echo "==================================="
echo "Backend wiam:     http://localhost:8081"
echo "Frontend:         http://localhost:8080"
echo ""
echo "PIDs:"
echo "  - wiam backend:  $WIAM_BACKEND_PID"
echo "  - wiam-frontend: $WIAM_FRONTEND_PID"
echo ""
echo "Per fermare i servizi:"
echo "  kill $WIAM_BACKEND_PID $WIAM_FRONTEND_PID"
echo ""
echo "==================================="

# Mantieni lo script attivo
echo "Premi Ctrl+C per terminare i servizi..."
trap "echo 'Terminazione servizi...'; kill $WIAM_BACKEND_PID $WIAM_FRONTEND_PID 2>/dev/null; exit 0" INT
wait
