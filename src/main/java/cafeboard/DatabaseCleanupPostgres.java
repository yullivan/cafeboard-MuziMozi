package cafeboard;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DatabaseCleanupPostgres implements InitializingBean {
    @PersistenceContext
    private EntityManager entityManager;

    private List<String> tableNames;

    @Override
    public void afterPropertiesSet() {
        // 엔티티의 이름을 테이블 이름으로 추출
        tableNames = entityManager.getMetamodel().getEntities().stream()
                .filter(e -> e.getJavaType().getAnnotation(Entity.class) != null)
                .map(e -> e.getName()
                        .replaceAll("([a-z])([A-Z])", "$1_$2") // camel case를 snake case로 변환
                        .toLowerCase())
                .collect(Collectors.toList());
    }

    @Transactional
    public void execute() {
        // 데이터 플러시
        entityManager.flush();

        // 외래 키 제약 비활성화
        entityManager.createNativeQuery("SET session_replication_role = 'replica';").executeUpdate();

        for (String tableName : tableNames) {
            // 테이블 데이터 삭제
            entityManager.createNativeQuery("TRUNCATE TABLE " + tableName + " CASCADE;").executeUpdate();

            // ID 시퀀스 초기화
            entityManager.createNativeQuery(
                            "DO $$ " +
                                    "DECLARE seq RECORD; " +
                                    "BEGIN " +
                                    "    FOR seq IN (SELECT c.relname FROM pg_class c WHERE c.relkind = 'S' AND c.relname LIKE '" + tableName + "_id_seq') LOOP " +
                                    "        EXECUTE 'ALTER SEQUENCE ' || seq.relname || ' RESTART WITH 1'; " +
                                    "    END LOOP; " +
                                    "END $$;")
                    .executeUpdate();
        }

        // 외래 키 제약 활성화
        entityManager.createNativeQuery("SET session_replication_role = 'origin';").executeUpdate();
    }
}
