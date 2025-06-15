package ramzi.eljabali.quotes.db.entities

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany

@Entity
data class Quote(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val text: String,
    val author: String,

    @ManyToMany(cascade = [
        CascadeType.PERSIST,
        CascadeType.MERGE,
        CascadeType.REFRESH,
    ])
    @JoinTable(
        name = "quote_tags",
        joinColumns = [JoinColumn(name = "quote_id")],
        inverseJoinColumns = [JoinColumn(name = "tag_id")]
    )
    val tags: Set<Tag> = setOf()
)