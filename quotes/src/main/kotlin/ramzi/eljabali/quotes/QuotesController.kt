package ramzi.eljabali.quotes

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ramzi.eljabali.quotes.db.dto.QuoteRequest
import ramzi.eljabali.quotes.db.entities.Quote
import ramzi.eljabali.quotes.service.QuoteService

@RestController
@RequestMapping("/quotes")
class QuotesController(private val quoteService: QuoteService) {
    @PostMapping
    fun addQuote(@RequestBody request: QuoteRequest) = quoteService.createQuote(request)

    @GetMapping("/tag/{tagName}")
    fun getQuotesTag(@PathVariable tagName: String): List<Quote> {
        return quoteService.getQuotesByTag(tagName)
    }
}