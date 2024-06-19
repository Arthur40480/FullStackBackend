package fr.ldnr.FullStackBackend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@AutoConfigureMockMvc
class FullStackBackendApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testGetHotelAnGetName() throws Exception {
		mockMvc.perform(get("/hotels"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name",is("Hôtel de Crillon")));
	}
}
