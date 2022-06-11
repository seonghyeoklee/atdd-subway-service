package nextstep.subway.favorite;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import nextstep.subway.auth.domain.LoginMember;
import nextstep.subway.exception.BadRequestException;
import nextstep.subway.exception.ExceptionType;
import nextstep.subway.exception.NotFoundException;
import nextstep.subway.favorite.application.FavoriteService;
import nextstep.subway.favorite.domain.FavoriteRepository;
import nextstep.subway.favorite.dto.FavoriteRequest;
import nextstep.subway.favorite.dto.FavoriteResponse;
import nextstep.subway.member.application.MemberService;
import nextstep.subway.member.domain.Member;
import nextstep.subway.station.application.StationService;
import nextstep.subway.station.domain.Station;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("즐겨찾기 추가에 대한 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class FavoriteServiceTest {

    @Mock
    private FavoriteRepository favoriteRepository;
    @Mock
    private StationService stationService;
    @Mock
    private MemberService memberService;

    @InjectMocks
    private FavoriteService favoriteService;

    private Station 대림역;
    private Station 구로디지털단지역;
    private Member 회원;
    private LoginMember 로그인_멤버;

    @BeforeEach
    void setUp() {
        대림역 = new Station(1L, "대림");
        구로디지털단지역 = new Station(2L, "구로디지털단지");
        회원 = new Member("woobeen@naver.com", "password", 29);
        로그인_멤버 = new LoginMember(1L, "woobeen@naver.com", 29);
    }

    @DisplayName("지하철역을 즐겨찾기로 등록하면 정상적으로 등록되어야 한다")
    @Test
    void register_favorite_test() {
        // given
        FavoriteRequest request = new FavoriteRequest(1L, 2L);

        when(stationService.findById(anyLong()))
            .thenReturn(대림역)
            .thenReturn(구로디지털단지역);

        when(memberService.findById(anyLong()))
            .thenReturn(회원);

        // when
        FavoriteResponse result = favoriteService.registerFavorite(로그인_멤버, request);

        // then
        assertThat(result.getSource().getId()).isEqualTo(대림역.getId());
        assertThat(result.getTarget().getId()).isEqualTo(구로디지털단지역.getId());
    }

    @DisplayName("즐겨찾기로 등록할 출발역이 null 이라면 예외가 발생한다")
    @Test
    void register_favorite_exception_test() {
        // given
        FavoriteRequest request = new FavoriteRequest(1L, 2L);

        when(stationService.findById(anyLong()))
            .thenReturn(대림역)
            .thenReturn(null);

        when(memberService.findById(anyLong()))
            .thenReturn(회원);

        // then
        assertThatThrownBy(() -> {
            favoriteService.registerFavorite(로그인_멤버, request);
        }).isInstanceOf(NotFoundException.class)
            .hasMessageContaining(ExceptionType.NOT_FOUND_STATION.getMessage());
    }

    @DisplayName("즐겨찾기로 등록할 도착역이 null 이라면 예외가 발생한다")
    @Test
    void register_favorite_exception_test2() {
        // given
        FavoriteRequest request = new FavoriteRequest(1L, 2L);

        when(stationService.findById(anyLong()))
            .thenReturn(null)
            .thenReturn(구로디지털단지역);

        when(memberService.findById(anyLong()))
            .thenReturn(회원);

        // then
        assertThatThrownBy(() -> {
            favoriteService.registerFavorite(로그인_멤버, request);
        }).isInstanceOf(NotFoundException.class)
            .hasMessageContaining(ExceptionType.NOT_FOUND_STATION.getMessage());
    }

    @DisplayName("즐겨찾기로 등록할 역이 서로 같다면 예외가 발생한다")
    @Test
    void register_favorite_same_exception_test() {
        // given
        FavoriteRequest request = new FavoriteRequest(1L, 1L);

        when(stationService.findById(anyLong()))
            .thenReturn(구로디지털단지역)
            .thenReturn(구로디지털단지역);

        when(memberService.findById(anyLong()))
            .thenReturn(회원);

        // then
        assertThatThrownBy(() -> {
            favoriteService.registerFavorite(로그인_멤버, request);
        }).isInstanceOf(BadRequestException.class)
            .hasMessageContaining(ExceptionType.CAN_NOT_SAME_STATION.getMessage());
    }
}
