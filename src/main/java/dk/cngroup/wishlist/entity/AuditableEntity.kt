package dk.cngroup.wishlist.entity

import org.apache.commons.lang3.StringUtils.isBlank
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass
import javax.persistence.PrePersist

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
open class AuditableEntity(
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