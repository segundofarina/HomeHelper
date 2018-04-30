package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.STypeDao;
import ar.edu.itba.paw.model.ServiceType;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.mock;

@Sql("classpath:schema.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class STypeJdbcDaoTest {
    private final static String NAME = "Name";
    private final static String NAME_UPDATE = "NameUpdate";
    private int insertedId = 0;


    @Autowired
    private DataSource ds;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    STypeDao sTypeDao;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "serviceTypes");
    }

    @Test
    public void testCreate() {
        final ServiceType serviceType = sTypeDao.create(NAME);
        assertNotNull(serviceType);
        assertEquals(NAME, serviceType.getName());
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate,  "serviceTypes"));
    }

    @Test
    public void testGetList() {

        sTypeDao.create(NAME);
        final List<ServiceType> list = sTypeDao.getServiceTypes();
        assertNotNull(list);
        assertEquals( JdbcTestUtils.countRowsInTable(jdbcTemplate, "serviceTypes"),list.size());
        assertEquals( 1, list.size());
        assertEquals( 0, list.get(0).getServiceTypeId());
        assertEquals( NAME, list.get(0).getName());
    }

    @Test
    public void testGetServiceTypeWithId() {

        final ServiceType serviceType = sTypeDao.create(NAME);
        final ServiceType st = sTypeDao.getServiceTypeWithId(serviceType.getServiceTypeId());
        assertNotNull(st);
        assertEquals(serviceType.getServiceTypeId(), st.getServiceTypeId());
        assertEquals(NAME, st.getName());
    }


    @Test
    public void testUpdateServiceTypeWithId() {

        final ServiceType serviceType = sTypeDao.create(NAME);
        final ServiceType st = sTypeDao.updateServiceTypeWithId(serviceType.getServiceTypeId(), NAME_UPDATE);
        assertNotNull(st);
        assertEquals( serviceType.getServiceTypeId(), st.getServiceTypeId());
        assertEquals( NAME_UPDATE, st.getName());
    }

    public static ServiceType  insertDummyServiceType(STypeDao sTypeDao){
         ServiceType serviceType = sTypeDao.create(NAME);
        return serviceType;
    }
}
