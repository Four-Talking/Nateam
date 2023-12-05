package fourtalking.Nateam.common;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class})
public class BaseLastModifiedTimeEntity extends BaseCreatedTimeEntity{

    @LastModifiedDate
    private LocalDateTime lastModifiedTime;
}