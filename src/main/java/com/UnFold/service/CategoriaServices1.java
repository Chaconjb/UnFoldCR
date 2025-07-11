package com.UnFold.service; 

import com.UnFold.domain.Categoria;
import com.UnFold.repository.CategoriaRepository; // Necesitarás importar tu interfaz de repositorio
import com.UnFold.service.CategoriaService;     // ¡Importa la interfaz del servicio que implementas!
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;       // ¡FUNDAMENTAL! Para que Spring la detecte como un bean
import org.springframework.transaction.annotation.Transactional; // Para el manejo de transacciones

import java.util.List;
import java.util.Optional; // Para manejar los resultados de findById

@Service // ¡Esta anotación es crucial! Le dice a Spring que esta clase es un componente de servicio.
public class CategoriaServices1 implements CategoriaService { // ¡Debe implementar la interfaz!

    @Autowired // Spring inyectará automáticamente una instancia de CategoriaRepository aquí
    private CategoriaRepository categoriaRepository;

    @Override
    @Transactional(readOnly = true) // Anotación para indicar que este método es de solo lectura en la BD
    public List<Categoria> getCategorias(boolean activos) {
        List<Categoria> categorias = categoriaRepository.findAll(); // Obtiene todas las categorías
        if (activos) {
            // Filtra solo las categorías que están activas si el parámetro 'activos' es true
            categorias.removeIf(c -> !c.isActivo()); // Asumiendo que tu clase Categoria tiene un método isActivo()
        }
        return categorias;
    }

    @Override
    @Transactional(readOnly = true)
    public Categoria getCategoria(Categoria categoria) {
        // Busca una categoría por su ID. Optional.orElse(null) devuelve null si no se encuentra.
        return categoriaRepository.findById(categoria.getIdCategoria()).orElse(null);
    }

    @Override
    @Transactional // Esta transacción es de lectura/escritura (para guardar cambios)
    public void save(Categoria categoria) {
        categoriaRepository.save(categoria); // Guarda (o actualiza) la categoría en la base de datos
    }

    @Override
    @Transactional // Esta transacción también es de lectura/escritura (para eliminar)
    public void delete(Categoria categoria) {
        categoriaRepository.delete(categoria); // Elimina la categoría de la base de datos
    }
}