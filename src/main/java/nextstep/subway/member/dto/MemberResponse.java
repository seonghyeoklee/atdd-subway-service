package nextstep.subway.member.dto;

import nextstep.subway.member.domain.Member;

public class MemberResponse {
    private final Long id;
    private final String email;
    private final Integer age;

    public MemberResponse(final Long id, final String email, final Integer age) {
        this.id = id;
        this.email = email;
        this.age = age;
    }

    public static MemberResponse of(final Member member) {
        return new MemberResponse(member.getId(), member.getEmail(), member.getAge());
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Integer getAge() {
        return age;
    }
}
