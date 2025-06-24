package ramzi.eljabali.quotes.service

import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ramzi.eljabali.quotes.db.dto.quotes.QuoteRequest
import ramzi.eljabali.quotes.db.dto.quotes.QuoteResponse
import ramzi.eljabali.quotes.db.dto.quotes.mapper.toQuoteResponse
import ramzi.eljabali.quotes.db.entities.Quote
import ramzi.eljabali.quotes.db.entities.Tag
import ramzi.eljabali.quotes.repositories.QuoteRepository
import ramzi.eljabali.quotes.repositories.TagRepository
import kotlin.random.Random

@Service
class QuoteService(
    private val quoteRepository: QuoteRepository,
    private val tagRepository: TagRepository
) {
    @Transactional
    fun createQuote(request: QuoteRequest): QuoteResponse {
        val tagEntities = request.tags.map { tagName ->
            tagRepository.findByName(tagName) ?: Tag(name = tagName)
        }.toSet()

        val quote = Quote(
            text = request.text,
            author = request.author,
            tags = tagEntities
        )
        val saved = quoteRepository.save(quote)

        return saved.toQuoteResponse()
    }

    @Transactional(readOnly = true)
    fun getQuotesByTag(tagName: String): List<QuoteResponse>{
        return quoteRepository.findByTags_Name(tagName).map { it.toQuoteResponse() }
    }

    @Transactional(readOnly = true)
    fun getRandomQuote(): QuoteResponse {
        val count = quoteRepository.count()
        if (count == 0L) throw IllegalStateException("No quotes in database.")
        val randomIndex = Random.nextInt(count.toInt())
        val quote = quoteRepository.findAll(
            PageRequest.of(randomIndex, 1)
        ).first()
        return quote.toQuoteResponse()
    }
}
