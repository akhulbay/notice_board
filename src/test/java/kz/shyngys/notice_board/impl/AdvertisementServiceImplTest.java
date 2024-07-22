package kz.shyngys.notice_board.impl;

import kz.shyngys.notice_board.dto.read.AdvertisementToReadDto;
import kz.shyngys.notice_board.exception.NoAdvertisementWithId;
import kz.shyngys.notice_board.model.Advertisement;
import kz.shyngys.notice_board.repository.AdvertisementRepository;
import kz.shyngys.notice_board.util.RND;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class AdvertisementServiceImplTest {

    @Mock
    private AdvertisementRepository advertisementRepository;

    @InjectMocks
    private AdvertisementServiceImpl advertisementService;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void loadById__shouldFindAdvertisement() {
        Long id = 1L;

        Advertisement testAd = Advertisement.builder()
                .id(id)
                .title("U5vlxRGL4o")
                .description("gAEXhFjZHP")
                .createdAt(LocalDateTime.now())
                .minPrice(RND.randomLong())
                .build();

        Mockito.doReturn(Optional.of(testAd)).when(advertisementRepository).findById(id);

        //
        //
        AdvertisementToReadDto adToReadDto = advertisementService.loadById(id);
        //
        //

        Mockito.verify(advertisementRepository, Mockito.times(1)).findById(id);

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

        Mockito.doReturn(Optional.empty()).when(advertisementRepository).findById(id);

        //
        //
        advertisementService.loadById(id);
        //
        //
    }

}
