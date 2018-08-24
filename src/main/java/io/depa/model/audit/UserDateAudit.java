package io.depa.model.audit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.MappedSuperclass;

/**
 * JPA Date & User audit object.
 *
 * @author Artem Kostritsa
 * @version 1.0
 */

@MappedSuperclass
@JsonIgnoreProperties(
        value = {"createdBy", "updatedBy"},
        allowGetters = true
)
public abstract class UserDateAudit extends DateAudit {
    @CreatedBy
    private int createdBy;

    @LastModifiedBy
    private int updatedBy;

    public int getCreatedBy() {
        return createdBy;
    }
    public int getUpdatedBy() {
        return updatedBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }
    public void setUpdatedBy(int updatedBy) {
        this.updatedBy = updatedBy;
    }
}
