package kg.kasymaliev.parking;

import com.fasterxml.jackson.databind.ObjectMapper;
import kg.kasymaliev.parking.entity.Place;
import kg.kasymaliev.parking.repository.PlaceRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class ParkingApplicationTests {
    private MockMvc mockMvc;
    private WebApplicationContext webApplicationContext;

    @Autowired
    public ParkingApplicationTests(MockMvc mockMvc,
                                   WebApplicationContext webApplicationContext) {
        this.mockMvc = mockMvc;
        this.webApplicationContext = webApplicationContext;
    }

    @MockBean
    private PlaceRepository repository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        JacksonTester.initFields(this, new ObjectMapper());
        mockMvc = webAppContextSetup(webApplicationContext).build();
    }


    @Test
    public void test() throws Exception {
        List<Place> list = new ArrayList<>(5);
        list.add(new Place(1L, "A1", true));
        list.add(new Place(2L, "A2", true));
        list.add(new Place(6L, "B1", true));
        list.add(new Place(7L, "B2", true));
        list.add(new Place(15L, "C5", true));
        when(repository.findAllByIsfreeTrue()).thenReturn(list);

        mockMvc.perform(get("/api/v1/parking/places/free").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].placeName").value("A1"))
                .andExpect(jsonPath("$[1].placeName").value("A2"))
                .andExpect(jsonPath("$[2].placeName").value("B1"))
                .andExpect(jsonPath("$[3].placeName").value("B2"))
                .andExpect(jsonPath("$[4].placeName").value("C5"))
                .andReturn();
    }

}
