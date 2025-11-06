function mostrarImagen(input) {
    if (input.files && input.files[0]) {
        const imagen = input.files[0];
        const maximo = 512 * 1024; // Limite 512 KB

        if (imagen.size <= maximo) {
            const lector = new FileReader();
            lector.onload = function (e) {
                document.getElementById('previewLibro').src = e.target.result;
                document.getElementById('previewLibro').style.height = "200px";
            };
            lector.readAsDataURL(imagen);
        } else {
            alert(" La imagen seleccionada es muy grande. No debe superar los 512 KB.");
            input.value = ''; // Limpia el input
        }
    }
}

document.addEventListener('DOMContentLoaded', function () {
    const confirmModal = document.getElementById('confirmModal');
    if (confirmModal) {
        confirmModal.addEventListener('show.bs.modal', function (event) {
            const button = event.relatedTarget;
            document.getElementById('modalId').value = button.getAttribute('data-bs-id');
            document.getElementById('modalDescripcion').textContent = button.getAttribute('data-bs-descripcion');
        });
    }
});
// Ocultar automáticamente los toasts de mensajes de éxito/error
setTimeout(() => {
    document.querySelectorAll('.toast').forEach(toast => toast.classList.remove('show'));
}, 4000);