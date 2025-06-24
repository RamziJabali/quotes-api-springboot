package ramzi.eljabali.quotes.db.dto.quotes

data class QuoteResponse(
    val id: Long,
    val text: String,
    val author: String,
    val tags: List<String>
)
