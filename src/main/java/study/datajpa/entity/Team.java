package study.datajpa.entity;

import lombok.*;
import study.datajpa.entity.auditing.JpaBaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "name"})
public class Team extends JpaBaseEntity {

    @Id @GeneratedValue
    @Column(name = "team_id")
    private Long id;
    private String name;

    @OneToMany(mappedBy = "team") //member랑 1:다 관계
    List<Member> members = new ArrayList<>();

    public Team(String name) {
        this.name = name;
    }
}