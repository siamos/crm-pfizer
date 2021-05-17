package resource.doctor;

import exception.AuthorizationException;
import jpaUtil.JpaUtil;
import model.Consultation;
import model.Doctor;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import repository.ConsultationRepository;
import repository.DoctorRepository;
import representation.ConsultationRepresentation;
import resource.ResourceUtils;
import security.Shield;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DoctorConsultationListResource extends ServerResource {
    private long doctorId;

    protected void doInit() {
        doctorId = Long.parseLong(getAttribute("doctorId"));
    }

    @Get("json")
    public List<ConsultationRepresentation> getConsultationList() throws AuthorizationException {
        ResourceUtils.checkRole(this, Shield.ROLE_DOCTOR);
        EntityManager entityManager = JpaUtil.getEntityManager();
        DoctorRepository doctorRepository = new DoctorRepository(entityManager);
        List<Consultation> consultationList = doctorRepository.getConsultationList(this.doctorId);
        List<ConsultationRepresentation> consultationRepresentationList = new ArrayList<>();

        for (Consultation consultation : consultationList) {
            consultationRepresentationList.add(new ConsultationRepresentation(consultation));
        }

        entityManager.close();

        return consultationRepresentationList;
    }

    @Post("json")
    public ConsultationRepresentation add(ConsultationRepresentation consultationRepresentationIn) throws AuthorizationException {
        ResourceUtils.checkRole(this, Shield.ROLE_DOCTOR);
        if (consultationRepresentationIn == null) return null;

        consultationRepresentationIn.setDoctorId(this.doctorId);
        Consultation consultation = consultationRepresentationIn.createConsultation();
        EntityManager entityManager = JpaUtil.getEntityManager();
        ConsultationRepository consultationRepository = new ConsultationRepository(entityManager);
        consultationRepository.save(consultation);
        ConsultationRepresentation c = new ConsultationRepresentation(consultation);

        DoctorRepository doctorRepository = new DoctorRepository(entityManager);
        Doctor doctor = doctorRepository.read(doctorId);
        entityManager.detach(doctor);
        doctor.setRecentConsultation(new Date());
        doctorRepository.update(doctor);

        return c;
    }

}
