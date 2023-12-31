package zw.co.munyasys.common.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.UUID;

@NoRepositoryBean
public interface BaseDao<T> extends JpaRepository<T, UUID>, JpaSpecificationExecutor<T> {
}
