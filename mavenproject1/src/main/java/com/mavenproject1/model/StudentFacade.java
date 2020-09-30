/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mavenproject1.model;

import com.mavenproject1.Student;
import controller.StudentForm;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Bishesh Katwal
 */
@Stateless
public class StudentFacade extends AbstractFacade<Student>  {
    
    StudentForm s = new StudentForm();

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public StudentFacade() {
        super(Student.class);
    }
    
    public void delete(Student std){
        javax.persistence.Query query = getEntityManager().createQuery("delete from student where id = :id");
        query.setParameter("id", std.getId());
        query.executeUpdate();
    }
    
    
    public void editRow(Student stds)
    {
       Student std = getEntityManager().find(Student.class,stds.getId());
       std.setFirstname(stds.getFirstname());
       std.setMiddlename(stds.getMiddlename());
       std.setLastname(stds.getLastname());
       std.setFaculty(stds.getFaculty());
       std.setProgram(std.getProgram());
    }
    
    
    
}
