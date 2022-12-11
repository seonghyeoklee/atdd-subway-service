package nextstep.subway.favorite.domain;

import nextstep.subway.member.domain.Member;
import nextstep.subway.station.domain.Station;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static nextstep.subway.member.acceptance.MemberAcceptanceTest.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FavoritesTest {
    private Member 사용자;
    private Member 다른_사용자;
    private Station 강남역;
    private Station 양재역;
    private Station 판교역;
    private Favorite 첫번째_즐겨찾기;
    private Favorite 두번째_즐겨찾기;

    @BeforeEach
    void setUp() {
        강남역 = new Station("강남역");
        양재역 = new Station("양재역");
        판교역 = new Station("판교역");
        사용자 = new Member(EMAIL, PASSWORD, AGE);
        다른_사용자 = new Member(NEW_EMAIL, NEW_PASSWORD, NEW_AGE);
        첫번째_즐겨찾기 = Favorite.builder()
                .member(사용자)
                .source(강남역)
                .target(양재역)
                .build();
        두번째_즐겨찾기 = Favorite.builder()
                .member(사용자)
                .source(판교역)
                .target(양재역)
                .build();
    }

    @DisplayName("불변 객체에 즐겨찾기를 추가하는 경우 예외가 발생한다")
    @Test
    void 즐겨찾기_불변객체_예외() {
        // given
        Favorites favorites = new Favorites();

        // then
        assertThatThrownBy(() -> favorites.values().add(첫번째_즐겨찾기))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @DisplayName("즐겨찾기를 중복으로 추가하는 경우 예외가 발생한다")
    @Test
    void 즐겨찾기_중복_예외() {
        // given
        Favorites favorites = new Favorites();
        favorites.add(첫번째_즐겨찾기);

        // then
        assertThatThrownBy(() -> favorites.add(첫번째_즐겨찾기))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("즐겨찾기 추가")
    @Test
    void 즐겨찾기_추가() {
        // given
        Favorites favorites = new Favorites();
        favorites.add(첫번째_즐겨찾기);
        favorites.add(두번째_즐겨찾기);

        // then
        assertThat(favorites.values()).hasSize(2);
    }

    @DisplayName("즐겨찾기 삭제")
    @Test
    void 즐겨찾기_삭제() {
        // given
        Favorites favorites = new Favorites();
        favorites.add(첫번째_즐겨찾기);
        favorites.add(두번째_즐겨찾기);
        favorites.remove(첫번째_즐겨찾기);

        // then
        assertThat(favorites.values()).hasSize(1);
        assertThat(favorites.values()).containsExactly(두번째_즐겨찾기);
    }
}
