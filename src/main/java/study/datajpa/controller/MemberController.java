package study.datajpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.repository.MemberRepository;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberRepository memberRepository;

    //도메인 클래스 컨버터 사용 전
    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id") Long id) {
        Member member = memberRepository.findById(id).get();
        return member.getUsername();
    }

    //도메인 클래스 컨버터 사용 후
    @GetMapping("/members2/{id}")
    public String findMember(@PathVariable("id") Member member) {
        return member.getUsername();
    }

    /**
     * 요청 파라미터
     * 예) /members?page=0&size=3&sort=id,desc&sort=username,desc
     * page: 현재 페이지, 0부터 시작한다.
     * size: 한 페이지에 노출할 데이터 건수
     * sort: 정렬 조건을 정의한다. 예) 정렬 속성,정렬 속성...(ASC | DESC), 정렬 방향을 변경하고 싶으면 sort
     * 파라미터 추가 ( asc 생략 가능)
     * **/
    //페이징
    @GetMapping("/members")
    public Page<MemberDto> list(@PageableDefault(size = 12, sort = "username",
            direction = Sort.Direction.DESC) Pageable pageable) {

        //되도록 엔티티를 API로 노출하지 않는 것이 좋다.
        Page<Member> page = memberRepository.findAll(pageable);

        //dto로 송출하자.
        Page<MemberDto> pageDto = page.map(member -> new MemberDto(member.getId(), member.getUsername(), null));
        return pageDto;
    }
}
