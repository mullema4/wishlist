package dk.cngroup.wishlist.entity

import org.apache.commons.lang3.StringUtils.isBlank
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Parameter
import org.hibernate.id.enhanced.SequenceStyleGenerator.CONFIG_PREFER_SEQUENCE_PER_ENTITY
import org.hibernate.id.enhanced.SequenceStyleGenerator.INCREMENT_PARAM
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
class AuditableEntity(
    @Id
    @GeneratedValue(generator = "sequenceIdGenerator")
    @GenericGenerator(
        name = "sequenceIdGenerator",
        strategy = "sequence",
        parameters = [
            Parameter(name = CONFIG_PREFER_SEQUENCE_PER_ENTITY, value = "true"),
            Parameter(name = INCREMENT_PARAM, value = "50")
        ]
    )
    var id: Long? = null,

    @CreatedDate
    var created: LocalDateTime? = null,

    @LastModifiedDate
    var updated: LocalDateTime? = null,

    @CreatedBy //needs AuditorAware support - check SecurityConfig.getCurrentAuditor
    var createdBy: String? = null
) {
    @PrePersist
    fun setCreateByDefault() {
        if (isBlank(createdBy)) createdBy = "system"
    }
}