package ru.bs.interview.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.bs.interview.demo.model.entities.Gender;
import ru.bs.interview.demo.model.entities.User;
import ru.bs.interview.demo.model.repo.UserRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserApiControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

    private ObjectMapper generateObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

    private XmlMapper generateXmlMapper() {
        XmlMapper mapper = new XmlMapper();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

    @PostConstruct
    private void init() {
        User user = new User();
        user.setBirthDate(LocalDate.now().minusYears(3));
        user.setGender(Gender.MALE);
        user.setName("Terminator1");
        user.setWeight(123.45);

        userRepository.save(user);
    }

    @Test
    public void readUsersJson() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/api/users")
                .param("page", "0")
                .param("limit", "100")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        System.out.println("readUsers(): " + content);
    }

    @Test
    public void readUsersXml() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/api/users")
                .param("page", "0")
                .param("limit", "100")
                .accept(MediaType.APPLICATION_XML))
                .andExpect(status().isOk())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        System.out.println("readUsers(): " + content);
    }

    @Test
    public void readUserJson() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/api/users/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());

        String content = mvcResult.getResponse().getContentAsString();
        User user = generateObjectMapper().readValue(content, User.class);
        assertEquals("Terminator1", user.getName());
    }

    @Test
    public void readUserXml() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/api/users/1")
                .accept(MediaType.APPLICATION_XML))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());

        String content = mvcResult.getResponse().getContentAsString();
        User user = generateXmlMapper().readValue(content, User.class);
        assertEquals("Terminator1", user.getName());
    }

    @Test
    public void createUserJson() throws Exception {
        String userJson = "{\"name\":\"Terminator2\",\"birthDate\":\"2018-03-22\",\"weight\":1.2,\"gender\":\"MALE\"}";

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void createUserXml() throws Exception {
        String userJson = "<User><name>Terminator2</name><birthDate>2016-03-23</birthDate><weight>123.45</weight><gender>MALE</gender></User>";

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/api/users")
                .contentType(MediaType.APPLICATION_XML)
                .content(userJson))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void updateUserJson() throws Exception {
        String userJson = "{\"name\":\"Terminator3\",\"birthDate\":\"2018-03-22\",\"weight\":1.2,\"gender\":\"MALE\"}";

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put("/api/users/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());

        String content = mvcResult.getResponse().getContentAsString();
        User user = generateObjectMapper().readValue(content, User.class);
        assertEquals("Terminator3", user.getName());
    }

    @Test
    public void updateUserXml() throws Exception {
        String userJson = "<User><name>Terminator3</name><birthDate>2016-03-23</birthDate><weight>123.45</weight><gender>MALE</gender></User>";

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put("/api/users/2")
                .contentType(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_XML)
                .content(userJson))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());

        String content = mvcResult.getResponse().getContentAsString();
        User user = generateXmlMapper().readValue(content, User.class);
        assertEquals("Terminator3", user.getName());
    }

    @Test
    public void deleteUser() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());
    }
}