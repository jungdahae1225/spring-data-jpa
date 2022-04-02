package study.datajpa.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) //JPA의 로직 상 PROTECTED 기본 생성자 넣어주기
@ToString(of = {"id", "username", "age"}) //toString에서 연관관계 필드는 무한루프에 빠질 수 있으므로 넣지 않는다.

@NamedQuery(
        name="Member.findByUsername",
        query="select m from Member m where m.username = :username"
)
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String username;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY) //team이랑 다:1
    @JoinColumn(name = "team_id")
    private Team team;
    public Member(String username) {
        this(username, 0);
    }

    public Member(String username, int age) {
        this(username, age, null);
    }

    public Member(String username, int age, Team team) {
        this.username = username;
        this.age = age;

        if (team != null) {
            changeTeam(team);
        }
    }

    public void changeTeam(Team team) { //Member는 팀을 변경 할 수 있도록(그냥 초기 세팅도 이거 사용)
        this.team = team;
        team.getMembers().add(this);
    }
}
