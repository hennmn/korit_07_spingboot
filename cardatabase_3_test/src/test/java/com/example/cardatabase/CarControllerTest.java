package com.example.cardatabase;

import com.example.cardatabase.domain.CarRepository;
import com.example.cardatabase.web.CarController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.HttpHeaders;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarController.class)
public class CarControllerTest {
    @Autowired
    private MockMvc mockMvc;  // 여기에 perform 메서드를 사용할거라서 가져옴

    @MockitoBean
    private CarRepository carRepository;

    @Test
    @DisplayName("GET /cars 요청 테스트 검증")
    void testGetCars() throws Exception {
        this.mockMvc.perform(get("/cars")).andExpect(status().isOk());

        // CarRestTest에 있는 거 참고해서 만듬
    }

}
