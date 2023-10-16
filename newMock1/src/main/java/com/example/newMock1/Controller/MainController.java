package com.example.newMock1.Controller;

import com.example.newMock1.Model.RequestDTO;
import com.example.newMock1.Model.ResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;

@RestController
public class MainController {

    private Logger log = LoggerFactory.getLogger(MainController.class);
    private ObjectMapper mapper = new ObjectMapper();

    @PostMapping(
            value = "/info/postBalance",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public Object postBalance(@RequestBody RequestDTO requestDTO) {
        try {
            String clientId = requestDTO.getClientId();
            char firstDigit = clientId.charAt(0);
            BigDecimal maxLimit;
            String rqUID = requestDTO.getRqUID();

            if (firstDigit == '8') {
                maxLimit = new BigDecimal(2000);
                ResponseDTO responseDTO = createResponseDTO(rqUID, clientId, requestDTO.getAccount(), "US", maxLimit);
                logResponseAndRequest(requestDTO, responseDTO);
                return responseDTO;
            } else if (firstDigit == '9') {
                maxLimit = new BigDecimal(1000);
                ResponseDTO responseDTO = createResponseDTO(rqUID, clientId, requestDTO.getAccount(), "EU", maxLimit);
                logResponseAndRequest(requestDTO, responseDTO);
                return responseDTO;
            } else {
                maxLimit = new BigDecimal(10000);
                ResponseDTO responseDTO = createResponseDTO(rqUID, clientId, requestDTO.getAccount(), "RU", maxLimit);
                logResponseAndRequest(requestDTO, responseDTO);
                return responseDTO;
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    private ResponseDTO createResponseDTO(String rqUID, String clientId, String account, String currency, BigDecimal maxLimit) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setRqUID(rqUID);
        responseDTO.setClientId(clientId);
        responseDTO.setAccount(account);
        responseDTO.setCurrency(currency);
        double randomBalance = Math.random() * maxLimit.doubleValue();
        BigDecimal roundedBalance = new BigDecimal(randomBalance).setScale(0, RoundingMode.HALF_UP); // Округление до целого числа
        responseDTO.setBalance(roundedBalance);
        responseDTO.setMaxLimit(maxLimit);
        return responseDTO;
    }

    private void logResponseAndRequest(RequestDTO requestDTO, ResponseDTO responseDTO) {
        try {
            log.error("********** LimitRequestDTO **********" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestDTO));
            log.error("********** LimitResponseDTO **********" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseDTO));
            log.info("********** LimitResponseDTO **********" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseDTO));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

