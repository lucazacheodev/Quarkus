# Guida alla Configurazione di Profili Multipli per Test in Quarkus

## Passo 1: Configurazione del file pom.xml

```xml
<!-- Nella sezione configuration del plugin maven-failsafe-plugin -->
<skipITs>false</skipITs>

<!-- profili per i diversi database -->
<profiles>
    <!-- Profilo H2 per Unit Test -->
    <profile>
        <id>h2</id>
        <activation>
            <activeByDefault>true</activeByDefault>
        </activation>
        <properties>
            <quarkus.profile>h2</quarkus.profile>
        </properties>
    </profile>

    <!-- Profilo Oracle per Integration Test -->
    <profile>
        <id>oracle</id>
        <properties>
            <quarkus.profile>oracle</quarkus.profile>
        </properties>
    </profile>
</profiles>
```

## Passo 2: Configurazione del file application.properties

```properties
# Configurazione H2 per unit test
%h2.quarkus.datasource.db-kind=h2
%h2.quarkus.datasource.jdbc.url=jdbc:h2:mem:testdb
%h2.quarkus.datasource.username=sa
%h2.quarkus.datasource.password=sa
%h2.quarkus.hibernate-orm.database.generation=update

# Configurazione Oracle per integration test
%oracle.quarkus.datasource.db-kind=oracle
%oracle.quarkus.datasource.jdbc.url=jdbc:oracle:thin:@//localhost:1521/testdb
%oracle.quarkus.datasource.username=sa
%oracle.quarkus.datasource.password=sa
%oracle.quarkus.hibernate-orm.dialect=org.hibernate.dialect.OracleDialect
```

## Passo 3: Configurazione delle classi "TestProfile"

```java
public class H2TestProfile implements QuarkusTestProfile {
    @Override
    public String getConfigProfile() {
        return "h2";
    }
}
```

```java
public class OracleTestProfile implements QuarkusTestProfile {
    @Override
    public String getConfigProfile() {
        return "oracle";
    }
}
```

## Passo 4: Configurazione delle classi di test

```java

@QuarkusTest
@TestProfile(H2TestProfile.class)
class MyControllerTest {
    //Unit Tests
}
```

```java
@QuarkusTest
@TestProfile(OracleTestProfile.class)
class MyControllerIT {
    //Integration Tests
}
```

## Conclusione

Ora lanciando il comando ```mvn verify``` eseguiremo prima i test unitari con il plugin ```maven-surefire-plugin``` con il profilo ```H2``` sul database omonimo e, successivamente, i test di integrazione con il plugin ```maven-failsafe-plugin``` con il profilo ```Oracle``` sul database omonimo.
