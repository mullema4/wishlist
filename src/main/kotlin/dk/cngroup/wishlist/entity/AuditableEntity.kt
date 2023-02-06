package dk.cngroup.wishlist.entity

import jakarta.persistence.*
import org.apache.commons.lang3.StringUtils.isBlank
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
class AuditableEntity(
    @Id
    @GeneratedValue
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