package resource.patient;

import Service.PatientServiceImpl;
import exception.AuthorizationException;
import jpaUtil.JpaUtil;
import main.java.Main;
import model.Patient;
import org.modelmapper.ModelMapper;
import org.restlet.engine.Engine;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import repository.ChiefDoctorRepository;
import repository.DoctorRepository;
import repository.PatientRepository;
import representation.PatientRepresentation;
import resource.ResourceUtils;
import security.Shield;

import javax.persistence.EntityManager;
import java.util.logging.Logger;

public class PatientSettingsResource extends ServerResource {
    private long id;
    private EntityManager em;
    public static final Logger LOGGER = Engine.getLogger(PatientSettingsResource.class);

    protected void doInit() {
        em = JpaUtil.getEntityManager();
        try {
            id = Long.parseLong(getAttribute("id"));
        } catch (Exception e) {
            LOGGER.info("Id is not in the right format");
        }
    }

    protected void doRelease() {
        em.close();
    }

    @Get("json")
    public PatientRepresentation getPatient() throws AuthorizationException {
        ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);

        PatientServiceImpl patientService = new PatientServiceImpl(
                new PatientRepository(em),
                new DoctorRepository(em),
                new ChiefDoctorRepository(em),
                new ModelMapper());

        if (id <= 0) return null;

        return patientService.getPatient(id);
    }

    @Put("json")
    public PatientRepresentation updatePatient(PatientRepresentation patientRepresentation) throws AuthorizationException {
        ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);

        EntityManager em = JpaUtil.getEntityManager();
        PatientRepository patientRepository = new PatientRepository(em);

        PatientServiceImpl patientService = new PatientServiceImpl(
                new PatientRepository(em),
                new DoctorRepository(em),
                new ChiefDoctorRepository(em),
                new ModelMapper());

        Patient patient = patientService.createPatient(patientRepresentation);
        Patient oldPatient = patientRepository.read(id);
        em.detach(patient);
        patient.setId(id);
        patient.setDateRegistered(oldPatient.getDateRegistered());
        patientRepository.update(patient);

        return patientRepresentation;
    }

    @Delete("json")
    public void deletePatient() throws AuthorizationException {
        ResourceUtils.checkRole(this, Shield.ROLE_PATIENT);

        EntityManager em = JpaUtil.getEntityManager();
        PatientRepository patientRepository = new PatientRepository(em);
        patientRepository.delete(patientRepository.read(id).getId());
    }

}
