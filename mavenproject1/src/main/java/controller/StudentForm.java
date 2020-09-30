/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.mavenproject1.Student;
import com.mavenproject1.model.StudentFacade;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Bishesh Katwal
 */
@Named(value = "studentForm")
@SessionScoped

public class StudentForm implements Serializable{
    
    private Map<String, Map<String,String>> data = new HashMap<String, Map<String, String>>();
        
    private Map<String,String> facultyOptions;
    private Map<String,String> programOptions;
    
    private String faculty;
    private String program;
    
    private Boolean editFlag = null; //when this is null new row is created, when this is not null a row is updated
    

    @EJB
    private StudentFacade studentFacade;
    private Student student = new Student();
    

    

    //getter and setter methods
    
    public Boolean getEditFlag() {
        return editFlag;
    }
    public void setEditFlag(Boolean editFlag) {    
        this.editFlag = editFlag;
    }

    public Map<String, String> getFacultyOptions() {
        return facultyOptions;
    }

    public void setFacultyOptions(Map<String, String> facultyOptions) {
        this.facultyOptions = facultyOptions;
    }

    public Map<String, String> getProgramOptions() {
        return programOptions;
    }

    public void setProgramOptions(Map<String, String> programOptions) {
        this.programOptions = programOptions;
    }
     public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }
    

   
    // constructor puts the value of faculty options and program options
    public StudentForm() {
              
        facultyOptions = new HashMap <String,String>();
        facultyOptions.put("Management","Management");
        facultyOptions.put("Science & Technology","Science & Technology");
        
        Map <String, String> map = new HashMap<String,String>();
        map.put("BBA","BBA");
        map.put("BBS", "BBS");
        data.put("Management", map);
        
        Map <String, String> map2 = new HashMap <String, String>();
        map2.put("BSc CSIT", "BSc CSIT");
        map2.put("BE Comp.","BE Comp.");
        map2.put("BCA","BCA");
        data.put("Science & Technology",map2);
    }

   
    // method to change program on faculty change
    public void onFacultyChange(){
       
        if(student.getFaculty() != null && !student.getFaculty().equals(""))
            programOptions = data.get(student.getFaculty());    
        else
            programOptions = new HashMap <>();
     }
    
    
    // method to save info in database
    public void saveInfo(){
        this.studentFacade.create(this.student);
        saveNull();

    }
    
    //method to list all the rows in database
    public List<Student> findAll(){
        return this.studentFacade.findAll();
    }
    
    //method to delete row
    public void delete(Student std){ 
        
        this.studentFacade.remove(std);
    }
    
    //method to bring table data into form elements
    public void edit(Student student){
        
        
        editFlag = true;
        
        this.student.setId(student.getId());
        this.student.setFirstname(student.getFirstname());
        this.student.setMiddlename(student.getMiddlename());
        this.student.setLastname(student.getLastname());
        this.student.setFaculty(student.getFaculty());
        String prog = student.getProgram();
        onFacultyChange();
        this.student.setProgram(prog);       
        
        
    }
    
   
    
    //method to save edited data
    public void saveEdit()
    {
        Student s = new Student();
        s.setId(this.student.getId());
        s.setFirstname(this.student.getFirstname());
        s.setMiddlename(this.student.getMiddlename());
        s.setLastname(this.student.getLastname());
        s.setFaculty(this.student.getFaculty());        
        onFacultyChange();
        s.setProgram(this.student.getProgram()); 
       
        this.studentFacade.edit(s);
        editFlag = null;
        saveNull();       
    }
    
    // method to clear form data
    public void saveNull(){
        this.student.setId(null);
        this.student.setFirstname(null);
        this.student.setMiddlename(null);
        this.student.setLastname(null);
        this.student.setFaculty(null);
        onFacultyChange();
        this.student.setProgram(null);
    }
}
