package ar.edu.itba.paw;

import ar.edu.itba.paw.interfaces.daos.TemporaryImagesDao;
import ar.edu.itba.paw.model.TemporaryImage;
import ar.edu.itba.paw.service.TempImagesServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static testUtils.TemporaryImageTestUtils.assertEqualsTemporaryImages;
import static testUtils.TemporaryImageTestUtils.dummyTemporaryImage;

@RunWith(MockitoJUnitRunner.class)
public class TemporaryImageServiceImplTest {

    @Mock
    private TemporaryImagesDao temporaryImagesDaoMock;

    @InjectMocks
    private TempImagesServiceImpl tempImagesService;


    @Test
    public void insertImage() {
        TemporaryImage expected = dummyTemporaryImage();

        when(temporaryImagesDaoMock.insertImage(expected.getImage())).thenReturn(expected);

        TemporaryImage actual = tempImagesService.insertImage(expected.getImage());

        assertEqualsTemporaryImages(expected, actual);

        verify(temporaryImagesDaoMock, times(1)).insertImage(expected.getImage());

    }


    @Test
    public void getImage() {
        TemporaryImage expected = dummyTemporaryImage();

        when(temporaryImagesDaoMock.getImage(expected.getImageId())).thenReturn(Optional.ofNullable(expected));

        TemporaryImage actual = tempImagesService.getImage(expected.getImageId());

        assertEqualsTemporaryImages(actual, expected);

        verify(temporaryImagesDaoMock, times(1)).getImage(anyInt());
    }
}
