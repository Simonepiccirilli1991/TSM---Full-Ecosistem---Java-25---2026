// WIAM Frontend JavaScript

document.addEventListener('DOMContentLoaded', function() {
    // Auto-hide alerts after 5 seconds
    const alerts = document.querySelectorAll('.alert');
    alerts.forEach(alert => {
        setTimeout(() => {
            const bsAlert = new bootstrap.Alert(alert);
            bsAlert.close();
        }, 5000);
    });

    // Confirm delete actions
    const deleteLinks = document.querySelectorAll('a[href*="/delete"]');
    deleteLinks.forEach(link => {
        link.addEventListener('click', function(e) {
            if (!confirm('Sei sicuro di voler eliminare questo elemento?')) {
                e.preventDefault();
            }
        });
    });

    // Format currency inputs
    const currencyInputs = document.querySelectorAll('input[type="number"][step="0.01"]');
    currencyInputs.forEach(input => {
        input.addEventListener('blur', function() {
            if (this.value) {
                this.value = parseFloat(this.value).toFixed(2);
            }
        });
    });

    // Set current date as default for date inputs
    const dateInputs = document.querySelectorAll('input[type="date"]');
    dateInputs.forEach(input => {
        if (!input.value) {
            const today = new Date().toISOString().split('T')[0];
            input.value = today;
        }
    });

    // Add loading spinner on form submit
    const forms = document.querySelectorAll('form');
    forms.forEach(form => {
        form.addEventListener('submit', function() {
            const submitBtn = this.querySelector('button[type="submit"]');
            if (submitBtn) {
                submitBtn.disabled = true;
                submitBtn.innerHTML = '<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span> Caricamento...';
            }
        });
    });

    // Table search functionality (if needed in future)
    const searchInputs = document.querySelectorAll('.table-search');
    searchInputs.forEach(searchInput => {
        searchInput.addEventListener('keyup', function() {
            const filter = this.value.toLowerCase();
            const table = this.closest('.table-responsive').querySelector('tbody');
            const rows = table.querySelectorAll('tr');

            rows.forEach(row => {
                const text = row.textContent.toLowerCase();
                row.style.display = text.includes(filter) ? '' : 'none';
            });
        });
    });

    console.log('WIAM Frontend loaded successfully!');
});

// Helper function to format currency
function formatCurrency(value) {
    return new Intl.NumberFormat('it-IT', {
        style: 'currency',
        currency: 'EUR'
    }).format(value);
}

// Helper function to format date
function formatDate(dateString) {
    const date = new Date(dateString);
    return new Intl.DateTimeFormat('it-IT').format(date);
}
