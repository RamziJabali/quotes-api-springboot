package ramzi.eljabali.quotes

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.hamcrest.Matchers
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import ramzi.eljabali.quotes.db.dto.quotes.QuoteRequest
import ramzi.eljabali.quotes.db.dto.quotes.QuoteResponse
import ramzi.eljabali.quotes.service.QuoteService
import kotlin.test.Test

@WebMvcTest(QuoteController::class)
class QuoteControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockitoBean
    lateinit var quoteService: QuoteService

    private val objectMapper = jacksonObjectMapper()

    @Test
    fun `POST addQuote returns created quote`() {
        val quoteRequest = QuoteRequest(
            text = "Be fearless",
            author = "John Doe",
            tags = listOf("motivational", "brave")
        )

        val quoteResponse = QuoteResponse(
            id = 1L,
            text = "Be fearless",
            author = "John Doe",
            tags = listOf("motivational", "brave")
        )

        // Mock the service call
        given(quoteService.createQuote(quoteRequest)).willReturn(quoteResponse)

        mockMvc.perform(
            post("/quotes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(quoteRequest))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.text").value("Be fearless"))
            .andExpect(jsonPath("$.author").value("John Doe"))
            .andExpect(jsonPath("$.tags", Matchers.hasSize<Any>(2)))
            .andExpect(jsonPath("$.tags", Matchers.containsInAnyOrder("motivational", "brave")))
    }
}
