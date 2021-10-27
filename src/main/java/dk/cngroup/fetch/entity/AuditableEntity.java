package dk.cngroup.fetch.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.time.LocalDateTime;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditableEntity {

    @CreatedDate
    protected LocalDateTime created;
    @LastModifiedDate
    protected LocalDateTime updated;

    //needs AuditorAware support - check {@link SecurityConfig.getCurrentAuditor}
    @CreatedBy
    protected String createdBy;

    @PrePersist
    void setCreateByDefault() {
        if (isBlank(createdBy)) createdBy = "system";
    }
}