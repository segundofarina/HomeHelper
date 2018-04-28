package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.STypeDao;
import ar.edu.itba.paw.model.ServiceType;
import org.junit.Before;
import org.junit.Test;
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

@Sql("classpath:schema.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class STypeJdbcDaoTest {
    private final static String NAME = "Name";
    private final static String NAME_UPDATE = "NameUpdate";


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
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "serviceTypes"));
    }

    @Test
    public void testGetList() {
        testCreate();

        final List<ServiceType> list = sTypeDao.getServiceTypes();
        assertNotNull(list);
        assertEquals(list.size(), JdbcTestUtils.countRowsInTable(jdbcTemplate, "serviceTypes"));
        assertEquals(list.size(), 1);
        assertEquals(list.get(0).getServiceTypeId(), 0);
        assertEquals(list.get(0).getName(), NAME);
    }

    @Test
    public void testGetServiceTypeWithId() {
        testCreate();

        final ServiceType st = sTypeDao.getServiceTypeWithId(0);
        assertNotNull(st);
        assertEquals(st.getServiceTypeId(), 0);
        assertEquals(st.getName(), NAME);
    }


    @Test
    public void testUpdateServiceTypeWithId() {
        testCreate();

        final ServiceType st = sTypeDao.updateServiceTypeWithId(0, NAME_UPDATE);
        assertNotNull(st);
        assertEquals(st.getServiceTypeId(), 0);
        assertEquals(st.getName(), NAME_UPDATE);
    }
}
