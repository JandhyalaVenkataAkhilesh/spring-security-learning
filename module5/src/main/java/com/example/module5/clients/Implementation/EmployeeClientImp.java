package com.example.module5.clients.Implementation;

import com.example.module5.DTO.EmployeeDTO;
import com.example.module5.advice.ApiResponse;
import com.example.module5.clients.EmployeeClient;
import com.example.module5.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class EmployeeClientImp implements EmployeeClient {

    private final RestClient restClient;

    public EmployeeClientImp(@Qualifier("employeeRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    Logger logger = LoggerFactory.getLogger(EmployeeClientImp.class);

    @Override
    public List<EmployeeDTO> getAllEmployees() {

        logger.error("error log");
        logger.warn("warn log");
        logger.info("info log");
        logger.debug("debug log");
        logger.trace("trace log");

        try {
            ApiResponse<List<EmployeeDTO>> employeeDTOList=restClient.get()
                                                    .uri("employees")
                                                    .retrieve()
                                                    .body(new ParameterizedTypeReference<>() {
                                                    });
            return employeeDTOList.getData();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public EmployeeDTO getEmployeeById(Long employeeId) {
        try {
            ApiResponse<EmployeeDTO> employeeDTOList=restClient.get()
                    .uri("employees/{employeeId}",employeeId)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });
            return employeeDTOList.getData();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public EmployeeDTO createNewEmployee(EmployeeDTO employeeDTO) {
        try {
            ApiResponse<EmployeeDTO> employeeDTOList=restClient.post()
                    .uri("employees")
                    .body(employeeDTO)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError,(req,res) -> {
                        System.out.println("Error Occured : " + new String(res.getBody().readAllBytes()));
                        throw new ResourceNotFoundException("could not create employee");
                        // line 53 to 58 is about how to handle server errors
                        //4xx is client side error, 5xx is server side error
                    })
                    .body(new ParameterizedTypeReference<>() {
                    });
            return employeeDTOList.getData();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
