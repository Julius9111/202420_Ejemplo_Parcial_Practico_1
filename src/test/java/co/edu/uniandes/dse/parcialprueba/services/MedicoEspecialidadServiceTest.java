package co.edu.uniandes.dse.parcialprueba.services;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.parcialprueba.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialprueba.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(MedicoEspecialidadService.class)
public class MedicoEspecialidadServiceTest {

    @Autowired
    private MedicoEspecialidadService medicoEspecialidadService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<MedicoEntity> medicoList = new ArrayList<> ();
    private List<EspecialidadEntity> especialidadList = new ArrayList<> ();
    
    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from MedicoEntity");
    }


    private void insertData() {
        for (int i = 0; i < 3; i++) {
                MedicoEntity medicoEntity = factory.manufacturePojo(MedicoEntity.class);
                medicoEntity.setRegistro_medico("RM1745");
                entityManager.persist(medicoEntity);
                medicoList.add(medicoEntity);
            }

        for (int i = 0; i < 3; i++) {
                EspecialidadEntity especialidadEntity = factory.manufacturePojo(EspecialidadEntity.class);
                especialidadEntity.setDescripcion("texto con mas de 10 caracteres");
                entityManager.persist(especialidadEntity);
                especialidadList.add(especialidadEntity);
            }
    }
    
    @Test
    void TestAddEspecialidad() throws EntityNotFoundException, IllegalOperationException {
        medicoEspecialidadService.addEspecialidad(medicoList.get(1).getId(), especialidadList.get(1).getId());
    }

    @Test
    void TestAddEspecialidadWithInvalidMedico() {
        assertThrows(EntityNotFoundException.class, () -> {
            medicoEspecialidadService.addEspecialidad(0, especialidadList.get(1).getId());
        });
    }

    @Test
    void TestAddEspecialidadWithInvalidEspecialidad() {
        assertThrows(EntityNotFoundException.class, () -> {
        medicoEspecialidadService.addEspecialidad(medicoList.get(1).getId(), 0);
        });
    }
}
