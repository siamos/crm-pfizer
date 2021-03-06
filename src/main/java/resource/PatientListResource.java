package resource;

import Service.PatientServiceImpl;
import exception.AuthorizationException;
import jpaUtil.JpaUtil;
import model.Patient;
import org.modelmapper.ModelMapper;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import repository.ChiefDoctorRepository;
import repository.DoctorRepository;
import repository.PatientRepository;
import representation.PatientRepresentation;
import security.Shield;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PatientListResource extends ServerResource {
    @Get("json")
    public List<PatientRepresentation> getPatient() throws AuthorizationException {
        ResourceUtils.checkRole(this, Shield.ROLE_CHIEF_DOCTOR);

        EntityManager entityManager = JpaUtil.getEntityManager();
        PatientRepository patientRepository = new PatientRepository(entityManager);
        List<Patient> patients = patientRepository.findAll(0, 10);
        entityManager.close();

        List<PatientRepresentation> patientRepresentationList = new ArrayList<>();
        for (Patient p : patients)
            patientRepresentationList.add(new PatientRepresentation(p));

        return patientRepresentationList;
    }

    @Post("json")
    public PatientRepresentation add(PatientRepresentation patientRepresentationIn) throws AuthorizationException {
        ResourceUtils.checkRole(this, Shield.ROLE_CHIEF_DOCTOR);
        if (patientRepresentationIn == null) return null;
        if (patientRepresentationIn.getUsername() == null) return null;
        if (patientRepresentationIn.getPassword() == null) return null;
        EntityManager entityManager = JpaUtil.getEntityManager();

        PatientServiceImpl patientService = new PatientServiceImpl(
                new PatientRepository(entityManager),
                new DoctorRepository(entityManager),
                new ChiefDoctorRepository(entityManager),
                new ModelMapper());
        Patient patient = patientService.createPatient(patientRepresentationIn);

        if (patientRepresentationIn.getDateRegistered() == null) patient.setDateRegistered(new Date());
        PatientRepository patientRepository = new PatientRepository(entityManager);
        patientRepository.save(patient);
        PatientRepresentation p = new PatientRepresentation(patient);
        return p;
    }
}
