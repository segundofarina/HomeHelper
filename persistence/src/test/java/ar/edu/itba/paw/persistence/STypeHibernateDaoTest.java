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
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import java.util.List;

import static ar.edu.itba.paw.persistence.Const.*;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;


@Sql("classpath:schema.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class STypeHibernateDaoTest {
    private final static String NAME = "Name";
    private final static String NAME_UPDATE = "NameUpdate";

    @Autowired
    private DataSource ds;
    private JdbcTemplate jdbcTemplate;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    STypeDao sTypeDao;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
    }


    @Test
    public void createTest() {
        int count = JdbcTestUtils.countRowsInTable(jdbcTemplate, "serviceTypes");
        final ServiceType serviceType = sTypeDao.create(NAME);
        em.flush();
        assertNotNull(serviceType);
        assertEquals(NAME, serviceType.getName());
        assertEquals(count + 1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "serviceTypes"));
    }

    @Test
    public void getServiceTypesTest() {
        final List<ServiceType> list = sTypeDao.getServiceTypes();
        assertNotNull(list);
        assertEquals(JdbcTestUtils.countRowsInTable(jdbcTemplate, "serviceTypes"), list.size());

    }

    @Test
    public void getServiceTypeWithIdTest() {
        final ServiceType st = sTypeDao.getServiceTypeWithId(SERVICETYPE_ID).get();
        assertNotNull(st);
        assertEquals(SERVICETYPE_ID, st.getServiceTypeId());
        assertEquals(SERVICETYPE_NAME, st.getName());
    }


    @Test
    public void updateServiceTypeWithIdTest() {
        final ServiceType st = sTypeDao.updateServiceTypeWithId(SERVICETYPE_ID, NAME_UPDATE).get();
        em.flush();
        assertNotNull(st);
        assertEquals(SERVICETYPE_ID, st.getServiceTypeId());
        assertEquals(NAME_UPDATE, st.getName());
    }

}
