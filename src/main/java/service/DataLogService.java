package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import dao.EventDataDao;
import dto.LogDataDTO;
import converter.EvenDataConverter;
import model.EventData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataLogService {

    private EvenDataConverter evenDataConverter = new EvenDataConverter();
    private Map<String, List<LogDataDTO>> logMap = new HashMap<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(DataLogService.class);
    private EventDataDao eventDataDao = new EventDataDao();

    public void processLogFile(String path) {
        try {
            eventDataDao.openCurrentSessionwithTransaction();
            InputStream fileStream = new FileInputStream(path);
            JsonReader reader = new JsonReader(new InputStreamReader(fileStream));
            reader.setLenient(true);
            Gson gson = new GsonBuilder().create();

            LOGGER.info("Starting to process of JSON file: " + path);
            while (reader.peek() != JsonToken.END_DOCUMENT) {
                LogDataDTO logDataDTO = gson.fromJson(reader, LogDataDTO.class);
                LOGGER.debug("Current saving Log Data: " + logDataDTO);
                prepareLogsToSave(logDataDTO);
            }
            reader.close();
            LOGGER.info("Processing of JSON file " + path +" finished");
        } catch (IOException ex){
            LOGGER.error(ex.getMessage());
        } finally {
            eventDataDao.closeCurrentSessionwithTransaction();
        }
    }

    private void prepareLogsToSave(LogDataDTO logDataDTO) {
        String logId = logDataDTO.getId();
        List<LogDataDTO> logDtoList = new ArrayList<>();
        Long eventTime = 0L;
        EventData eventData;

        if(logMap.get(logId) != null){
            logDtoList = logMap.get(logId);
            logDtoList.add(logDataDTO);
            logMap.put(logId, logDtoList);
            if(logDtoList.size() == 2){
                eventTime = calculateEventTime(logDtoList);
                eventData = evenDataConverter.convertLogDataDtoToEventDataWithDurationTime(logDataDTO, eventTime);
                saveLog(eventData);
                logMap.remove(logId);
            }
        } else{
            logDtoList.add(logDataDTO);
            logMap.put(logId, logDtoList);
        }
    }

    private Long calculateEventTime(List<LogDataDTO> logList){
       return Math.abs(Long.parseLong(logList.get(0).getTimestamp()) - Long.parseLong(logList.get(1).getTimestamp()));
    }

    private void saveLog(EventData evenData) {
        eventDataDao.saveDataToDb(evenData);
    }

}
