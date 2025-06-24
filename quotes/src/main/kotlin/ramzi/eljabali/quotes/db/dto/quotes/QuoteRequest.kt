package ramzi.eljabali.quotes.db.dto.quotes


data class QuoteRequest(
    val text: String,
    val author: String,
    val tags: List<String>
)
