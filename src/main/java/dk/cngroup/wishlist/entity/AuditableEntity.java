package dk.cngroup.wishlist.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.hibernate.id.enhanced.SequenceStyleGenerator.CONFIG_PREFER_SEQUENCE_PER_ENTITY;
import static org.hibernate.id.enhanced.SequenceStyleGenerator.INCREMENT_PARAM;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditableEntity {
    @Id
    @GeneratedValue(generator = "sequenceIdGenerator")
    @GenericGenerator(
            name = "sequenceIdGenerator",
            strategy = "sequence",
            parameters = {
                    @Parameter(name = CONFIG_PREFER_SEQUENCE_PER_ENTITY, value = "true"),
                    @Parameter(name = INCREMENT_PARAM, value = "50")
            }
    )
    Long id;

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