package com.ltp.gradesubmission;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class GradeSubmissionApplicationTests {

  @Autowired
  private MockMvc mocMvc;

  @Test
  void contextLoads() {
    assertNotNull(mocMvc);
  }

  @Test
  public void TestShowGradeForm() throws Exception {
    RequestBuilder request = MockMvcRequestBuilders.get("/?id=123");

    mocMvc.perform(request)
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("form"))

        .andExpect(model().attributeExists("grade"));
  }

  @Test
  public void testSuccessfulSubmission() throws Exception {
    RequestBuilder request = MockMvcRequestBuilders.post("/handleSubmit")
        .param("name", "Harry")
        .param("subject", "Potions")
        .param("score", "C-");

    mocMvc.perform(request)
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/grades"));
  }

  @Test
  public void testUnsuccessfulSubmission() throws Exception {
    RequestBuilder request = MockMvcRequestBuilders.post("/handleSubmit")
        .param("name", " ")
        .param("subject", " ")
        .param("score", "R+");

    mocMvc.perform(request)
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("form"));
  }

  @Test
  public void testGetGrades() throws Exception {
    RequestBuilder request = MockMvcRequestBuilders.get("/grades");

    mocMvc.perform(request)
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("grades"))
        .andExpect(model().attributeExists("grades"));
        


  }
}