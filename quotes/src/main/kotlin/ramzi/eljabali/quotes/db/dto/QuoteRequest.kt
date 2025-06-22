package ramzi.eljabali.quotes.db.dto


data class QuoteRequest(
    val text: String,
    val author: String,
    val tags: List<String>
)
