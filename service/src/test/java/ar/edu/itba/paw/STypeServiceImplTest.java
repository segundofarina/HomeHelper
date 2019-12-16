package ar.edu.itba.paw;

import ar.edu.itba.paw.interfaces.daos.STypeDao;
import ar.edu.itba.paw.model.ServiceType;
import ar.edu.itba.paw.service.STypeServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static testUtils.ServiceTypeTestUtils.*;

@RunWith(MockitoJUnitRunner.class)
public class STypeServiceImplTest {

    @Mock
    private STypeDao sTypeDaoMock;

    @InjectMocks
    private STypeServiceImpl sTypeService;

    @Test
    public void create() {
        ServiceType expected = dummyServiceType();
        when(sTypeDaoMock.create(expected.getName()))
                .thenReturn(expected)
                .thenReturn(null);

        ServiceType actual = sTypeService.create(expected.getName());

        assertEqualsServiceTypes(expected, actual);

        ServiceType shouldBeNull = sTypeService.create(expected.getName());
        assertNull(shouldBeNull);

        verify(sTypeDaoMock, times(2)).create(expected.getName());
    }

    @Test
    public void getServiceTypes() {
        List<ServiceType> expectedList = dummyServiceTypes(20);
        when(sTypeDaoMock.getServiceTypes())
                .thenReturn(expectedList)
                .thenReturn(null);

        List<ServiceType> actualList = sTypeService.getServiceTypes();

        for (int i = 0; i < expectedList.size(); i++) {
            assertEqualsServiceTypes(expectedList.get(i), actualList.get(i));
        }

        verify(sTypeDaoMock, times(1)).getServiceTypes();

    }

    @Test
    public void getServiceTypeWithId() {
        Optional<ServiceType> expected = Optional.ofNullable(dummyServiceType());
        when(sTypeDaoMock.getServiceTypeWithId(expected.get().getId()))
                .thenReturn(expected);

        ServiceType actual = sTypeService.getServiceTypeWithId(expected.get().getId());
        assertEqualsServiceTypes(expected.get(), actual);

        ServiceType shouldBeNull = sTypeService.getServiceTypeWithId(3);
        assertEqualsServiceTypes(shouldBeNull, null);
        verify(sTypeDaoMock, times(2)).getServiceTypeWithId(anyInt());

    }

    @Test
    public void updateServiceTypeWithId() {
        Optional<ServiceType> expected = Optional.ofNullable(dummyServiceType());
        String newPassword = new Random().toString();
        expected.get().setName(newPassword);
        when(sTypeDaoMock.updateServiceTypeWithId(expected.get().getId(), newPassword))
                .thenReturn(expected).thenReturn(Optional.empty());

        ServiceType actual = sTypeService.updateServiceTypeWithId(expected.get().getId(), newPassword);

        assertEqualsServiceTypes(expected.get(), actual);

        ServiceType shouldBeNull = sTypeService.updateServiceTypeWithId(3, newPassword);

        assertEqualsServiceTypes(shouldBeNull, null);
        verify(sTypeDaoMock, times(2)).updateServiceTypeWithId(anyInt(), anyString());
    }
}
