package service;

import dao.EventDataDao;
import model.EventData;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DataLogServiceTest {

    DataLogService dataLogService = new DataLogService();
    EventDataDao eventDataDao = new EventDataDao();

    @Test
    public void processLogFileTest(){
        convertLogDataToEventData();
        assertEquals(getFirstElement(), createEventDataListForTest().get(0));
        assertEquals(getLastElement(), createEventDataListForTest().get(1));
    }

    private void convertLogDataToEventData() {
        ClassLoader classLoader = getClass().getClassLoader();
        String path = classLoader.getResource("example.log").getPath();
        dataLogService.processLogFile(path);
    }

    private EventData getFirstElement(){
        eventDataDao.openCurrentSession();
        return eventDataDao.getDataById(1L);
    }

    private EventData getLastElement(){
        eventDataDao.openCurrentSession();
        return eventDataDao.getDataById(3L);
    }

    private List<EventData> createEventDataListForTest(){
        List<EventData> eventDataList = new ArrayList<>();

        EventData ed1 = new EventData();
        ed1.setDuration(5L);
        ed1.setEventId("scsmbstgra");
        ed1.setAlert(true);
        ed1.setHost("12345");
        ed1.setId(1L);
        ed1.setType("APPLICATION_LOG");

        EventData ed2 = new EventData();
        ed2.setDuration(3L);
        ed2.setEventId("scsmbstgrb");
        ed2.setAlert(false);
        ed2.setId(3L);

        eventDataList.add(ed1);
        eventDataList.add(ed2);

        return eventDataList;
    }


}
