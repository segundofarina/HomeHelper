package ar.edu.itba.paw.persistence;


import ar.edu.itba.paw.interfaces.ChatDao;
import ar.edu.itba.paw.model.Chat;
import ar.edu.itba.paw.model.Message;
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
import java.util.Optional;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

@Sql("classpath:schema.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)

public class ChatJdbcDaoTest {

    @Autowired
    ChatDao chatDao;

    @Autowired
    private DataSource ds;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
        //JdbcTestUtils.deleteFromTables(jdbcTemplate, "posts","serviceProviders","serviceTypes","users");

    }
    @Test
    public void writeMessageTest(){
        final String message = "Hello";
        Optional<Message> response =chatDao.writeMessage(Const.USER_ID,Const.SPROVIDER_ID,message);
        assertTrue(response.isPresent());

        assertEquals(Const.USER_ID,response.get().getFrom());
        assertEquals(Const.SPROVIDER_ID,response.get().getTo());
        assertEquals(message,response.get().getMessage());
    }
    @Test
    public void  testGetChatBetween(){
        Optional<Chat> response = chatDao.getChatBetween(Const.USER2_ID,Const.SPROVIDER2_ID);
        assertTrue(response.isPresent());
        Chat chat = response.get();

        assertEquals(Const.USER2_ID,chat.getFrom().getId());
        assertEquals(Const.SPROVIDER2_ID,chat.getTo().getId());

        assertEquals(3,chat.getMessages().size());
    }


    @Test
    public void  testGetChatsOf(){
        List<Chat> list = chatDao.getChatsOf(Const.USER2_ID);

        assertEquals(2,list.size());

    }


}
