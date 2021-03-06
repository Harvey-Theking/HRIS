/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import Entities.Education;
import Entities.EmployeeHasEducation;
import Entities.Worker;
import java.time.Year;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Harvey
 */
@Stateless
public class EmployeeHasEducationFacade extends AbstractFacade<EmployeeHasEducation> {
    @PersistenceContext(unitName = "SampleAppPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EmployeeHasEducationFacade() {
        super(EmployeeHasEducation.class);
    }

    public void addDegree(String workerId, Education level, String school, Year year,
            String specialty, String score, String maxScore, Date startDate, Date endDate) {
        EmployeeHasEducation employeeHasEducation = new EmployeeHasEducation(level);
        employeeHasEducation.setEndDate(endDate);
        employeeHasEducation.setStartDate(startDate);
        employeeHasEducation.setGradYear(year);
        employeeHasEducation.setInstitute(school);
        employeeHasEducation.setScore(score);
        employeeHasEducation.setMaxScore(maxScore);
        employeeHasEducation.setSpecialty(specialty);
        employeeHasEducation.setWorker(em.find(Worker.class, workerId));
        em.persist(employeeHasEducation);
    }
    
    public void editDegree(Long degreeId, Education level, String school, Year year,
            String specialty, String score, String maxScore, Date startDate, Date endDate) {
        
        EmployeeHasEducation find = em.find(EmployeeHasEducation.class, degreeId);
        find.setDegree(level);
        find.setEndDate(endDate);
        find.setStartDate(startDate);
        find.setGradYear(year);
        find.setInstitute(school);
        find.setScore(score);
        find.setMaxScore(maxScore);
        find.setSpecialty(specialty);
        em.merge(find);
    }
    
    public List<EmployeeHasEducation> degreeList(String workerId){
//        return em.createQuery("SELECT d FROM EmployeeHasEducation d WHERE d.id = '"
//        + workerId + "'").getResultList();
        return em.createQuery("SELECT em FROM EmployeeHasEducation em WHERE em.worker.id = :id")
                .setParameter("id", workerId).getResultList();
    }
}
