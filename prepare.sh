#!/bin/bash

# Script per rendere eseguibili gli script di test

echo "🔧 Preparazione ambiente di test..."

# Vai nella directory del progetto
cd "/Users/simonepiccirilli/Desktop/TSM Resell Full Project"

# Rendi eseguibili gli script
chmod +x start-and-test.sh
chmod +x test-integration.sh

echo "✅ Script preparati con successo!"
echo ""
echo "Puoi ora eseguire:"
echo "  ./start-and-test.sh      - Avvia i microservizi e testa"
echo "  ./test-integration.sh    - Esegue test di integrazione"
echo ""
echo "Oppure apri nel browser:"
echo "  test-integration.html    - Interfaccia web per testare le API"
echo ""
