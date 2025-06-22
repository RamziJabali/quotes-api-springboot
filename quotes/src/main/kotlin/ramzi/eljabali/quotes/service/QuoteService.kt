package ramzi.eljabali.quotes.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ramzi.eljabali.quotes.db.dto.QuoteRequest
import ramzi.eljabali.quotes.db.entities.Quote
import ramzi.eljabali.quotes.db.entities.Tag
import ramzi.eljabali.quotes.repositories.QuoteRepository
import ramzi.eljabali.quotes.repositories.TagRepository

@Service
class QuoteService(
    private val quoteRepository: QuoteRepository,
    private val tagRepository: TagRepository
) {
    @Transactional
    fun createQuote(request: QuoteRequest): Quote {
        val tagEntities = request.tags.map { tagName ->
            tagRepository.findByName(tagName) ?: Tag(name = tagName)
        }.toSet()

        val quote = Quote(
            text = request.text,
            author = request.author,
            tags = tagEntities
        )

        return quoteRepository.save(quote)
    }

    @Transactional(readOnly = true)
    fun getQuotesByTag(tagName: String): List<Quote>{
        return quoteRepository.findByTags_Name(tagName)
    }
}
