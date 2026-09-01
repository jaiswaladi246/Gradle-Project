document.addEventListener('DOMContentLoaded', () => {
    const search = document.getElementById('buildSearch');
    const status = document.getElementById('statusFilter');
    const table = document.getElementById('buildTable');
    const empty = document.getElementById('emptyState');

    if (!search || !status || !table) return;

    const rows = Array.from(table.querySelectorAll('tbody tr'));

    const filterRows = () => {
        const term = search.value.trim().toLowerCase();
        const selectedStatus = status.value;
        let visible = 0;

        rows.forEach(row => {
            const textMatch = row.textContent.toLowerCase().includes(term);
            const statusMatch = selectedStatus === 'all' || row.dataset.status === selectedStatus;
            const show = textMatch && statusMatch;
            row.hidden = !show;
            if (show) visible += 1;
        });

        if (empty) empty.hidden = visible !== 0;
    };

    search.addEventListener('input', filterRows);
    status.addEventListener('change', filterRows);

    const toast = document.querySelector('.toast');
    if (toast) {
        window.setTimeout(() => {
            toast.style.opacity = '0';
            toast.style.transform = 'translateY(-4px)';
            toast.style.transition = '.25s ease';
        }, 3500);
    }
});
