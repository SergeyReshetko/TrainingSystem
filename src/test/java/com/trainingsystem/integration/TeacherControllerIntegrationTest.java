package com.trainingsystem.integration;

import com.trainingsystem.model.dto.TeacherDto;
import com.trainingsystem.model.entity.TeacherEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class TeacherControllerIntegrationTest extends AbstractIntegrationTest {
    
    private static final String TEACHER_URL = "/api/v1/teacher";
    private static final String FIRST_NAME_1 = "Петр";
    private static final String LAST_NAME_1 = "Романов";
    private static final String FIRST_NAME_2 = "Екатерина";
    private static final String LAST_NAME_2 = "Романова";
    
    @BeforeEach
    void setUp() {
        teachersRepository.deleteAll();
    }
    
    @Nested
    class CreateOperations {
        
        @Test
        void shouldReturnCreatedTeacher() {
            TeacherDto teacherDto = TeacherDto
                                            .builder()
                                            .firstName(FIRST_NAME_1)
                                            .lastName(LAST_NAME_1)
                                            .build();
            
            ResponseEntity<TeacherDto> response = restTemplate.postForEntity(
                    TEACHER_URL,
                    teacherDto,
                    TeacherDto.class
            );
            
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
            assertThat(response.getBody()).isNotNull();
            assertThat(response.getBody().getFirstName()).isEqualTo(FIRST_NAME_1);
            assertThat(response.getBody().getLastName()).isEqualTo(LAST_NAME_1);
            
            TeacherEntity savedTeacher = teachersRepository.findById(response.getBody().getId()).orElse(null);
            assertThat(savedTeacher).isNotNull();
            assertThat(savedTeacher.getFirstName()).isEqualTo(FIRST_NAME_1);
            assertThat(savedTeacher.getLastName()).isEqualTo(LAST_NAME_1);
        }
    }
    
    @Nested
    class ReadOperations {
        
        @Test
        void shouldGetTeacherById() {
            TeacherEntity teacher = createTestTeacher(FIRST_NAME_1, LAST_NAME_1);
            TeacherEntity savedTeacher = teachersRepository.save(teacher);
            
            ResponseEntity<TeacherDto> response = restTemplate.getForEntity(
                    TEACHER_URL + "/" + savedTeacher.getId(),
                    TeacherDto.class
            );
            
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isNotNull();
            assertThat(response.getBody().getId()).isEqualTo(savedTeacher.getId());
            assertThat(response.getBody().getFirstName()).isEqualTo(FIRST_NAME_1);
            assertThat(response.getBody().getLastName()).isEqualTo(LAST_NAME_1);
        }
        
        @Test
        void shouldGetAllTeachers() {
            teachersRepository.saveAll(List.of(
                    createTestTeacher(FIRST_NAME_1, LAST_NAME_1),
                    createTestTeacher(FIRST_NAME_2, LAST_NAME_2)
            ));
            
            ResponseEntity<List<TeacherDto>> response = restTemplate.exchange(
                    TEACHER_URL,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {
                    }
            );
            
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isNotNull();
            assertThat(response.getBody()).hasSize(2);
        }
    }
    
    @Nested
    class UpdateOperations {
        
        @Test
        void shouldUpdatedTeacher() {
            TeacherEntity teacher = createTestTeacher(FIRST_NAME_1, LAST_NAME_1);
            teachersRepository.save(teacher);
            
            TeacherDto updateRequest = TeacherDto
                                               .builder()
                                               .id(teacher.getId())
                                               .firstName(FIRST_NAME_2)
                                               .lastName(LAST_NAME_2)
                                               .build();
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<TeacherDto> request = new HttpEntity<>(updateRequest, headers);
            
            ResponseEntity<TeacherDto> response = restTemplate.exchange(
                    TEACHER_URL,
                    HttpMethod.PUT,
                    request,
                    TeacherDto.class
            );
            
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isNotNull();
            assertThat(response.getBody().getId()).isEqualTo(updateRequest.getId());
            assertThat(response.getBody().getFirstName()).isEqualTo(updateRequest.getFirstName());
            assertThat(response.getBody().getLastName()).isEqualTo(updateRequest.getLastName());
            TeacherEntity updatedTeacher = teachersRepository.findById(response.getBody().getId()).orElse(null);
            assertThat(updatedTeacher).isNotNull();
            assertThat(updatedTeacher.getFirstName()).isEqualTo(updateRequest.getFirstName());
            assertThat(updatedTeacher.getLastName()).isEqualTo(updateRequest.getLastName());
        }
    }
    
    @Nested
    class DeleteOperations {
        
        @Test
        void shouldDeleteTeacher() {
            TeacherEntity teacher = createTestTeacher(FIRST_NAME_1, LAST_NAME_1);
            teachersRepository.save(teacher);
            
            ResponseEntity<TeacherDto> response = restTemplate.exchange(
                    TEACHER_URL + "/" + teacher.getId(),
                    HttpMethod.DELETE,
                    null,
                    TeacherDto.class
            );
            
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isNotNull();
            assertThat(response.getBody().getId()).isEqualTo(teacher.getId());
            assertThat(response.getBody().getFirstName()).isEqualTo(FIRST_NAME_1);
            assertThat(response.getBody().getLastName()).isEqualTo(LAST_NAME_1);
        }
    }
    
    @Nested
    class NullAndValidationTests {
        
        @Test
        void saveTeacher_ShouldReturnBadRequest_WhenTeacherDtoIsNull() {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
            
            ResponseEntity<String> response = restTemplate.exchange(
                    TEACHER_URL,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );
            
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        }
        
        @Test
        void saveTeacher_ShouldReturnBadRequest_WhenFirstNameIsEmpty() {
            TeacherDto teacherDto = TeacherDto
                                            .builder()
                                            .firstName("")
                                            .lastName(LAST_NAME_1)
                                            .build();
            
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    TEACHER_URL,
                    HttpMethod.POST,
                    new HttpEntity<>(teacherDto),
                    new ParameterizedTypeReference<>() {
                    }
            );
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        }
        
        @Test
        void saveTeacher_ShouldReturnBadRequest_WhenFirstNameIsNull() {
            TeacherDto teacherDto = TeacherDto
                                            .builder()
                                            .firstName(null)
                                            .lastName(LAST_NAME_1)
                                            .build();
            
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    TEACHER_URL,
                    HttpMethod.POST,
                    new HttpEntity<>(teacherDto),
                    new ParameterizedTypeReference<>() {
                    }
            );
            
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        }
        
        @Test
        void saveTeacher_ShouldReturnBadRequest_WhenMultipleFieldsAreInvalid() {
            TeacherDto teacherDto = TeacherDto
                                            .builder()
                                            .firstName("")
                                            .lastName(null)
                                            .build();
            
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    TEACHER_URL,
                    HttpMethod.POST,
                    new HttpEntity<>(teacherDto),
                    new ParameterizedTypeReference<>() {
                    }
            );
            
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        }
        
        @Test
        void updateTeacher_ShouldReturnBadRequest_WhenTeacherDtoIsNull() {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<TeacherDto> requestEntity = new HttpEntity<>(null, headers);
            
            ResponseEntity<String> response = restTemplate.exchange(
                    TEACHER_URL,
                    HttpMethod.PUT,
                    requestEntity,
                    String.class
            );
            
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        }
        
        @Test
        void updateTeacher_ShouldReturnBadRequest_WhenIdIsNull() {
            TeacherDto teacherDto = TeacherDto
                                            .builder()
                                            .id(null)
                                            .firstName(FIRST_NAME_1)
                                            .lastName(LAST_NAME_1)
                                            .build();
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<TeacherDto> requestEntity = new HttpEntity<>(teacherDto, headers);
            
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    "/api/v1/teacher",
                    HttpMethod.PUT,
                    requestEntity,
                    new ParameterizedTypeReference<>() {
                    }
            );
            
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        }
    }
    
    @Test
    void updateTeacher_ShouldReturnBadRequest_WhenIdIsNegative() {
        TeacherDto teacherDto = TeacherDto
                                        .builder()
                                        .id(-1)
                                        .firstName(FIRST_NAME_1)
                                        .lastName(LAST_NAME_1)
                                        .build();
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TeacherDto> requestEntity = new HttpEntity<>(teacherDto, headers);
        
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                TEACHER_URL,
                HttpMethod.PUT,
                requestEntity,
                new ParameterizedTypeReference<>() {
                }
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
    
    private TeacherEntity createTestTeacher(String firstName, String lastName) {
        TeacherEntity teacher = new TeacherEntity();
        teacher.setFirstName(firstName);
        teacher.setLastName(lastName);
        return teacher;
    }
}
