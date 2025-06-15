package ramzi.eljabali.quotes.db.dto

import ramzi.eljabali.quotes.db.entities.Tag


data class QuoteRequest(
    val text: String,
    val author: String,
    val tags: List<String>
)
