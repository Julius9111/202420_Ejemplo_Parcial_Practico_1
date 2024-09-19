package co.edu.uniandes.dse.parcialprueba.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcialprueba.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialprueba.repositories.EspecialidadRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EspecialidadService {

    @Autowired
    EspecialidadRepository especialidadRepository;

    @Transactional
    public EspecialidadEntity createEspecialidad(EspecialidadEntity especialidadEntity) throws IllegalOperationException {
        log.info("Inicia proceso de creación de una especialidad");

        if (especialidadEntity.getDescripcion().length() < 10)
            throw new IllegalOperationException("Descripcion is not valid");

        if (especialidadEntity.getNombre() == null)
            throw new IllegalOperationException("Nombre is not valid");

        log.info("Termina proceso de creación de una especialidad");
        return especialidadRepository.save(especialidadEntity);
    }
    
}