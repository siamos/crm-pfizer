package resource;

import exception.AuthorizationException;
import jpaUtil.JpaUtil;
import model.Consultation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import repository.ConsultationRepository;
import representation.ConsultationRepresentation;
import security.Shield;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConsultationListResource extends ServerResource {
    @Get("json")
    public List<ConsultationRepresentation> getConsultation() throws AuthorizationException {
        ResourceUtils.checkRole(this, Shield.ROLE_CHIEF_DOCTOR);
        EntityManager entityManager = JpaUtil.getEntityManager();
        ConsultationRepository consultationRepository = new ConsultationRepository(entityManager);
        List<Consultation> consultationList = consultationRepository.findAll(0,10);
        entityManager.close();

        List<ConsultationRepresentation> consultationRepresentationList = new ArrayList<>();
        for (Consultation p : consultationList)
            consultationRepresentationList.add(new ConsultationRepresentation(p));

        return consultationRepresentationList;
    }

    @Post("json")
    public ConsultationRepresentation add(ConsultationRepresentation consultationRepresentationIn) throws AuthorizationException {
        ResourceUtils.checkRole(this, Shield.ROLE_CHIEF_DOCTOR);
        if (consultationRepresentationIn == null) return null;

        Consultation consultation = consultationRepresentationIn.createConsultation();
        if (consultation.getDate() == null) consultation.setDate(new Date());

        EntityManager entityManager = JpaUtil.getEntityManager();
        ConsultationRepository consultationListRepository = new ConsultationRepository(entityManager);
        consultationListRepository.save(consultation);
        ConsultationRepresentation p = new ConsultationRepresentation(consultation);

        return p;
    }
}
