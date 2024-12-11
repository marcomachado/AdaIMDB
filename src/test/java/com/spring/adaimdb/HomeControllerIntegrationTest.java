package com.spring.adaimdb;

import com.spring.adaimdb.imdb.ReadCsv;
import com.spring.adaimdb.imdb.RequestDataFromOmdb;
import com.spring.adaimdb.imdb.model.ImdbMovie;
import com.spring.adaimdb.services.OmdbService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class HomeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        Mockito.reset();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @WithMockUser
    void testHomeEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/home"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Welcome to AdaImdb"));
    }

    @Test
    @WithMockUser
    void testRootEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Welcome to AdaImdb"));
    }
}

