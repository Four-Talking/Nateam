### 우리가 JPA를 사용하면서 `연관관계`를 형성하지 않고 외래키(Id)만을 필드로 두면서 엔티티를 설계한 이유
1. JPA를 사용하면서 객체 그래프 탐색할 때 실행되는 쿼리가 궁금해졌다. JPA는 연관관계만 설정해주면 해당 연관관계에 접근했을 때, 자동으로 쿼리를 실행해준다.
 이 자동으로 실행되는 쿼리는 우리가 예상하지 못하는 무수히 많은 수의 쿼리가 실행될 수도 있고, 성능상 좋지 못한 쿼리가 실행될 수도 있다. 그래서 예상되는 쿼리를 데이터베이스 조인을 통해 우리가 SQL로 직접 작성하여 실행함으로써 JPA의 종속성을 조금이나마 제거하였다.
2. 혹시나 추후에 다른 ORM으로 변경되더라도, 우리가 작성한 코드의 변경없이 마이그레이션하기 좋고, 유지보수가 용이한 코드를 구성하고 싶었다.
3. 지금까지 JPA의 연관관계를 적극 활용해오다 보니, 데이터베이스 테이블 자체에 대한 이해도가 부족한 것을 느꼈다. 부족한 이해도를 높이기 위해 엔티티 클래스를 RDB 테이블과 유사한 형태를 가지게끔 했다.
4. 어떤 기능이 필요하면 외부 라이브러리를 그냥 무작정 사용을 하는데 이렇게 되면 문제점이 일단 첫번째문제 이런 라이브러리에 너무 의존을 하게 되는것이고, 
두번째는 만약에 스프링 버전을 업그레이드 해야한다면 어떤 라이브러리는 스프링 버전 업그레이드에 대해 호환되는 것도 있지만 그렇지 않은 라이브러리도 있을 수 있어서 이런것이 개발을 진행하는데 발목을 잡을 수 있다.

### 이 프로젝트를 하고 나서 느낀점
1. 연관관계를 형성하지 않으면서 어떤 상황에서 연관관계를 형성해야 하는지, 정확한 연관관계 형성 시점에 대해서 알았다.
2. 데이터베이스의 조인에 대해서 학습하고 SQL의 대한 숙련도가 높아졌다.
3. 우리가 갈 모든 회사가 JPA를 사용하지 않는다. mybatis를 사용할 수 도 있고, JPA와는 다른 ORM을 사용할 수도 있기 때문에 좀 더 근본적인 학습을 통해서 다른 라이브러리를 수월하게 사용할 수 있게 되었다.
