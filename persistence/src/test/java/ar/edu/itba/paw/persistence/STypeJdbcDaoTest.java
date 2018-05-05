package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.STypeDao;
import ar.edu.itba.paw.model.ServiceType;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
        //JdbcTestUtils.deleteFromTables(jdbcTemplate, "messages","posts","serviceProviders","serviceTypes","users");
    }

    @After
    public void check() {

    }

    @Test
    public void testCreate() {
        int count =JdbcTestUtils.countRowsInTable(jdbcTemplate,  "serviceTypes");
        final ServiceType serviceType = sTypeDao.create(NAME);
        assertNotNull(serviceType);
        assertEquals(NAME, serviceType.getName());
        assertEquals(count+1, JdbcTestUtils.countRowsInTable(jdbcTemplate,  "serviceTypes"));
    }

    @Test
    public void testGetList() {

        final List<ServiceType> list = sTypeDao.getServiceTypes();
        assertNotNull(list);
        assertEquals( JdbcTestUtils.countRowsInTable(jdbcTemplate, "serviceTypes"),list.size());

    }

    @Test
    public void testGetServiceTypeWithId() {

        final ServiceType st = sTypeDao.getServiceTypeWithId(Const.SERVICETYPE_ID);
        assertNotNull(st);
        assertEquals(Const.SERVICETYPE_ID, st.getServiceTypeId());
        assertEquals(Const.SERVICETYPE_NAME, st.getName());
    }


    @Test
    public void testUpdateServiceTypeWithId() {


        final ServiceType st = sTypeDao.updateServiceTypeWithId(Const.SERVICETYPE_ID, NAME_UPDATE);
        assertNotNull(st);
        assertEquals( Const.SERVICETYPE_ID, st.getServiceTypeId());
        assertEquals( NAME_UPDATE, st.getName());
    }

    public static ServiceType  insertDummyServiceType(STypeDao sTypeDao){
         ServiceType serviceType = sTypeDao.create(NAME);
        return serviceType;
    }
}
