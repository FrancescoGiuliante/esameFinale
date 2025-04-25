package com.francescogiuliante.esameFinale.util;

import com.francescogiuliante.esameFinale.dto.request.*;
import com.francescogiuliante.esameFinale.dto.request.registration.StudentRegistrationDTO;
import com.francescogiuliante.esameFinale.dto.request.registration.TeacherRegistrationDTO;
import com.francescogiuliante.esameFinale.enums.EnrollmentStatus;
import com.francescogiuliante.esameFinale.model.*;
import com.francescogiuliante.esameFinale.repository.*;
import com.francescogiuliante.esameFinale.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Slf4j
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private StudentFactory studentFactory;

    @Autowired
    private TeacherFactory teacherFactory;

    @Autowired
    private CourseFactory courseFactory;

    @Autowired
    private ExamFactory examFactory;

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private ExamService examService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    StudentExamService studentExamService;

    @Autowired
    CourseExamRepository courseExamRepository;

    @Autowired
    CourseExamService courseExamService;

    @Autowired
    CourseTeacherService courseTeacherService;

    @Autowired
    Random random;

    @Override
    public void run(String... args) throws Exception {
        log.info("DataLoader executed.");

        //Course
        if (courseRepository.count() == 0) {
            log.info("No courses found in database. Creating 5 courses.");
            for (int i = 0; i < 5; i++) {
                CourseDTO courseDTO = courseFactory.createRandomCourseDTO();
                courseService.createCourse(courseDTO);
                log.info("Created course: {}", courseDTO.getName());
            }
        } else {
            log.info("Courses already exist in the database. Skipping course creation.");
        }

        //Exam and CourseExam
        if (examRepository.count() == 0) {
            log.info("No exams found in database. Creating 4 exams.");
            List<Course> courses = courseRepository.findAll();
            for (int i = 0; i < 4; i++) {
                ExamDTO examDTO = examFactory.createRandomExamDTO();
                Long examId = examService.createExam(examDTO);
                log.info("Created exam: {}", examDTO.getName());
                if (courses != null && !courses.isEmpty()) {
                    Course randomCourse = courses.get(random.nextInt(courses.size()));
                    CourseExamDTO courseExamDTO = new CourseExamDTO();
                    courseExamDTO.setCourseId(randomCourse.getId());
                    courseExamDTO.setExamId(examId);
                    courseExamDTO.setExamDate(LocalDate.now().plusDays(random.nextInt(60)));
                    courseExamService.createCourseExam(courseExamDTO);
                    log.info("Created CourseExam for exam {} and course {}", examId, randomCourse.getId());
                }
            }
        } else {
            log.info("Exams already exist in the database. Skipping exam creation.");
        }

        //Student, Enrollment and StudentExam
        if (studentRepository.count() == 0) {
            List<StudentRegistrationDTO> studentDTOs = studentFactory.createMultipleRandomStudents(10);
            log.info("Created 10 students and registering them:");
            for (StudentRegistrationDTO studentDTO : studentDTOs) {
                List<Course> courses = courseRepository.findAll();
                Student student = registrationService.registerStudent(studentDTO);
                log.info("Registered student: {}", studentDTO.getEmail());
                if (!courses.isEmpty()) {
                    Course randomCourse = courses.get(random.nextInt(courses.size()));
                    EnrollmentDTO enrollmentDTO = new EnrollmentDTO();
                    enrollmentDTO.setStudentId(student.getId());
                    enrollmentDTO.setCourseId(randomCourse.getId());
                    enrollmentDTO.setEnrollmentDate(LocalDate.now().minusDays(random.nextInt(365)));
                    enrollmentDTO.setEnrollmentStatus(EnrollmentStatus.values()[random.nextInt(EnrollmentStatus.values().length)]);
                    enrollmentService.createEnrollment(enrollmentDTO);

                    List<CourseExam> courseExams = courseExamRepository.findByCourse_Id(randomCourse.getId());
                    if(!courseExams.isEmpty()){
                        for(CourseExam courseExam : courseExams){
                            StudentExamDTO studentExamDTO = new StudentExamDTO();
                            studentExamDTO.setStudentId(student.getId());
                            studentExamDTO.setExamId(courseExam.getExam().getId());
                            studentExamDTO.setGrade(60 + random.nextInt(41));
                            studentExamService.createStudentExam(studentExamDTO);
                        }
                    }
                }
            }
        } else {
            log.info("Students already exist in the database. Skipping student creation.");
        }

        //Teacher and CourseTeacher
        if (teacherRepository.count() == 0) {
            List<TeacherRegistrationDTO> teacherDTOs = teacherFactory.createMultipleRandomTeachers(4);
            log.info("Created 4 teachers and registering them:");
            for (TeacherRegistrationDTO teacherDTO : teacherDTOs) {
                List<Course> courses = courseRepository.findAll();
                Teacher teacher = registrationService.registerTeacher(teacherDTO);
                log.info("Registered teacher: {}", teacherDTO.getEmail());
                if (!courses.isEmpty()) {
                    Course randomCourse = courses.get(random.nextInt(courses.size()));
                    CourseTeacherDTO courseTeacherDTO = new CourseTeacherDTO();
                    courseTeacherDTO.setCourseId(randomCourse.getId());
                    courseTeacherDTO.setTeacherId(teacher.getId());
                    courseTeacherDTO.setStartDate(LocalDate.now().minusDays(random.nextInt(365)));
                    courseTeacherService.createCourseTeacher(courseTeacherDTO);
                }
            }
        } else {
            log.info("Teachers already exist in the database. Skipping teacher creation.");
        }
    }
}
