package ramzi.eljabali.quotes

import org.mockito.ArgumentMatchers.any
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.test.context.bean.override.mockito.MockitoBean
import ramzi.eljabali.quotes.db.dto.quotes.QuoteResponse
import ramzi.eljabali.quotes.db.dto.quotes.mapper.toQuoteResponse
import ramzi.eljabali.quotes.db.entities.Quote
import ramzi.eljabali.quotes.db.entities.Tag
import ramzi.eljabali.quotes.repositories.QuoteRepository
import ramzi.eljabali.quotes.service.QuoteService
import kotlin.test.Test
import kotlin.test.assertEquals

@SpringBootTest
class QuoteServiceTest {
    @Autowired
    lateinit var quoteService: QuoteService

    @MockitoBean
    lateinit var quoteRepository: QuoteRepository

    @Test
    fun `getRandomQuote returns a random quote`() {
        // Setup test data
        val tag = Tag(id = 1L, name = "motivational")
        val quotes = listOf(
            Quote(id = 1L, text = "Be brave", author = "Someone", tags = setOf(tag))
        )
        val page = PageImpl(quotes)

        // Mock the repository to return test data
        given(quoteRepository.count()).willReturn(1L)
        given(quoteRepository.findAll(any<Pageable>())).willReturn(page)

        // Call the service
        val result = quoteService.getRandomQuote()

        // Assert
        assertEquals("Be brave", result.text)
        assertEquals("Someone", result.author)
    }

    @Test
    fun `getQuotesByTag returns a quote with that tag`() {
        val strongTag = Tag(id = 1L, name = "strong")
        val braveTag = Tag(id = 2L, name = "brave")
        val motivationalTag = Tag(id = 2L, name = "motivational")

        val expectedQuotes = listOf(
            Quote(id = 2L, text = "Be brave!", author = "Someone", tags = setOf(braveTag)),
            Quote(
                id = 2L,
                text = "Be really brave and inspiring!",
                author = "Someone",
                tags = setOf(braveTag, motivationalTag)
            )
        )
        // Mock the repository to return test data
        given(quoteRepository.count()).willReturn(4L)
        given(quoteRepository.findByTags_Name(braveTag.name))
            .willReturn(expectedQuotes)

        // Call the service
        val result = quoteService.getQuotesByTag(braveTag.name)

        assertEquals(expectedQuotes.size, result.size)
        assertEquals(expectedQuotes.map { it.toQuoteResponse() }, result)
    }
}
