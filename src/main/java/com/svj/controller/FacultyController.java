package com.svj.controller;

import com.svj.dto.CourseRequestDTO;
import com.svj.dto.CourseResponseDTO;
import com.svj.dto.ServiceResponse;
import com.svj.service.FacultyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/faculties")
public class FacultyController {

    private FacultyService facultyService;

    FacultyController(FacultyService facultyService){
        this.facultyService= facultyService;
    }

    @PostMapping("/courses")
    public ServiceResponse<CourseResponseDTO> addNewCourse(@RequestBody  CourseRequestDTO courseRequestDTO){
        return facultyService.addNewCourseToDashboard(courseRequestDTO);
    }

    @GetMapping("/courses/all")
    public ServiceResponse<List<CourseResponseDTO>> viewAllCourses(){
        return facultyService.fetchAllCourses();
    }

    @GetMapping("/courses/{courseId}")
    public ServiceResponse<CourseResponseDTO> viewCourseById(@PathVariable Integer courseId){
        return facultyService.fetchCourseByPathParam(courseId);
    }

    @GetMapping("/courses")
    public ServiceResponse<CourseResponseDTO> viewCourseByIdReqParam(@RequestParam Integer courseId){
        ServiceResponse<CourseResponseDTO> response= facultyService.fetchCourseByReqParam(courseId);
        return response;
    }

    @PutMapping("/courses/{courseId}")
    public ServiceResponse<CourseResponseDTO> updateCourse(@PathVariable Integer courseId, @RequestBody CourseRequestDTO courseRequestDTO){
        facultyService.updateCourseInDashboard(courseRequestDTO, courseId);
        return facultyService.fetchCourseByPathParam(courseId);
    }

    @DeleteMapping("/courses/{courseId}")
    public String deleteCourse(@PathVariable Integer courseId) {
        facultyService.deleteCourseInDashboard(courseId);
        return "Course deleted with ID: ".concat(Integer.toString(courseId));
    }

    @GetMapping("/courses/search")
    public ServiceResponse<List<CourseResponseDTO>> findCourseByWord(@RequestParam String courseKey){
        ServiceResponse serviceResponse= new ServiceResponse(HttpStatus.OK, facultyService.findCoursesWithKey(courseKey));
        return serviceResponse;

    }
}
