package kz.shyngys.notice_board.impl;

import kz.shyngys.notice_board.dto.read.AdvertisementToReadDto;
import kz.shyngys.notice_board.dto.write.AdvertisementToCreateUpdateDto;
import kz.shyngys.notice_board.exception.NoAdvertisementWithId;
import kz.shyngys.notice_board.model.db.Advertisement;
import kz.shyngys.notice_board.model.db.User;
import kz.shyngys.notice_board.repository.AdvertisementRepository;
import kz.shyngys.notice_board.repository.UserRepository;
import kz.shyngys.notice_board.service.AuthService;
import kz.shyngys.notice_board.util.RND;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AdvertisementServiceImplTest {

    @Mock
    private AdvertisementRepository advertisementRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthService authService;

    @InjectMocks
    private AdvertisementServiceImpl advertisementService;

    @SuppressWarnings("EmptyTryBlock")
    @BeforeMethod
    public void setUp() {
        try (var ignore = MockitoAnnotations.openMocks(this)) {
            // ignore
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void loadById() {
        Long id = RND.randomLong();

        Advertisement testAd = Advertisement.builder()
                .id(id)
                .title("U5vlxRGL4o")
                .description("gAEXhFjZHP")
                .createdAt(LocalDateTime.now())
                .minPrice(RND.randomLong())
                .build();

        doReturn(Optional.of(testAd)).when(advertisementRepository).findById(id);

        //
        //
        AdvertisementToReadDto adToReadDto = advertisementService.loadById(id);
        //
        //

        verify(advertisementRepository, times(1)).findById(id);

        assertThat(adToReadDto).isNotNull();
        assertThat(adToReadDto.id()).isEqualTo(testAd.getId());
        assertThat(adToReadDto.title()).isEqualTo(testAd.getTitle());
        assertThat(adToReadDto.description()).isEqualTo(testAd.getDescription());
        assertThat(adToReadDto.createdAt()).isEqualTo(testAd.getCreatedAt());
        assertThat(adToReadDto.minPrice()).isEqualTo(testAd.getMinPrice());
    }

    @Test(expectedExceptions = NoAdvertisementWithId.class, expectedExceptionsMessageRegExp = "^Advertisement with id.*")
    public void loadById__shouldThrowException_whenIdNotFound() {
        Long id = 1L;

        doReturn(Optional.empty()).when(advertisementRepository).findById(id);

        //
        //
        advertisementService.loadById(id);
        //
        //
    }

    @Test
    public void create() {
        Long id = RND.randomLong();

        AdvertisementToCreateUpdateDto dto = new AdvertisementToCreateUpdateDto(
                "hXFec1e7EZ", "0bTzd5706f", RND.randomLong(), LocalDateTime.now()
        );

        Advertisement resultToReturn = Advertisement.builder()
                .id(id)
                .build();

        doReturn(resultToReturn).when(advertisementRepository).save(any(Advertisement.class));
        doReturn(Optional.of(RND.randomLong())).when(authService).getCurrentUserId();
        doReturn(Optional.of(new User())).when(userRepository).findById(anyLong());

        //
        //
        Long resultId = advertisementService.create(dto, null);
        //
        //

        assertThat(resultId).isEqualTo(id);

        // capture object that is going to be saved, then assert its fields
        ArgumentCaptor<Advertisement> capturedAd = ArgumentCaptor.forClass(Advertisement.class);

        verify(advertisementRepository, times(1)).save(capturedAd.capture());
        verify(userRepository, times(1)).findById(anyLong());
        verify(authService, times(1)).getCurrentUserId();

        Advertisement valueToSave = capturedAd.getValue();

        assertThat(valueToSave).isNotNull();
        assertThat(valueToSave.getTitle()).isEqualTo(dto.title());
        assertThat(valueToSave.getDescription()).isEqualTo(dto.description());
        assertThat(valueToSave.getCreatedAt()).isEqualTo(dto.createdAt());
        assertThat(valueToSave.getMinPrice()).isEqualTo(dto.minPrice());
    }

    @Test(expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "^Title cannot be null or empty$")
    public void create__shouldThrowException_whenTitleIsNull() {

        AdvertisementToCreateUpdateDto dto = new AdvertisementToCreateUpdateDto(
                null, "0bTzd5706f", RND.randomLong(), LocalDateTime.now()
        );

        //
        //
        advertisementService.create(dto, null);
        //
        //
    }

    @Test(expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "^Title cannot be null or empty$")
    public void create__shouldThrowException_whenTitleIsEmpty() {

        AdvertisementToCreateUpdateDto dto = new AdvertisementToCreateUpdateDto(
                "", "0bTzd5706f", RND.randomLong(), LocalDateTime.now()
        );

        //
        //
        advertisementService.create(dto, null);
        //
        //
    }

    @Test(expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "^Description cannot be null or empty$")
    public void create__shouldThrowException_whenDescriptionIsNull() {

        AdvertisementToCreateUpdateDto dto = new AdvertisementToCreateUpdateDto(
                "wLT0u9FUEx", null, RND.randomLong(), LocalDateTime.now()
        );

        //
        //
        advertisementService.create(dto, null);
        //
        //
    }

    @Test(expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "^Description cannot be null or empty$")
    public void create__shouldThrowException_whenDescriptionIsEmpty() {

        AdvertisementToCreateUpdateDto dto = new AdvertisementToCreateUpdateDto(
                "mWkhIf3oSk", "", RND.randomLong(), LocalDateTime.now()
        );

        //
        //
        advertisementService.create(dto, null);
        //
        //
    }

    @Test(expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "^Minimum price cannot be null$")
    public void create__shouldThrowException_whenMinPriceIsNull() {

        AdvertisementToCreateUpdateDto dto = new AdvertisementToCreateUpdateDto(
                "wLT0u9FUEx", "suNxmTyKEJ", null, LocalDateTime.now()
        );

        //
        //
        advertisementService.create(dto, null);
        //
        //
    }

}
