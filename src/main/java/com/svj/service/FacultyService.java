package com.svj.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.svj.dto.CourseRequestDTO;
import com.svj.dto.CourseResponseDTO;
import com.svj.dto.ServiceResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private RestTemplate restTemplate;

    private static final String BASE_URL="http://localhost:8080";

    FacultyService(RestTemplate restTemplate){
        this.restTemplate= restTemplate;
    }

    public ServiceResponse<CourseResponseDTO> addNewCourseToDashboard(CourseRequestDTO courseRequestDTO){
        return restTemplate.postForObject(BASE_URL.concat("/courses"), courseRequestDTO, ServiceResponse.class);
    }

    public ServiceResponse<List<CourseResponseDTO>> fetchAllCourses(){
        return restTemplate.getForObject(BASE_URL.concat("/courses"), ServiceResponse.class);
    }

    public ServiceResponse<CourseResponseDTO> fetchCourseByPathParam(Integer id){
        return restTemplate.getForObject(BASE_URL.concat("/courses/search/").concat(Integer.toString(id)), ServiceResponse.class);
    }

    public ServiceResponse<CourseResponseDTO> fetchCourseByReqParam(Integer id){
        Map<String, Integer> requestMap= new HashMap<>();
        requestMap.put("courseId", id);
        ServiceResponse<CourseResponseDTO> response= restTemplate.getForObject(BASE_URL.concat("/courses/search?courseId={courseId}"), ServiceResponse.class, requestMap);
        return response;
    }

    public void updateCourseInDashboard(CourseRequestDTO courseRequestDTO, int courseId){
        restTemplate.put(BASE_URL.concat("/courses/").concat(Integer.toString(courseId)), courseRequestDTO);
    }

    public void deleteCourseInDashboard(int courseId){
        restTemplate.delete(BASE_URL.concat("/courses/").concat(Integer.toString(courseId)));
    }

    public List<CourseResponseDTO> findCoursesWithKey(String courseKey) {
        ServiceResponse<ArrayList<CourseResponseDTO>> allCoursesResponse=  restTemplate.getForObject(BASE_URL.concat("/courses"),ServiceResponse.class);
        ObjectMapper mapper = new  ObjectMapper();
        ArrayList<CourseResponseDTO> response = mapper.convertValue(allCoursesResponse.getResponse(), new TypeReference<ArrayList<CourseResponseDTO>>() {});
        return response.stream()
                .filter(courseDTO-> courseDTO.getName().toLowerCase().contains(courseKey.toLowerCase()))
                .collect(Collectors.toList());
    }
}
