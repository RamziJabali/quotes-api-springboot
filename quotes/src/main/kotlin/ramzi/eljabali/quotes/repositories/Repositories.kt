package ramzi.eljabali.quotes.repositories

import org.springframework.data.jpa.repository.JpaRepository
import ramzi.eljabali.quotes.db.entities.Quote
import ramzi.eljabali.quotes.db.entities.Tag

interface QuoteRepository : JpaRepository<Quote, Long> {
    fun findByTagsName(name: String): List<Quote>
}

interface TagRepository : JpaRepository<Tag, Long> {
    fun findByName(name: String): Tag?
}
