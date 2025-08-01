/*Para hacer un preview de una imagen */
function mostrarImagen(input) {
    if (input.files && input.files[0]) {
        var lector = new FileReader();

        lector.onload = function (e) {
            $('#blah').attr('src', e.target.result)
                    .height(200); 
        };

        lector.readAsDataURL(input.files[0]);

    }
}
//Para insertar información en el modal según el registro...
document.addEventListener('DOMContentLoaded', function () {
    const confirmModal = document.getElementById('confirmModal');
    confirmModal.addEventListener('show.bs.modal', function (event) {
        const button = event.relatedTarget;
        document.getElementById('modalId').value = button.getAttribute('data-bs-id');
        document.getElementById('modalDescripcion').textContent = button.getAttribute('data-bs-descripcion');
    });
});


setTimeout(() => {
    document.querySelectorAll('.toast').forEach(t => t.classList.remove('show'));
}, 3000);


