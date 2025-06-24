package ramzi.eljabali.quotes.db.dto.quotes.mapper

import ramzi.eljabali.quotes.db.dto.quotes.QuoteResponse
import ramzi.eljabali.quotes.db.entities.Quote

fun Quote.toQuoteResponse(): QuoteResponse = QuoteResponse(
    id = this.id,
    text = this.text,
    author = this.author,
    tags = this.tags.map { it.name }
)