package sainthonore.loaddatatotouroperator.Model;

import static jakarta.persistence.TemporalType.TIMESTAMP;

import java.util.Date;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@MappedSuperclass
@Data
public class Audit<U> {

    @CreatedBy
    protected U createdBy;

    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Temporal(TIMESTAMP)
    protected Date createdAt;

    @LastModifiedBy
    protected U lastModifiedBy;

    @LastModifiedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Temporal(TIMESTAMP)
    protected Date lastModifiedAt;

    @LastModifiedBy
    protected U deleteBy;

    @LastModifiedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Temporal(TIMESTAMP)
    protected Date deleteAt;

}