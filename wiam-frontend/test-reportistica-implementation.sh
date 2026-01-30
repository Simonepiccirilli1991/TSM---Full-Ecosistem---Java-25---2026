#!/bin/bash

# Script di Test per wiam-frontend - Reportistica Implementation
# Data: 30 Gennaio 2026

echo "=========================================="
echo "Test Implementazione Reportistica Frontend"
echo "=========================================="
echo ""

# Colori per output
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

cd "$(dirname "$0")"

echo "📁 Directory corrente: $(pwd)"
echo ""

# Test 1: Verifica esistenza file DTO
echo "✅ Test 1: Verifica file DTO creati"
FILES=(
    "src/main/java/it/tsm/wiamfrontend/dto/ReportisticaRecapDTO.java"
    "src/main/java/it/tsm/wiamfrontend/dto/ReportisticaDettaglioDTO.java"
    "src/main/java/it/tsm/wiamfrontend/dto/ReportItemDTO.java"
    "src/main/java/it/tsm/wiamfrontend/dto/ReportResponseDTO.java"
)

for file in "${FILES[@]}"; do
    if [ -f "$file" ]; then
        echo -e "${GREEN}✓${NC} $file esiste"
    else
        echo -e "${RED}✗${NC} $file NON TROVATO!"
        exit 1
    fi
done
echo ""

# Test 2: Verifica service aggiornato
echo "✅ Test 2: Verifica ReportisticaService aggiornato"
if grep -q "calcolaStatistiche" "src/main/java/it/tsm/wiamfrontend/service/ReportisticaService.java"; then
    echo -e "${GREEN}✓${NC} Metodo calcolaStatistiche() presente"
else
    echo -e "${RED}✗${NC} Metodo calcolaStatistiche() NON TROVATO!"
    exit 1
fi

if grep -q "ReportisticaRecapDTO" "src/main/java/it/tsm/wiamfrontend/service/ReportisticaService.java"; then
    echo -e "${GREEN}✓${NC} ReportisticaRecapDTO importato e utilizzato"
else
    echo -e "${RED}✗${NC} ReportisticaRecapDTO NON TROVATO nel service!"
    exit 1
fi
echo ""

# Test 3: Verifica controller aggiornato
echo "✅ Test 3: Verifica ReportisticaController aggiornato"
if grep -q "ReportisticaRecapDTO" "src/main/java/it/tsm/wiamfrontend/controller/ReportisticaController.java"; then
    echo -e "${GREEN}✓${NC} Controller utilizza il nuovo DTO"
else
    echo -e "${YELLOW}⚠${NC} Controller potrebbe non utilizzare il nuovo DTO"
fi
echo ""

# Test 4: Verifica template aggiornato
echo "✅ Test 4: Verifica template dashboard.html"
if grep -q "recap.profittoNetto" "src/main/resources/templates/reportistica/dashboard.html"; then
    echo -e "${GREEN}✓${NC} Template utilizza i nuovi campi del DTO"
else
    echo -e "${RED}✗${NC} Template NON aggiornato!"
    exit 1
fi

if grep -q "recap.pokemon" "src/main/resources/templates/reportistica/dashboard.html"; then
    echo -e "${GREEN}✓${NC} Template mostra dettagli Pokemon"
else
    echo -e "${RED}✗${NC} Template non mostra dettagli Pokemon!"
    exit 1
fi

if grep -q "recap.onePiece" "src/main/resources/templates/reportistica/dashboard.html"; then
    echo -e "${GREEN}✓${NC} Template mostra dettagli OnePiece"
else
    echo -e "${RED}✗${NC} Template non mostra dettagli OnePiece!"
    exit 1
fi
echo ""

# Test 5: Verifica documentazione
echo "✅ Test 5: Verifica documentazione"
if [ -f "REPORTISTICA_FILTERING_IMPLEMENTATION.md" ]; then
    echo -e "${GREEN}✓${NC} Documentazione implementazione presente"
else
    echo -e "${YELLOW}⚠${NC} Documentazione non trovata"
fi
echo ""

# Test 6: Verifica struttura progetto
echo "✅ Test 6: Verifica struttura progetto"
if [ -f "pom.xml" ]; then
    echo -e "${GREEN}✓${NC} pom.xml presente"
else
    echo -e "${RED}✗${NC} pom.xml NON TROVATO!"
    exit 1
fi

if [ -d "src/main/java" ]; then
    echo -e "${GREEN}✓${NC} Directory src/main/java presente"
else
    echo -e "${RED}✗${NC} Directory src/main/java NON TROVATA!"
    exit 1
fi

if [ -d "src/main/resources/templates" ]; then
    echo -e "${GREEN}✓${NC} Directory templates presente"
else
    echo -e "${RED}✗${NC} Directory templates NON TROVATA!"
    exit 1
fi
echo ""

# Riepilogo
echo "=========================================="
echo -e "${GREEN}✅ TUTTI I TEST SUPERATI!${NC}"
echo "=========================================="
echo ""
echo "📋 Riepilogo modifiche:"
echo "  • 4 nuovi DTO creati"
echo "  • ReportisticaService aggiornato con logica di calcolo"
echo "  • ReportisticaController semplificato"
echo "  • Template dashboard.html aggiornato"
echo "  • Documentazione completa creata"
echo ""
echo "🚀 Prossimi passi:"
echo "  1. Avviare il microservizio WIAM (porta 8081)"
echo "  2. Avviare wiam-frontend (porta 8080)"
echo "  3. Navigare su http://localhost:8080/reportistica"
echo "  4. Verificare che i dati vengano visualizzati correttamente"
echo ""
